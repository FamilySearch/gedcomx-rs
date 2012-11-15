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

import org.gedcomx.Gedcomx;
import org.gedcomx.rt.GedcomxConstants;
import org.gedcomx.rt.rs.ResourceDefinition;
import org.gedcomx.rt.rs.ResponseCode;
import org.gedcomx.rt.rs.StateDefinition;
import org.gedcomx.rt.rs.StatusCodes;

import javax.ws.rs.POST;
import javax.ws.rs.core.Response;

/**
 * The relationships resource defines the interface for the set of relationships in the application.
 */
@ResourceDefinition (
  projectId = "gedcomx-rs",
  namespace = GedcomxConstants.GEDCOMX_NAMESPACE,
  resourceElement = Gedcomx.class,
  states = {
    @StateDefinition ( name = "Couple Relationships", rel = RelationshipsRSDefinition.COUPLE_RELATIONSHIPS_REL, description = "The set of couple relationships." ),
    @StateDefinition ( name = "Parent Relationships", rel = RelationshipsRSDefinition.PARENT_CHILD_RELATIONSHIPS_REL, description = "The set of couple relationships." )
  }
)
public interface RelationshipsRSDefinition extends CommonRSParameters {

  public static final String COUPLE_RELATIONSHIPS_REL = GEDCOMX_LINK_REL_PREFIX + "couple-relationships";
  public static final String PARENT_CHILD_RELATIONSHIPS_REL = GEDCOMX_LINK_REL_PREFIX + "parent-child-relationships";

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
  Response post(Gedcomx relationship);
}
