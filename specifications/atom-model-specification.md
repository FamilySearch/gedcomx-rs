# The GEDCOM X Atom Extensions

## Status

This document specifies a set of genealogical data extensions to [RFC 4287](http://tools.ietf.org/html/rfc4287),
The Atom Syndication Format, and requests discussion and suggestions for improvements.

The current state of this document is as a DRAFT, and as such, the document
may be subject to changes, including backwards-incompatible changes, according to the
discussion and suggestions for improvement.

## Copyright Notice

Copyright 2011-2012 Intellectual Reserve, Inc.

## License

This document is distributed under a Creative Commons Attribution-ShareAlike license.
For details, see:

http://creativecommons.org/licenses/by-sa/3.0/

# 1. Introduction

The GEDCOM X Atom Extensions is a specification for a set of extensions to [RFC 4287](http://tools.ietf.org/html/rfc4287),
for the use of the Atom Syndication Format in the context of a genealogical data application. This specification also defines
a JSON representation of the Atom Syndication Format.

## 1.1 Identifier, Version, and Dependencies

The identifier for this specification is:

`http://gedcomx.org/atom-extensions/v1`

For convenience, the GEDCOM X Atom Extensions may be referred to as "GEDCOM X Atom Extensions 1.0".
This specification uses "GEDCOM X Atom Extensions" internally.

This specification defines a media type for the JSON representation of the Atom
Syndication Format. The identifier for this media type is:

`application/x-gedcomx-atom+json`

This specification references the GEDCOM X XML specification identified
by [`http://gedcomx.org/xml/v1`](https://github.com/FamilySearch/gedcomx/blob/master/specifications/xml-format-specification.md).

This specification also references the GEDCOM X JSON specification identified
by [`http://gedcomx.org/json/v1`](https://github.com/FamilySearch/gedcomx/blob/master/specifications/json-format-specification.md).


## 1.2 Notational Conventions

### 1.2.1 Keywords

The key words "MUST", "MUST NOT", "REQUIRED", "SHALL", "SHALL NOT",
"SHOULD", "SHOULD NOT", "RECOMMENDED", "MAY", and "OPTIONAL" in this
document are to be interpreted as described in BCP 14,
[RFC2119](http://tools.ietf.org/html/rfc2119), as scoped to those conformance
targets.

## 1.2.2 Compliance

An implementation of the GEDCOM X Atom Extensions is "non-compliant" if it fails to satisfy
one or more of the MUST or REQUIRED level requirements. An implementation that satisfies all of
the  MUST or REQUIRED and all of the SHOULD level requirements is said to be "unconditionally
compliant"; and implementation that satisfies all of the MUST level requirements but not all of the
SHOULD level requirements is said to be "conditionally compliant".

### 1.2.3 Namespace Prefixes

This specification uses the same namespace prefix conventions that are used by the
[GEDCOM X XML](https://github.com/FamilySearch/gedcomx/blob/master/specifications/xml-format-specification.md)
specification.

# 2. Atom Element Extensions

This section defines how the Atom Container Elements are extended for the purposes of this
specification.

## 2.1 "atom:feed" Extensions

The following metadata elements are specified as available child elements on the "atom:feed" element,
defined by [RFC 4287, Section 4.1.1](http://tools.ietf.org/html/rfc4287#section-4.1.1).

### 2.1.1 The "gx:index" Element

The "gx:index" element provides the index of the first entry in a page of data. It is used when the
feed is providing a paged set of data, such as when providing the results of a query. The value
of the "gx:index" element MUST be provided as an unsigned integer.

### 2.1.2 The "gx:results" Element

The "gx:results" element provides the total number of available results. It is used when the
feed is providing a paged set of data, such as when providing the results of a query. The value
of the "gx:results" element MUST be provided as an unsigned integer.

## 2.2 "atom:entry" Extensions

The following metadata elements are specified as available child elements on the "atom:entry" element,
defined by [RFC 4287, Section 4.1.2](http://tools.ietf.org/html/rfc4287#section-4.1.2).

### 2.2.1 The "gx:score" Element

The "gx:score" element provides the score of the relevance of an entry. It is used when the containing
"atom:feed" element is providing the results of a query. The value of the "gx:score" element is
interpreted as a floating-point number, and the relevance is application-specific, but MUST be consistent
between entries so that all scores can be ranked relative to one another.

### 2.2.2 The "gx:confidence" Element

The "gx:confidence" element provides a standard level of confidence of an entry. It is used when the containing
"atom:feed" element is providing the results of a query. The value of the "gx:confidence" element is
interpreted as an unsigned integer between 1 and 5, inclusive. A higher number implies a higher degree of
confidence.

## 2.3 "atom:content" Extensions

### 2.3.1 Processing Model

In accordance with the processing model defined by [RFC 4287, Section 4.1.3.3](http://tools.ietf.org/html/rfc4287#section-4.1.3.3),
the media type `application/x-gedcom-v1+xml` MAY be used as the value of the "type" attribute of the "atom:content"
element. In this case, the content MAY contain a single "gx:gedcomx" child element to provide the genealogical data associated
with the entry.

## 2.4 "atom:link" Extensions

The following metadata attributes are specified as available on the "atom:link" element,
defined by [RFC 4287, Section 4.2.7](http://tools.ietf.org/html/rfc4287#section-4.2.71).

### 2.4.1 The "template" Attribute

The "template" attribute is used to provide a URI template per [RFC 6570](http://tools.ietf.org/html/rfc6570),
used to link to a range of URIs, such as for the purpose of linking to a query.

### 2.4.2 The "accept" Attribute

The "accept" attribute is metadata about the acceptable media type(s) that can be used to update (i.e. change the state
of) the resource being linked to. The value of the "accept" attribute is as defined by the HTTP specification,
[RFC 2616](http://www.ietf.org/rfc/rfc2616.txt), Section 3.7.

### 2.4.3 The "allow" Attribute

The "allow" attribute is metadata about the allowable methods that can be used to transition to the resource being linked.
The value of the "allow" attribute is as defined by the HTTP specification, [RFC 2616](http://www.ietf.org/rfc/rfc2616.txt),
Section 14.7.

# 3. The "application/x-gedcomx-atom+json" Media Type

This specification defines a JSON format for the model defined by [RFC 4287](http://tools.ietf.org/html/rfc4287). The
`application/x-gedcomx-atom+json` media type is provide a near-complete JSON representation, with the notable exception
being necessary restrictions on the "atom:content" processing model defined by [RFC 4287, Section 4.1.3.3](http://tools.ietf.org/html/rfc4287#section-4.1.3.3).

The JSON format is defined by supplying a JSON member name for each element and attribute specified by the Atom Syndication
Format. Where the Atom element is defined by the Atom Syndication Format as plural, the JSON member name is plural
and its type is an array. Where a date value is specified, the JSON format provides the date as an number indicating the
number of milliseconds since January 1, 1970.

## 3.1. JSON Member Names

The following tables provide the JSON member names and types for each Atom element and attribute:

Atom element | JSON member | JSON type
-----|-------------|--------------
[`atom:name`](http://tools.ietf.org/html/rfc4287#section-3.2.1) | `name` | string
[`atom:uri`](http://tools.ietf.org/html/rfc4287#section-3.2.2) | `uri` | string
[`atom:email`](http://tools.ietf.org/html/rfc4287#section-3.2.2) | `email` | string
[`atom:feed`](http://tools.ietf.org/html/rfc4287#section-4.1.1) | n/a (root) | object
[`atom:entry`](http://tools.ietf.org/html/rfc4287#section-4.1.2) | `entries` | array of object
[`atom:content`](http://tools.ietf.org/html/rfc4287#section-4.1.3) | `content` | object
[`atom:author`](http://tools.ietf.org/html/rfc4287#section-4.2.1) | `author` | object
[`atom:category`](http://tools.ietf.org/html/rfc4287#section-4.2.2) | `categories` | array of object
[`atom:contributor`](http://tools.ietf.org/html/rfc4287#section-4.2.3) | `contributor` | object
[`atom:generator`](http://tools.ietf.org/html/rfc4287#section-4.2.4) | `generator` | object
[`atom:icon`](http://tools.ietf.org/html/rfc4287#section-4.2.5) | `icon` | object
[`atom:id`](http://tools.ietf.org/html/rfc4287#section-4.2.6) | `id` | string
[`atom:link`](http://tools.ietf.org/html/rfc4287#section-4.2.7) | `links` | array of object
[`atom:logo`](http://tools.ietf.org/html/rfc4287#section-4.2.8) | `logo` | string
[`atom:published`](http://tools.ietf.org/html/rfc4287#section-4.2.9) | `published` | number
[`atom:rights`](http://tools.ietf.org/html/rfc4287#section-4.2.10) | `rights` | string
[`atom:source`](http://tools.ietf.org/html/rfc4287#section-4.2.11) | `source` | object
[`atom:subtitle`](http://tools.ietf.org/html/rfc4287#section-4.2.12) | `subtitle` | string
[`atom:summary`](http://tools.ietf.org/html/rfc4287#section-4.2.13) | `summary` | string
[`atom:title`](http://tools.ietf.org/html/rfc4287#section-4.2.14) | `title` | string
[`atom:updated`](http://tools.ietf.org/html/rfc4287#section-4.2.15) | `updated` | number

Atom attribute | JSON member | JSON type
-----|-------------|--------------
[lang](http://tools.ietf.org/html/rfc4287#section-3) | `lang` | string
[type](http://tools.ietf.org/html/rfc4287#section-3.1.1) | `type` | string
[term](http://tools.ietf.org/html/rfc4287#section-4.2.2.1) | `term` | string
[scheme](http://tools.ietf.org/html/rfc4287#section-4.2.2.2) | `scheme` | string
[label](http://tools.ietf.org/html/rfc4287#section-4.2.2.3) | `label` | string
[uri](http://tools.ietf.org/html/rfc4287#section-4.2.4) | `uri` | string
[version](http://tools.ietf.org/html/rfc4287#section-4.2.4) | `version` | string
[href](http://tools.ietf.org/html/rfc4287#section-4.2.7.1) | `href` | string
[rel](http://tools.ietf.org/html/rfc4287#section-4.2.7.2) | `rel` | string
[type](http://tools.ietf.org/html/rfc4287#section-4.2.7.3) | `type` | string
[hreflang](http://tools.ietf.org/html/rfc4287#section-4.2.7.4) | `hreflang` | string
[title](http://tools.ietf.org/html/rfc4287#section-4.2.7.5) | `title` | string
[length](http://tools.ietf.org/html/rfc4287#section-4.2.7.6) | `length` | string
template | `template` | string
accept | `accept` | string
allow | `allow` | string

## 3.2. The Content Processing Model

The `application/x-gedcomx-atom+json` does not support the same processing model for the content
of an Atom entry. The content of an entry MAY have at most a single member, "gedcomx" to
supply the genealogical data associated with the entry. The value of the member is interpreted per
[GEDCOM X JSON](https://github.com/FamilySearch/gedcomx/blob/master/specifications/json-format-specification.md).