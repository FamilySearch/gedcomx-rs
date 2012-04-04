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
 *
 * @author Mike Gardiner
 */

package org.gedcomx.xrd;

import javax.xml.XMLConstants;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

/**
 * The <Title> element contains a string value that provides a human-readable description
 * for the link. This value is intended only for human consumption and MUST NOT be used by
 * an XRD consumer to affect the processing of the document.
 *
 * See http://docs.oasis-open.org/xri/xrd/v1.0/xrd-1.0.html#element.title
 */
@XmlType( name = "Title")
@SuppressWarnings({"rdf-incompatible-ns", "unqualified-attribute"})
public final class Title {
    private String lang;
    private String value;

    /**
     * Optional Language of the title (i.e. en)
     *
     * @return String representing the language
     */
    @XmlAttribute(required = false, namespace = XMLConstants.XML_NS_URI )
    public String getLang() {
        return lang;
    }

    /**
     * Optional Language of the title (i.e. en)
     *
     * @param lang - A String representing the language
     */
    public void setLang(String lang) {
        this.lang = lang;
    }

    /**
     * The actual value of the Title element
     *
     * @return String representing the value
     */
    @XmlValue
    public String getValue() {
        return value;
    }

    /**
     * The actual value of the Title element
     *
     * @param value - A String representing the value
     */
    public void setValue(String value) {
        this.value = value;
    }
}
