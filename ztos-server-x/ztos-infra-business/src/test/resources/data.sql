DROP TABLE IF EXISTS dummy;
CREATE TABLE dummy (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  created_by VARCHAR(50) NOT NULL,
  created_date TIMESTAMP NOT NULL,
  last_modified_by VARCHAR(50) NOT NULL,
  last_modified_date TIMESTAMP NOT NULL
);

DROP TABLE IF EXISTS yard_allocation_group;

DROP TABLE IF EXISTS yard_allocation_filter;
CREATE TABLE yard_allocation_filter (
    id INT AUTO_INCREMENT  PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    iso_type VARCHAR(50),
    pod VARCHAR(50),
    trade_code VARCHAR(50),
    freight_kind INT,
    category VARCHAR(50),
    is_reefer BIT,
    is_hazardous BIT,
    is_damaged BIT,
    is_high_cube BIT,
    is_out_of_gauge BIT,
    created_by VARCHAR(50) NOT NULL,
    created_date TIMESTAMP NOT NULL,
    last_modified_by VARCHAR(50) NOT NULL,
    last_modified_date TIMESTAMP NOT NULL
);

CREATE ALIAS AddGeometryColumn for "geodb.GeoDB.AddGeometryColumn"
CREATE ALIAS CreateSpatialIndex for "geodb.GeoDB.CreateSpatialIndex"
CREATE ALIAS DropGeometryColumn for "geodb.GeoDB.DropGeometryColumn"
CREATE ALIAS DropGeometryColumns for "geodb.GeoDB.DropGeometryColumns"
CREATE ALIAS DropSpatialIndex for "geodb.GeoDB.DropSpatialIndex"
CREATE ALIAS EnvelopeAsText for "geodb.GeoDB.EnvelopeAsText"
CREATE ALIAS GeometryType for "geodb.GeoDB.GeometryType"
CREATE ALIAS ST_Area FOR "geodb.GeoDB.ST_Area"
CREATE ALIAS ST_AsEWKB FOR "geodb.GeoDB.ST_AsEWKB"
CREATE ALIAS ST_AsEWKT FOR "geodb.GeoDB.ST_AsEWKT"
CREATE ALIAS ST_AsHexEWKB FOR "geodb.GeoDB.ST_AsHexEWKB"
CREATE ALIAS ST_AsText FOR "geodb.GeoDB.ST_AsText"
CREATE ALIAS ST_BBOX FOR "geodb.GeoDB.ST_BBox"
CREATE ALIAS ST_Buffer FOR "geodb.GeoDB.ST_Buffer"
CREATE ALIAS ST_Centroid FOR "geodb.GeoDB.ST_Centroid"
CREATE ALIAS ST_Crosses FOR "geodb.GeoDB.ST_Crosses"
CREATE ALIAS ST_Contains FOR "geodb.GeoDB.ST_Contains"
CREATE ALIAS ST_DWithin FOR "geodb.GeoDB.ST_DWithin"
CREATE ALIAS ST_Disjoint FOR "geodb.GeoDB.ST_Disjoint"
CREATE ALIAS ST_Distance FOR "geodb.GeoDB.ST_Distance"
CREATE ALIAS ST_Envelope FOR "geodb.GeoDB.ST_Envelope"
CREATE ALIAS ST_Equals FOR "geodb.GeoDB.ST_Equals"
CREATE ALIAS ST_GeoHash FOR "geodb.GeoDB.ST_GeoHash"
CREATE ALIAS ST_GeomFromEWKB FOR "geodb.GeoDB.ST_GeomFromEWKB"
CREATE ALIAS ST_GeomFromEWKT FOR "geodb.GeoDB.ST_GeomFromEWKT"
CREATE ALIAS ST_GeomFromText FOR "geodb.GeoDB.ST_GeomFromText"
CREATE ALIAS ST_GeomFromWKB FOR "geodb.GeoDB.ST_GeomFromWKB"
CREATE ALIAS ST_Intersects FOR "geodb.GeoDB.ST_Intersects"
CREATE ALIAS ST_IsEmpty FOR "geodb.GeoDB.ST_IsEmpty"
CREATE ALIAS ST_IsSimple FOR "geodb.GeoDB.ST_IsSimple"
CREATE ALIAS ST_IsValid FOR "geodb.GeoDB.ST_IsValid"
CREATE ALIAS ST_MakePoint FOR "geodb.GeoDB.ST_MakePoint"
CREATE ALIAS ST_MakeBox2D FOR "geodb.GeoDB.ST_MakeBox2D"
CREATE ALIAS ST_Overlaps FOR "geodb.GeoDB.ST_Overlaps"
CREATE ALIAS ST_SRID FOR "geodb.GeoDB.ST_SRID"
CREATE ALIAS ST_SetSRID FOR "geodb.GeoDB.ST_SetSRID"
CREATE ALIAS ST_Simplify FOR "geodb.GeoDB.ST_Simplify"
CREATE ALIAS ST_Touches FOR "geodb.GeoDB.ST_Touches"
CREATE ALIAS ST_Within FOR "geodb.GeoDB.ST_Within"
CREATE ALIAS Version FOR "geodb.GeoDB.Version"
CREATE ALIAS InitGeoDB for "geodb.GeoDB.InitGeoDB"
CALL InitGeoDB()