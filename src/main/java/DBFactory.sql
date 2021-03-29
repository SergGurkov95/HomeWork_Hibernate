create schema IF NOT EXISTS `shop_db`;


use shop_db;


create table IF NOT EXISTS `categories`(
`category_id` int not null auto_increment,
`category_name` varchar(50) not null,
primary key(`category_id`),
unique index `category_name_UNIQUE` (`category_name` asc) visible);


create table IF NOT EXISTS `products`(
`product_id` int not null auto_increment,
`product_name` varchar(50) not null,
`category` int not null,
`price` decimal(10,2) not null,
primary key (`product_id`),
constraint `fk_category_id`
foreign key (`category`)
references categories(`category_id`)
on delete no action
on update no action);



create table IF NOT EXISTS `users`(
`user_id` int not null auto_increment,
`first_name` varchar(50) not null,
`last_name` varchar(50) not null,
`address` varchar(50) not null,
primary key (`user_id`))
ENGINE=InnoDB;

insert into users (user_id, first_name, last_name, address)
select * from (select '1', 'user', 'was', 'deleted') as tmp
where not exists (select user_id from users where user_id = '1');


create table IF NOT EXISTS `order_status`(
`status_id` int not null,
`status_name` varchar(50) not null,
primary key(`status_id`))
ENGINE=InnoDB;


insert into order_status (status_id, status_name)
select * from (select '1', 'open') as tmp
where not exists (select status_id from order_status where status_id = '1');


insert into order_status (status_id, status_name)
select * from (select '2', 'in progress') as tmp
where not exists (select status_id from order_status where status_id = '2');


insert into order_status (status_id, status_name)
select * from (select '3', 'completed') as tmp
where not exists (select status_id from order_status where status_id = '3');


insert into order_status (status_id, status_name)
select * from (select '4', 'canceled') as tmp
where not exists (select status_id from order_status where status_id = '4');


create table IF NOT EXISTS `orders`(
`order_id` int not null auto_increment,
`user_id` int not null,
`date` timestamp not null,
`status` int not null default '1',
primary key (`order_id`),
constraint `fk_user_id`
foreign key (`user_id`)
references users(user_id)
on delete no action
on update no action,
constraint `fk_status_id`
foreign key (`status`)
references order_status(status_id)
on delete no action
on update no action);


create table IF NOT EXISTS `orders_map`(
`order_id` int not null,
`product_id` int not null,
`quantity` int not null,
primary key (`order_id`, `product_id`),
constraint `fk_order_id`
foreign key (`order_id`)
references orders(order_id)
on delete no action
on update no action,
constraint `fk_product_id`
foreign key (`product_id`)
references products(product_id)
on delete no action
on update no action);

