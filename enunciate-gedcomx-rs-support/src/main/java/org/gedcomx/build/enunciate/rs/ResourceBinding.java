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

import com.sun.mirror.declaration.Declaration;
import net.sf.jelly.apt.decorations.declaration.DecoratedDeclaration;
import org.codehaus.enunciate.contract.jaxb.ElementDeclaration;
import org.codehaus.enunciate.contract.jaxrs.ResourceMethod;
import org.codehaus.enunciate.contract.jaxrs.ResourceParameter;

import javax.xml.namespace.QName;
import java.util.*;

/**
 * @author Ryan Heaton
 */
public class ResourceBinding extends DecoratedDeclaration {

  private final String path;
  private final List<ResourceDefinitionDeclaration> definitions = new ArrayList<ResourceDefinitionDeclaration>();
  private final List<ResourceMethod> methods = new ArrayList<ResourceMethod>();
  private final Set<ResponseCode> statusCodes = new HashSet<ResponseCode>();
  private final Set<ResponseCode> warnings = new HashSet<ResponseCode>();
  private final Set<StateTransition> links = new TreeSet<StateTransition>();
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

  private final Set<String> states;
  final String namespace;
  final String projectId;

  public ResourceBinding(Declaration delegate, String path, ResourceDefinitionDeclaration definition, org.gedcomx.rt.rs.ResourceBinding metadata) {
    super(delegate);
    this.path = path;
    this.definitions.add(definition);
    this.namespace = metadata == null || "##default".equals(metadata.namespace()) ? null : metadata.namespace();
    this.projectId = metadata == null || "##default".equals(metadata.projectId()) ? null : metadata.projectId();
    this.states = new TreeSet<String>(Arrays.asList(metadata.states()));
  }

  public String getNamespace() {
    return this.namespace;
  }

  public String getProjectId() {
    return this.projectId;
  }

  public String getPath() {
    return path;
  }

  public Set<String> getStates() {
    return states;
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

  public Set<StateTransition> getLinks() {
    return links;
  }

  public Set<StateTransition> getAllLinks() {
    TreeSet<StateTransition> allLinks = new TreeSet<StateTransition>();
    allLinks.addAll(this.links);
    for (ResourceDefinitionDeclaration definition : this.definitions) {
      for (ApplicationState applicationState : definition.getApplicationStates()) {
        if (this.states.contains(applicationState.getRel())) {
          allLinks.addAll(applicationState.getTransitions());
        }
      }
    }
    return allLinks;
  }

  public List<ResourceMethod> getMethods() {
    return methods;
  }

  public Set<ResourceParameter> getResourceParameters() {
    return resourceParameters;
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
}
