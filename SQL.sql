create database osahaneat;
use osahaneat;

create table roles(
	id int auto_increment,
    role_name varchar(20),
    created_date timestamp,
    primary key(id)
);

create table users(
	id int auto_increment,
    username varchar(100),
    password varchar(100),
    fullname varchar(50),
    created_date timestamp,
    role_id int,
    primary key(id)
);

create table rating_food(
	id int auto_increment,
    user_id int,
    food_id int,
    content text,
    rate_point int(5),
    primary key(id)
);

create table category (
	id int auto_increment,
    name_cate varchar(100),
    created_date timestamp,
    primary key(id)
);

create table food(
	id int auto_increment,
    title varchar(255),
    image text,
    time_ship varchar(10),
    price decimal,
    cate_id int,
    primary key(id)
);

create table rating_restaurant(
	id int auto_increment,
    user_id int,
    res_id int,
    content text,
    rate_point int(5),
    primary key(id)
);

create table orders(
	id int auto_increment,
    user_id int,
    res_id int,
    created_date timestamp,
    primary key(id)
);

create table menu_restaurant(
	cate_id int,
    res_id int,
    created_date timestamp,
    
    primary key(cate_id, res_id)
);

create table restaurant(
	id int auto_increment,
    title varchar(255),
    subtitle varchar(255),
    description text,
    image text,
    is_freeship boolean,
    address varchar(255),
    open_date timestamp,
    primary key(id)
);

create table promo(
	id int auto_increment,
    res_id int,
    percent int,
    start_date timestamp,
    end_date timestamp,
    primary key(id)
);

create table order_item(
	order_id int,
    food_id int,
    created_date timestamp,
    
    primary key(order_id, food_id)
);

alter table users add constraint fk_users_role_id foreign key(role_id) references roles(id);
alter table rating_food add constraint fk_ratingfood_user_id foreign key(user_id) references users(id);
alter table rating_food add constraint fk_ratingfood_food_id foreign key(food_id) references food(id);
alter table food add constraint fk_food_cate_id foreign key(cate_id) references category(id);
alter table rating_restaurant add constraint fk_ratingrestaurant_user_id foreign key(user_id) references users(id);
alter table rating_restaurant add constraint fk_ratingrestaurant_res_id foreign key(res_id) references restaurant(id);
alter table orders add constraint fk_orders_user_id foreign key(user_id) references users(id);
alter table orders add constraint fk_orders_res_id foreign key(res_id) references restaurant(id);
alter table order_item add constraint fk_orderitem_order_id foreign key(order_id) references orders(id);
alter table order_item add constraint fk_orderitem_food_id foreign key(food_id) references food(id);
alter table menu_restaurant add constraint fk_menurestaurant_cate_id foreign key(cate_id) references category(id);
alter table menu_restaurant add constraint fk_menurestaurant_res_id foreign key(res_id) references restaurant(id);
alter table promo add constraint fk_promo_res_id foreign key(res_id) references restaurant(id);
alter table food add column is_freeship bool default false;

insert into roles(role_name) values ("ROLE_ADMIN"),("ROLE_USER");

insert into users(username,password,fullname,role_id) values ("nguyenvana@gmail.com","123456","Nguyen Van A",1),("nguyenvanb@gmail.com","123456","Nguyen Van B",2);

select * from users;