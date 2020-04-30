drop procedure if exists UpdateBestsellers;

DELIMITER $$

create procedure UpdateBestsellers()
begin
	declare booksRent, days, bk_id int;
    declare finished int default 0;
    declare all_books cursor for select book_id from books;
    declare continue handler for not found set finished = 1;

	open all_books;
	while(finished = 0) do
		fetch all_books into bk_id;
        if(finished = 0) then
			select count(*) From rents where book_id = bk_id into booksRent;
			select datediff(max(rent_date), min(rent_date)) + 1 from rents where book_id = bk_id into days;
			update books set bestseller = BestsellerLevel(booksRent, days) where book_id = bk_id;
            commit;
		end if;
	end while;
	close all_books;
end $$

DELIMITER ;
