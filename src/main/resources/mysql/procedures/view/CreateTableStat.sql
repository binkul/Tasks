use kodilla_course;

create table stat
(
	stat_id int not null auto_increment,
    stat_date datetime not null default current_timestamp,
    stat varchar(20) not null,
    value int not null,
    
    primary key (stat_id)
)