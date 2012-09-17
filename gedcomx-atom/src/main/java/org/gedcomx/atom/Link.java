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
package org.gedcomx.atom;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.gedcomx.common.URI;
import org.gedcomx.rt.json.HasUniqueJsonKey;
import org.gedcomx.rt.json.JsonElementWrapper;
import org.gedcomx.search.SearchModel;

import javax.xml.XMLConstants;
import javax.xml.bind.annotation.*;

/**
 * A reference to a Web resource.
 *
 * @author Ryan Heaton
 * @see <a href="http://tools.ietf.org/html/rfc4287#section-4.2.7">Atom Syndication Format, Section 4.2.7</a>
 */
@XmlRootElement
@XmlType ( name = "Link" )
@JsonElementWrapper ( name = "links" )
@SuppressWarnings("gedcomx:no_id")
public class Link implements HasUniqueJsonKey {

  private String rel;
  private URI href;
  private String template;
  private String type;
  private String hreflang;
  private String title;
  private String length;

  public Link(String rel, URI href) {
    this.rel = rel;
    this.href = href;
  }

  public Link() {
  }

  /**
   * The link relationship.
   *
   * @return The link relationship.
   */
  @XmlAttribute
  @JsonIgnore
  @org.codehaus.enunciate.json.JsonIgnore
  public String getRel() {
    return rel;
  }

  /**
   * The link relationship.
   *
   * @param rel The link relationship.
   */
  @JsonIgnore
  public void setRel(String rel) {
    this.rel = rel;
  }

  /**
   * The json key that is used define this link in a map.
   *
   * @return The json key that is used define this link in a map.
   */
  @XmlTransient
  @JsonIgnore
  @Override
  public String getJsonKey() {
    return getRel();
  }

  /**
   * The json key that is used define this link in a map.
   *
   * @param jsonKey The json key that is used define this link in a map.
   */
  @Override
  @JsonIgnore
  public void setJsonKey(String jsonKey) {
    setRel(jsonKey);
  }

  /**
   * The link URI.
   *
   * @return The link URI.
   */
  @XmlAttribute
  @XmlSchemaType (name = "anyURI", namespace = XMLConstants.W3C_XML_SCHEMA_NS_URI)
  public URI getHref() {
    return href;
  }

  /**
   * The link URI.
   *
   * @param href The link URI.
   */
  public void setHref(URI href) {
    this.href = href;
  }

  /**
   * The opensearch URI template (used in links to search resources).
   *
   * @return The opensearch URI template (used in links to search resources).
   */
  @XmlAttribute ( namespace = SearchModel.GEDCOMX_SEARCH_NAMESPACE )
  public String getTemplate() {
    return template;
  }

  /**
   * The opensearch URI template (used in links to search resources).
   *
   * @param template The opensearch URI template (used in links to search resources).
   */
  public void setTemplate(String template) {
    this.template = template;
  }

  /**
   * An advisory media type: it is a hint about the type of the representation that is expected to be returned when the value of the href attribute is dereferenced.
   *
   * @return An advisory media type: it is a hint about the type of the representation that is expected to be returned when the value of the href attribute is dereferenced.
   */
  @XmlAttribute
  public String getType() {
    return type;
  }

  /**
   * An advisory media type: it is a hint about the type of the representation that is expected to be returned when the value of the href attribute is dereferenced.
   *
   * @param type An advisory media type: it is a hint about the type of the representation that is expected to be returned when the value of the href attribute is dereferenced.
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * The language of the resource pointed to by the href attribute.
   *
   * @return The language of the resource pointed to by the href attribute.
   */
  @XmlAttribute
  public String getHreflang() {
    return hreflang;
  }

  /**
   * The language of the resource pointed to by the href attribute.
   *
   * @param hreflang The language of the resource pointed to by the href attribute.
   */
  public void setHreflang(String hreflang) {
    this.hreflang = hreflang;
  }

  /**
   * Human-readable information about the link.
   *
   * @return Human-readable information about the link.
   */
  @XmlAttribute
  public String getTitle() {
    return title;
  }

  /**
   * Human-readable information about the link.
   *
   * @param title Human-readable information about the link.
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * An advisory length of the linked content in octets; it is a hint about the content length of the representation returned when the IRI in the href attribute is mapped to a URI and dereferenced.
   *
   * @return An advisory length of the linked content in octets; it is a hint about the content length of the representation returned when the IRI in the href attribute is mapped to a URI and dereferenced.
   */
  @XmlAttribute
  public String getLength() {
    return length;
  }

  /**
   * An advisory length of the linked content in octets; it is a hint about the content length of the representation returned when the IRI in the href attribute is mapped to a URI and dereferenced.
   *
   * @param length An advisory length of the linked content in octets; it is a hint about the content length of the representation returned when the IRI in the href attribute is mapped to a URI and dereferenced.
   */
  public void setLength(String length) {
    this.length = length;
  }

  @Override
  public String toString() {
    return String.format("<%s>; rel=\"%s\"", href.toString(), rel);
  }
}
