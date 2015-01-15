# shrinkerdb schema
 
# --- !Ups

CREATE TABLE LinkMap (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    originallink varchar(255) NOT NULL,
    shortlink varchar(255) NOT NULL,
    PRIMARY KEY (id)
);
 
# --- !Downs
 
DROP TABLE LinkMap;