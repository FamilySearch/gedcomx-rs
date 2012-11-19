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

import com.sun.mirror.type.MirroredTypesException;
import net.sf.jelly.apt.freemarker.FreemarkerModel;
import org.codehaus.enunciate.apt.EnunciateFreemarkerModel;
import org.codehaus.enunciate.contract.jaxb.TypeDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Ryan Heaton
 */
public final class StateTransition implements Comparable<StateTransition> {

  final String rel;
  final String endpoint;
  final String description;
  final Set<String> scope;
  final ResourceServiceProcessor processor;
  final boolean template;

  public StateTransition(org.gedcomx.rt.rs.StateTransition meta, ResourceServiceProcessor processor) {
    this.processor = processor;
    this.rel = meta.rel();
    this.description = meta.description();
    this.template = meta.template();
    String endpoint = meta.endpoint();
    if ("##default".equals(endpoint)) {
      endpoint = this.rel;
    }
    this.endpoint = endpoint;

    this.scope = new TreeSet<String>();
    try {
      Class<?>[] classes = meta.scope();
      for (Class<?> clazz : classes) {
        scope.add(clazz.getName());
      }
    }
    catch (MirroredTypesException e) {
      scope.addAll(e.getQualifiedNames());
    }
  }

  public String getRel() {
    return rel;
  }

  public String getDescription() {
    return description;
  }

  public ApplicationState getState() {
    return this.processor.getApplicationStates().get(this.endpoint);
  }

  public boolean isTemplate() {
    return template;
  }

  public List<TypeDefinition> getScope() {
    EnunciateFreemarkerModel model = (EnunciateFreemarkerModel) FreemarkerModel.get();

    ArrayList<TypeDefinition> typeDefs = new ArrayList<TypeDefinition>();
    for (TypeDefinition typeDef : model.getTypeDefinitions()) {
      if (this.scope.contains(typeDef.getQualifiedName())) {
        typeDefs.add(typeDef);
      }
    }
    return typeDefs;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    StateTransition that = (StateTransition) o;
    return this.rel.equals(that.rel);
  }

  @Override
  public int hashCode() {
    return this.rel.hashCode();
  }

  @Override
  public int compareTo(StateTransition o) {
    return this.rel.compareTo(o.rel);
  }
}
