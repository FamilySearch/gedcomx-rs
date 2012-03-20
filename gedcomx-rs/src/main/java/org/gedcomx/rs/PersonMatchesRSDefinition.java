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

import org.gedcomx.atom.Feed;
import org.gedcomx.conclusion.ConclusionModel;
import org.gedcomx.rt.rs.*;

import javax.ws.rs.GET;
import javax.ws.rs.core.Response;

/**
 * The person matches resource defines the set of matches in the system that are applicable to a person.
 *
 * @author Ryan Heaton
 */
@ResourceDefinition (
  name = "PersonMatches",
  projectId = RSModel.RS_PROJECT_ID,
  namespace = RSModel.RS_V1_NAMESPACE,
  resourceElement = Feed.class,
  subresources = { PersonMatchRSDefinition.class }
)
@ResourceRelationships ( {
  @ResourceRelationship( identifier = PersonRSDefinition.REL, definedBy = PersonRSDefinition.class, description = "The link to the person for which matches are being identified." ),
  @ResourceRelationship( identifier = PersonSummaryRSDefinition.REL, definedBy = PersonSummaryRSDefinition.class, description = "The link to the summary of the person that is identified as a match candidate." )
})
public interface PersonMatchesRSDefinition extends CommonRSParameters {

  public static final String REL = GEDCOMX_LINK_REL_PREFIX + "matches/person";

  /**
   * Read a set of matches for a person.
   *
   * @return The match set.
   */
  @GET
  @StatusCodes({
    @ResponseCode ( code = 200, condition = "Upon a successful read."),
    @ResponseCode ( code = 301, condition = "If the match set has been moved to a different location, e.g. as the result of a merge."),
    @ResponseCode ( code = 404, condition = "If the match set is not found. (Different from the case of the empty match set.)")
  })
  Response get();

}
