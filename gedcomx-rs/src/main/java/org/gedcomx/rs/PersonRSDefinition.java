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

import org.gedcomx.common.Gedcomx;
import org.gedcomx.conclusion.Person;
import org.gedcomx.rt.CommonModels;
import org.gedcomx.rt.rs.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * The person resource defines a person.
 *
 * @author Ryan Heaton
 */
@ResourceDefinition (
  name = "Person",
  resourceElement = Gedcomx.class,
  projectId = "gedcomx-rs",
  namespace = CommonModels.GEDCOMX_NAMESPACE
)
@ResourceLinks({
  @ResourceLink ( rel = "self", definedBy = PersonRSDefinition.class, description = "The person itself." ),
  @ResourceLink ( rel = SourceReferencesRSDefinition.REL, definedBy = SourceReferencesRSDefinition.class, description = "The source references for the person." ),
  @ResourceLink ( rel = NotesRSDefinition.REL, definedBy = NotesRSDefinition.class, description = "The set of notes for the person." ),
  @ResourceLink ( rel = PersonWithRelationshipsRSDefinition.REL, definedBy = PersonWithRelationshipsRSDefinition.class, description = "The person and associated relationships." ),
  @ResourceLink ( rel = PersonRelationshipsRSDefinition.SPOUSE_RELATIONSHIPS_REL, definedBy = PersonRelationshipsRSDefinition.class, description = "The relationships to the spouses of the person." ),
  @ResourceLink ( rel = PersonRelationshipsRSDefinition.CHILD_RELATIONSHIPS_REL, definedBy = PersonRelationshipsRSDefinition.class, description = "The relationships to the children of the person." ),
  @ResourceLink ( rel = PersonRelationshipsRSDefinition.PARENT_RELATIONSHIPS_REL, definedBy = PersonRelationshipsRSDefinition.class, description = "The relationships to the parents of the person." )
})
public interface PersonRSDefinition extends CommonRSParameters {

  public static final String REL = GEDCOMX_LINK_REL_PREFIX + "person";

  /**
   * Read a person header attributes.
   *
   * @return The header attributes for the person.
   */
  @HEAD
  @StatusCodes({
    @ResponseCode ( code = 200, condition = "Upon a successful read."),
    @ResponseCode ( code = 301, condition = "If the requested person has been merged to another person."),
    @ResponseCode ( code = 404, condition = "If the requested person is not found."),
    @ResponseCode ( code = 410, condition = "If the requested person has been deleted.")
  })
  Response head();

  /**
   * Read a person.
   *
   * @return The person.
   */
  @GET
  @StatusCodes({
    @ResponseCode ( code = 200, condition = "Upon a successful read."),
    @ResponseCode ( code = 301, condition = "If the requested person has been merged into another person."),
    @ResponseCode ( code = 404, condition = "If the requested person is not found."),
    @ResponseCode ( code = 410, condition = "If the requested person has been deleted.")
  })
  Response get();

  /**
   * Update a person.
   *
   * @param person The person to be used for the update.
   *
   */
  @POST
  @StatusCodes({
    @ResponseCode ( code = 204, condition = "The update was successful."),
    @ResponseCode ( code = 404, condition = "If the requested person is not found."),
    @ResponseCode ( code = 410, condition = "If the requested person has been deleted.")
  })
  Response post(Gedcomx person);

  /**
   * Delete a person.
   *
   */
  @DELETE
  @StatusCodes({
    @ResponseCode ( code = 204, condition = "The delete was successful."),
    @ResponseCode ( code = 404, condition = "If the requested person is not found."),
    @ResponseCode ( code = 410, condition = "If the requested person has already been deleted.")
  })
  Response delete();

}
