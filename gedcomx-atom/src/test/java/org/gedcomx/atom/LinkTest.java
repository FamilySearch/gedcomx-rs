package org.gedcomx.atom;

import org.testng.annotations.Test;

import org.gedcomx.common.URI;

import static org.gedcomx.rt.SerializationUtil.processThroughJson;
import static org.gedcomx.rt.SerializationUtil.processThroughXml;
import static org.testng.AssertJUnit.assertEquals;

/**
 * @author Ryan Heaton
 */
@Test
public class LinkTest {

  /**
   * tests link xml
   */
  public void testLinkXml() throws Exception {
    Link link = new Link();
    link.setHref(URI.create("urn:link"));
    link.setRel("rel");
    link.setTemplate("template");
    link = processThroughXml(link);
    assertEquals(URI.create("urn:link"), link.getHref());
    assertEquals("rel", link.getRel());
    assertEquals("template", link.getTemplate());
  }

  /**
   * tests link json
   */
  public void testLinkJson() throws Exception {
    Link link = new Link();
    link.setHref(URI.create("urn:link"));
    link.setRel("rel");
    link.setTemplate("template");
    link = processThroughJson(link);
    assertEquals(URI.create("urn:link"), link.getHref());
    assertEquals("rel", link.getRel());
    assertEquals("template", link.getTemplate());
  }
}
