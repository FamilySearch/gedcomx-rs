/**
 * Copyright 2011 Intellectual Reserve, Inc.
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
package org.gedcomx.rs;

import org.gedcomx.common.ResourceSet;
import org.gedcomx.conclusion.ConclusionModel;
import org.gedcomx.rt.rs.ResourceDefinition;
import org.gedcomx.rt.rs.ResponseCode;
import org.gedcomx.rt.rs.StatusCodes;

import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.core.Response;


/**
 * The extended source description resource service is used to retrieve a source description with extended information about the source description contributor(s).
 */
@ResourceDefinition(
    name = "Source Description (Extended)",
    namespace = ConclusionModel.GEDCOMX_CONCLUSION_V1_NAMESPACE,
    projectId = RSModel.RS_PROJECT_ID,
    resourceElement = ResourceSet.class,
    subresources = { SourceDescriptionRSDefinition.class }
)
public interface SourceDescriptionExRSDefinition extends CommonRSParameters {

  public static final String REL = GEDCOMX_LINK_REL_PREFIX + "source/description/ex";

  /**
   * Read the header attributes for an extended source description --
   * a source description with extended information about the description contributor(s).
   *
   * @return The header attributes for the extended source description.
   */
  @HEAD
  @StatusCodes({
      @ResponseCode( code = 200, condition = "Upon a successful read."),
      @ResponseCode ( code = 404, condition = "If the requested source description is not found.")
  })
  Response head();

  /**
   * Read an extended source description -- a source description with
   * extended information about the description contributor(s).
   *
   * @return The extended source description.
   */
  @GET
  @StatusCodes({
      @ResponseCode ( code = 200, condition = "Upon a successful read."),
      @ResponseCode ( code = 404, condition = "If the requested source description is not found.")
  })
  Response get();

}
