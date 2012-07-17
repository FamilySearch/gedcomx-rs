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
import org.gedcomx.conclusion.Relationship;
import org.gedcomx.rt.rs.ResourceDefinition;
import org.gedcomx.rt.rs.ResponseCode;
import org.gedcomx.rt.rs.StatusCodes;

import javax.ws.rs.POST;
import javax.ws.rs.core.Response;

/**
 * The relationships resource service is used to manage a collection of relationships.
 */
@ResourceDefinition (
  name = "Relationships",
  projectId = RSModel.RS_PROJECT_ID,
  namespace = RSModel.RS_V1_NAMESPACE,
  resourceElement = ResourceSet.class,
  subresources = RelationshipRSDefinition.class
)
public interface RelationshipsRSDefinition extends CommonRSParameters {

  public static final String COUPLE_RELATIONSHIPS_REL = GEDCOMX_LINK_REL_PREFIX + "relationships/couple";
  public static final String PARENT_CHILD_RELATIONSHIPS_REL = GEDCOMX_LINK_REL_PREFIX + "relationships/parent-child";

  /**
   * Create a relationship.
   *
   * @param relationship The relationship to be created.
   * @return The appropriate response.
   */
  @POST
  @StatusCodes({
    @ResponseCode ( code = 201, condition = "The creation of the relationship was successful. Expect a location header specifying the link to the created relationship.")
  })
  Response post(Relationship relationship);
}
