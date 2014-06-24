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
and the [GEDCOM X Record Extension](https://github.com/FamilySearch/gedcomx-record/blob/master/specifications/record-specification.md)
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
* Removable components.

#### Applicable media types

Media types are used to represent the data elements of application states to the client. The client
interprets the data according to the definition of the media type, and uses a media type to
communicate changes of the application state to the server. The definition of each application 
state specifies the media types that servers are required to support. The definition of each application
state also lists other media types that are optional.

#### Implication of HTTP operations

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

#### Removable components

An application state may designate specific components that can be deleted. Examples of a removable component
include the name of a person, a source reference, a role in an event, or a marriage fact of a couple 
relationship.

Removable components are designated in the same way as state transitions and embedded states, with a `rel` id
and a target URI. An HTTP `DELETE` operation MAY be applied to the URI to remove the component from the
application state.




<a name="agent"/>

## 3.1 The "Agent" State

The `Agent` application state consists of a single agent.

### 3.1.1 Media Types

Applications that implement the `Agent` state MUST support the `application/x-gedcomx-v1+json` media type
as defined by the [GEDCOM X JSON](https://github.com/FamilySearch/gedcomx/blob/master/specifications/json-format-specification.md)
specification. Support for the `application/x-gedcomx-v1+xml` media type as defined by [GEDCOM X XML](https://github.com/FamilySearch/gedcomx/blob/master/specifications/xml-format-specification.md)
is RECOMMENDED.

### 3.1.2 Operations

The following operations are defined as applicable to the `Agent` state:

operation|description|constraints
---------|-----------|-----------
`GET` | Read an agent. | REQUIRED
`POST` | Update an agent. | OPTIONAL
`DELETE` | Delete an agent. | OPTIONAL

A successful `GET` request SHOULD result in a `200` response code.

A successful `POST` request SHOULD result in a `204` response code.

A successful `DELETE` request SHOULD result in a `204` response code.

A server MAY provide other HTTP response codes as applicable under conditions established by the HTTP specification.

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

### 3.1.6 Removable Components

No removable components are specified for the `Agent` state.




<a name="ancestry"/>

## 3.2 The "Ancestry Results" State

The `Ancestry Results` state consists of the results of a query for multiple generations of the ancestry
of a person.

### 3.2.1 Media Types

Applications that implement the `Ancestry Results` state MUST support the `application/x-gedcomx-v1+json` media type
as defined by the [GEDCOM X JSON](https://github.com/FamilySearch/gedcomx/blob/master/specifications/json-format-specification.md)
specification. Support for the `application/x-gedcomx-v1+xml` media type as defined by [GEDCOM X XML](https://github.com/FamilySearch/gedcomx/blob/master/specifications/xml-format-specification.md)
is RECOMMENDED.

### 3.2.2 Operations

The following operations are defined as applicable to the `Ancestry Results` state:

operation|description|constraints
---------|-----------|-----------
`GET` | Read the ancestry for a person. | REQUIRED

A successful `GET` request SHOULD result in a `200` response code.

A server MAY provide other HTTP response codes as applicable under conditions established by the HTTP specification.

### 3.2.3 Data Elements

The results of a successful query for the ancestry of a person MUST contain a list of instances of the
[`Person` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#person). Each
`Person` in the list MUST provide a value for the `ascendancyNumber` of the person using [`DisplayProperties`](#display).

### 3.2.4 Transitions

The following state transitions are specified for the `Ancestry Results` state:

rel|target state|scope|description
--|------------|-----|-----------
`person` | [`Person` State](#person) | Each instance of [`Person` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#person) | Transition from the ancestry results to the persons in the results.

[Section 4, State Transitions](#transitions) defines other transitions that MAY be 
provided by the server for the `Ancestry Results` state. Even though other transitions 
are not formally included in the definition of the `Ancestry Results` state, use of 
other transitions is RECOMMENDED where applicable. 

### 3.2.5 Embedded States

No embedded states are specified for the `Ancestry Results` state.

### 3.2.6 Removable Components

No removable components are specified for the `Ancestry Results` state.




<a name="collections"/>

## 3.3 The "Collections" State

The `Collections` state consists of a list of collections. Examples of usages of the `Collections` state include 
to provide a list of subcollections of a collection, or to list all the collections in a system, or to provide a 
means for a client to create a collection in a system.

### 3.3.1 Media Types

Applications that implement the `Collections` state MUST support the `application/x-gedcomx-v1+json` media type
as defined by the [GEDCOM X JSON](https://github.com/FamilySearch/gedcomx/blob/master/specifications/json-format-specification.md)
specification. Support for the `application/x-gedcomx-v1+xml` media type as defined by [GEDCOM X XML](https://github.com/FamilySearch/gedcomx/blob/master/specifications/xml-format-specification.md)
is RECOMMENDED.

### 3.3.2 Operations

The following operations are defined as applicable to the `Collections` state:

operation|description|constraints
---------|-----------|-----------
`GET` | Read a list of collections. | OPTIONAL
`POST` | Create a collection or set of collections. | OPTIONAL

A successful `GET` request SHOULD result in a `200` response code, if the list contains one or more collections. If the list is empty, 
a successful `GET` SHOULD result in a `204` response code.

If one (and only one) collection was created as a result of a successful `POST` request, the request SHOULD result in a `201` response 
code and a `Location` header specifying the URI of the created collection. If multiple collections were created as a result of a successful 
`POST` request, the request SHOULD result in a `204` response code.

A server MAY provide other HTTP response codes as applicable under conditions established by the HTTP specification.

### 3.3.3 Data Elements

A list of instances of the
[`Collection` Data Type](https://github.com/FamilySearch/gedcomx-record/blob/master/specifications/record-specification.md#collection).
MUST be provided by the server in the successful response of a `GET` operation. If the list of collections is large, the server MAY
break up the list into multiple pages according to [Section 6, Paged Application States](#paging).

A list of instances of the
[`Collection` Data Type](https://github.com/FamilySearch/gedcomx-record/blob/master/specifications/record-specification.md#collection).
MUST be provided by the client in a request using the `POST` operation. The server considers each instance of `Collection` provided by 
the client as a candidate to be created and added to the list of collections. 


### 3.3.4 Transitions

The following state transitions are specified for the `Collections` state:

rel|target state|scope|description
--|------------|-----|-----------
`collection` | [`Collection` State](#collection) | Each instance of [`Collection` Data Type](https://github.com/FamilySearch/gedcomx-record/blob/master/specifications/record-specification.md#collection) | Transition from the list of collections to a single collection.

[Section 4, State Transitions](#transitions) defines other transitions that MAY be 
provided by the server for the `Collections` state. Even though other transitions 
are not formally included in the definition of the `Collections` state, use of 
other transitions is RECOMMENDED where applicable. 

### 3.3.5 Embedded States

No embedded states are specified for the `Collections` state.

### 3.3.6 Removable Components

No removable components are specified for the `Collections` state.




<a name="collection"/>

## 3.4 The "Collection" State

The `Collection` application state consists of a single collection.

### 3.4.1 Media Types

Applications that implement the `Collection` state MUST support the `application/x-gedcomx-v1+json` media type
as defined by the [GEDCOM X JSON](https://github.com/FamilySearch/gedcomx/blob/master/specifications/json-format-specification.md)
specification. Support for the `application/x-gedcomx-v1+xml` media type as defined by [GEDCOM X XML](https://github.com/FamilySearch/gedcomx/blob/master/specifications/xml-format-specification.md)
is RECOMMENDED.

### 3.4.2 Operations

The following operations are defined as applicable to the `Collection` state:

operation|description|constraints
---------|-----------|-----------
`GET` | Read a collection. | REQUIRED
`POST` | Update a collection. | OPTIONAL
`DELETE` | Delete a collection. | OPTIONAL

A successful `GET` request SHOULD result in a `200` response code.

A successful `POST` request SHOULD result in a `204` response code.

A successful `DELETE` request SHOULD result in a `204` response code.

A server MAY provide other HTTP response codes as applicable under conditions established by the HTTP specification.

### 3.4.3 Data Elements

At least one instance of the [`Collection` Data Type](https://github.com/FamilySearch/gedcomx-record/blob/master/specifications/record-specification.md#collection)
MUST be provided by the server in the successful response of a `GET` operation. If more than one instance of `Collection` is provided, the instance that 
represents the "main" collection MUST be provided as the first element in the list.

At least one instance of the [`Collection` Data Type](https://github.com/FamilySearch/gedcomx-record/blob/master/specifications/record-specification.md#collection)
MUST be provided by the client in a request using the `POST` operation. If more than one instance of `Collection` is provided, the instance that 
represents the "main" collection MUST be provided as the first element in the list.

### 3.4.4 Transitions

rel|target state|scope|description
--|------------|-----|-----------
`collection` | [`Collection` State](#collection) | [`Collection` Data Type](https://github.com/FamilySearch/gedcomx-record/blob/master/specifications/record-specification.md#collection) | Self-link to the `Collection` state.
`subcollections` | [`Collections` State](#collections) | [`Collection` Data Type](https://github.com/FamilySearch/gedcomx-record/blob/master/specifications/record-specification.md#collection) | Link to the list of subcollections for this collection.
`persons` | [`Persons` State](#persons) | [`Collection` Data Type](https://github.com/FamilySearch/gedcomx-record/blob/master/specifications/record-specification.md#collection) | Link to the list of persons in the collection.
`relationships` | [`Relationships` State](#relationsihps) | [`Collection` Data Type](https://github.com/FamilySearch/gedcomx-record/blob/master/specifications/record-specification.md#collection) | Link to the list of relationships between persons in this collection.
`events` | [`Events` State](#events) | [`Collection` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#collection) | Link to the list of events in this collection.
`records` | [`Records` State](#records) | [`Collection` Data Type](https://github.com/FamilySearch/gedcomx-record/blob/master/specifications/record-specification.md#collection) | Link to the list of records in the collection.
`artifacts` | [`Source Descriptions` State](#descriptions) | [`Collection` Data Type](https://github.com/FamilySearch/gedcomx-record/blob/master/specifications/record-specification.md#collection) | Link to the list of digital artifacts in the collection.
`source-descriptions` | [`Source Descriptions` State](#descriptions) | [`Collection` Data Type](https://github.com/FamilySearch/gedcomx-record/blob/master/specifications/record-specification.md#collection) | Link to the list of sources described in the collection.
`person-search` | [`Person Search Results` State](#search) | [`Collection` Data Type](https://github.com/FamilySearch/gedcomx-record/blob/master/specifications/record-specification.md#collection) | _Templated_ Link to the query used to search for persons in the system.
`place-search` | [`Place Search Results` State](#place-search) | [`Collection` Data Type](https://github.com/FamilySearch/gedcomx-record/blob/master/specifications/record-specification.md#collection) | _Templated_ Link to the query used to search for places in the system.
`http://oauth.net/core/2.0/endpoint/authorize` | OAuth 2 Authorization Page | [`Collection` Data Type](https://github.com/FamilySearch/gedcomx-record/blob/master/specifications/record-specification.md#collection) | Link to the authorization page used by a user to authenticate to the system. See [Section 7, Authentication and Authorization](#auth).
`http://oauth.net/core/2.0/endpoint/token` | OAuth 2 Token | [`Collection` Data Type](https://github.com/FamilySearch/gedcomx-record/blob/master/specifications/record-specification.md#collection) | Link to the endpoint used to obtain an access token. See [Section 7, Authentication and Authorization](#auth).

[Section 4, State Transitions](#transitions) defines other transitions that MAY be 
provided by the server for the `Collection` state. Even though other transitions 
are not formally included in the definition of the `Collection` state, use of 
other transitions is RECOMMENDED where applicable. 

### 3.4.5 Embedded States

No embedded states are specified for the `Collection` state.

### 3.4.6 Removable Components

No removable components are specified for the `Collection` state.




<a name="descendancy"/>

## 3.5 The "Descendancy Results" State

The `Descendancy Results` state consists of the results of a query for multiple generations of the descendancy
of a person.

### 3.5.1 Media Types

Applications that implement the `Descendancy Results` state MUST support the `application/x-gedcomx-v1+json` media type
as defined by the [GEDCOM X JSON](https://github.com/FamilySearch/gedcomx/blob/master/specifications/json-format-specification.md)
specification. Support for the `application/x-gedcomx-v1+xml` media type as defined by [GEDCOM X XML](https://github.com/FamilySearch/gedcomx/blob/master/specifications/xml-format-specification.md)
is RECOMMENDED.

### 3.5.2 Operations

The following operations are defined as applicable to the `Descendancy Results` state:

operation|description|constraints
---------|-----------|-----------
`GET` | Read the descendancy for a person. | REQUIRED

A successful `GET` request SHOULD result in a `200` response code.

A server MAY provide other HTTP response codes as applicable under conditions established by the HTTP specification.

### 3.5.3 Data Elements

The results of a successful query for the ancestry of a person MUST contain a list of instances of the
[`Person` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#person). Each
`Person` in the list MUST provide a value for the `descendancyNumber` of the person using [`DisplayProperties`](#display).

### 3.5.4 Transitions

The following state transitions are specified for the `Descendancy Results` state:

rel|target state|scope|description
--|------------|-----|-----------
`person` | [`Person` State](#person) | Each instance of [`Person` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#person) | Transition from the descendancy results to the persons in the results.

[Section 4, State Transitions](#transitions) defines other transitions that MAY be 
provided by the server for the `Descendancy Results` state. Even though other transitions 
are not formally included in the definition of the `Descendancy Results` state, use of 
other transitions is RECOMMENDED where applicable. 

### 3.5.5 Embedded States

No embedded states are specified for the `Descendancy Results` state.

### 3.5.6 Removable Components

No removable components are specified for the `Descendancy Results` state.



<a name="events"/>

## 3.6 The "Events" State

The `Events` state consists of a list of events. Examples of usages of the `Events` state include
to list all the events in a system or to provide a means for a client to create a event in a system.

### 3.6.1 Media Types

Applications that implement the `Events` state MUST support the `application/x-gedcomx-v1+json` media type
as defined by the [GEDCOM X JSON](https://github.com/FamilySearch/gedcomx/blob/master/specifications/json-format-specification.md)
specification. Support for the `application/x-gedcomx-v1+xml` media type as defined by [GEDCOM X XML](https://github.com/FamilySearch/gedcomx/blob/master/specifications/xml-format-specification.md)
is RECOMMENDED.

### 3.6.2 Operations

The following operations are defined as applicable to the `Events` state:

operation|description|constraints
---------|-----------|-----------
`GET` | Read a list of events. | OPTIONAL
`POST` | Create an event or set of events. | OPTIONAL

A successful `GET` request SHOULD result in a `200` response code, if the list contains one or more events. If the list is empty, 
a successful `GET` SHOULD result in a `204` response code.

If one (and only one) event was created as a result of a successful `POST` request, the request SHOULD result in a `201` response 
code and a `Location` header specifying the URI of the created event. If multiple events were created as a result of a successful 
`POST` request, the request SHOULD result in a `204` response code.

A server MAY provide other HTTP response codes as applicable under conditions established by the HTTP specification.

### 3.6.3 Data Elements

A list of instances of the
[`Event` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#event).
MUST be provided by the server in the successful response of a `GET` operation. If the list of events is large, the server MAY
break up the list into multiple pages according to [Section 6, Paged Application States](#paging).

A list of instances of the
[`Event` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#event).
MUST be provided by the client in a request using the `POST` operation. The server considers each instance of `Event` provided by 
the client as a candidate to be created and added to the list of events. 


### 3.6.4 Transitions

The following state transitions are specified for the `Events` state:

rel|target state|scope|description
--|------------|-----|-----------
`event` | [`Event` State](#event) | Each instance of [`Event` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#event) | Transition from the list of events to a single event.

[Section 4, State Transitions](#transitions) defines other transitions that MAY be 
provided by the server for the `Events` state. Even though other transitions 
are not formally included in the definition of the `Events` state, use of 
other transitions is RECOMMENDED where applicable. 

### 3.6.5 Embedded States

No embedded states are specified for the `Events` state.

### 3.6.6 Removable Components

No removable components are specified for the `Events` state.




<a name="event"/>

## 3.7 The "Event" State

The `Event` application state consists of a single event.

### 3.7.1 Media Types

Applications that implement the `Event` state MUST support the `application/x-gedcomx-v1+json` media type
as defined by the [GEDCOM X JSON](https://github.com/FamilySearch/gedcomx/blob/master/specifications/json-format-specification.md)
specification. Support for the `application/x-gedcomx-v1+xml` media type as defined by [GEDCOM X XML](https://github.com/FamilySearch/gedcomx/blob/master/specifications/xml-format-specification.md)
is RECOMMENDED.

### 3.7.2 Operations

The following operations are defined as applicable to the `Event` state:

operation|description|constraints
---------|-----------|-----------
`GET` | Read an event. | REQUIRED
`POST` | Update an event. | OPTIONAL
`DELETE` | Delete an event. | OPTIONAL

A successful `GET` request SHOULD result in a `200` response code.

A successful `POST` request SHOULD result in a `204` response code.

A successful `DELETE` request SHOULD result in a `204` response code.

A server MAY provide other HTTP response codes as applicable under conditions established by the HTTP specification.

### 3.7.3 Data Elements

At least one instance of the [`Event` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#event)
MUST be provided by the server in the successful response of a `GET` operation. If more than one instance of `Event` is provided, the instance that 
represents the "main" event MUST be provided as the first element in the list.

At least one instance of the [`Event` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#event)
MUST be provided by the client in a request using the `POST` operation. If more than one instance of `Event` is provided, the instance that 
represents the "main" event MUST be provided as the first element in the list.

### 3.7.4 Transitions

rel|target state|scope|description
--|------------|-----|-----------
`event` | [`Event` State](#event) | [`Event` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#event) | Self-link to the `Event` state.


### 3.7.5 Embedded States

No embedded states are specified for the `Event` state.

### 3.7.6 Removable Components

The following components of the `Event` state MAY be designated by the server as removable:

rel|scope|description
--|-----|-----------
`role` | Each instance of [`Event Role` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#conclusion-event-role) | The link used to remove the component.

A successful `DELETE` request to the removable component SHOULD result in a `204` response code.


<a name="persons"/>

## 3.8 The "Persons" State

The `Persons` state consists of a list of persons. Examples of usages of the `Persons` state include
to provide a list of all the persons in a collection, or to provide a means for a client to create a 
person in a collection.

### 3.8.1 Media Types

Applications that implement the `Persons` state MUST support the `application/x-gedcomx-v1+json` media type
as defined by the [GEDCOM X JSON](https://github.com/FamilySearch/gedcomx/blob/master/specifications/json-format-specification.md)
specification. Support for the `application/x-gedcomx-v1+xml` media type as defined by [GEDCOM X XML](https://github.com/FamilySearch/gedcomx/blob/master/specifications/xml-format-specification.md)
is RECOMMENDED.

### 3.8.2 Operations

The following operations are defined as applicable to the `Persons` state:

operation|description|constraints
---------|-----------|-----------
`GET` | Read a list of persons. | OPTIONAL
`POST` | Create a person or set of persons. | OPTIONAL

A successful `GET` request SHOULD result in a `200` response code, if the list contains one or more persons. If the list is empty, 
a successful `GET` SHOULD result in a `204` response code.

If one (and only one) person was created as a result of a successful `POST` request, the request SHOULD result in a `201` response 
code and a `Location` header specifying the URI of the created person. If multiple persons were created as a result of a successful 
`POST` request, the request SHOULD result in a `204` response code.

A server MAY provide other HTTP response codes as applicable under conditions established by the HTTP specification.

### 3.8.3 Data Elements

A list of instances of the
[`Person` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#person).
MUST be provided by the server in the successful response of a `GET` operation. If the list of persons is large, the server MAY
break up the list into multiple pages according to [Section 6, Paged Application States](#paging).

A list of instances of the
[`Person` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#person).
MUST be provided by the client in a request using the `POST` operation. The server considers each instance of `Person` provided by 
the client as a candidate to be created and added to the list of persons.


### 3.8.4 Transitions

The following state transitions are specified for the `Persons` state:

rel|target state|scope|description
--|------------|-----|-----------
`person` | [`Person` State](#person) | Each instance of [`Person` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#person) | Transition from the list of persons to a single person.

[Section 4, State Transitions](#transitions) defines other transitions that MAY be 
provided by the server for the `Persons` state. Even though other transitions 
are not formally included in the definition of the `Persons` state, use of 
other transitions is RECOMMENDED where applicable. 

### 3.8.5 Embedded States

No embedded states are specified for the `Persons` state.

### 3.8.6 Removable Components

No removable components are specified for the `Persons` state.




<a name="person"/>

## 3.9 The "Person" State

The `Person` application state consists of a single person.

### 3.9.1 Media Types

Applications that implement the `Person` state MUST support the `application/x-gedcomx-v1+json` media type
as defined by the [GEDCOM X JSON](https://github.com/FamilySearch/gedcomx/blob/master/specifications/json-format-specification.md)
specification. Support for the `application/x-gedcomx-v1+xml` media type as defined by [GEDCOM X XML](https://github.com/FamilySearch/gedcomx/blob/master/specifications/xml-format-specification.md)
is RECOMMENDED.

### 3.9.2 Operations

The following operations are defined as applicable to the `Person` state:

operation|description|constraints
---------|-----------|-----------
`GET` | Read a person. | REQUIRED
`POST` | Update a person. | OPTIONAL
`DELETE` | Delete a person. | OPTIONAL

A successful `GET` request SHOULD result in a `200` response code.

A successful `POST` request SHOULD result in a `204` response code.

A successful `DELETE` request SHOULD result in a `204` response code.

A server MAY provide other HTTP response codes as applicable under conditions established by the HTTP specification.

### 3.9.3 Data Elements

At least one instance of the [`Person` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#person)
MUST be provided by the server in the successful response of a `GET` operation. If more than one instance of `Person` is provided, the instance that 
represents the "main" person MUST be provided as the first element in the list.

At least one instance of the [`Person` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#person)
MUST be provided by the client in a request using the `POST` operation. If more than one instance of `Person` is provided, the instance that 
represents the "main" person MUST be provided as the first element in the list.

### 3.9.4 Transitions

rel|target state|scope|description
--|------------|-----|-----------
`person` | [`Person` State](#person) | [`Person` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#person) | Self-link to the `Person` state.
`collection` | [`Collection` State](#collection) | [`Person` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#person) | Link to the collection that contains this person.
`parents` | [`Person Parents` State](#parents) | [`Person` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#person) | Link to the list of parents for this person.
`children` | [`Person Children` State](#children) | [`Person` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#person) | Link to the list of children for this person.
`spouses` | [`Person Spouses` State](#spouses) | [`Person` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#person) | Link to the list of spouses for this person.
`ancestry` | [`Ancestry` State](#ancestry) | [`Person` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#person) | Link to the ancestry query for this person.
`descendancy` | [`Descendancy` State](#descendancy) | [`Person` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#person) | Link to the descendancy query for this person. 
`http://oauth.net/core/2.0/endpoint/authorize` | OAuth 2 Authorization Page | [`Person` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#person) | Link to the authorization page used by a user to authenticate to the system. See [Section 7, Authentication and Authorization](#auth).
`http://oauth.net/core/2.0/endpoint/token` | OAuth 2 Token | [`Person` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#person) | Link to the endpoint used to obtain an access token. See [Section 7, Authentication and Authorization](#auth).

[Section 4, State Transitions](#transitions) defines other transitions that MAY be 
provided by the server for the `Person` state. Even though other transitions 
are not formally included in the definition of the `Person` state, use of 
other transitions is RECOMMENDED where applicable. 

### 3.9.5 Embedded States

The following embedded states are specified for the `Person` state.

rel|scope|description
--|-----|-----------
`child-relationships` | [`Person` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#person) | List of relationships to children on the person. If no link to `child-relationships` is provided, the list of child relationships MUST be included in the original request for the `Person` state.
`parent-relationships` | [`Person` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#person) | List of relationships to parents on the person. If no link to `parent-relationships` is provided, the list of parent relationships MUST be included in the original request for the `Person` state.
`spouse-relationships` | [`Person` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#person) | List of relationships to spouses on the person. If no link to `spouse-relationships` is provided, the list of spouse relationships MUST be included in the original request for the `Person` state.
`evidence-references` | [`Person` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#person) | List of evidence (persona) references for the person. If no link to `evidence-references` is provided, the list of evidence references MUST be included in the original request for the `Person` state. If a link to `evidence-references` is provided, this link SHOULD be used to create new evidence references or update existing evidence references with a `POST` request.
`media-references` | [`Person` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#person) | List of media references for the person. If no link to `media-references` is provided, the list of media references MUST be included in the original request for the `Person` state. If a link to `media-references` is provided, this link SHOULD be used to create new media references or update existing media references with a `POST` request.
`notes` | [`Person` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#person) | List of notes for the person. If no link to `notes` is provided, the list of notes MUST be included in the original request for the `Person` state. If a link to `notes` is provided, this link SHOULD be used to create new notes or update existing notes with a `POST` request.
`source-references` | [`Person` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#person) | List of source references for the person. If no link to `source-references` is provided, the list of source references MUST be included in the original request for the `Person` state. If a link to `source-references` is provided, this link SHOULD be used to create new source references or update existing source references with a `POST` request.

### 3.9.6 Removable Components

The following components of the `Person` state MAY be designated by the server as removable:

rel|scope|description
--|-----|-----------
`conclusion` | Each instance of [`Fact` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#fact-conclusion), [`Gender` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#gender), and [`Name` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#name-conclusion) | The link used to remove the fact, gender, or name.
`evidence-reference` | Each instance of [`EvidenceReference` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#evidence-reference) | The link used to remove the persona (evidence) reference.
`media-reference` | Each instance of [`SourceReference` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#source-reference) | The link used to remove the media reference.
`note` | Each instance of [`Note` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#note) | The link used to remove the note.
`source-reference` | Each instance of [`SourceReference` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#source-reference) | The link used to remove the source reference.

A successful `DELETE` request to the removable component SHOULD result in a `204` response code.



<a name="search"/>

## 3.8 The "Person Search Results" State

The `Person Search Results` state consists of the results of a search query for persons in the system.

### 3.8.1 Media Types

Applications that implement the `Person Search Results` state MUST support the `application/x-gedcomx-atom+json` media type
as defined by the [GEDCOM X Atom Extensions](https://github.com/FamilySearch/gedcomx-rs/blob/master/specifications/atom-model-specification.md)
specification. Support for the `application/atom+xml` media type as defined by [RFC 4287 (The Atom Syndication Format)](http://www.ietf.org/rfc/rfc4287.txt) 
is RECOMMENDED.

### 3.8.2 Operations

The following operations are defined as applicable to the `Person Search Results` state:

operation|description|constraints
---------|-----------|-----------
`GET` | Search for persons. | REQUIRED

A successful `GET` request SHOULD result in a `200` response code, if the search results contain one or more persons. If the search results are
empty, a successful `GET` SHOULD result in a `204` response code.  If the list of results is large, the server MAY
break up the list into multiple pages according to [Section 6, Paged Application States](#paging).

### 3.8.3 Data Elements

The results of a successful query for the ancestry of a person MUST contain a list of entries that each describe a person.
The content of each entry is a GEDCOM X document that MUST contain at least one instance of the 
[`Person` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#person)
If more than one instance of `Person` is provided, the instance that represents the "main" person for the result MUST be provided 
as the first element in the list. Other elements might include relatives of the person and/or their relationships.

### 3.8.4 Transitions

The following state transitions are specified for the `Person Search Results` state:

rel|target state|scope|description
--|------------|-----|-----------
`person` | [`Person` State](#person) | Each instance of `Entry` Data Type as specified by [RFC 4287](http://www.ietf.org/rfc/rfc4287.txt) | Transition from the search results to the persons in the results.

[Section 4, State Transitions](#transitions) defines other transitions that MAY be 
provided by the server for the `Person Search Results` state. Even though other transitions 
are not formally included in the definition of the `Person Search Results` state, use of 
other transitions is RECOMMENDED where applicable. 

### 3.8.5 Embedded States

No embedded states are specified for the `Person Search Results` state.

### 3.8.6 Removable Components

No removable components are specified for the `Person Search Results` state.




<a name="children"/>

## 3.9 The "Person Children" State

The `Person Children` state consists of a list of children for a specific person.

### 3.9.1 Media Types

Applications that implement the `Person Children` state MUST support the `application/x-gedcomx-v1+json` media type
as defined by the [GEDCOM X JSON](https://github.com/FamilySearch/gedcomx/blob/master/specifications/json-format-specification.md)
specification. Support for the `application/x-gedcomx-v1+xml` media type as defined by [GEDCOM X XML](https://github.com/FamilySearch/gedcomx/blob/master/specifications/xml-format-specification.md)
is RECOMMENDED.

### 3.9.2 Operations

The following operations are defined as applicable to the `Person Children` state:

operation|description|constraints
---------|-----------|-----------
`GET` | Read the list of children of a person. | REQUIRED

A successful `GET` request SHOULD result in a `200` response code, if the list contains one or more persons. If the list is empty, 
a successful `GET` SHOULD result in a `204` response code.

A server MAY provide other HTTP response codes as applicable under conditions established by the HTTP specification.

### 3.9.3 Data Elements

A list of instances of the
[`Person` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#person)
MUST be provided by the server in the successful response of a `GET` operation. A list of instances of the
[`Relationship` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#relationship)
describing the relationships to each child SHOULD be provided by the server in the successful response of a `GET` operation. 

### 3.9.4 Transitions

The following state transitions are specified for the `Person Children` state:

rel|target state|scope|description
--|------------|-----|-----------
`person` | [`Person` State](#person) | Each instance of [`Person` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#person) | Transition from the list of persons to a single person.

[Section 4, State Transitions](#transitions) defines other transitions that MAY be 
provided by the server for the `Person Children` state. Even though other transitions 
are not formally included in the definition of the `Person Children` state, use of 
other transitions is RECOMMENDED where applicable. 

### 3.9.5 Embedded States

No embedded states are specified for the `Person Children` state.

### 3.9.6 Removable Components

No removable components are specified for the `Person Children` state.



<a name="spouses"/>

## 3.10 The "Person Spouses" State

The `Person Spouses` state consists of a list of spouses for a specific person.

### 3.10.1 Media Types

Applications that implement the `Person Spouses` state MUST support the `application/x-gedcomx-v1+json` media type
as defined by the [GEDCOM X JSON](https://github.com/FamilySearch/gedcomx/blob/master/specifications/json-format-specification.md)
specification. Support for the `application/x-gedcomx-v1+xml` media type as defined by [GEDCOM X XML](https://github.com/FamilySearch/gedcomx/blob/master/specifications/xml-format-specification.md)
is RECOMMENDED.

### 3.10.2 Operations

The following operations are defined as applicable to the `Person Spouses` state:

operation|description|constraints
---------|-----------|-----------
`GET` | Read the list of spouses of a person. | REQUIRED

A successful `GET` request SHOULD result in a `200` response code, if the list contains one or more persons. If the list is empty, 
a successful `GET` SHOULD result in a `204` response code.

A server MAY provide other HTTP response codes as applicable under conditions established by the HTTP specification.

### 3.10.3 Data Elements

A list of instances of the
[`Person` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#person).
MUST be provided by the server in the successful response of a `GET` operation. A list of instances of the
[`Relationship` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#relationship)
describing the relationships to each spouse SHOULD be provided by the server in the successful response of a `GET` operation. 

### 3.10.4 Transitions

The following state transitions are specified for the `Person Spouses` state:

rel|target state|scope|description
--|------------|-----|-----------
`person` | [`Person` State](#person) | Each instance of [`Person` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#person) | Transition from the list of persons to a single person.

[Section 4, State Transitions](#transitions) defines other transitions that MAY be 
provided by the server for the `Person Spouses` state. Even though other transitions 
are not formally included in the definition of the `Person Spouses` state, use of 
other transitions is RECOMMENDED where applicable. 

### 3.10.5 Embedded States

No embedded states are specified for the `Person Spouses` state.

### 3.10.6 Removable Components

No removable components are specified for the `Person Spouses` state.



<a name="parents"/>

## 3.11 The "Person Parents" State

The `Person Parents` state consists of a list of parents for a specific person.

### 3.11.1 Media Types

Applications that implement the `Person Parents` state MUST support the `application/x-gedcomx-v1+json` media type
as defined by the [GEDCOM X JSON](https://github.com/FamilySearch/gedcomx/blob/master/specifications/json-format-specification.md)
specification. Support for the `application/x-gedcomx-v1+xml` media type as defined by [GEDCOM X XML](https://github.com/FamilySearch/gedcomx/blob/master/specifications/xml-format-specification.md)
is RECOMMENDED.

### 3.11.2 Operations

The following operations are defined as applicable to the `Person Parents` state:

operation|description|constraints
---------|-----------|-----------
`GET` | Read the list of parents of a person. | REQUIRED

A successful `GET` request SHOULD result in a `200` response code, if the list contains one or more persons. If the list is empty, 
a successful `GET` SHOULD result in a `204` response code.

A server MAY provide other HTTP response codes as applicable under conditions established by the HTTP specification.

### 3.11.3 Data Elements

A list of instances of the
[`Person` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#person).
MUST be provided by the server in the successful response of a `GET` operation. A list of instances of the
[`Relationship` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#relationship)
describing the relationships to each parent SHOULD be provided by the server in the successful response of a `GET` operation. 

### 3.11.4 Transitions

The following state transitions are specified for the `Person Parents` state:

rel|target state|scope|description
--|------------|-----|-----------
`person` | [`Person` State](#person) | Each instance of [`Person` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#person) | Transition from the list of persons to a single person.

[Section 4, State Transitions](#transitions) defines other transitions that MAY be 
provided by the server for the `Person Parents` state. Even though other transitions 
are not formally included in the definition of the `Person Parents` state, use of 
other transitions is RECOMMENDED where applicable. 

### 3.11.5 Embedded States

No embedded states are specified for the `Person Parents` state.

### 3.11.6 Removable Components

No removable components are specified for the `Person Parents` state.



<a name="place-descriptions"/>

## 3.12 The "Place Descriptions" State

The `Place Descriptions` state consists of a list of place descriptions. Examples of usages of the `Place Descriptions` state include
to list all the descriptions of places in a system or to provide a means for a client to create place description in a system.

### 3.12.1 Media Types

Applications that implement the `Place Descriptions` state MUST support the `application/x-gedcomx-v1+json` media type
as defined by the [GEDCOM X JSON](https://github.com/FamilySearch/gedcomx/blob/master/specifications/json-format-specification.md)
specification. Support for the `application/x-gedcomx-v1+xml` media type as defined by [GEDCOM X XML](https://github.com/FamilySearch/gedcomx/blob/master/specifications/xml-format-specification.md)
is RECOMMENDED.

### 3.12.2 Operations

The following operations are defined as applicable to the `Place Descriptions` state:

operation|description|constraints
---------|-----------|-----------
`GET` | Read a list of place descriptions. | OPTIONAL
`POST` | Create an event or set of place descriptions. | OPTIONAL

A successful `GET` request SHOULD result in a `200` response code, if the list contains one or more place descriptions. If the list is empty, 
a successful `GET` SHOULD result in a `204` response code.

If one (and only one) event was created as a result of a successful `POST` request, the request SHOULD result in a `201` response 
code and a `Location` header specifying the URI of the created place description. If multiple place descriptions were created as a result of a successful 
`POST` request, the request SHOULD result in a `204` response code.

A server MAY provide other HTTP response codes as applicable under conditions established by the HTTP specification.

### 3.12.3 Data Elements

A list of instances of the
[`PlaceDescription` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#place-description).
MUST be provided by the server in the successful response of a `GET` operation. If the list of place descriptions is large, the server MAY
break up the list into multiple pages according to [Section 6, Paged Application States](#paging).

A list of instances of the
[`PlaceDescription` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#place-description).
MUST be provided by the client in a request using the `POST` operation. The server considers each instance of `PlaceDescription` provided by 
the client as a candidate to be created and added to the list of place descriptions. 


### 3.12.4 Transitions

The following state transitions are specified for the `Place Descriptions` state:

rel|target state|scope|description
--|------------|-----|-----------
`description` | [`Place Description` State](#place-description) | Each instance of [`Place Description` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#place-description) | Transition from the list of places descriptions to a single place description.

[Section 4, State Transitions](#transitions) defines other transitions that MAY be 
provided by the server for the `Place Descriptions` state. Even though other transitions 
are not formally included in the definition of the `Place Descriptions` state, use of 
other transitions is RECOMMENDED where applicable. 

### 3.12.5 Embedded States

No embedded states are specified for the `Place Descriptions` state.

### 3.12.6 Removable Components

No removable components are specified for the `Place Descriptions` state.




<a name="place-description"/>

## 3.13 The "Place Description" State

The `Place Description` application state consists of a single description of a place.

### 3.13.1 Media Types

Applications that implement the `Place Description` state MUST support the `application/x-gedcomx-v1+json` media type
as defined by the [GEDCOM X JSON](https://github.com/FamilySearch/gedcomx/blob/master/specifications/json-format-specification.md)
specification. Support for the `application/x-gedcomx-v1+xml` media type as defined by [GEDCOM X XML](https://github.com/FamilySearch/gedcomx/blob/master/specifications/xml-format-specification.md)
is RECOMMENDED.

### 3.13.2 Operations

The following operations are defined as applicable to the `Place Description` state:

operation|description|constraints
---------|-----------|-----------
`GET` | Read a place description. | REQUIRED
`POST` | Update a place description. | OPTIONAL
`DELETE` | Delete a place description. | OPTIONAL

A successful `GET` request SHOULD result in a `200` response code.

A successful `POST` request SHOULD result in a `204` response code.

A successful `DELETE` request SHOULD result in a `204` response code.

A server MAY provide other HTTP response codes as applicable under conditions established by the HTTP specification.

### 3.13.3 Data Elements

At least one instance of the [`PlaceDescription` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#place-description)
MUST be provided by the server in the successful response of a `GET` operation. If more than one instance of `PlaceDescription` is provided, the instance that 
represents the "main" place description MUST be provided as the first element in the list.

At least one instance of the [`PlaceDescription` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#event)
MUST be provided by the client in a request using the `POST` operation. If more than one instance of `PlaceDescription` is provided, the instance that 
represents the "main" place description MUST be provided as the first element in the list.

### 3.13.4 Transitions

rel|target state|scope|description
--|------------|-----|-----------
`description` | [`Place Description` State](#place-description) | Each instance of [`Place Description` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#place-description) | Self-link to the place description.


### 3.13.5 Embedded States

No embedded states are specified for the `Place Description` state.

### 3.13.6 Removable Components

No removable components are specified for the `Place Description` state.




<a name="place-search"/>

## 3.14 The "Place Search Results" State

The `Place Search Results` state consists of the results of a search query for places in the system.

### 3.14.1 Media Types

Applications that implement the `Place Search Results` state MUST support the `application/x-gedcomx-atom+json` media type
as defined by the [GEDCOM X Atom Extensions](https://github.com/FamilySearch/gedcomx-rs/blob/master/specifications/atom-model-specification.md)
specification. Support for the `application/atom+xml` media type as defined by [RFC 4287 (The Atom Syndication Format)](http://www.ietf.org/rfc/rfc4287.txt) 
is RECOMMENDED.

### 3.14.2 Operations

The following operations are defined as applicable to the `Place Search Results` state:

operation|description|constraints
---------|-----------|-----------
`GET` | Search for places. | REQUIRED

A successful `GET` request SHOULD result in a `200` response code, if the search results contain one or more places. If the search results are
empty, a successful `GET` SHOULD result in a `204` response code.  If the list of results is large, the server MAY
break up the list into multiple pages according to [Section 6, Paged Application States](#paging).

### 3.14.3 Data Elements

The results of a successful query for the ancestry of a place MUST contain a list of entries that each describe a place.
The content of each entry is a GEDCOM X document that MUST contain at least one instance of the 
[`PlaceDescription` Data Type](https://github.com/FamilySearch/gedcomx/blob/master/specifications/conceptual-model-specification.md#place-description)
If more than one instance of `PlaceDescription` is provided, the instance that represents the "main" place for the result MUST be provided 
as the first element in the list.

### 3.14.4 Transitions

The following state transitions are specified for the `Place Search Results` state:

rel|target state|scope|description
--|------------|-----|-----------
`description` | [`Place Description` State](#place-description) | Each instance of `Entry` Data Type as specified by [RFC 4287](http://www.ietf.org/rfc/rfc4287.txt) | Transition from the search results to the descriptions of the places in the results.

[Section 4, State Transitions](#transitions) defines other transitions that MAY be 
provided by the server for the `Place Search Results` state. Even though other transitions 
are not formally included in the definition of the `Place Search Results` state, use of 
other transitions is RECOMMENDED where applicable. 

### 3.14.5 Embedded States

No embedded states are specified for the `Place Search Results` state.

### 3.14.6 Removable Components

No removable components are specified for the `Place Search Results` state.




<a name="records"/>

## 3.15 The "Records" State





<a name="record"/>

## 3.16 The "Record" State





<a name="relationships"/>

## 3.17 The "Relationships" State





<a name="relationship"/>

## 3.18 The "Relationship" State





<a name="descriptions"/>

## 3.19 The "Source Descriptions" State





<a name="description"/>

## 3.20 The "Source Description" State






<a name="transitions"/>

# 4. State Transitions

todo: embedded links

todo: search query

todo: explain that this media type assumes the 'rels' are URIs relative to the 'http://gedcomx.org/' URI. This is to address the definition of a rel per RFC 5988.

todo: 'sortKey' attribute



<a name="embedding"/>

# 5. Embedding Application States



<a name="paging"/>

# 6. Paged Application States



<a name="auth"/>

# 7. Authentication and Authorization

