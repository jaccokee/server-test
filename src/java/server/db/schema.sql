create table movie (
 id long primary key,
 name varChar(64) not null,
 genre varChar(64) not null,
 year_released integer,
 rating varChar(10)
);
