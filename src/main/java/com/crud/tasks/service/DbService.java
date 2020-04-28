package com.crud.tasks.service;

import com.crud.tasks.controller.EntityNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DbService {
    @Autowired
    TaskRepository repository;

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public Task getTaskById(Long id) throws EntityNotFoundException {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Task " + id + " not found!"));
    }

    public Task saveTask(Task task) {
        return repository.save(task);
    }

    public Task updateTask(Task task) throws EntityNotFoundException {
        getTaskById(task.getId());
        return saveTask(task);
    }

    public void deleteTask(Long id) throws EntityNotFoundException {
        getTaskById(id);
        repository.deleteById(id);
    }
}
