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

import org.gedcomx.common.ResourceReference;
import org.gedcomx.conclusion.ConclusionModel;
import org.gedcomx.rt.rs.ResourceDefinition;
import org.gedcomx.rt.rs.ResponseCode;
import org.gedcomx.rt.rs.StatusCodes;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Response;


@ResourceDefinition(
    name = "SourceReference",
    namespace = ConclusionModel.GEDCOMX_CONCLUSION_V1_NAMESPACE
    //, resourceElement = ResourceReference.class
)
public interface SourceReferenceRSDefinition extends CommonRSParameters {

  public static final String REL = GEDCOMX_LINK_REL_PREFIX + "source/reference";

  /**
   * Read a source reference header attributes.
   *
   * @return The header attributes for the source reference.
   */
  @HEAD
  @StatusCodes({
      @ResponseCode( code = 200, condition = "Upon a successful read."),
      @ResponseCode ( code = 404, condition = "If the requested source reference is not found."),
      @ResponseCode ( code = 410, condition = "If the requested source reference has been deleted.")
  })
  Response head();

  /**
   * Read a source reference.
   *
   * @return The source reference.
   */
  @GET
  @StatusCodes({
      @ResponseCode ( code = 200, condition = "Upon a successful read."),
      @ResponseCode ( code = 404, condition = "If the requested source reference is not found."),
      @ResponseCode ( code = 410, condition = "If the requested source reference has been deleted.")
  })
  Response get();

  /**
   * Update a source reference.
   *
   * @param sourceReference The source reference to be used for the update.
   * @return The appropriate response.
   */
  @PUT
  @StatusCodes({
      @ResponseCode ( code = 204, condition = "The update was successful.")
  })
  Response put(ResourceReference sourceReference);

  /**
   * Delete a source reference.
   *
   * @return The appropriate response.
   */
  @DELETE
  @StatusCodes({
      @ResponseCode ( code = 204, condition = "The delete was successful.")
  })
  Response delete();

}
