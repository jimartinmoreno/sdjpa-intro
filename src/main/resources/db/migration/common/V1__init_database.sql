drop table if exists book;
drop table if exists author;
drop table if exists hibernate_sequence;

create table book
(
    id        bigint not null,
    isbn      varchar(255),
    publisher varchar(255),
    title     varchar(255),
    author_id bigint,
    primary key (id)
);

create table author
(
    id         bigint not null,
    first_name varchar(255),
    last_name  varchar(255),
    primary key (id)
);

create table hibernate_sequence
(
    next_val bigint
);

insert into hibernate_sequence
values (1);