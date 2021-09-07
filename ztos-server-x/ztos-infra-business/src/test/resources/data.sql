DROP TABLE IF EXISTS dummy;
CREATE TABLE dummy (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  created_by VARCHAR(50) NOT NULL,
  created_date TIMESTAMP NOT NULL,
  location GEOMETRY,
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

