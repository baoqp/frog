create table user (
	id int(11) NOT NULL AUTO_INCREMENT,
	name varchar(64) NOT NULL,
	age int(4) NULL default 0,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;