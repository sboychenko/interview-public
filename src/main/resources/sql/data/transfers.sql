create table TRANSFERS(
    ID              int          primary key,
    SOURCE_ID       int          not null,
    TARGET_ID       int          not null,
    AMOUNT          float        not null,
    TRANSFER_TIME   date         not null,
    constraint SOURCE_CONSTRAINT foreign key (SOURCE_ID) references ACCOUNTS (ID),
    constraint TARGET_CONSTRAINT foreign key (TARGET_ID) references ACCOUNTS (ID)
);