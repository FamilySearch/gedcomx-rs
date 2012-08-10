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

import org.gedcomx.conclusion.ConclusionModel;
import org.gedcomx.conclusion.Relationship;
import org.gedcomx.metadata.source.SourceReference;
import org.gedcomx.rt.rs.*;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;


/**
 * The source references resource service is used to manage a collection of source references.
 */
@ResourceDefinition (
  name = "Relationship Source References",
  namespace = ConclusionModel.GEDCOMX_CONCLUSION_V1_NAMESPACE,
  projectId = RSModel.RS_PROJECT_ID,
  resourceElement = Relationship.class,
  subresources = {SourceReferenceRSDefinition.class}
)
@ResourceLinks ( {
                   @ResourceLink ( rel = "self", definedBy = PersonRSDefinition.class, description = "This source reference set." ),
                   @ResourceLink ( rel = RelationshipRSDefinition.REL, definedBy = RelationshipRSDefinition.class, description = "The relationship for which this is a set of source references." )
                 } )
public interface RelationshipSourceReferencesRSDefinition extends CommonRSParameters {

  public static final String REL = GEDCOMX_LINK_REL_PREFIX + "source/references";

  /**
   * Read the references to sources.
   *
   * @return The list of source references.
   */
  @GET
  @StatusCodes ( {
                   @ResponseCode ( code = 200, condition = "Upon a successful read." ),
                   @ResponseCode ( code = 204, condition = "Upon a successful query with no results." ),
                   @ResponseCode ( code = 404, condition = "The specified relationship has been moved, deleted, or otherwise not found." )
                 } )
  Response get();

  /**
   * Create a source reference.
   *
   * @param sourceReference The source reference to be created.
   * @return The appropriate response.
   */
  @POST
  @StatusCodes ( {
                   @ResponseCode ( code = 201, condition = "The creation of the source reference was successful. Expect a location header specifying the link to the created source reference." ),
                   @ResponseCode ( code = 400, condition = "If the request was unable to be understood by the application." ),
                   @ResponseCode ( code = 404, condition = "The specified relationship has been moved, deleted, or otherwise not found." )
                 } )
  Response post(SourceReference sourceReference);

}
