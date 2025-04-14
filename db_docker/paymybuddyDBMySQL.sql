CREATE DATABASE IF NOT EXISTS dbpaymybuddy;
use dbpaymybuddy;
CREATE TABLE IF NOT EXISTS user (
	email varchar(30) not null primary key,
    first_name varchar(25) not null,
    last_name varchar(25) not null,
    password varchar(100) not null,
    balance decimal (8, 2),
    role varchar(30))
        ENGINE = innoDB;

    CREATE TABLE IF NOT EXISTS contact (
	user_email varchar(30) not null,
    contact_email varchar(30)not null,
    primary key (user_email, contact_email))
        ENGINE = innoDB;
    
	CREATE TABLE IF NOT EXISTS refill_balance(
	refill_id integer primary key NOT NULL AUTO_INCREMENT,
    refill_amount decimal(8,2),
    user_email varchar(30))
        ENGINE = innoDB;
    
    CREATE TABLE IF NOT EXISTS transaction (
	transaction_id integer primary key NOT NULL AUTO_INCREMENT,
    date TIMESTAMP,
    user_sender varchar(30),
    user_receiver varchar(30),
    amount decimal(8,2) not null,
    description varchar(500),
    fee decimal(8,2))
        ENGINE = innoDB;

 insert into user (email, first_name, last_name, password, balance, role) values
('tom@gmail.com','Tom','Jones','$2a$12$EG733c7dhQZMvKiWaZ8JKeNrTaiq3/5JySgQJz2BDiVXAw/vHVPOi',1400.37,'ROLE_USER'),
('igor@gmail.com','Igor','Nikolaienko','$2a$10$E7ICdxI02HSly4sm1kBVdewbWTh/OcJ12N0iGAhO/IRtt0yHSR1I.',1400.37,'ROLE_USER'),
('john@gmail.com','John','Loyd','$2a$10$E7ICdxI02HSly4sm1kBVdewbWTh/OcJ12N0iGAhO/IRtt0yHSR1I.',1250.25,'ROLE_USER'),
('max@gmail.com','Max','Madison','$2a$10$E7ICdxI02HSly4sm1kBVdewbWTh/OcJ12N0iGAhO/IRtt0yHSR1I.',3257.25,'ROLE_USER'),
('tom@gmail.com','Tom','Gibson','$2a$10$E7ICdxI02HSly4sm1kBVdewbWTh/OcJ12N0iGAhO/IRtt0yHSR1I.',6987.94,'ROLE_USER'),
('lily@gmail.com','Lily','Russell','$2a$10$E7ICdxI02HSly4sm1kBVdewbWTh/OcJ12N0iGAhO/IRtt0yHSR1I.',91648.27,'ROLE_USER'),
('stella@gmail.com','Stella','Miller','$2a$10$E7ICdxI02HSly4sm1kBVdewbWTh/OcJ12N0iGAhO/IRtt0yHSR1I.',5218.54,'ROLE_USER'),
('kate@gmail.com','Kate','Smith','$2a$10$E7ICdxI02HSly4sm1kBVdewbWTh/OcJ12N0iGAhO/IRtt0yHSR1I.',4218.76,'ROLE_USER'),
('allan@gmail.com','Allan','Beck','$2a$10$E7ICdxI02HSly4sm1kBVdewbWTh/OcJ12N0iGAhO/IRtt0yHSR1I.',5000.76,'ROLE_USER');

commit;