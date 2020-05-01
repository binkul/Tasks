use kodilla_course;

create view bestsellers_count as
select count(*) as best_count from books
where bestseller = true