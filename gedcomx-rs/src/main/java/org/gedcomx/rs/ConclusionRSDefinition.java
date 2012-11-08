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

import org.gedcomx.rt.CommonModels;
import org.gedcomx.rt.rs.*;

import javax.ws.rs.DELETE;
import javax.ws.rs.core.Response;

/**
 * The conclusion resource defines the interface for a specific conclusion.
 *
 * @author Ryan Heaton
 */
@ResourceDefinition (
  name = "Conclusion",
  projectId = "gedcomx-rs",
  namespace = CommonModels.GEDCOMX_NAMESPACE
)
@ResourceLinks ({
  @ResourceLink ( rel = "self", definedBy = ConclusionRSDefinition.class, description = "Link to this conclusion." ),
  @ResourceLink ( rel = PersonRSDefinition.REL, definedBy = PersonRSDefinition.class, description = "The link to the person to which the conclusion applies." ),
  @ResourceLink ( rel = RelationshipRSDefinition.REL, definedBy = RelationshipRSDefinition.class, description = "The link to the relationship to which the conclusion applies." )
})
public interface ConclusionRSDefinition extends CommonRSParameters {

  public static final String REL = GEDCOMX_LINK_REL_PREFIX + "conclusion";

  /**
   * Delete a conclusion.
   *
   */
  @DELETE
  @StatusCodes({
    @ResponseCode ( code = 204, condition = "The delete was successful."),
    @ResponseCode ( code = 404, condition = "If the requested resource is not found.")
  })
  Response delete();
}
