create table ACCOUNTS (
    ID          int             primary key,
    FIRST_NAME  varchar(100)    not null,
    LAST_NAME   varchar(100)    not null,
    BALANCE     float           not null default 0
);