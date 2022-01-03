# Reference Data Application (rd-app)

# Quarkus Version
This codebase contains an implementation of the application using the Quarkus framework.

# Domain Model
The main entities of reference data model are the `Entity` and the `Entry`. <br/>
An entity is a named group of reference data and the entries are the elements of that collection. <br/>

An entity is uniquely identified by a _name_ and an entry is uniquely identified by a _key_. <br/>
The entry key can be a simple or composite. A simple key is represented by a string value and a composite key is
encoded in a well-known format (i.e. JSON).
The entry contains a reference to the name of the entity that it belongs to. 

An entry may have additional data declared as `attributes` and is encoded using a well-known format into a string field.
An entry can also have meta-data, such as the validity period and the version.

An entity can have a schema or can be schemaless. A schemaless entity can accept entries with any attribute structure.

## Versioning
Reference Data are version-aware data. That is, the historical values are maintained and the consultation operations are executed on
a specific applicable date. The precision of the validity period is at the day level. 
Version-aware entities like the `RD Entry` and the `RD Entity` have 5 attributes that define the versioning:
 - _**Validity Start date**_: specified in the create operation and represents the date that the information updated in the
specific operation becomes effective at the specified day. In case this is the first version of the specific entity record,
then before that date, any consultation operation will ignore the entity.
 - _**Validity End date**_: is a calculated field, which is updated when a new version of an entity is created. Then, the older version
 (not with respect to the creation timestamp, but to the validity date) has a validity end date equal to the validity start date
of the new record.
 - _**Modification Date**_: a timestamp that represents the order of execution of insert / update operations
 - _**Action**_: the action applied on the updated entity. Can be `C` (create), `U` (update) or `R` (removed). Removed entities are ignored
by the consultation queries and are shown only in historical consultation operations.
 - _**Version**_: an auto-incremented integer used for optimistic locking purposes.

## Metamodel
The simple flavour of reference data app contains simply a set of {Entry, Entity} data, plus the versioning information.

The advanced flavour defines a metamodel that leverages the power of the application:

- **_Entity Schema_**: an entity may contain a schema against which entries are validated. The schema may define the following:
  - Key structure and format
  - Attribute structure and format
- **_Logical Relationships_** between key elements and/or attribute elements of different entities
- **_Filters_** based on custom-defined conditions expressed in RD-DSL
  - **_row filters_** limit the number of entries fetched in consultation methods
  - **_column filters_** limit the entry information fetched in consultation methods
- **_Logical Entities_**: logical extension of entities based on filters without need of redundant data definition
- **_Views_**: Named collections of entities optionally combined with filters
- Custom **_Validation Rules_** based on RD-DSL

** **RD-DSL** is a domain specific language, used to define the meta-model entities such as filters and rules.
