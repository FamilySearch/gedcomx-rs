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

import javax.ws.rs.DELETE;
import javax.ws.rs.core.Response;


/**
 * The source reference resource defines the interface for a source reference.
 */
@ResourceDefinition (
  namespace = GedcomxConstants.GEDCOMX_NAMESPACE,
  projectId = "gedcomx-rs",
  resourceElement = Gedcomx.class,
  states = {
    @StateDefinition ( name = "Source Reference", rel = SourceReferenceRSDefinition.REL, description = "The source reference." )
  }
)
public interface SourceReferenceRSDefinition extends CommonRSParameters {

  public static final String REL = GEDCOMX_LINK_REL_PREFIX + "source-reference";

  /**
   * Delete a source reference.
   *
   * @return The appropriate response.
   */
  @DELETE
  @StatusCodes ( {
    @ResponseCode ( code = 204, condition = "The delete was successful." ),
    @ResponseCode ( code = 404, condition = "If the requested source reference is not found." )
  } )
  Response delete();

}
