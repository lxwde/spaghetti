DROP TABLE IF EXISTS item;
CREATE TABLE item (
  id int(11) NOT NULL AUTO_INCREMENT,
  checked bit,
  description varchar(255),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS post;
DROP TABLE IF EXISTS blog;

CREATE TABLE blog (
  id int(10) unsigned NOT NULL auto_increment,
  name varchar(45) NOT NULL,
  created_on datetime NOT NULL,
  PRIMARY KEY  (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE post (
  id int(10) unsigned NOT NULL auto_increment,
  title varchar(45) NOT NULL,
  content varchar(1024) NOT NULL,
  created_on varchar(45) NOT NULL,
  blog_id int(10) unsigned NOT NULL,
  PRIMARY KEY  (id),
  FOREIGN KEY (blog_id) REFERENCES blog (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE user (
  id int(10) unsigned NOT NULL auto_increment,
  email varchar(45) NOT NULL,
  password varchar(45) NOT NULL,
  first_name varchar(45) NOT NULL,
  last_name varchar(45) default NULL,
  blog_id int(10) unsigned default NULL,
  PRIMARY KEY  (id),
  UNIQUE KEY Index_2_email_uniq (email),
  CONSTRAINT FK_user_blog FOREIGN KEY (blog_id) REFERENCES blog (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




