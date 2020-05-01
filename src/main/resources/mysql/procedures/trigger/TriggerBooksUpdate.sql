DELIMITER $$

create trigger books_update after update on books
for each row
begin
	insert into books_aud(event_date, event_type, book_id, old_title, new_title, 
    old_pubyear, new_pubyear, old_bestseller, new_bestseller)
    values(curtime(), 'UPDATE', old.book_id, old.title, new.title,
    old.pubyear, new.pubyear, old.bestseller, new.bestseller);

end $$

DELIMITER ;