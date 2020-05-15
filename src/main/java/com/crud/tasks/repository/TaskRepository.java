package com.crud.tasks.repository;

import com.crud.tasks.domain.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends CrudRepository<Task, Long> {
    @Override
    List<Task> findAll();

    @Override
    Task save(Task task);

    @Override
    void deleteById(Long id);

    @Override
    long count();

    @Query(value = "SELECT * FROM tasks order by id desc limit 1", nativeQuery = true)
    Optional<Task> getLastTask();
}
