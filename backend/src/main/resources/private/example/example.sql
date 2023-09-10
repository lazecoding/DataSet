create table attach
(
    iid      int auto_increment not null,
    name     varchar(255)                              not null,
    filename varchar(255),
    trashed  smallint    default 0                     not null,
    fileinfo varchar(255),
    typeid   int         default 0                     not null,
    userid   varchar(10) default '00000'               not null,
    filesize varchar(10)                               not null,
    filetype char(5)                                   not null,
    ctime    char(20)    default '0000-00-00 00:00:00' not null,
    webId    int         default 1                     not null,
    uuid     varchar(60),
    primary key (iid)
)engine=innodb default charset=utf8;