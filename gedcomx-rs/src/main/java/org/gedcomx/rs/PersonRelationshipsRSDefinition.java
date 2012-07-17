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
import org.gedcomx.rt.rs.*;

import javax.ws.rs.GET;
import javax.ws.rs.core.Response;

/**
 * The person relationships resource manages a set of relationships for a specific person.
 */
@ResourceDefinition (
  name = "Person Relationships",
  projectId = RSModel.RS_PROJECT_ID,
  namespace = RSModel.RS_V1_NAMESPACE,
  resourceElement = ResourceSet.class,
  subresources = { RelationshipRSDefinition.class }
)
@ResourceLinks ({
  @ResourceLink ( rel = "self", definedBy = PersonRSDefinition.class, description = "This relationship set." ),
  @ResourceLink ( rel = PersonRSDefinition.REL, definedBy = PersonRSDefinition.class, description = "The person for which this is a set of relationships." )
})
public interface PersonRelationshipsRSDefinition extends CommonRSParameters {

  public static final String SPOUSE_RELATIONSHIPS_REL = GEDCOMX_LINK_REL_PREFIX + "relationships/spouse";
  public static final String PARENT_RELATIONSHIPS_REL = GEDCOMX_LINK_REL_PREFIX + "relationships/parent";
  public static final String CHILD_RELATIONSHIPS_REL = GEDCOMX_LINK_REL_PREFIX + "relationships/child";

  /**
   * Read the set of relationships for a specific person.
   *
   * @return The set of relationships.
   */
  @GET
  @StatusCodes({
    @ResponseCode ( code = 200, condition = "Upon a successful read."),
    @ResponseCode ( code = 404, condition = "If the requested resource is not found.")
  })
  Response get();
}
