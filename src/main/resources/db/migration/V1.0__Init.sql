create table users
(
    user_id serial primary key,
    user_name varchar(100) unique not null,
    user_password varchar(100) not null,
    user_first_name varchar(100) not null,
    user_middle_name varchar(100),
    user_last_name varchar(100) not null
);

create table messages
(
    message_id serial primary key,
    message varchar(100) not null,
    creation_date TIMESTAMP not null,
    user_id INTEGER,
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);