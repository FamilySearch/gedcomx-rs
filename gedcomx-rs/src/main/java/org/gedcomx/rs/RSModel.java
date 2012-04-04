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

import org.gedcomx.rt.Model;
import org.gedcomx.rt.Models;

import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Ryan Heaton
 */
@XmlTransient
@Models ( {
  @Model (
    id = "rs",
    namespace = RSModel.RS_V1_NAMESPACE,
    projectId = RSModel.RS_PROJECT_ID,
    label = "RS Model",
    description = "The RS model supplied the types and elements needed to provide core GEDCOM X Resource Definitions.",
    version = "2005",
    xmlMediaType = RSModel.RS_V1_XML_MEDIA_TYPE,
    jsonMediaType = RSModel.RS_V1_JSON_MEDIA_TYPE
  )
} )
public class RSModel {

  private RSModel() {}

  public static final String RS_PROJECT_ID = "gedcomx-rs";
  public static final String RS_V1_NAMESPACE = "http://rs.gedcomx.org/v1";
  public static final String RS_V1_XML_MEDIA_TYPE = "application/x-gedcomx-rs-v1+xml";
  public static final String RS_V1_JSON_MEDIA_TYPE = "application/x-gedcomx-rs-v1+json";
}
