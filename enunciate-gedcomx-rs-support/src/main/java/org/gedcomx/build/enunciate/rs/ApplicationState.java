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

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Ryan Heaton
 */
public class ApplicationState {

  private final String name;
  private final String id;
  private final String description;
  private final List<StateTransition> transitions;
  private final ResourceDefinitionDeclaration definition;
  private ResourceBinding binding;

  public ApplicationState(String name, String id, String description, List<StateTransition> transitions, ResourceDefinitionDeclaration definition) {
    this.name = name;
    this.id = id;
    this.description = description;
    this.transitions = transitions;
    this.definition = definition;
  }

  public String getSystemId() {
    return getId().replace('/', '-').replace(':', '_');
  }

  public String getNamespace() {
    return this.binding != null ? this.binding.getNamespace() : definition.getNamespace();
  }

  public String getName() {
    return this.name;
  }

  public String getProjectId() {
    return definition.getProjectId();
  }

  public List<ElementDeclaration> getResourceElements() {
    return definition.getResourceElements();
  }

  public String getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public Set<StateTransition> getTransitions() {
    TreeSet<StateTransition> transitions = new TreeSet<StateTransition>(this.transitions);
    if (this.binding != null) {
      transitions.addAll(this.binding.getLinks());
    }
    return transitions;
  }

  public Set<ResponseCode> getStatusCodes() {
    TreeSet<ResponseCode> statusCodes = new TreeSet<ResponseCode>();
    statusCodes.addAll(this.definition.getStatusCodes());
    if (this.binding != null) {
      statusCodes.addAll(this.binding.getStatusCodes());
    }
    return statusCodes;
  }

  public Set<ResponseCode> getWarnings() {
    TreeSet<ResponseCode> warnings = new TreeSet<ResponseCode>();
    warnings.addAll(this.definition.getWarnings());
    if (this.binding != null) {
      warnings.addAll(this.binding.getWarnings());
    }
    return warnings;
  }

  public List<ResourceMethod> getResourceMethods() {
    return this.binding != null ? this.binding.getMethods() : this.definition.getResourceMethods();
  }

  public Collection<ResourceParameter> getResourceParameters() {
    return this.binding != null ? this.binding.getResourceParameters() : this.definition.getResourceParameters();
  }

  public ResourceDefinitionDeclaration getDefinition() {
    return definition;
  }

  public ResourceBinding getBinding() {
    return binding;
  }

  public void setBinding(ResourceBinding binding) {
    this.binding = binding;
  }

  public String getDocValue() {
    return this.binding != null ? this.binding.getDocValue(): this.definition.getDocValue();
  }
}
