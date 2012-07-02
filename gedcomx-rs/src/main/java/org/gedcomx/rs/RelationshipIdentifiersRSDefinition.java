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
import org.gedcomx.rt.rs.ResourceDefinition;
import org.gedcomx.rt.rs.ResponseCode;
import org.gedcomx.rt.rs.StatusCodes;

import javax.ws.rs.PUT;
import javax.ws.rs.core.Response;

/**
 * The conclusion resource service is used to manage a conclusion.
 *
 * @author Ryan Heaton
 */
@ResourceDefinition (
  name = "Relationship Identifiers",
  projectId = RSModel.RS_PROJECT_ID,
  resourceElement = { Relationship.class },
  namespace = RSModel.RS_V1_NAMESPACE
)
public interface RelationshipIdentifiersRSDefinition extends CommonRSParameters {

  public static final String REL = GEDCOMX_LINK_REL_PREFIX + "relationship/ids";

  /**
   * Update a conclusion.
   *
   * @param conclusion The conclusion to be used for the update.
   *
   */
  @PUT
  @StatusCodes({
    @ResponseCode ( code = 204, condition = "The update was successful."),
    @ResponseCode ( code = 404, condition = "If the requested resource is not found.")
  })
  Response put(Relationship relationship);
}
