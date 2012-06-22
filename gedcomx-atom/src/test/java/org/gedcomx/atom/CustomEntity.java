package org.gedcomx.atom;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.map.annotate.JsonTypeIdResolver;
import org.gedcomx.common.GenealogicalResource;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Ryan Heaton
 */
@XmlRootElement (namespace = "urn:custom")
@XmlType(namespace = "urn:custom")
public class CustomEntity extends GenealogicalResource {
}
