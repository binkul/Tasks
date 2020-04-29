drop function if exists BestsellerLevel;

DELIMITER $$

create function BestsellerLevel(booksRent int, days int) returns boolean
begin
	declare result boolean default false;
    declare booksPerMonth decimal(5,2);

    if booksRent >= 2 then
		set booksPerMonth = (booksRent / days) * 30;
        if booksPerMonth >= 2 then
			set result = true;
		end if;
	end if;
    return result;
end $$

DELIMITER ;