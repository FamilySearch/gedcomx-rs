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
import org.codehaus.enunciate.contract.jaxrs.ResourceMethod;
import org.codehaus.enunciate.contract.jaxrs.ResourceParameter;
import org.gedcomx.rt.rs.StateTransitionParameter;

import java.util.*;

/**
 * @author Ryan Heaton
 */
public class ResourceBinding extends DecoratedDeclaration {

  private final String path;
  private final ResourceDefinitionDeclaration definition;
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

  private final ApplicationState state;
  final String namespace;
  final String projectId;

  public ResourceBinding(Declaration delegate, String path, ResourceDefinitionDeclaration definition, ApplicationState state, org.gedcomx.rt.rs.ResourceBinding metadata) {
    super(delegate);
    this.path = path;
    this.definition = definition;
    this.namespace = metadata == null || "##default".equals(metadata.namespace()) ? null : metadata.namespace();
    this.projectId = metadata == null || "##default".equals(metadata.projectId()) ? null : metadata.projectId();
    this.state = state;
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

  public ApplicationState getState() {
    return state;
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

  public Set<StateTransition> getLinks() {
    return links;
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

  public Properties getTransitionTemplateProperties() {
    Properties properties = new Properties();
    if (this.state != null) {
      String state = this.state.getId();
      StringBuilder queryParams = new StringBuilder();
      boolean appendComma = false;
      for (ResourceParameter parameter : getResourceParameters()) {
        if (parameter.isPathParam() || parameter.isQueryParam()) {
          String parameterName = parameter.getParameterName();

          if (parameter.isQueryParam()) {
            if (appendComma) {
              queryParams.append(',');
            }
            queryParams.append(parameterName);
            appendComma = true;
          }

          boolean optional = true;
          String variableName = parameterName;
          StateTransitionParameter transitionParameter = parameter.getAnnotation(StateTransitionParameter.class);
          if (transitionParameter != null) {
            optional = transitionParameter.optional();
            if (!"##default".equals(transitionParameter.name())) {
              variableName = transitionParameter.name();
            }
          }

          properties.setProperty(state + "." + parameterName + ".optional", String.valueOf(optional));
          properties.setProperty(state + "." + parameterName + ".variableName", variableName);
        }
      }

      properties.setProperty(state + ".queryParams", queryParams.toString());
      properties.setProperty(state + ".path", getPath());
      properties.setProperty(state + ".namespace", getNamespace());
      properties.setProperty(state + ".title", this.state.getName());

      Iterator<String> it = getConsumes().iterator();
      StringBuilder accept = new StringBuilder();
      while (it.hasNext()) {
        String consumes = it.next();
        accept.append(consumes);
        if (it.hasNext()) {
          accept.append(',');
        }
      }
      properties.setProperty(state + ".accept", accept.toString());

      it = getProduces().iterator();
      StringBuilder type = new StringBuilder();
      while (it.hasNext()) {
        String produces = it.next();
        type.append(produces);
        if (it.hasNext()) {
          type.append(',');
        }
      }
      properties.setProperty(state + ".type", type.toString());

      StringBuilder allow = new StringBuilder();
      appendComma = false;
      for (ResourceMethod method : getMethods()) {
        for (String op : method.getHttpMethods()) {
          if (appendComma) {
            allow.append(',');
          }
          allow.append(op);
          appendComma = true;
        }
      }
      properties.setProperty(state + ".allow", allow.toString());
    }
    return properties;
  }
}
