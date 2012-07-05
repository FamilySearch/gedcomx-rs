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
import org.gedcomx.rt.CommonModels;
import org.gedcomx.rt.json.JsonElementWrapper;
import org.gedcomx.rt.SupportsExtensionElements;

import javax.xml.namespace.QName;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author Ryan Heaton
 */
public abstract class GenerateResourceExampleHttpMethod implements TemplateMethodModelEx {
  
  static final QName RESOURCE_BUNDLE_TYPE_QNAME = new QName( CommonModels.RDF_NAMESPACE, "RDF" );

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

  protected abstract Object generateExample(ResourceDefinitionDeclaration def, ResourceMethod resourceMethod, ElementDeclaration element, List<SubresourceElement> subresources, boolean json);

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
            if (def.getSubresourceElements().isEmpty() && RESOURCE_BUNDLE_TYPE_QNAME.equals(typeDef.getQname())) {
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

  protected void writeExampleToBody(ElementDeclaration element, List<SubresourceElement> subresources, boolean json, PrintWriter body, boolean writeLinks, Collection<ResourceLink> links) {
    if (json) {
      body.printf("{\n");
      body.printf("  ...\n");
      if (writeLinks) {
        writeLinks(body, links, json, 0, null);
      }
      writeSubresourcesExampleToBody(element.getQname(), subresources, json, body, 1, 2, writeLinks);
      body.printf("  ...\n");
      body.printf("}");
    }
    else {
      boolean isAtom = "http://www.w3.org/2005/Atom".equals(element.getNamespace());
      body.printf("<%s xmlns=\"%s\"%s>\n", element.getName(), element.getNamespace(), isAtom ? "" : writeLinks ? " xmlns:atom=\"http://www.w3.org/2005/Atom\"" : "");
      body.printf("  ...\n");
      if (writeLinks) {
        writeLinks(body, links, json, 0, isAtom ? "link" : "atom:link");
      }
      writeSubresourcesExampleToBody(element.getQname(), subresources, json, body, 1, 2, writeLinks);
      body.printf("  ...\n");
      body.printf("</%s>", element.getName());
    }
  }

  private void writeLinks(PrintWriter body, Collection<ResourceLink> links, boolean json, int depth, String linkElement) {
    StringBuilder tab = new StringBuilder();
    for (int i = 0; i < depth; i++) {
      tab.append("  ");
    }

    Iterator<ResourceLink> relIt = links.iterator();
    if (json) {
      if (relIt.hasNext()) {
        body.printf("%s  \"links\" : {\n", tab);
      }
      while (relIt.hasNext()) {
        ResourceLink rel = relIt.next();
        body.printf("%s    \"%s\" : { \"%s\" : \"...\" }", tab, rel.getRel(), rel.isTemplate() ? "template" : "href");
        if (!relIt.hasNext()) {
          body.printf("\n%s  },\n", tab);
        }
        else {
          body.printf("%s,\n", tab);
        }
      }
    }
    else {
      while (relIt.hasNext()) {
        ResourceLink rel = relIt.next();
        body.printf("%s  <%s rel=\"%s\" %s=\"...\"/>\n", tab, linkElement, rel.getRel(), rel.isTemplate() ? "template" : "href");
      }
    }
  }

  private void writeSubresourcesExampleToBody(QName parentQName, List<SubresourceElement> subresources, boolean json, PrintWriter body, int depth, int maxDepth, boolean writeLinks) {
    StringBuilder tab = new StringBuilder();
    for (int i = 0; i < depth; i++) {
      tab.append("  ");
    }

    if (json) {
      for (SubresourceElement subresource : subresources) {
        body.printf("%s\"%s\" :%s{\n", tab, subresource.getJsonName(), subresource.isCollection() ? " [ " : " ");
        if (depth < maxDepth) {
          body.printf("%s  ...\n", tab);
          if (writeLinks) {
            writeLinks(body, subresource.getDefinition().getLinks(), json, depth, null);
          }
          writeSubresourcesExampleToBody(subresource.getXmlName(), gatherSubresourceElements(subresource.getTypeDefinition(), subresource.getDefinition()), json, body, depth + 1, maxDepth, writeLinks);
        }
        body.printf("%s  ...\n", tab);
        body.printf("%s}%s,\n", tab, subresource.isCollection() ? " ]" : "");
      }
    }
    else {
      for (SubresourceElement subresource : subresources) {
        String ns = subresource.getXmlName().getNamespaceURI();
        String localPart = subresource.getXmlName().getLocalPart();
        body.printf("%s<%s", tab, localPart);
        if (!"".equals(ns) && !parentQName.getNamespaceURI().equals(ns)) {
          body.printf(" xmlns=\"%s\"", ns);
        }
        body.printf(">\n");
        if (depth < maxDepth) {
          body.printf("%s  ...\n", tab);
          if (writeLinks) {
            writeLinks(body, subresource.getDefinition().getLinks(), json, depth, "http://www.w3.org/2005/Atom".equals(ns) ? "link" : "atom:link");
          }
          writeSubresourcesExampleToBody(subresource.getXmlName(), gatherSubresourceElements(subresource.getTypeDefinition(), subresource.getDefinition()), json, body, depth + 1, maxDepth, writeLinks);
        }
        body.printf("%s  ...\n", tab);
        body.printf("%s</%s>\n", tab, localPart);
      }
    }
  }
}