create table salad (
    ID serial primary key,
    name varchar(255) not null unique,
    description varchar(255),
    total_weight int,
    total_calories double precision,
    total_proteins double precision,
    total_fats double precision,
    total_carbohydrates double precision,
    total_sugar double precision
);