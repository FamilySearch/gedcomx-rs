# GEDCOM X RS

## Status

This document specifies a standard interface for a genealogical data application on the World Wide Web, and requests
discussion and suggestions for improvements.

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

GEDCOM X RS is a specification that defines a standard interface for a genealogical data application on the World Wide Web.
A set of data types are defined as extensions to the [GEDCOM X Conceptual Model](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md)
to accommodate the requirements of a web-based application. The states of the application are identified and the transitions
between the application states are defined.

## 1.1 Identifier, Version, and Dependencies

The identifier for this specification is:

`http://rs.gedcomx.org/v1`

For convenience, GEDCOM X RS may be referred to as "GEDCOM X RS 1.0". This specification uses "GEDCOM X RS" internally.

This specification references the GEDCOM X XML specification identified
by [`http://gedcomx.org/xml/v1`](https://github.com/FamilySearch/gedcomx/blob/master/specifications/xml-format-specification.md).

This specification also references the GEDCOM X JSON specification identified
by [`http://gedcomx.org/json/v1`](https://github.com/FamilySearch/gedcomx/blob/master/specifications/json-format-specification.md).

This specification also references the GEDCOM X Atom Extensions specification identified
by [`http://gedcomx.org/atom-extensions/v1`](https://github.com/FamilySearch/gedcomx-rs/blob/master/specifications/atom-model-specification.md).

## 1.2 Notational Conventions

### 1.2.1 Keywords

The key words "MUST", "MUST NOT", "REQUIRED", "SHALL", "SHALL NOT",
"SHOULD", "SHOULD NOT", "RECOMMENDED", "MAY", and "OPTIONAL" in this
document are to be interpreted as described in BCP 14,
[RFC2119](http://tools.ietf.org/html/rfc2119), as scoped to those conformance
targets.

## 1.2.2 Compliance

An implementation of GEDCOM X RS is "non-compliant" if it fails to satisfy
one or more of the MUST or REQUIRED level requirements. An implementation that satisfies all of
the  MUST or REQUIRED and all of the SHOULD level requirements is said to be "unconditionally
compliant"; and implementation that satisfies all of the MUST level requirements but not all of the
SHOULD level requirements is said to be "conditionally compliant".

### 1.2.3 Application States

GEDCOM X RS is defined in terms of application states. Examples of an application state include resources, such as persons,
relationships, or sources managed by an application. Each application state is defined in terms of the available transitions
to other states (i.e. "links"), the operations that are applicable to those resources, and the media types that are used to
read or update application states.

## 1.3 Operations

The set of available operations is constrained to the set defined by [RFC 2616, Section 9](http://tools.ietf.org/html/rfc2616#section-9).

## 1.4 Media Types

todo: explain how media types are used.

# 2. Data Type Extensions

This section defines a set of extensions to the [GEDCOM X Conceptual Model](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md)
and provides the representations of those data types in both XML and JSON as extensions to
[GEDCOM X JSON](https://github.com/FamilySearch/gedcomx/blob/master/specifications/json-format-specification.md) and
[GEDCOM X XML](https://github.com/FamilySearch/gedcomx/blob/master/specifications/xml-format-specification.md).

## 2.1 The "Link" Data Type

The `Link` data type defines a representation of a transition from one application state to another. The base definition
of a link is provided by [RFC 5988](http://tools.ietf.org/html/rfc5988).

### identifier

The identifier for the `Link` data type is:

`http://gedcomx.org/v1/Link`

### properties

name  | description | data type | constraints
------|-------------|-----------|------------
rel | Link relation type. | URI | REQUIRED.
href | Link target. | URI | OPTIONAL. If not provided, a 'template' MUST be provided instead.
template | Link template, per [RFC 6570](http://tools.ietf.org/html/rfc6570). | URI Template | OPTIONAL. If not provided, a 'href' MUST be provided instead.
type | Metadata about the available media type(s) of the resource being linked to. | string | OPTIONAL. The value of the "type" attribute is as defined by [RFC 2616](http://www.ietf.org/rfc/rfc2616.txt), Section 3.7.
accept | Metadata about the acceptable media type(s) that can be used to update (i.e. change the state of) the resource being linked to. | string | OPTIONAL. The value of the "accept" attribute is as defined by [RFC 2616](http://www.ietf.org/rfc/rfc2616.txt), Section 3.7.
allow | Metadata about the allowable methods that can be used to transition to the resource being linked. | string | OPTIONAL. The value of the "allow" attribute is as defined by [RFC 2616](http://www.ietf.org/rfc/rfc2616.txt), Section 14.7.
hreflang | The language of the resource being linked to. | string | OPTIONAL.
title | Human-readable information about the link. | string | OPTIONAL.

### 2.1.1 The "Link" XML Element

todo:

### 2.1.1 The "Link" JSON Member

todo:

## 2.2 The "DisplayProperties" Data Type

The `DisplayProperties` data type defines a set of properties applicable as an extension property of the `http://gedcomx.org/v1/Person` data type
for convenience in immediately displaying a summary of a person to a user. All display property values are provided as
appropriate for the locale requested by the consuming application, or the default locale if no locale was specified.

### identifier

The identifier for the `Link` data type is:

`http://gedcomx.org/v1/DisplayProperties`

### properties

name  | description | data type | constraints
------|-------------|-----------|------------
name | The name of the person. | string | OPTIONAL.
gender | The displayable label for the gender of the person. | string | OPTIONAL.
lifespan | The displayable label for the lifespan of the person. | string | OPTIONAL.
birthDate | The displayable label for the birth date of the person. | string | OPTIONAL.
birthPlace | The displayable label for the birth date of the person. | string | OPTIONAL.
deathDate | The displayable label for the death date of the person. | string | OPTIONAL.
deathPlace | The displayable label for the death date of the person. | string | OPTIONAL.
ascendancyNumber | The context-specific ascendancy number for the person in relation to the other persons in the request. The ancestry number is defined using the Ahnentafel numbering system. | string | OPTIONAL.
descendancyNumber | The context-specific descendancy number for the person in relation to the other persons in the request. The descendancy number is defined using the d'Aboville numbering system. | string | OPTIONAL.

### 2.2.1 The "DisplayProperties" XML Element

todo:

### 2.2.1 The "DisplayProperties" JSON Element

todo:

# 3. The Application States

todo: fill in

todo: explain that this media type assumes the 'rels' are URIs relative to the 'http://rs.gedcomx.org' URI. This is to address the definition of a rel per RFC 5988.