create table readers_aud
(
	event_id int not null auto_increment,
    event_date datetime not null,
    event_type varchar(255) default null,
    reader_id int not null,
    old_firstname varchar(255),
    new_firstname varchar(255),
    old_lastname varchar(255),
    new_lastname varchar(255),
    old_peselid varchar(11),
    new_peselid varchar(11),
    old_vip_level varchar(20),
    new_vip_level varchar(20),
    
    primary key (event_id)
)