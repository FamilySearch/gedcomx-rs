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
import org.gedcomx.common.ResourceSet;
import org.gedcomx.rt.CommonModels;
import org.gedcomx.rt.rs.ResourceDefinition;
import org.gedcomx.rt.rs.ResponseCode;
import org.gedcomx.rt.rs.StatusCodes;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;


/**
 * The notes resource service is used to manage a collection of notes.
 */
@ResourceDefinition(
    name = "Notes",
    namespace = CommonModels.GEDCOMX_NAMESPACE,
    projectId = RSModel.RS_PROJECT_ID,
    resourceElement = ResourceSet.class,
    subresources = { NoteRSDefinition.class }
)
public interface NotesRSDefinition extends CommonRSParameters {

  public static final String REL = GEDCOMX_LINK_REL_PREFIX + "notes";

  /**
   * Read the notes on to entity. Each note in the set is a note summary which is a note but
   * without the text field populated.
   *
   * @return The list of note summaries.
   */
  @GET
  @StatusCodes({
    @ResponseCode( code = 200, condition = "Upon a successful read."),
    @ResponseCode( code = 204, condition = "Upon a successful query with no results."),
    @ResponseCode( code = 404, condition = "The specified entity has been moved, deleted, or otherwise not found.")
  })
  Response get();

  /**
   * Create a note.
   *
   * @param note The note to be created.
   * @return The appropriate response.
   */
  @POST
  @StatusCodes({
      @ResponseCode( code = 201, condition = "The creation of the note was successful. Expect a location header specifying the link to the note."),
      @ResponseCode( code = 400, condition = "If the request was unable to be understood by the application.")
  })
  Response post( Note note );

}
