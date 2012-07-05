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

import org.codehaus.enunciate.apt.EnunciateFreemarkerModel;
import org.codehaus.enunciate.config.SchemaInfo;
import org.codehaus.enunciate.contract.jaxb.ElementDeclaration;
import org.codehaus.enunciate.contract.jaxrs.ResourceMethod;
import org.codehaus.enunciate.contract.jaxrs.ResourceParameter;

import javax.ws.rs.HttpMethod;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

/**
 * @author Ryan Heaton
 */
public class GenerateExampleRequestHeadersMethod extends GenerateResourceExampleHttpMethod {

  public GenerateExampleRequestHeadersMethod(EnunciateFreemarkerModel model) {
    super(model);
  }

  protected Object generateExample(ResourceDefinitionDeclaration def, ResourceMethod resourceMethod, ElementDeclaration element, List<SubresourceElement> subresources, boolean json) {
    String label = def != null ? def.getName().toLowerCase() : element != null ? element.getName() : "resource";
    String method = resourceMethod.getHttpMethods().iterator().next().toUpperCase();
    StringWriter out = new StringWriter();
    PrintWriter writer = new PrintWriter(out);
    String path = resourceMethod.getFullpath();
    if (path == null || path.isEmpty()) {
      path = "/path/to/" + label;
    }
    writer.printf("%s %s\n", method, path);

    List<ResourceParameter> resourceParameters = def != null ? def.getResourceParameters() : resourceMethod.getResourceParameters();
    for (ResourceParameter parameter : resourceParameters) {
      if (parameter.isHeaderParam()) {
        writer.printf("%s: ...\n", parameter.getParameterName());
      }
    }

    if (HttpMethod.POST.equals(method)) {
      for (SubresourceElement subresource : subresources) {
        if (!subresource.getDefinition().getResourceElements().isEmpty()) {
          element = subresource.getDefinition().getResourceElements().get(0);
        }
      }
    }

    String mediaType = null;
    SchemaInfo schemaInfo = element != null ? model.getNamespacesToSchemas().get(element.getNamespace()) : null;
    if (schemaInfo != null) {
      mediaType = json ? (String) schemaInfo.getProperty("jsonMediaType") : (String) schemaInfo.getProperty("xmlMediaType");
    }

    if (mediaType == null) {
      mediaType = json ? "application/json" : "application/xml";
    }

    if (HttpMethod.POST.equals(method) || HttpMethod.PUT.equals(method)) {
      writer.printf("Content-Type: %s\n", mediaType);
    }
    else if (HttpMethod.GET.equals(method)) {
      writer.printf("Accept: %s\n", mediaType);
    }

    return out.toString();
  }

}