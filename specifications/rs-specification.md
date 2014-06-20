# GEDCOM X RS

## Status

This document specifies a standard interface for a genealogical data application on the World Wide Web, and requests
discussion and suggestions for improvements.

The current state of this document is as a DRAFT, and as such, the document
may be subject to changes, including backwards-incompatible changes, according to the
discussion and suggestions for improvement.

## Copyright Notice

Copyright 2011-2014 Intellectual Reserve, Inc.

## License

This document is distributed under a Creative Commons Attribution-ShareAlike license.
For details, see:

http://creativecommons.org/licenses/by-sa/3.0/

# 1. Introduction

GEDCOM X RS is a specification that defines a standard interface for a genealogical data application on the World Wide Web.
A set of data types are defined as extensions to the [core GEDCOM X specification set](https://github.com/FamilySearch/gedcomx/blob/master/specifications/)
and the [GEDCOM X Records Specification](https://github.com/FamilySearch/gedcomx-record/blob/master/specifications/record-specification.md)
to accommodate the requirements of a web-based application. The states of the application are identified and the transitions
between the application states are defined.

A genealogical application that conforms to this specification uses the Hypertext Transfer Protocol (HTTP) to accept requests from
and provide responses to client applications. This specification leverages the concepts and principles defined by HTTP to describe
how a client can expect to interact with a conforming genealogical application.

## 1.1 Identifier, Version, and Dependencies

The identifier for this specification is:

`http://gedcomx.org/rs/v1`

For convenience, GEDCOM X RS may be referred to as "GEDCOM X RS 1.0". This specification uses "GEDCOM X RS" internally.

This specification references the GEDCOM X Conceptual Model specification identified
by [`http://gedcomx.org/conceptual-model/v1`](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md).

This specification references the GEDCOM X XML specification identified
by [`http://gedcomx.org/xml/v1`](https://github.com/FamilySearch/gedcomx/blob/master/specifications/xml-format-specification.md).

This specification references the GEDCOM X JSON specification identified
by [`http://gedcomx.org/json/v1`](https://github.com/FamilySearch/gedcomx/blob/master/specifications/json-format-specification.md).

This specification references the GEDCOM X Atom Extensions specification identified
by [`http://gedcomx.org/atom-extensions/v1`](https://github.com/FamilySearch/gedcomx-rs/blob/master/specifications/atom-model-specification.md).

This specification references the GEDCOM X Record Extensions specification identified
by [`http://gedcomx.org/records/v1`](https://github.com/FamilySearch/gedcomx-record/blob/master/specifications/record-specification.md).

This specification references the HTTP 1/1 specification, [RFC 2616](http://tools.ietf.org/html/rfc2616).

## 1.2 Notational Conventions

### 1.2.1 Keywords

The key words "MUST", "MUST NOT", "REQUIRED", "SHALL", "SHALL NOT",
"SHOULD", "SHOULD NOT", "RECOMMENDED", "MAY", and "OPTIONAL" in this
document are to be interpreted as described in BCP 14,
[RFC2119](http://tools.ietf.org/html/rfc2119), as scoped to those conformance
targets.

### 1.2.2 Compliance

An implementation of GEDCOM X RS is "non-compliant" if it fails to satisfy
one or more of the MUST or REQUIRED level requirements. An implementation that satisfies all of
the  MUST or REQUIRED and all of the SHOULD level requirements is said to be "unconditionally
compliant"; and implementation that satisfies all of the MUST level requirements but not all of the
SHOULD level requirements is said to be "conditionally compliant".

## 1.3 Concepts and Definitions

### 1.3.1 Client

todo:

### 1.3.2 Server

todo:

### 1.3.3 Application States

GEDCOM X RS is defined by a set of application states. An "application state" is a snapshot of 
the state of genealogical data in an application at a specific point in time. Examples of an application 
state include resources such as persons, relationships, or sources. 

### 1.3.4 State Transitions (i.e. "Links")

A client drives the state of a GEDCOM X application by capturing transitions from application state to application state.
Each application state provides to the client the set of transitions (i.e. "links") to other application states 
that are available. For example, a client may be able to follow a link from a person to its source.

### 1.3.5 Operations

The set of available operations is constrained to the set defined by [RFC 2616, Section 9](http://tools.ietf.org/html/rfc2616#section-9).

### 1.3.6 Media Types

todo: 

### 1.3.7 The Application Entry Point

todo:

## 1.4 Use of HTTP

todo:

### 1.4.1 The Request

todo:

### 1.4.2 The Response

todo:

### 1.4.3 Content Negotiation

todo:

### 1.4.4 The OPTIONS Operation

todo:

### 1.4.5 Example

todo:

# 2. Data Type Extensions

This section defines a set of extensions to the [GEDCOM X Conceptual Model](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md)
and provides the representations of those data types in both XML and JSON as extensions to
[GEDCOM X JSON](https://github.com/FamilySearch/gedcomx/blob/master/specifications/json-format-specification.md) and
[GEDCOM X XML](https://github.com/FamilySearch/gedcomx/blob/master/specifications/xml-format-specification.md).

<a name="link"/>

## 2.1 The "Link" Data Type

The `Link` data type defines a representation of an available transition from one application state to another. The base definition
of a link is provided by [RFC 5988](http://tools.ietf.org/html/rfc5988).

Instances of `Link` can be reasonably expected as extension elements to any GEDCOM X data type.

### identifier

The identifier for the `Link` data type is:

`http://gedcomx.org/v1/Link`

### properties

name  | description | data type | constraints
------|-------------|-----------|------------
rel | Link relation identifier. | [`URI`](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#uri) | REQUIRED.
href | Link target. | [`URI`](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#uri) | OPTIONAL. If not provided, a `template` MUST be provided.
template | Link template, per [RFC 6570](http://tools.ietf.org/html/rfc6570). | URI Template | OPTIONAL. If not provided, a `href` MUST be provided.
type | Metadata about the available media type(s) of the resource being linked to. | string | OPTIONAL. The value of the "type" attribute is as defined by [RFC 2616](http://www.ietf.org/rfc/rfc2616.txt), Section 3.7.
accept | Metadata about the acceptable media type(s) that can be used to update (i.e. change the state of) the resource being linked to. | string | OPTIONAL. The value of the "accept" attribute is as defined by [RFC 2616](http://www.ietf.org/rfc/rfc2616.txt), Section 3.7.
allow | Metadata about the allowable methods that can be used to transition to the resource being linked. | string | OPTIONAL. The value of the "allow" attribute is as defined by [RFC 2616](http://www.ietf.org/rfc/rfc2616.txt), Section 14.7.
hreflang | The language of the resource being linked to. | string | OPTIONAL.
title | Human-readable information about the link. | string | OPTIONAL.

### 2.1.1 The "Link" XML Type and Element

The `gx:Link` XML type is used to (de)serialize the `http://gedcomx.org/v1/Link` data type.
The `gx:link` XML element is used to provide instances of the `gx:Link` XML type as extension elements.

### properties

name | description | XML property | XML type
-----|-------------|--------------|---------
rel | Link relation identifier. | rel (attribute) | [anyURI](https://github.com/FamilySearch/gedcomx/blob/master/specifications/xml-format-specification.md#uri)
href | Link target. | href (attribute) | [anyURI](https://github.com/FamilySearch/gedcomx/blob/master/specifications/xml-format-specification.md#uri)
template | Link template, per [RFC 6570](http://tools.ietf.org/html/rfc6570). | template (attribute) | xsd:string
type | Metadata about the available media type(s) of the resource being linked to. | type (attribute) | xsd:string
accept | Metadata about the acceptable media type(s) that can be used to update (i.e. change the state of) the resource being linked to. | accept (attribute) | xsd:string
allow | Metadata about the allowable methods that can be used to transition to the resource being linked. | allow (attribute) | xsd:string
hreflang | The language of the resource being linked to. | hreflang (attribute) | xsd:string
title | Human-readable information about the link. | title (attribute) | xsd:string

### examples

```xml
<gx:link rel="..." href="..." template="..." type="..." accept="..." allow="..." hreflang="..." title="...">
  <!-- possibility of extension elements -->
</gx:link>
```

### 2.1.2 The "Link" JSON Type

The `Link` JSON type is used to (de)serialize the `http://gedcomx.org/v1/Link` data type.

### properties

name | description | JSON member | JSON object type
-----|-------------|--------------|---------
rel | Link relation identifier. | rel | [anyURI](https://github.com/FamilySearch/gedcomx/blob/master/specifications/xml-format-specification.md#uri)
href | Link target. | href | [anyURI](https://github.com/FamilySearch/gedcomx/blob/master/specifications/xml-format-specification.md#uri)
template | Link template, per [RFC 6570](http://tools.ietf.org/html/rfc6570). | template | xsd:string
type | Metadata about the available media type(s) of the resource being linked to. | type | xsd:string
accept | Metadata about the acceptable media type(s) that can be used to update (i.e. change the state of) the resource being linked to. | accept | xsd:string
allow | Metadata about the allowable methods that can be used to transition to the resource being linked. | allow | xsd:string
hreflang | The language of the resource being linked to. | hreflang | xsd:string
title | Human-readable information about the link. | title | xsd:string

### examples

```json
{
  "rel" : "...",
  "href" : "...",
  "template" : "...",
  "type" : "...",
  "accept" : "...",
  "allow" : "...",
  "hreflang" : "...",

  ...possibility of extension elements...
}
```

### 2.1.3 The "Links" JSON Type and Member

For convenience in working with JSON, a list of instances of the `http://gedcomx.org/v1/Link` data type
are provided as a JSON object, with the members of the object being the value of the `rel` property of the
link. The `links` member is reserved to be used as an instance of this "Links" JSON Type.

### examples

The following example shows how a list of links is serialized in JSON.

```json
"links" {
  "rel1" : {
    "href" : "...",
  },
  "rel2" : {
    "href" : "...",
  },
  ...etc...
}
```


## 2.2 The "DisplayProperties" Data Type

The `DisplayProperties` data type defines a set of properties for convenience in displaying a summary of a person to a 
user. All display property values are provided as appropriate for the locale requested by the consuming application, 
or the default locale if no locale was specified. Instances of `DisplayProperties` can be expected as extension elements 
to the [`Person` Data Type](http://gedcomx.org/conceptual-model/v1`](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#person).

### identifier

The identifier for the `DisplayProperties` data type is:

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
marriageDate | The displayable label for the marriage date of the person. | string | OPTIONAL.
marriagePlace | The displayable label for the marriage date of the person. | string | OPTIONAL.
ascendancyNumber | The context-specific ascendancy number for the person in relation to the other persons in the request. The ancestry number is defined using the Ahnentafel numbering system. | string | OPTIONAL.
descendancyNumber | The context-specific descendancy number for the person in relation to the other persons in the request. The descendancy number is defined using the d'Aboville numbering system. | string | OPTIONAL.

### 2.2.1 The "DisplayProperties" XML Element

The `gx:DisplayProperties` XML type is used to (de)serialize the `http://gedcomx.org/v1/DisplayProperties` data type.
The `gx:display` XML element is used to provide instances of the `gx:DisplayProperties` XML type as extension elements.

### properties

name | description | XML property | XML type
-----|-------------|--------------|---------
name | The name of the person. | gx:name | xsd:string
gender | The displayable label for the gender of the person. | gx:gender | xsd:string
lifespan | The displayable label for the lifespan of the person. | gx:lifespan | xsd:string
birthDate | The displayable label for the birth date of the person. | gx:birthDate | xsd:string
birthPlace | The displayable label for the birth date of the person. | gx:birthPlace | xsd:string
deathDate | The displayable label for the death date of the person. | gx:deathDate | xsd:string
deathPlace | The displayable label for the death date of the person. | gx:deathPlace | xsd:string
marriageDate | The displayable label for the marriage date of the person. | gx:marriageDate | xsd:string
marriagePlace | The displayable label for the marriage date of the person. | gx:marriagePlace | xsd:string
ascendancyNumber | The context-specific ascendancy number for the person in relation to the other persons in the request. The ancestry number is defined using the Ahnentafel numbering system. | gx:ascendancyNumber | xsd:string
descendancyNumber | The context-specific descendancy number for the person in relation to the other persons in the request. The descendancy number is defined using the d'Aboville numbering system. | gx:descendancyNumber | xsd:string

### examples

```xml
<gx:display>
  <gx:name>...</gx:name>
  <gx:gender>...</gx:gender>
  <gx:lifespan>...</gx:lifespan>
  <gx:birthDate>...</gx:birthDate>
  <gx:birthPlace>...</gx:birthPlace>
  <gx:deathDate>...</gx:deathDate>
  <gx:deathPlace>...</gx:deathPlace>
  <gx:marriageDate>...</gx:marriageDate>
  <gx:marriagePlace>...</gx:marriagePlace>
  <gx:ascendancyNumber>...</gx:ascendancyNumber>
  <gx:descendancyNumber>...</gx:descendancyNumber>
  <!-- possibility of extension elements -->
</gx:display>
```

<a name="display"/>

### 2.2.2 The "DisplayProperties" JSON Element

The `DisplayProperties` JSON type is used to (de)serialize the `http://gedcomx.org/v1/DisplayProperties` data type.
The `display` JSON member is used to provide instances of the `DisplayProperties` JSON type as an extension element.

### properties

name | description | JSON member | JSON object type
-----|-------------|--------------|---------
name | The name of the person. | name | string
gender | The displayable label for the gender of the person. | gender | string
lifespan | The displayable label for the lifespan of the person. | lifespan | string
birthDate | The displayable label for the birth date of the person. | birthDate | string
birthPlace | The displayable label for the birth date of the person. | birthPlace | string
deathDate | The displayable label for the death date of the person. | deathDate | string
deathPlace | The displayable label for the death date of the person. | deathPlace | string
marriageDate | The displayable label for the marriage date of the person. | marriageDate | string
marriagePlace | The displayable label for the marriage date of the person. | marriagePlace | string
ascendancyNumber | The context-specific ascendancy number for the person in relation to the other persons in the request. The ancestry number is defined using the Ahnentafel numbering system. | ascendancyNumber | string
descendancyNumber | The context-specific descendancy number for the person in relation to the other persons in the request. The descendancy number is defined using the d'Aboville numbering system. | descendancyNumber | string

### examples

```json
"display" : {
  "name" : "...",
  "gender" : "...",
  "lifespan" : "...",
  "birthDate" : "...",
  "birthPlace" : "...",
  "deathDate" : "...",
  "deathPlace" : "...",
  "marriageDate" : "...",
  "marriagePlace" : "...",
  "ascendancyNumber" : "...",
  "descendancyNumber" : "...",
}
```

# 3. Property Extensions

This section defines a set of extensions to data types already defined by the
[GEDCOM X Conceptual Model](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md)
and and describes how the properties are included as extensions to
[GEDCOM X JSON](https://github.com/FamilySearch/gedcomx/blob/master/specifications/json-format-specification.md) and
[GEDCOM X XML](https://github.com/FamilySearch/gedcomx/blob/master/specifications/xml-format-specification.md).

## 3.1 Extensions to the "Date" Data Type

The following properties are defined as extensions to the
[`Date` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#date):

name  | description | data type | constraints
------|-------------|-----------|------------
normalized | A list of normalized values for a date for display purposes. | List of [`http://gedcomx.org/TextValue`](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#text-value). Order is preserved. | OPTIONAL. If more than one normalized value is provided, normalized values are assumed to be given in order of preference, with the most preferred normalized value in the first position in the list.

### 3.1.1 "Date" XML Type Extensions

name | XML property | XML type
-----|-------------|--------------
normalized | gx:normalized | [`gx:TextValue`](https://github.com/FamilySearch/gedcomx/blob/master/specifications/xml-format-specification.md#text-value)

### 3.1.2 "Date" JSON Type Extensions

name | JSON member | JSON object type
-----|-------------|-------------
normalized | normalized | array of [`TextValue`](https://github.com/FamilySearch/gedcomx/blob/master/specifications/json-format-specification.md#text-value)

## 3.2 Extensions to the "PlaceReference" Data Type

The following properties are defined as extensions to the
[`PlaceReference` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#place-reference):

name  | description | data type | constraints
------|-------------|-----------|------------
normalized | A list of normalized values for a place for display purposes. | List of [`http://gedcomx.org/TextValue`](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#text-value). Order is preserved. | OPTIONAL. If more than one normalized value is provided, normalized values are assumed to be given in order of preference, with the most preferred normalized value in the first position in the list.

### 3.2.1 "PlaceReference" XML Type Extensions

name | XML property | XML type
-----|-------------|--------------
normalized | gx:normalized | [`gx:TextValue`](https://github.com/FamilySearch/gedcomx/blob/master/specifications/xml-format-specification.md#text-value)

### 3.2.2 "PlaceReference" JSON Type Extensions

name | JSON member | JSON object type
-----|-------------|-------------
normalized | normalized | array of [`TextValue`](https://github.com/FamilySearch/gedcomx/blob/master/specifications/json-format-specification.md#text-value)

## 3.3 Extensions to the "Name" Data Type

The following properties are defined as extensions to the
[`Name` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#name):

name  | description | data type | constraints
------|-------------|-----------|------------
preferred | Whether the name is considered the "preferred" name for display purposes. | boolean | OPTIONAL.

### 3.3.1 "Name" XML Type Extensions

name | XML property | XML type
-----|-------------|--------------
preferred | preferred (attribute) | xsd:boolean

### 3.3.2 "Name" JSON Type Extensions

name | JSON member | JSON object type
-----|-------------|-------------
preferred | preferred | boolean

## 3.3 Extensions to the "Person" Data Type

The following properties are defined as extensions to the
[`Person` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#person):

name  | description | data type | constraints
------|-------------|-----------|------------
living | Whether the person is considered living by the application. | boolean | OPTIONAL.
display | The display properties for the person. | [`DisplayProperties`](#display) | OPTIONAL.

### 3.3.1 "Person" XML Type Extensions

name | XML property | XML type
-----|-------------|--------------
living | living (attribute) | xsd:boolean
display | gx:display | [`DisplayProperties`](#display)

### 3.3.2 "Person" JSON Type Extensions

name | JSON member | JSON object type
-----|-------------|-------------
living | living | boolean
display | display | [`DisplayProperties`](#display)


# 3. Application States

This section defines a set of application states for a genealogical data application.

An "application state" is a snapshot of the state of genealogical data in an application at a specific 
point in time. The formal definition of an application state is comprised of the following components:

* Applicable media types.
* Implication of HTTP operations.
* Expected data elements.
* Transitions to other application states.
* Embedded application states.

#### Applicable Media Types

Media types are used to represent the data elements of application states to the client. The client
interprets the data according to the definition of the media type, and uses a media type to
communicate changes of the application state to the server. The definition of each application 
state specifies the media types that servers are required to support. The definition of each application
state also lists other media types that are optional.

#### Implication of HTTP Operations

The definition of each application state includes how a given HTTP operation is expected to be 
interpreted by a server. Note that although this specification provides a discrete meaning to each
HTTP operation for each application state, some HTTP operations are optional. If an application
chooses to not support an optional operation, the server MUST respond with the HTTP `406` 
(Method Not Allowed) status code. Note that the `OPTIONS` and the `HEAD` operations are not defined
explicitly by the definition of each application state. The HTTP specification already provides
a sufficient definition of the `OPTIONS` and `HEAD` operations and their application is implicit
in the definition of each application state.
 
#### Expected data elements

Each application state captures a specific set of data that is managed by the server. For example,
the `Person` application state provides data about a person. The definition
of each application state declares the data elements that client can expect. Note that the application server
may choose to provide more data elements than those that are formally declared to be expected.

#### Transitions to other application states.

Each application state captures a limited portion of state. In order to enable the client to do useful
things, controls are provided by each application state that describe the available choices to the 
client. For example, at a `Person` state, the client may want to read the children of the person, or
it may want to read the spouses of a person, or it may want to update the name of the person, etc. The 
choices that are available to the client take the form of "transitions" to other application states.

The [`Link` data type](#link) provides the controls to the client that declare the available state 
transitions. For a list of transitions defined by GEDCOM X RS, see [Section 4, State Transitions](#transitions).
  
#### Embedded application states.

Some state transitions are designated as transitions to "embedded" application states. Embedded application 
states are to be considered as components of the state from which the transition starts. The use of
embedded application states is a common in online applications; an image embedded in an HTML page is 
an example.

Embedded application states are defined to accommodate designs of genealogical applications that break
up a genealogical resource into multiple requests. For example, a server may choose to break up the 
`Person` state into two requests: one to get the conclusions of the person(name, gender, facts) and 
another one to get the source references of the person. One reason for such a design might be to target
a specific caching strategy. Another reason might be to optimize requests for a specific database schema.

[Section 5 (Embedding Application States)](#embedding) specifies how embedded application states are used.

## 3.1 The "Agent" State

The `Agent` application state consists of a single agent in the system.

### 3.1.1 Media Types

Applications that implement the `Agent` state MUST support the `application/x-gedcomx-v1+json` media type
as defined by the [GEDCOM X JSON](https://github.com/FamilySearch/gedcomx/blob/master/specifications/json-format-specification.md)
specification. Support for the [GEDCOM X XML](https://github.com/FamilySearch/gedcomx/blob/master/specifications/xml-format-specification.md)
is RECOMMENDED.

### 3.1.2 Operations

The following operations are defined as applicable to the `Agent` state:

operation|description|constraints
---------|-----------|-----------
`GET` | Read an agent. | REQUIRED
`POST` | Update an agent. | OPTIONAL
`DELETE` | Delete an agent. | OPTIONAL

### 3.1.3 Data Elements

At least one instance of the [`Agent` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#agent)
MUST be provided by the server in the successful response of a `GET` operation. If more than one instance of `Agent` is provided, the instance that 
represents the "main" agent MUST be provided as the first element in the list.

At least one instance of the [`Agent` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#agent)
MUST be provided by the client in a request using the `POST` operation. If more than one instance of `Agent` is provided, the instance that 
represents the "main" agent MUST be provided as the first element in the list.

### 3.1.4 Transitions

No state transitions are specified for the `Agent` state.

### 3.1.5 Embedded States

No embedded states are specified for the `Agent` state.


## 3.2 The "Ancestry Results" State

The `Ancestry Results` state consists of the results of a query for multiple generations of the ancestry
of a person.

### 3.2.1 Media Types

Applications that implement the `Ancestry Results` state MUST support the `application/x-gedcomx-v1+json` media type
as defined by the [GEDCOM X JSON](https://github.com/FamilySearch/gedcomx/blob/master/specifications/json-format-specification.md)
specification. Support for the [GEDCOM X XML](https://github.com/FamilySearch/gedcomx/blob/master/specifications/xml-format-specification.md)
is RECOMMENDED.

### 3.2.2 Operations

The following operations are defined as applicable to the `Ancestry Results` state:

operation|description|constraints
---------|-----------|-----------
`GET` | Read the ancestry for a person. | REQUIRED

### 3.2.3 Data Elements

The results of a successful query for the ancestry of a person MUST contain a list of instances of the
[`Person` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#person). Each
`Person` in the list MUST provide a value for the `ascendancyNumber` of the person using [`DisplayProperties`](#display).

### 3.2.4 Transitions

The following state transitions are specified for the `Ancestry Results` state:

id|target state|scope|description
--|------------|-----|-----------
`person` | [`Person` State](#person) | Each instance of [`Person` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#person) | Transition from the ancestry results to the persons in the results.

[Section 4, State Transitions](#transitions) defines other transitions that MAY be 
provided by the server for the `Ancestry Results` state. Even though other transitions 
are not formally included in the definition of the `Ancestry Results` state, use of 
other transitions is RECOMMENDED where applicable. 

### 3.2.5 Embedded States

No embedded states are specified for the `Ancestry Results` state.


## 3.3 The "Collections" State

## 3.4 The "Collection" State

## 3.5 The "Descendancy Results" State

## 3.6 The "Persons" State

<a name="person"/>

## 3.7 The "Person" State

## 3.8 The "Person Search Results" State

## 3.9 The "Person Children" State

## 3.10 The "Person Spouses" State

## 3.11 The "Person Parents" State

## 3.12 The "Place Descriptions" State

## 3.13 The "Place Description" State

## 3.14 The "Place Search Results" State

## 3.15 The "Records" State

## 3.16 The "Record" State

## 3.17 The "Relationships" State

## 3.18 The "Relationship" State

## 3.19 The "Source Descriptions" State

## 3.20 The "Source Description" State

<a name="transitions"/>

# 4. State Transitions

todo: embedded links

todo: search query

todo: explain that this media type assumes the 'rels' are URIs relative to the 'http://gedcomx.org/' URI. This is to address the definition of a rel per RFC 5988.

todo: 'anchor' element

todo: 'sortKey' attribute

<a name="embedding"/>

# 5. Embedding Application States

# 5. Authentication and Authorization

