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

import org.gedcomx.atom.Feed;
import org.gedcomx.rt.rs.*;

import javax.ws.rs.GET;
import javax.ws.rs.core.Response;

/**
 * <p>The discovery resource is used to discover application (or "host") -level metadata. It is the process where machines negotiate how to interact with each other.
 * Discovery not only implies "tell me how to talk to you" but also "tell me what you can talk about". Discovery is an inherently application-level concept, so
 * it's usefulness is limited to the definition of a Web service API.</p>
 *
 * <p>The discovery resource should describe all the root-level links that are needed for a client to use the system. These links should include links to the various resources
 * that the system supports and links to the authentication and authorization policies that the system applies. The discovery resource is also used to allow
 * applications to assert conformance to individual GEDCOM X application profiles.</p>
 *
 * <p>The discovery resource references the following specs and standards:</p>
 *
 * <h4>The Atom Syndication Format</h4>
 *
 * <p>The <a href="http://www.ietf.org/rfc/rfc4287">Atom Specification</a> defines a simple generic format for xxx.</p>
 *
 * <h4>Well Known Uniform Resource Identifiers (URI)</h4>
 *
 * <p><a href="http://tools.ietf.org/html/rfc5785">RFC5785</a> defines a path prefix for "well-known locations", "/.well-known/", in selected Uniform Resource
 * Identifier (URI) schemes. For more details see <a href="http://tools.ietf.org/html/rfc5785">the spec</a>.<p>
 *
 * <h4>Web Host Metadata</h4>
 *
 * <p>The <a href="http://tools.ietf.org/html/draft-hammer-hostmeta">Web Host Metadata Specification</a> defines a lightweight metadata document format for describing
 * hosts (thus the name "host-meta"), intended for use by web-based protocols. This document also registers the well-known URI suffix "host-meta" in the
 * Well-Known URI Registry established by <a href="http://tools.ietf.org/html/rfc5785">RFC5785</a>. For more information see
 * <a href="http://tools.ietf.org/html/draft-hammer-hostmeta">the spec</a>.</p>
 *
 * @author Mike Gardiner
 * @author Ryan Heaton
 */
@ResourceDefinition(
    name = "Discovery",
    projectId = RSModel.RS_PROJECT_ID,
    resourceElement = Feed.class,
    namespace = RSModel.RS_V1_NAMESPACE
)
@ResourceLinks({
  @ResourceLink (rel = PersonsRSDefinition.REL, definedBy = DiscoveryRSDefinition.class, description = "The persons resource for this application."),
  @ResourceLink (rel = PersonRSDefinition.REL, definedBy = PersonRSDefinition.class, description = "The person for the currently logged in user." ),
  @ResourceLink (rel = PersonSummaryRSDefinition.REL, definedBy = PersonSummaryRSDefinition.class, description = "The person summary for the currently logged in user." ),
  @ResourceLink (rel = SearchRSDefinition.REL, definedBy = SearchRSDefinition.class, description = "The search resource for this application (linked via URI template).", template = true )
})
public interface DiscoveryRSDefinition extends CommonRSParameters {

  String REL = GEDCOMX_LINK_REL_PREFIX + "discovery";

  /**
   * Read the host metadata.
   *
   * @return The host metadata.
   */
  @GET
  @StatusCodes({
    @ResponseCode(code = 200, condition = "Upon a successful read.")
  })
  Response get();

}
