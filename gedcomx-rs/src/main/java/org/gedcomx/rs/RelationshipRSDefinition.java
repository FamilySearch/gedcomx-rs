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

import org.gedcomx.common.Gedcomx;
import org.gedcomx.rt.CommonModels;
import org.gedcomx.rt.rs.*;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;

/**
 * The relationship resource service is used to manage a relationship.
 */
@ResourceDefinition (
  name = "Relationship",
  resourceElement = Gedcomx.class,
  projectId = "gedcomx-rs",
  namespace = CommonModels.GEDCOMX_NAMESPACE,
  subresources = { ConclusionRSDefinition.class }
)
@ResourceLinks ({
  @ResourceLink ( rel = "self", definedBy = RelationshipRSDefinition.class, description = "The relationship itself." ),
})
public interface RelationshipRSDefinition extends CommonRSParameters {

  public static final String REL = GEDCOMX_LINK_REL_PREFIX + "relationship";

  /**
   * Read a relationship header attributes.
   *
   * @return The header attributes for the relationship.
   */
  @HEAD
  @StatusCodes({
    @ResponseCode ( code = 200, condition = "Upon a successful read."),
    @ResponseCode ( code = 404, condition = "If the requested relationship is not found."),
    @ResponseCode ( code = 410, condition = "If the requested relationship has been deleted.")
  })
  Response head();

  /**
   * Read a relationship.
   *
   * @return The relationship.
   */
  @GET
  @StatusCodes({
    @ResponseCode ( code = 200, condition = "Upon a successful read."),
    @ResponseCode ( code = 404, condition = "If the requested relationship is not found."),
    @ResponseCode ( code = 410, condition = "If the requested relationship has been deleted.")
  })
  Response get();

  /**
   * Update a relationship.
   *
   * @param relationship The relationship to be used for the update.
   *
   */
  @POST
  @StatusCodes({
    @ResponseCode ( code = 204, condition = "The update was successful."),
    @ResponseCode ( code = 404, condition = "If the requested relationship is not found."),
    @ResponseCode ( code = 410, condition = "If the requested relationship has been deleted.")
})
  Response post(Gedcomx relationship);

  /**
   * Delete a relationship.
   *
   */
  @DELETE
  @StatusCodes({
    @ResponseCode ( code = 204, condition = "The delete was successful."),
    @ResponseCode ( code = 404, condition = "If the requested relationship is not found."),
    @ResponseCode ( code = 410, condition = "If the requested relationship has been deleted.")
})
  Response delete();
}
