DELIMITER $$

create trigger books_insert after insert on books
for each row
begin
	insert into books_aud(event_date, event_type, book_id, new_title, new_pubyear, new_bestseller)
    values(curtime(), 'INSERT', new.book_id, new.title, new.pubyear, new.bestseller);
end $$

DELIMITER ;