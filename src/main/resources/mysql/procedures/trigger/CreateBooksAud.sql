create table books_aud
(
	event_id int not null auto_increment,
    event_date datetime not null,
    event_type varchar(10) default null,
    book_id int not null,
    old_title varchar(255),
    new_title varchar(255),
    old_pubyear int,
    new_pubyear int,
    old_bestseller boolean,
    new_bestseller boolean,
    
    primary key (event_id)
)