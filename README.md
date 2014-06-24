# Welcome

The GEDCOM X RS project is an extension to [GEDCOM X](http://www.gedcomx.org) to define a RESTful API for genealogical data.

Online genealogical data applications that provide an interface in conformance to GEDCOM X RS will be able to leverage the
GEDCOM X-compatible tools and libraries that are being developed to manage genealogical data. Clients will be enabled to
read, analyze, update, and correlate genealogical data across multiple applications using the same toolset.

GEDCOM X RS is to online genealogical data what HTML is to online web pages.

# The Specification

[Here is the formal specification](./specifications/rs-specification.md).

# Examples

Here are some examples of useful things clients can do with interfaces compatible with GEDCOM X RS.

## Find Records

1. Read the collection to be searched.
2. Use the link to the `person-search` endpoint to put together a search query.

For details see [`Person Search` State](./specifications/rs-specification.md#person-search).

#### example

##### request

```
GET /path/to/person/search
```

##### response

```
HTTP/1.1 200 OK
Content-Type: application/x-gedcomx-atom+json

{
  ...search results...
}

```

## Search for Persons in a Collection

1. Read the collection to be searched.
2. Use the link to the `person-search` endpoint to put together a search query.

For details see [`Person Search` State](./specifications/rs-specification.md#person-search).

#### example

##### request

```
GET /path/to/person/search
```

##### response

```
HTTP/1.1 200 OK
Content-Type: application/x-gedcomx-atom+json

{
  ...search results...
}

```

## Read the Ancestry of a Person

1. Read the person.
2. Use the link to the `ancestry` endpoint to read the ancestry.

For details see [`Ancestry Results` State](./specifications/rs-specification.md#ancestry).

#### example

##### request

```
GET /path/to/person/ancestry
```

##### response

```
HTTP/1.1 200 OK
Content-Type: application/x-gedcomx-v1+json

{
  ...data about the ancestry of a person...
}

```


## Read a Description of a Source

1. Read the source description.

For details see [`Source Description` State](./specifications/rs-specification.md#source-description).

#### example

##### request

```
GET /path/to/source/description
```

##### response

```
HTTP/1.1 200 OK
Content-Type: application/x-gedcomx-v1+json

{
  ...data about the source...
}

```

## Read a Record

todo:

## Read a Person

todo:

## Read the Sources of a Person

todo:

## Update the Name of a Person

todo:

## Create a Person

todo:

## Create a Relationship Between Two Persons

todo:

## Add a Source to a Person

todo:

## Upload an Image of a Birth Certificate to a Person

todo:

