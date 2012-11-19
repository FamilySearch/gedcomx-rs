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

import java.util.List;

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
    return definition.getNamespace();
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

  public List<StateTransition> getTransitions() {
    return transitions;
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
}
