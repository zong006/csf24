-- TODO Task 3

drop table if exists order_details;
drop table if exists orders;

create table orders (
    order_id varchar(26) not null primary key,
    name varchar(64) not null,
    address varchar(64) not null,
    priority boolean,
    order_date date not null,
    comments varchar(256)
);

create table order_details (
    id int not null auto_increment primary key,
    order_id varchar(26) not null,
    product_id varchar(24) not null,
    name varchar(100) not null,
    quantity int not null,
    price decimal(15,2) not null,
    constraint fk_id foreign key (order_id) references orders(order_id) on delete cascade
);

