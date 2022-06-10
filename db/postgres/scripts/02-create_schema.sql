-- connect as rd_user in rd_app database
\c rd_app rd_user;

-- create 'entry' table
create table ENTRY (
   SID int4 not null,
    ACTION int4,
    APPL_DATE date,
    ATTRIBUTES varchar(255),
    DESCRIPTION varchar(255),
    ENTITY varchar(255),
    KEY varchar(255),
    KEY_HASH varchar(255),
    KEY_TYPE varchar(255),
    MODIF_DATE timestamp,
    primary key (SID)
)

create sequence ENTRY_SEQUENCE start 1 increment 10