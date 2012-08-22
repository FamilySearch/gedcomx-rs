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

import org.gedcomx.common.ResourceSet;
import org.gedcomx.rt.rs.ResourceDefinition;
import org.gedcomx.rt.rs.ResponseCode;
import org.gedcomx.rt.rs.StatusCodes;

import javax.ws.rs.GET;
import javax.ws.rs.core.Response;

/**
 * The person with relationships resource is used to get a person and their associated relationships.
 */
@ResourceDefinition (
  name = "Person With Relationships",
  projectId = RSModel.RS_PROJECT_ID,
  namespace = RSModel.RS_V1_NAMESPACE,
  resourceElement = ResourceSet.class,
  subresources = {PersonRSDefinition.class, RelationshipRSDefinition.class }
)
public interface PersonWithRelationshipsRSDefinition extends CommonRSParameters {

  public static final String REL = GEDCOMX_LINK_REL_PREFIX + "persons-with-relationships";

  /**
   * Read a person and their relationships.
   *
   * @return The person and relationships.
   */
  @GET
  @StatusCodes({
    @ResponseCode ( code = 200, condition = "Upon a successful read."),
    @ResponseCode ( code = 301, condition = "If the requested person has been merged into another person."),
    @ResponseCode ( code = 404, condition = "If the requested person is not found."),
    @ResponseCode ( code = 410, condition = "If the requested person has been deleted.")
  })
  Response get();
}
