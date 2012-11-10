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

import org.codehaus.enunciate.contract.jaxb.ElementDeclaration;
import org.codehaus.enunciate.contract.jaxrs.ResourceMethod;
import org.codehaus.enunciate.contract.jaxrs.ResourceParameter;

import javax.xml.namespace.QName;
import java.util.*;

/**
 * @author Ryan Heaton
 */
public class ResourceBinding implements LinkReference {

  private final ResourceServiceProcessor processor;
  private final String path;
  private final List<ResourceDefinitionDeclaration> definitions = new ArrayList<ResourceDefinitionDeclaration>();
  private String docValue;
  private final List<ResourceMethod> methods = new ArrayList<ResourceMethod>();
  private final Set<ResponseCode> statusCodes = new HashSet<ResponseCode>();
  private final Set<ResponseCode> warnings = new HashSet<ResponseCode>();
  private final Set<ResourceLink> links = new TreeSet<ResourceLink>();
  private final Set<ResourceParameter> resourceParameters = new TreeSet<ResourceParameter>(new Comparator<ResourceParameter>() {
    @Override
    public int compare(ResourceParameter param1, ResourceParameter param2) {
      int comparison = param1.getParameterName().compareTo(param2.getParameterName());
      if (comparison == 0) {
        comparison = param1.getTypeName().compareTo(param2.getTypeName());
      }
      return comparison;
    }
  });

  private final List<ElementDeclaration> resourceElements;
  private final org.gedcomx.rt.rs.ResourceBinding metadata;

  public ResourceBinding(ResourceServiceProcessor processor, String path, ResourceDefinitionDeclaration definition, List<ElementDeclaration> resourceElements, org.gedcomx.rt.rs.ResourceBinding metadata) {
    this.processor = processor;
    this.path = path;
    this.definitions.add(definition);
    this.resourceElements = resourceElements;
    this.metadata = metadata;
  }
  
  @Override
  public String getName() {
    String name = this.metadata == null ? "##default" : this.metadata.name();
    if ("##default".equals(name)) {
      if (this.definitions.size() > 1) {
        throw new IllegalStateException(String.format("Cannot determine the name of the binding for %s because its binding multiple definitions. Use the @ResourceBinding annotation to specify a name.", this.path));
      }
      name = this.definitions.get(0).getName();
    }
    return name;
  }

  @Override
  public String getNamespace() {
    String namespace = this.metadata == null ? "##default" : this.metadata.namespace();
    if ("##default".equals(namespace)) {
      if (this.definitions.size() > 1) {
        throw new IllegalStateException(String.format("Cannot determine the namespace of the binding for %s because its binding multiple definitions. Use the @ResourceBinding annotation to specify a name.", this.path));
      }
      namespace = this.definitions.get(0).getNamespace();
    }
    return namespace;
  }

  @Override
  public String getProjectId() {
    String pid = this.metadata == null ? "##default" : this.metadata.projectId();
    if ("##default".equals(pid)) {
      if (this.definitions.size() > 1) {
        throw new IllegalStateException(String.format("Cannot determine the project id of the binding for %s because its binding multiple definitions. Use the @ResourceBinding annotation to specify a name.", this.path));
      }
      pid = this.definitions.get(0).getProjectId();
    }
    return pid;
  }

  public String getPath() {
    return path;
  }

  public List<ResourceDefinitionDeclaration> getDefinitions() {
    return Collections.unmodifiableList(definitions);
  }

  void addResourceDefinitionConditionally(ResourceDefinitionDeclaration rsd) {
    boolean found = false;
    for (ResourceDefinitionDeclaration definition : this.definitions) {
      if (definition.getQualifiedName().equals(rsd.getQualifiedName())) {
        found = true;
        break;
      }
    }

    if (!found) {
      this.definitions.add(rsd);
    }
  }

  public Set<ResponseCode> getStatusCodes() {
    return statusCodes;
  }

  public Set<ResponseCode> getAllStatusCodes() {
    TreeSet<ResponseCode> allStatusCodes = new TreeSet<ResponseCode>();
    allStatusCodes.addAll(this.statusCodes);
    for (ResourceDefinitionDeclaration definition : this.definitions) {
      allStatusCodes.addAll(definition.getStatusCodes());
    }
    return allStatusCodes;
  }

  public Set<ResponseCode> getWarnings() {
    return warnings;
  }

  public Set<ResponseCode> getAllWarnings() {
    TreeSet<ResponseCode> allWarnings = new TreeSet<ResponseCode>();
    allWarnings.addAll(this.warnings);
    for (ResourceDefinitionDeclaration definition : this.definitions) {
      allWarnings.addAll(definition.getWarnings());
    }
    return allWarnings;
  }

  @Override
  public Set<ResourceLink> getLinks() {
    return links;
  }

  @Override
  public List<ElementDeclaration> getResourceElements() {
    return resourceElements;
  }

  public Set<ResourceLink> getAllLinks() {
    TreeSet<ResourceLink> allLinks = new TreeSet<ResourceLink>();
    allLinks.addAll(this.links);
    for (ResourceDefinitionDeclaration definition : this.definitions) {
      allLinks.addAll(definition.getLinks());
    }
    return allLinks;
  }

  public Map<ResourceLink, ResourceBinding> getIncomingLinks() {
    LinkedHashMap<ResourceLink, ResourceBinding> linksIn = new LinkedHashMap<ResourceLink, ResourceBinding>();
    QName thisQName = new QName(getNamespace(), getName());
    for (ResourceBinding binding : this.processor.getBindingsByPath().values()) {
      for (ResourceLink link : binding.getLinks()) {
        if (link.resource.equals(thisQName)) {
          linksIn.put(link, binding);
        }
      }
    }
    return linksIn;
  }

  public List<ResourceMethod> getMethods() {
    return methods;
  }

  public Set<ResourceParameter> getResourceParameters() {
    return resourceParameters;
  }

  @Override
  public String getDocValue() {
    return this.docValue == null || this.docValue.trim().isEmpty() ? this.definitions.size() == 1 ? this.definitions.get(0).getDocValue() : null : this.docValue;
  }

  public void setDocValue(String docValue) {
    this.docValue = docValue;
  }

  public Set<String> getProduces() {
    TreeSet<String> produces = new TreeSet<String>();
    for (ResourceMethod method : getMethods()) {
      produces.addAll(method.getProducesMime());
    }
    return produces;
  }

  public Set<String> getConsumes() {
    TreeSet<String> produces = new TreeSet<String>();
    for (ResourceMethod method : getMethods()) {
      produces.addAll(method.getConsumesMime());
    }
    return produces;
  }

  @Override
  public boolean isBinding() {
    return true;
  }
}
