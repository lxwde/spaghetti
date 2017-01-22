DROP TABLE IF EXISTS item;

CREATE TABLE item (
  id int(11) NOT NULL AUTO_INCREMENT,
  checked bit,
  description varchar(255),
  PRIMARY KEY (id)
)