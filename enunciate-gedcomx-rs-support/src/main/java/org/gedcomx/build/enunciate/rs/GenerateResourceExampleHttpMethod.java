/**
 * Copyright 2011-2012 Intellectual Reserve, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gedcomx.build.enunciate.rs;

import com.sun.mirror.declaration.ClassDeclaration;
import com.sun.mirror.type.ClassType;
import com.sun.mirror.type.InterfaceType;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.AdapterTemplateModel;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import net.sf.jelly.apt.decorations.TypeMirrorDecorator;
import net.sf.jelly.apt.decorations.type.DecoratedClassType;
import net.sf.jelly.apt.decorations.type.DecoratedInterfaceType;
import org.codehaus.enunciate.apt.EnunciateFreemarkerModel;
import org.codehaus.enunciate.contract.jaxb.*;
import org.codehaus.enunciate.contract.jaxb.types.XmlClassType;
import org.codehaus.enunciate.contract.jaxb.types.XmlType;
import org.codehaus.enunciate.contract.jaxrs.ResourceEntityParameter;
import org.codehaus.enunciate.contract.jaxrs.ResourceMethod;
import org.codehaus.enunciate.contract.jaxrs.ResourceRepresentationMetadata;
import org.codehaus.enunciate.modules.docs.GenerateExampleXmlMethod;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.gedcomx.build.enunciate.GenerateExampleJsonMethod;
import org.gedcomx.rt.CommonModels;
import org.gedcomx.rt.SupportsExtensionElements;
import org.gedcomx.rt.json.JsonElementWrapper;
import org.jdom.Namespace;

import javax.xml.namespace.QName;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author Ryan Heaton
 */
public abstract class GenerateResourceExampleHttpMethod implements TemplateMethodModelEx {
  
  static final QName RESOURCE_SET_TYPE_QNAME = new QName( CommonModels.GEDCOMX_NAMESPACE, "Gedcomx" );

  final EnunciateFreemarkerModel model;

  protected GenerateResourceExampleHttpMethod(EnunciateFreemarkerModel model) {
    this.model = model;
  }

  public Object exec(List list) throws TemplateModelException {
    if (list.size() < 1) {
      throw new TemplateModelException("The generateExampleRequestHeaders method must have a resource method as a parameter.");
    }

    Object object = BeansWrapper.getDefaultInstance().unwrap((TemplateModel) list.get(0));

    ResourceMethod resourceMethod;
    if (object instanceof ResourceMethod) {
      resourceMethod = (ResourceMethod) object;
    }
    else {
      throw new TemplateModelException("The generateExampleRequestHeaders method must have a resource method as a parameter.");
    }

    ResourceDefinitionDeclaration def = null;
    if (resourceMethod.getParent() instanceof ResourceDefinitionDeclaration) {
      def = (ResourceDefinitionDeclaration) resourceMethod.getParent();
    }
    else if (resourceMethod.getMetaData().get("definedBy") instanceof ResourceDefinitionDeclaration) {
      def = (ResourceDefinitionDeclaration) resourceMethod.getMetaData().get("definedBy");
    }

    ElementDeclaration element = null;
    if (def != null) {
      List<ElementDeclaration> elements = def.getResourceElements();
      if (resourceMethod.getMetaData().get("boundBy") instanceof ResourceBinding) {
        elements = ((ResourceBinding) resourceMethod.getMetaData().get("boundBy")).getResourceElements();
      }
      if (elements.size() > 0) {
        element = elements.get(0);
      }
    }
    else {
      ResourceRepresentationMetadata representationMetadata = resourceMethod.getRepresentationMetadata();
      if (representationMetadata != null) {
        element = representationMetadata.getXmlElement();
      }
      else {
        ResourceEntityParameter entityParam = resourceMethod.getEntityParameter();
        if (entityParam != null) {
          element = entityParam.getXmlElement();
        }
      }
    }

    boolean json = list.size() > 1 && Boolean.TRUE.equals(BeansWrapper.getDefaultInstance().unwrap((TemplateModel) list.get(1)));
    //todo: validate that the method actually produces,consumes json?
    return generateExample(def, resourceMethod, element, gatherSubresourceElements(element instanceof RootElementDeclaration ? ((RootElementDeclaration)element).getTypeDefinition() : null, def), json);
  }

  protected abstract Object generateExample(ResourceDefinitionDeclaration def, ResourceMethod resourceMethod, ElementDeclaration element, List<SubresourceElement> subresources, boolean json) throws TemplateModelException;

  protected List<SubresourceElement> gatherSubresourceElements(TypeDefinition typeDef, ResourceDefinitionDeclaration def) {
    List<SubresourceElement> subresourceElements = new ArrayList<SubresourceElement>();
    if (typeDef != null) {
      Map<QName, ResourceDefinitionDeclaration> subresourcesByType = new HashMap<QName, ResourceDefinitionDeclaration>();
      if (def != null) {
        List<ResourceDefinitionDeclaration> subresourceDeclarations = def.getSubresources();
        for (ResourceDefinitionDeclaration subresourceDeclaration : subresourceDeclarations) {
          for (ElementDeclaration subresource : subresourceDeclaration.getResourceElements()) {
            if (subresource instanceof RootElementDeclaration) {
              subresourcesByType.put(((RootElementDeclaration)subresource).getTypeDefinition().getQname(), subresourceDeclaration);
            }
          }
        }
      }

      if (def != null && isInstanceOf(typeDef, SupportsExtensionElements.class.getName())) {
        for (ResourceDefinitionDeclaration subresource : def.getSubresources()) {
          for (ElementDeclaration el : subresource.getResourceElements()) {
            if (def.getSubresourceElements().isEmpty() && RESOURCE_SET_TYPE_QNAME.equals(typeDef.getQname())) {
              //special case for resource bundles: resource elements of subresources are automatically included as subresource definitions.
              JsonElementWrapper elementWrapper = el.getAnnotation(JsonElementWrapper.class);
              String jsonName = elementWrapper != null ? (elementWrapper.namespace() + elementWrapper.name()) : (el.getNamespace() + el.getName());
              TypeDefinition typeDefinition = el instanceof RootElementDeclaration ? ((RootElementDeclaration) el).getTypeDefinition() : ((LocalElementDeclaration) el).getElementXmlType() instanceof XmlClassType ? ((XmlClassType)((LocalElementDeclaration) el).getElementXmlType()).getTypeDefinition() : null;
              subresourceElements.add(new SubresourceElement(el.getQname(), jsonName, typeDefinition, true, subresource));
            }
            else {
              QName typeQName = el instanceof RootElementDeclaration ? ((RootElementDeclaration) el).getTypeDefinition().getQname() : ((LocalElementDeclaration) el).getElementXmlType().getQname();
              for (Map.Entry<QName, TypeDefinition> entry : def.getSubresourceElements().entrySet()) {
                if (entry.getValue().getQname().equals(typeQName)) {
                  subresourceElements.add(new SubresourceElement(entry.getKey(), entry.getKey().getPrefix(), entry.getValue(), true, subresource));
                }
              }
            }
          }
        }
      }

      for (Element childElement : typeDef.getElements()) {
        XmlType baseType = childElement.getBaseType();
        QName childTypeName = baseType.getQname();
        ResourceDefinitionDeclaration subresource = subresourcesByType.get(childTypeName);
        if (subresource != null && baseType instanceof XmlClassType) {
          subresourceElements.add(new SubresourceElement(new QName(childElement.getNamespace(), childElement.getName()), childElement.getJsonMemberName(), ((XmlClassType)baseType).getTypeDefinition(), childElement.isCollectionType(), subresource));
        }
      }
    }
    return subresourceElements;
  }

  private boolean isInstanceOf(ClassDeclaration typeDef, String name) {
    for (InterfaceType interfaceType : typeDef.getSuperinterfaces()) {
      if (((DecoratedInterfaceType) TypeMirrorDecorator.decorate(interfaceType)).isInstanceOf(name)) {
        return true;
      }
    }

    ClassType superclass = typeDef.getSuperclass();
    return superclass != null && ((DecoratedClassType) TypeMirrorDecorator.decorate(superclass)).isInstanceOf(name);

  }

  protected void writeExampleToBody(final ElementDeclaration element, List<SubresourceElement> subresources, boolean json, PrintWriter body, boolean writeLinks, Collection<ResourceLink> links) throws TemplateModelException {
    if (json) {
      new ExampleJsonGenerator(element, subresources, writeLinks, links).writeTo(body);
    }
    else {
      new ExampleXmlGenerator(element, subresources, writeLinks, links).writeTo(body);
    }
  }

  private class ExampleXmlGenerator extends GenerateExampleXmlMethod {

    private final ElementDeclaration element;
    private final List<SubresourceElement> subresourceStack;
    private final boolean writeLinks;
    private final List<ResourceLink> linkStack;

    private ExampleXmlGenerator(ElementDeclaration element, List<SubresourceElement> subresources, boolean writeLinks, Collection<ResourceLink> links) {
      super(element.getNamespace(), model);
      this.element = element;
      this.subresourceStack = new ArrayList<SubresourceElement>(subresources);
      this.writeLinks = writeLinks;
      this.linkStack = new ArrayList<ResourceLink>(links);
    }

    public void writeTo(PrintWriter writer) throws TemplateModelException {
      String example = (String) exec(Arrays.asList(new AdapterTemplateModel() {
        @Override
        public Object getAdaptedObject(Class hint) {
          return element;
        }
      }));
      writer.print(example);
    }

    @Override
    public void generateExampleXml(TypeDefinition type, org.jdom.Element parent, String defaultNs, int maxDepth) {
      if (this.writeLinks) {
        if (!linkStack.isEmpty()) {
          if (!"http://www.w3.org/2005/Atom".equals(defaultNs)) {
            parent.addNamespaceDeclaration(Namespace.getNamespace("atom", "http://www.w3.org/2005/Atom"));
          }
          writeLinks(parent, this.linkStack, defaultNs);
          this.linkStack.clear();
        }
        else {
          //see if the type definition is a subresource, and if so, add the links.
          for (SubresourceElement sub : subresourceStack) {
            if (sub.getTypeDefinition().getQname().equals(type.getQname())) {
              writeLinks(parent, sub.getDefinition().getLinks(), defaultNs);
            }
          }
        }
      }

      super.generateExampleXml(type, parent, defaultNs, maxDepth);
    }

    private void writeLinks(org.jdom.Element parent, Collection<ResourceLink> links, String defaultNs) {
      for (ResourceLink link : links) {
        Namespace jdomNS = Namespace.getNamespace(!"http://www.w3.org/2005/Atom".equals(defaultNs) ? "atom" : "", "http://www.w3.org/2005/Atom");
        org.jdom.Element el = new org.jdom.Element("link", jdomNS);
        org.jdom.Attribute attr = new org.jdom.Attribute("rel", link.getRel(), Namespace.NO_NAMESPACE);
        el.setAttribute(attr);
        attr = new org.jdom.Attribute("href", "...", Namespace.NO_NAMESPACE);
        el.setAttribute(attr);
        parent.addContent(el);
      }
    }
  }

  private class ExampleJsonGenerator extends GenerateExampleJsonMethod {

    private final ElementDeclaration element;
    private final List<SubresourceElement> subresourceStack;
    private final boolean writeLinks;
    private final List<ResourceLink> linkStack;

    private ExampleJsonGenerator(ElementDeclaration element, List<SubresourceElement> subresources, boolean writeLinks, Collection<ResourceLink> links) {
      super(model);
      this.element = element;
      this.subresourceStack = new ArrayList<SubresourceElement>(subresources);
      this.writeLinks = writeLinks;
      this.linkStack = new ArrayList<ResourceLink>(links);
    }

    public void writeTo(PrintWriter writer) throws TemplateModelException {
      String example = (String) exec(Arrays.asList(new AdapterTemplateModel() {
        @Override
        public Object getAdaptedObject(Class hint) {
          return element;
        }
      }));
      writer.print(example);
    }

    @Override
    protected void generateExampleJson(TypeDefinition type, ObjectNode jsonNode, int maxDepth) {
      if (this.writeLinks) {
        if (!linkStack.isEmpty()) {
          writeLinks(jsonNode, this.linkStack);
          this.linkStack.clear();
        }
        else {
          //see if the type definition is a subresource, and if so, add the links.
          for (SubresourceElement sub : subresourceStack) {
            if (sub.getTypeDefinition().getQname().equals(type.getQname())) {
              writeLinks(jsonNode, sub.getDefinition().getLinks());
            }
          }
        }
      }

      super.generateExampleJson(type, jsonNode, maxDepth);
    }

    private void writeLinks(ObjectNode jsonNode, Collection<ResourceLink> links) {
      ObjectNode linksNode = JsonNodeFactory.instance.objectNode();
      for (ResourceLink link : links) {
        ObjectNode linkNode = JsonNodeFactory.instance.objectNode();
        linkNode.put("href", "...");
        linksNode.put(link.getRel(), linkNode);
      }
      jsonNode.put("links", linksNode);
    }


  }
}