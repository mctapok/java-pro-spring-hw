create table accounts
(
    id             bigserial primary key,
    client_id      bigint,
    account_number varchar(16),
    balance        numeric(6, 2),
    created_at     timestamp,
    updated_at     timestamp
);

create table clients
(
    id  bigserial primary key,
    fio varchar(255)
);

create table transfers
(
    id bigserial primary key,
    client_id bigserial not null references accounts(id) on delete cascade,
    source_account_number varchar(255),
    target_client_id bigserial,
    target_account_number varchar(255),
    transfer_status varchar(255),
    amount numeric(6, 2),
    constraint fk_account foreign key (client_id) references accounts(id),
    transfer_date timestamp,
    updated_at timestamp
);

insert into clients (fio)
values ('A A A'), ('B B B');

insert into accounts (client_id, account_number, balance)
values (1, '1234123412341234', 1000),(2, '4321432143214321', 500);