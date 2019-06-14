create table book
(
  id            int auto_increment
    primary key,
  title         varchar(30) not null,
  author        varchar(30) not null,
  yearOfPublish int         not null
);

create table user
(
  id          int auto_increment
    primary key,
  login       varchar(30)  not null,
  password    varchar(255) not null,
  name        varchar(30)  not null,
  surname     varchar(30)  not null,
  age         int          not null,
  pesel       bigint(11)   not null,
  phoneNumber bigint(11)   not null,
  role        varchar(30)  not null
);

create table orders
(
  id       int auto_increment
    primary key,
  user_id  int         not null,
  book_id  int         not null,
  dateFrom varchar(30) not null,
  dateTo   varchar(30) not null,
  constraint orders_ibfk_1
    foreign key (book_id) references book (id),
  constraint orders_ibfk_2
    foreign key (user_id) references user (id)
);

create index book_id
  on orders (book_id);

create index user_id
  on orders (user_id);

