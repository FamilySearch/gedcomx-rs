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

import org.codehaus.enunciate.contract.jaxrs.ResourceMethod;
import org.codehaus.enunciate.contract.jaxrs.ResourceParameter;

import java.util.*;

/**
 * @author Ryan Heaton
 */
public class ResourceBinding {

  private final String path;
  ResourceDefinitionDeclaration definition;
  private String docValue;
  private final List<ResourceMethod> methods = new ArrayList<ResourceMethod>();
  private final Set<ResponseCode> statusCodes = new HashSet<ResponseCode>();
  private final Set<ResponseCode> warnings = new HashSet<ResponseCode>();
  private final Set<ResourceLink> links = new TreeSet<ResourceLink>(new Comparator<ResourceLink>() {
    @Override
    public int compare(ResourceLink o1, ResourceLink o2) {
      return o1.rel.compareTo(o2.rel);
    }
  });
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
  private final org.gedcomx.rt.rs.ResourceBinding metadata;

  public ResourceBinding(String path, ResourceDefinitionDeclaration definition, org.gedcomx.rt.rs.ResourceBinding metadata) {
    this.path = path;
    this.definition = definition;
    this.metadata = metadata;
  }
  
  public String getName() {
    String name = this.metadata == null ? "##default" : this.metadata.name();
    if ("##default".equals(name)) {
      name = this.definition.getName();
    }
    return name;
  }

  public String getNamespace() {
    String namespace = this.metadata == null ? "##default" : this.metadata.namespace();
    if ("##default".equals(namespace)) {
      namespace = this.definition.getNamespace();
    }
    return namespace;
  }

  public String getProjectId() {
    String pid = this.metadata == null ? "##default" : this.metadata.projectId();
    if ("##default".equals(pid)) {
      pid = this.definition.getProjectId();
    }
    return pid;
  }

  public String getPath() {
    return path;
  }

  public ResourceDefinitionDeclaration getDefinition() {
    return definition;
  }

  public Set<ResponseCode> getStatusCodes() {
    return statusCodes;
  }

  public Set<ResponseCode> getWarnings() {
    return warnings;
  }

  public Set<ResourceLink> getLinks() {
    return links;
  }

  public Set<ResourceLink> getAllLinks() {
    TreeSet<ResourceLink> allLinks = new TreeSet<ResourceLink>(new Comparator<ResourceLink>() {
      @Override
      public int compare(ResourceLink o1, ResourceLink o2) {
        return o1.rel.compareTo(o2.rel);
      }
    });

    allLinks.addAll(this.links);
    allLinks.addAll(this.definition.getLinks());
    return allLinks;
  }

  public List<ResourceMethod> getMethods() {
    return methods;
  }

  public Set<ResourceParameter> getResourceParameters() {
    return resourceParameters;
  }

  public String getDocValue() {
    return docValue;
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

  void replaceDefinition(ResourceDefinitionDeclaration rsd) {
    this.definition = rsd;
  }
}
