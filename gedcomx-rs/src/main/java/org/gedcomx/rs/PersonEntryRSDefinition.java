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

import org.gedcomx.atom.Entry;
import org.gedcomx.rt.rs.ResourceDefinition;
import org.gedcomx.rt.rs.ResourceLink;
import org.gedcomx.rt.rs.ResourceLinks;

/**
 * The person entry resource defines a specific entry for a set of search results. The content
 * of the entry uses the person and relationship element(s) to describe the hit.
 *
 * @author Ryan Heaton
 */
@ResourceDefinition (
  name = "Person Entry",
  projectId = RSModel.RS_PROJECT_ID,
  namespace = RSModel.RS_V1_NAMESPACE,
  resourceElement = Entry.class,
  subresources = { PersonRSDefinition.class }
)
@ResourceLinks ( {
  @ResourceLink( rel = PersonRSDefinition.REL, definedBy = PersonRSDefinition.class, description = "The link to the person that is identified as a candidate in this search/result result." )
})
public interface PersonEntryRSDefinition {

}
