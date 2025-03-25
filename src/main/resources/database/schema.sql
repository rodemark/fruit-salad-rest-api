create table salad (
    ID serial primary key,
    name varchar(255) not null unique,
    description varchar(255),
    total_weight double precision,
    total_calories double precision,
    total_proteins double precision,
    total_fats double precision,
    total_carbohydrates double precision,
    total_sugar double precision
);

create table ingredient (
    ID serial primary key,
    fruit_name varchar(255),
    weight double precision,
    salad_id bigint,
    constraint fk_salad foreign key (salad_id) references salad(id)
);