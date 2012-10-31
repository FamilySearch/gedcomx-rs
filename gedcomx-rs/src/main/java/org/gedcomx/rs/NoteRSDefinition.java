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

import org.gedcomx.common.Note;
import org.gedcomx.rt.CommonModels;
import org.gedcomx.rt.rs.*;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Response;


/**
 * The note resource service is used to manage a note.
 */
@ResourceDefinition(
    name = "Note",
    namespace = CommonModels.GEDCOMX_NAMESPACE,
    projectId = "gedcomx-rs",
    resourceElement = Note.class
)
@ResourceLinks ({
  @ResourceLink ( rel = "self", definedBy = NoteRSDefinition.class, description = "This note set." )
})
public interface NoteRSDefinition extends CommonRSParameters {

  public static final String REL = GEDCOMX_LINK_REL_PREFIX + "note";

  /**
   * Read a note.
   *
   * @return The note.
   */
  @GET
  @StatusCodes({
      @ResponseCode ( code = 200, condition = "Upon a successful read."),
      @ResponseCode ( code = 404, condition = "If the requested note is not found.")
  })
  Response get();

  /**
   * Update a note.
   *
   * @param note The note to be used for the update.
   * @return The appropriate response.
   */
  @PUT
  @StatusCodes({
      @ResponseCode ( code = 204, condition = "The update was successful."),
      @ResponseCode ( code = 404, condition = "If the requested note is not found.")
  })
  Response put( Note note );

  /**
   * Delete a note.
   *
   * @return The appropriate response.
   */
  @DELETE
  @StatusCodes({
      @ResponseCode ( code = 204, condition = "The delete was successful."),
      @ResponseCode ( code = 404, condition = "If the requested note is not found.")
  })
  Response delete();

}
