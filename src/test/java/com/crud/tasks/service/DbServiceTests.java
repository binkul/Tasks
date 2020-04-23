package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.mapper.TrelloMapperTests;
import com.crud.tasks.repository.TaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DbServiceTests {
    private final static Logger logger = LoggerFactory.getLogger(TrelloMapperTests.class);

    @InjectMocks
    private DbService service;

    @Mock
    private TaskRepository repository;

    @Test
    public void shouldSaveTaskTest() {
        // Given
        Task task = new Task(1L, "task", "none");

        // When
        logger.info("Test shouldSaveTaskTest start ...");
        when(repository.save(task)).thenReturn(task);
        Task savedTask = service.saveTask(task);

        // Then
        assertEquals(1, (long) task.getId());
        assertEquals("task", task.getTitle());
        assertEquals("none", task.getContent());
    }

    @Test
    public void shouldGetEmptyTaskTest() {
        // Given

        // When
        logger.info("Test shouldGetEmptyTaskTest start ...");
        when(repository.findById(1L)).thenReturn(Optional.empty());
        Optional<Task> returnedTask = service.getTaskById(1L);

        // Then
        assertFalse(returnedTask.isPresent());
    }

    @Test
    public void shouldGetTaskTest() {
        // Given
        Task task = new Task(1L, "task", "none");

        // When
        logger.info("Test shouldGetTaskTest start ...");
        when(repository.findById(1L)).thenReturn(Optional.of(task));
        Optional<Task> returnedTask = service.getTaskById(1L);

        // Then
        assertEquals(1, (long) returnedTask.get().getId());
        assertEquals("task", returnedTask.get().getTitle());
        assertEquals("none", returnedTask.get().getContent());
    }

    @Test
    public void shouldGetTaskListTest() {
        // Given
        Task taskA = new Task(1L, "taskA", "none");
        Task taskB = new Task(2L, "taskB", "none");
        Task taskC = new Task(3L, "taskC", "none");
        List<Task> tasks = Arrays.asList(taskA, taskB, taskC);

        // When
        logger.info("Test shouldGetTaskListTest start ...");
        when(repository.findAll()).thenReturn(tasks);
        List<Task> returnedTasks = service.getAllTasks();

        // Then
        assertEquals(3, returnedTasks.size());
        assertEquals("taskB", returnedTasks.get(1).getTitle());
    }
}
