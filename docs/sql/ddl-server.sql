create sequence game_seq start with 1 increment by 50;
create sequence move_seq start with 1 increment by 50;
create sequence plot_seq start with 1 increment by 50;
create sequence user_profile_seq start with 1 increment by 50;
create table game
(
    firemans_turn boolean                     not null,
    score         integer                     not null,
    wind          tinyint                     not null check (wind between 0 and 3),
    arsonist_id   bigint,
    finished      timestamp(6) with time zone,
    fireman_id    bigint,
    game_id       bigint                      not null,
    started       timestamp(6) with time zone not null,
    external_key  UUID                        not null unique,
    primary key (game_id)
);
create table move
(
    column_number integer,
    move_number   integer not null,
    row_number    integer,
    game_id       bigint  not null,
    move_id       bigint  not null,
    user_id       bigint  not null,
    primary key (move_id)
);
create table plot
(
    column_number integer not null,
    plot_state    tinyint not null check (plot_state between 0 and 5),
    row_number    integer not null,
    game_id       bigint  not null,
    plot_id       bigint  not null,
    primary key (plot_id)
);
create table user_profile
(
    created         timestamp(6) with time zone not null,
    user_profile_id bigint                      not null,
    external_key    UUID                        not null unique,
    oauth_key       varchar(30)                 not null unique,
    display_name    varchar(50)                 not null unique,
    primary key (user_profile_id)
);
create index IDX177s1ru1b8wjmya62x2x8rqtw on game (score, finished);
alter table if exists game
    add constraint FKgovo5xp9etgobjyeqfyyijb9d foreign key (arsonist_id) references user_profile;
alter table if exists game
    add constraint FKmxlbfjmutin4euyji3w4bulpv foreign key (fireman_id) references user_profile;
alter table if exists move
    add constraint FK6khbbqe4c9jmywgvi9nehpkd1 foreign key (game_id) references game;
alter table if exists move
    add constraint FK6l018k7a4glxhtwoxnwfml77u foreign key (user_id) references user_profile;
alter table if exists plot
    add constraint FK90lwgowegmcek3ysfw3k1pl62 foreign key (game_id) references game;
