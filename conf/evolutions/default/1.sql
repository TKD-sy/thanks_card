# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table t_bumon (
  bumon_id                      integer not null,
  bumon_name                    varchar(255),
  bumon_buname                  varchar(255),
  bumon_situname                varchar(255),
  bumon_kaname                  varchar(255),
  bumon_group                   varchar(255),
  constraint pk_t_bumon primary key (bumon_id)
);
create sequence t_bumon_seq;

create table t_card (
  card_id                       integer not null,
  category_id                   integer,
  sousin_id                     integer,
  jyusin_id                     integer,
  hensin_id                     integer,
  card_kidokuflag               integer,
  card_flag                     integer,
  card_hensinflag               integer,
  card_help                     varchar(255),
  card_comment                  varchar(255),
  card_date                     timestamp,
  constraint pk_t_card primary key (card_id)
);
create sequence t_card_seq;

create table t_category (
  category_id                   integer not null,
  category_name                 varchar(255),
  constraint pk_t_category primary key (category_id)
);
create sequence t_category_seq;

create table t_syain (
  syain_id                      integer not null,
  bumon_id                      integer,
  yakusyoku_id                  integer,
  syain_name                    varchar(255),
  syain_kana                    varchar(255),
  syain_birth                   timestamp,
  syain_nyuusyabi               timestamp,
  syain_pass                    varchar(255),
  syain_sex                     varchar(255),
  constraint pk_t_syain primary key (syain_id)
);
create sequence t_syain_seq;


# --- !Downs

drop table if exists t_bumon;
drop sequence if exists t_bumon_seq;

drop table if exists t_card;
drop sequence if exists t_card_seq;

drop table if exists t_category;
drop sequence if exists t_category_seq;

drop table if exists t_syain;
drop sequence if exists t_syain_seq;

