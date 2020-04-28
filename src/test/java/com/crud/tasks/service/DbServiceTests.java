package com.crud.tasks.service;

import com.crud.tasks.controller.EntityNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DbServiceTests {

    @InjectMocks
    private DbService service;

    @Mock
    private TaskRepository repository;

    @Test
    public void shouldSaveTaskTest() {
        // Given
        Task task = new Task(1L, "task", "none");

        // When
        when(repository.save(task)).thenReturn(task);
        Task savedTask = service.saveTask(task);

        // Then
        assertEquals(1, (long) savedTask.getId());
        assertEquals("task", savedTask.getTitle());
        assertEquals("none", savedTask.getContent());
    }



    @Test(expected = EntityNotFoundException.class)
    public void shouldGetEmptyTaskTest() throws EntityNotFoundException {
        // Given

        // When & Then
        when(repository.findById(1L)).thenReturn(Optional.empty());
        Task returnedTask = service.getTaskById(1L);
    }



    @Test
    public void shouldGetTaskTest() throws EntityNotFoundException {
        // Given
        Task task = new Task(1L, "task", "none");

        // When
        when(repository.findById(1L)).thenReturn(Optional.of(task));
        Task returnedTask = service.getTaskById(1L);

        // Then
        assertEquals(1, (long) returnedTask.getId());
        assertEquals("task", returnedTask.getTitle());
        assertEquals("none", returnedTask.getContent());
    }

    @Test
    public void shouldGetTaskListTest() {
        // Given
        Task taskA = new Task(1L, "taskA", "none");
        Task taskB = new Task(2L, "taskB", "none");
        Task taskC = new Task(3L, "taskC", "none");
        List<Task> tasks = Arrays.asList(taskA, taskB, taskC);

        // When
        when(repository.findAll()).thenReturn(tasks);
        List<Task> returnedTasks = service.getAllTasks();

        // Then
        assertEquals(3, returnedTasks.size());
        assertEquals("taskB", returnedTasks.get(1).getTitle());
    }

    @Test
    public void shouldGetEmptyTaskList() {
        // Given
        List<Task> tasks = new ArrayList<>();

        // When
        when(repository.findAll()).thenReturn(tasks);
        List<Task> returnedTasks = service.getAllTasks();

        // Then
        assertEquals(0, returnedTasks.size());
    }
}
