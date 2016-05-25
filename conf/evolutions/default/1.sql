# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

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


# --- !Downs

drop table if exists t_card;
drop sequence if exists t_card_seq;

