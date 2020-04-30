DELIMITER $$

create trigger readers_update after update on readers
for each row
begin
	insert into readers_aud(event_date, event_type, reader_id, old_firstname, new_firstname, 
    old_lastname, new_lastname, old_peselid, new_peselid, old_vip_level, new_vip_level)
    values(curtime(), 'UPDATE', old.reader_id, old.firstname, new.firstname,
    old.lastname, new.lastname, old.peselid, new.peselid, old.vip_level, new.vip_level);
end $$

DELIMITER ;