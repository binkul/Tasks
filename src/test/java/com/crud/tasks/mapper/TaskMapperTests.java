package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TaskMapperTests {
    private static final Logger logger = LoggerFactory.getLogger(TaskMapperTests.class);

    @InjectMocks
    TaskMapper taskMapper;

    @Test
    public void mapToTaskTest() {
        // Given
        String title = "new task";
        String content = "none";
        TaskDto taskDto = new TaskDto(1L, title, content);

        // When
        logger.info("Test mapToTaskTest start ...");
        Task task = taskMapper.mapToTask(taskDto);
        Long id = task.getId();

        // Then
        assertEquals(id, task.getId());
        assertEquals(title, task.getTitle());
        assertEquals(content, task.getContent());
    }

    @Test
    public void mapToTaskDtoTest() {
        // Given
        String title = "new task";
        String content = "none";
        Task task = new Task(1L, title, content);

        // When
        logger.info("Test mapToTaskDtoTest start ...");
        TaskDto taskDto = taskMapper.mapToTaskDto(task);
        Long id = taskDto.getId();

        // Then
        assertEquals(id, taskDto.getId());
        assertEquals(title, taskDto.getTitle());
        assertEquals(content, taskDto.getContent());
    }

    @Test
    public void mapToTaskListDtoTest() {
        // Given
        List<Task> tasks = IntStream.rangeClosed(1, 20)
                .mapToObj(i -> new Task((long) i, "title" + i, "none"))
                .collect(Collectors.toList());

        // When
        logger.info("Test mapToTaskListDtoTest start ...");
        List<TaskDto> taskDtos = taskMapper.mapToTaskListDto(tasks);

        // Then
        assertEquals(20, taskDtos.size());
        assertEquals(10, (long) taskDtos.get(9).getId());
        assertEquals("title15", taskDtos.get(14).getTitle());
    }
}
