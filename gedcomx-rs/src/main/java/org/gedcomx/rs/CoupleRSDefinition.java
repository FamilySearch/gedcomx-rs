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
package org.gedcomx.rs;

import org.gedcomx.conclusion.Relationship;
import org.gedcomx.rt.rs.*;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Response;

/**
 * The couple resource service is used to manage a couple.
 */
@ResourceDefinition (
  name = "Couple",
  resourceElement = Relationship.class,
  projectId = RSModel.RS_PROJECT_ID,
  namespace = RSModel.RS_V1_NAMESPACE
)
@ResourceLinks ({
  @ResourceLink ( rel = "self", definedBy = CoupleRSDefinition.class, description = "The couple itself." )
})
public interface CoupleRSDefinition extends CommonRSParameters {

  public static final String REL = GEDCOMX_LINK_REL_PREFIX + "couple";

  /**
   * Read a couple header attributes.
   *
   * @return The header attributes for the couple.
   */
  @HEAD
  @StatusCodes({
    @ResponseCode ( code = 200, condition = "Upon a successful read."),
    @ResponseCode ( code = 301, condition = "If the requested couple has been merged to another couple."),
    @ResponseCode ( code = 404, condition = "If the requested couple is not found."),
    @ResponseCode ( code = 410, condition = "If the requested couple has been deleted.")
  })
  Response head();

  /**
   * Read a couple.
   *
   * @return The couple.
   */
  @GET
  @StatusCodes({
    @ResponseCode ( code = 200, condition = "Upon a successful read."),
    @ResponseCode ( code = 301, condition = "If the requested couple has been merged into another couple."),
    @ResponseCode ( code = 404, condition = "If the requested couple is not found."),
    @ResponseCode ( code = 410, condition = "If the requested couple has been deleted.")
  })
  Response get();

  /**
   * Update a couple.
   *
   * @param relationship The couple relationship to be used for the update.
   *
   */
  @PUT
  @StatusCodes({
    @ResponseCode ( code = 204, condition = "The update was successful."),
    @ResponseCode ( code = 404, condition = "If the requested couple is not found."),
    @ResponseCode ( code = 410, condition = "If the requested person has been deleted.")
})
  Response put(Relationship relationship);

  /**
   * Delete a couple.
   *
   */
  @DELETE
  @StatusCodes({
    @ResponseCode ( code = 204, condition = "The delete was successful."),
    @ResponseCode ( code = 404, condition = "If the requested couple is not found."),
    @ResponseCode ( code = 410, condition = "If the requested person has been deleted.")
})
  Response delete();
}
