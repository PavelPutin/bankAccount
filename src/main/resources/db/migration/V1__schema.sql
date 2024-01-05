create table currency (
    id varchar(2) primary key
);

create table client (
    id uuid default gen_random_uuid() primary key,
    name varchar(256) unique not null,
    password varchar(256) not null,
    whenCreated timestamp not null
);

create table account (
    id uuid default gen_random_uuid() primary key,
    name varchar(256) not null,
    whenOpened timestamp not null,
    whenClosed timestamp,
    balance decimal not null check (balance >= 0),
    currency_id varchar(2) not null references currency (id),
    creator_id uuid not null references client (id)
);

create table journal_operation (
    id uuid default gen_random_uuid() primary key,
    whenCreated timestamp not null,
    balance decimal not null check (balance >= 0),
    currency_id varchar(2) not null references currency (id),
    type varchar(256) not null,
    client_id uuid not null references client (id),
    sender_id uuid references account (id),
    recipient_id uuid references account (id)
);