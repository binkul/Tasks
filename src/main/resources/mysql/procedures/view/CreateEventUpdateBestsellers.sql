use kodilla_course;

DELIMITER $$
create event update_bestsellers
on schedule every 1 minute
do 
begin
	declare count int;
    
	call UpdateBestsellers();
    set count = (select best_count from bestsellers_count);
	insert into stat(stat, value) value('BESTSELLERS', count);
    commit;
end $$

DELIMITER ;