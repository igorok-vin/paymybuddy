CREATE DATABASE IF NOT EXISTS dbpaymybuddy;

CREATE TABLE IF NOT EXISTS user (
	email varchar(30) not null primary key,
    first_name varchar(25) not null,
    last_name varchar(25) not null,
    password varchar(100) not null,
    balance decimal (8, 2),
    role varchar(30));

    CREATE TABLE IF NOT EXISTS contact (
	user_email varchar(30) not null,
    contact_email varchar(30)not null,
    primary key (user_email, contact_email));
    
	CREATE TABLE IF NOT EXISTS refill_balance(
	refill_id tinyint primary key,
    refill_amount decimal(8,2),
    user_email varchar(30));
    
    CREATE TABLE IF NOT EXISTS transaction (
	transaction_id tinyint primary key,
    date TIMESTAMP,
    user_sender varchar(30),
    user_receiver varchar(30),
    amount decimal(8,2) not null,
    description varchar(500),
    fee decimal(8,2));
    
    
    drop table user;
    select * from  user_entity;
    delete from user_entity;
 insert into user (email, first_name, last_name, password, balance, role) values('ihor@gmail.com','Ihor', 'Nikolaienko','$2a$10$AsV.c7Hj8jzhgmbOKu8FkO11R3cP5VtjJAl8I7OQyZHgsi1fk9edi', 1400.00, 'USER');

select * from transaction;
select * from user_contact;
select * from refill_balance;
select * from contact;
select * from user;
select * from refill_balance;
 ALTER TABLE transaction RENAME COLUMN transactionId TO transaction_id;