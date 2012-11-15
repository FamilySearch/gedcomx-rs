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
import org.gedcomx.common.Note;
import org.gedcomx.rt.GedcomxConstants;
import org.gedcomx.rt.rs.*;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;


/**
 * The note resource defines the interface for a note.
 */
@ResourceDefinition(
  namespace = GedcomxConstants.GEDCOMX_NAMESPACE,
  projectId = "gedcomx-rs",
  resourceElement = Gedcomx.class,
  states = {
    @StateDefinition (
      name = "Person Note",
      rel = NoteRSDefinition.REL_PERSON,
      description = "A person note.",
      transitions = {
        @StateTransition( rel = NotesRSDefinition.REL_PERSON, description = "The list containing the note.", scope = Note.class ),
        @StateTransition( rel = PersonRSDefinition.REL, description = "The person.", scope = Note.class )
      }
    ),
    @StateDefinition (
      name = "Relationship Note",
      rel = NoteRSDefinition.REL_RELATIONSHIP,
      description = "A relationship note.",
      transitions = {
        @StateTransition( rel = NotesRSDefinition.REL_RELATIONSHIP, description = "The list containing the note.", scope = Note.class ),
        @StateTransition( rel = RelationshipRSDefinition.REL, description = "The relationship.", scope = Note.class )
      }
    )
  }
)
public interface NoteRSDefinition extends CommonRSParameters {

  public static final String REL_PERSON = GEDCOMX_LINK_REL_PREFIX + "person-note";
  public static final String REL_RELATIONSHIP = GEDCOMX_LINK_REL_PREFIX + "relationship-note";

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
  @POST
  @StatusCodes({
      @ResponseCode ( code = 204, condition = "The update was successful."),
      @ResponseCode ( code = 404, condition = "If the requested note is not found.")
  })
  Response post( Gedcomx note );

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
