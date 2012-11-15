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
import org.gedcomx.rt.rs.*;

import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.core.Response;

/**
 * The contributor resource defines the interface for a contributor.
 */
@ResourceDefinition (
  resourceElement = Gedcomx.class,
  projectId = "gedcomx-rs",
  namespace = GedcomxConstants.GEDCOMX_NAMESPACE,
  states = {
    @StateDefinition (name = "Contributor", rel = ContributorRSDefinition.REL, description = "A contributor .")
  }
)
public interface ContributorRSDefinition extends CommonRSParameters {

  public static final String REL = GEDCOMX_LINK_REL_PREFIX + "contributor";

  /**
   * Read a contributor header attributes.
   *
   * @return The header attributes for the contributor.
   */
  @HEAD
  @StatusCodes ( {
    @ResponseCode ( code = 200, condition = "Upon a successful read." ),
    @ResponseCode ( code = 404, condition = "If the requested contributor is not found." )
   } )
  Response head();

  /**
   * Read a contributor.
   *
   * @return The contributor.
   */
  @GET
  @StatusCodes ( {
    @ResponseCode ( code = 200, condition = "Upon a successful read." ),
    @ResponseCode ( code = 404, condition = "If the requested contributor is not found." )
   } )
  Response get();
}
