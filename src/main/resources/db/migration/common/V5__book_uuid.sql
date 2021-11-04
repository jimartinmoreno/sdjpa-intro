create table book_uuid
(
    id        varbinary(16) not null,
    isbn      varchar(255),
    publisher varchar(255),
    title     varchar(255),
    author_id bigint,
    primary key (id)
);