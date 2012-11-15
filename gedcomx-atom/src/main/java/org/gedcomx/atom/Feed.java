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

import org.codehaus.enunciate.json.JsonName;
import org.codehaus.jackson.annotate.JsonProperty;
import org.gedcomx.common.URI;
import org.gedcomx.links.Link;
import org.gedcomx.links.SupportsLinks;
import org.gedcomx.rt.MediaTypeDefinition;
import org.gedcomx.rt.Model;
import org.gedcomx.search.SearchModel;

import javax.xml.XMLConstants;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>The Atom media types provide a format for web content and metadata syndication. The XML media type is defined by
 * <a href="http://tools.ietf.org/html/rfc4287#section-4">RFC 4287</a>. The JSON media type is specific to GEDCOM X and is a
 * translation to JSON from the XML.</p>
 *
 * @author Ryan Heaton
 */
@MediaTypeDefinition (
  id = "atom",
  name = "Atom",
  description = "The Atom media types provide a format for web content and metadata syndication.",
  version = "1.0",
  xmlMediaType = AtomModel.ATOM_XML_MEDIA_TYPE,
  jsonMediaType = AtomModel.ATOM_GEDCOMX_JSON_MEDIA_TYPE,
  projectId = "gedcomx-rs",
  models = {
    @Model (
      id = "atom",
      namespace = AtomModel.ATOM_NAMESPACE,
      label = "Atom Model",
      description = "The Atom model supplied the types and elements defined by the Atom syndication format."
    ),
    @Model (
      id = "gxq",
      namespace = SearchModel.GEDCOMX_SEARCH_NAMESPACE,
      label = "Search Model",
      description = "The search model defines elements and types used for providing metadata about the results of a search."
    )
  }
)
@XmlRootElement
@XmlType( name = "Feed", propOrder = {"authors", "contributors", "generator", "icon", "id", "results", "index", "links", "logo", "rights", "subtitle", "title", "updated", "entries"} )
@SuppressWarnings("gedcomx:no_id")
public class Feed extends ExtensibleElement implements SupportsLinks {
  
  private List<Person> authors;
  private List<Person> contributors;
  private Generator generator;
  private URI icon;
  private URI id;
  private Integer results;
  private Integer index;
  private List<Link> links;
  private URI logo;
  private String rights;
  private String subtitle;
  private String title;
  private Date updated;
  private List<Entry> entries;

  /**
   * The author of the feed.
   *
   * @return The author of the feed.
   */
  @XmlElement (name = "author")
  @JsonName("authors")
  @JsonProperty("authors")
  public List<Person> getAuthors() {
    return authors;
  }

  /**
   * The author of the feed.
   *
   * @param authors The author of the feed.
   */
  @JsonProperty("authors")
  public void setAuthors(List<Person> authors) {
    this.authors = authors;
  }

  /**
   * information about a category associated with the feed
   *
   * @return information about a category associated with the feed
   */
  @XmlElement (name = "contributor")
  @JsonName("contributors")
  @JsonProperty("contributors")
  public List<Person> getContributors() {
    return contributors;
  }

  /**
   * information about a category associated with the feed
   *
   * @param contributors information about a category associated with the feed
   */
  @JsonProperty("contributors")
  public void setContributors(List<Person> contributors) {
    this.contributors = contributors;
  }

  /**
   * identifies the agent used to generate the feed
   *
   * @return identifies the agent used to generate the feed
   */
  public Generator getGenerator() {
    return generator;
  }

  /**
   * identifies the agent used to generate the feed
   *
   * @param generator identifies the agent used to generate the feed
   */
  public void setGenerator(Generator generator) {
    this.generator = generator;
  }

  /**
   * identifies an image that provides iconic visual identification for the feed.
   *
   * @return identifies an image that provides iconic visual identification for the feed.
   */
  @XmlSchemaType (name = "anyURI", namespace = XMLConstants.W3C_XML_SCHEMA_NS_URI)
  public URI getIcon() {
    return icon;
  }

  /**
   * identifies an image that provides iconic visual identification for the feed.
   *
   * @param icon identifies an image that provides iconic visual identification for the feed.
   */
  public void setIcon(URI icon) {
    this.icon = icon;
  }

  /**
   * a permanent, universally unique identifier for the feed.
   *
   * @return a permanent, universally unique identifier for the feed.
   */
  @XmlSchemaType (name = "anyURI", namespace = XMLConstants.W3C_XML_SCHEMA_NS_URI)
  public URI getId() {
    return id;
  }

  /**
   * a permanent, universally unique identifier for the feed.
   *
   * @param id a permanent, universally unique identifier for the feed.
   */
  public void setId(URI id) {
    this.id = id;
  }

  /**
   * The number of search results available for the current search, if this feed is supplying search results.
   *
   * @return The number of search results available for the current search, if this feed is supplying search results.
   */
  @XmlElement (namespace = SearchModel.GEDCOMX_SEARCH_NAMESPACE )
  public Integer getResults() {
    return results;
  }

  /**
   * The number of search results available for the current search, if this feed is supplying search results.
   *
   * @param results The number of search results available for the current search, if this feed is supplying search results.
   */
  public void setResults(Integer results) {
    this.results = results;
  }

  /**
   * The index of the first search result in the current set of search results, if this feed is supplying search results.
   *
   * @return The index of the first search result in the current set of search results, if this feed is supplying search results.
   */
  @XmlElement (namespace = SearchModel.GEDCOMX_SEARCH_NAMESPACE )
  public Integer getIndex() {
    return index;
  }

  /**
   * The index of the first search result in the current set of search results, if this feed is supplying search results.
   *
   * @param index The index of the first search result in the current set of search results, if this feed is supplying search results.
   */
  public void setIndex(Integer index) {
    this.index = index;
  }

  /**
   * a reference from a feed to a Web resource.
   *
   * @return a reference from a feed to a Web resource.
   */
  @XmlElement (name = "link")
  @JsonName("links")
  @JsonProperty("links")
  public List<Link> getLinks() {
    return links;
  }

  /**
   * a reference from a feed to a Web resource.
   *
   * @param links a reference from a feed to a Web resource.
   */
  @JsonProperty("links")
  public void setLinks(List<Link> links) {
    this.links = links;
  }

  /**
   * Add a hypermedia link.
   *
   * @param link The hypermedia link.
   */
  @Override
  public void addLink(Link link) {
    if (this.links == null) {
      setLinks(new ArrayList<Link>());
    }

    this.links.add(link);
  }

  /**
   * Add a hypermedia link.
   *
   * @param rel The link rel.
   * @param href The target URI.
   */
  @Override
  public void addLink(String rel, URI href) {
    addLink(new Link(rel, href));
  }

  /**
   * Add a templated link.
   *
   * @param rel The link rel.
   * @param template The link template.
   */
  @Override
  public void addTemplatedLink(String rel, String template) {
    Link link = new Link();
    link.setRel(rel);
    link.setTemplate(template);
    addLink(link);
  }

  /**
   * Get a link by its rel.
   *
   * @param rel The link rel.
   * @return The link by rel.
   */
  @Override
  public Link getLink(String rel) {
    List<Link> links = getLinks(rel);
    Link link = null;
    if (!links.isEmpty()) {
      link = links.get(0);
    }
    return link;
  }

  /**
   * Get a list of links by rel.
   *
   * @param rel The rel of the links.
   * @return The link.
   */
  @Override
  public List<Link> getLinks(String rel) {
    ArrayList<Link> links = new ArrayList<Link>();
    if (this.links != null) {
      for (Link link : getLinks()) {
        if (rel.equals(link.getRel())) {
          links.add(link);
        }
      }
    }
    return links;
  }

  /**
   * identifies an image that provides visual identification for the feed.
   *
   * @return identifies an image that provides visual identification for the feed.
   */
  @XmlSchemaType (name = "anyURI", namespace = XMLConstants.W3C_XML_SCHEMA_NS_URI)
  public URI getLogo() {
    return logo;
  }

  /**
   * identifies an image that provides visual identification for the feed.
   *
   * @param logo identifies an image that provides visual identification for the feed.
   */
  public void setLogo(URI logo) {
    this.logo = logo;
  }

  /**
   * information about rights held in and over the feed.
   *
   * @return information about rights held in and over the feed.
   */
  public String getRights() {
    return rights;
  }

  /**
   * information about rights held in and over the feed.
   *
   * @param rights information about rights held in and over the feed.
   */
  public void setRights(String rights) {
    this.rights = rights;
  }

  /**
   * a human-readable description or subtitle for the feed.
   *
   * @return a human-readable description or subtitle for the feed.
   */
  public String getSubtitle() {
    return subtitle;
  }

  /**
   * a human-readable description or subtitle for the feed.
   *
   * @param subtitle a human-readable description or subtitle for the feed.
   */
  public void setSubtitle(String subtitle) {
    this.subtitle = subtitle;
  }

  /**
   * a human-readable title for the feed
   *
   * @return a human-readable title for the feed
   */
  public String getTitle() {
    return title;
  }

  /**
   * a human-readable title for the feed
   *
   * @param title a human-readable title for the feed
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * the most recent instant in time when the feed was modified in a way the publisher considers significant.
   *
   * @return the most recent instant in time when the feed was modified in a way the publisher considers significant.
   */
  public Date getUpdated() {
    return updated;
  }

  /**
   * the most recent instant in time when the feed was modified in a way the publisher considers significant.
   *
   * @param updated the most recent instant in time when the feed was modified in a way the publisher considers significant.
   */
  public void setUpdated(Date updated) {
    this.updated = updated;
  }

  /**
   * The entries in the feed.
   *
   * @return The entries in the feed.
   */
  @XmlElement( name = "entry" )
  @JsonName("entries")
  @JsonProperty("entries")
  public List<Entry> getEntries() {
    return entries;
  }

  /**
   * The entries in the feed.
   *
   * @param entries The entries in the feed.
   */
  @JsonProperty("entries")
  public void setEntries(List<Entry> entries) {
    this.entries = entries;
  }
}
