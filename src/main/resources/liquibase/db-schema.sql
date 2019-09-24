create sequence hibernate_sequence start with 1 increment by 1;

create table framework_version (
  id bigint not null,
  release_date date not null,
  version varchar(100) not null,
  primary key (id)
);

create table hype_level (
  id bigint not null,
  name varchar(50) not null,
  score integer not null,
  primary key (id)
);

create table java_script_framework (
  id bigint not null,
  deprecated_date date,
  name varchar(30) not null,
  hype_level_id bigint,
  primary key (id)
);

create table java_script_framework_versions (
  java_script_framework_id bigint not null,
  versions_id bigint not null
);

alter table java_script_framework_versions add constraint UK_qpem9dfolg0nai5ngdol15kfo unique (versions_id);
alter table java_script_framework add constraint FKk2195urxx98wpexdybam4ebe2 foreign key (hype_level_id) references hype_level;
alter table java_script_framework_versions add constraint FKbkkafkv77euhydsyc9t6wef2i foreign key (versions_id) references framework_version;
alter table java_script_framework_versions add constraint FKna4wtepolf4m8plklkuqrggw foreign key (java_script_framework_id) references java_script_framework;
