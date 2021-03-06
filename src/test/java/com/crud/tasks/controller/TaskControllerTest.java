package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService taskService;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    public void shouldGetTaskList() throws Exception {
        // Given
        Task task = new Task(1L, "Task", "description");
        TaskDto taskDto = new TaskDto(1L, "Task", "description");
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        List<TaskDto> taskDtos = new ArrayList<>();
        taskDtos.add(taskDto);
        when(taskService.getAllTasks()).thenReturn(tasks);
        when(taskMapper.mapToTaskListDto(anyList())).thenReturn(taskDtos);

        // When & Then
        mockMvc.perform(get("/v1/tasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Task")))
                .andExpect(jsonPath("$[0].content", is("description")));
    }

    @Test
    public void shouldGetOneTask() throws Exception {
        // Given
        Task task = new Task(1L, "Task", "description");
        TaskDto taskDto = new TaskDto(1L, "Task", "description");
        when(taskService.getTaskById(1L)).thenReturn(task);
        when(taskMapper.mapToTaskDto(ArgumentMatchers.any(Task.class))).thenReturn(taskDto);

        // When & Then
        mockMvc.perform(get("/v1/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("taskId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Task")))
                .andExpect(jsonPath("$.content", is("description")));
    }

    @Test
    public void shouldGetEmptyTask() throws Exception {
        // Given
        when(taskService.getTaskById(1L)).thenThrow(new EntityNotFoundException("Task 1 not found!"));

        // When & Then
        mockMvc.perform(get("/v1/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("taskId", "1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Task 1 not found!"));
    }

    @Test
    public void shouldDeleteTask() throws Exception {
        // Given
        Task task = new Task(1L, "Task", "description");
        doNothing().when(taskService).deleteTask(1L);

        // When & Then
        mockMvc.perform(delete("/v1/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("taskId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void shouldDeleteTaskThrowException() throws Exception {
        // Given
        doThrow(new EntityNotFoundException("Task 100 not found!")).when(taskService).deleteTask(100L);

        // When & Then
        mockMvc.perform(delete("/v1/tasks/100")
                .contentType(MediaType.APPLICATION_JSON)
                .param("taskId", "100"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Task 100 not found!"));
    }

    @Test
    public void shouldCreateOneTask() throws Exception {
        // Given
        Task task = new Task(1L, "Task", "description");
        TaskDto taskDto = new TaskDto(1L, "Task", "description");
        when(taskMapper.mapToTask(ArgumentMatchers.any(TaskDto.class))).thenReturn(task);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        // When & Then
        mockMvc.perform(post("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void shouldUpdateTask() throws Exception {
        // Given
        TaskDto taskDto = new TaskDto(1L, "New task", "new description");
        Task task = new Task(1L, "New task", "new description");

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        when(taskMapper.mapToTask(ArgumentMatchers.any(TaskDto.class))).thenReturn(task);
        when(taskService.updateTask(ArgumentMatchers.any(Task.class))).thenReturn(task);
        when(taskMapper.mapToTaskDto(ArgumentMatchers.any(Task.class))).thenReturn(taskDto);

        // When & Then
        mockMvc.perform(put("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("New task")))
                .andExpect(jsonPath("$.content", is("new description")));
    }

    @Test
    public void shouldUpdateTaskThrowException() throws Exception {
        // Given
        TaskDto taskDto = new TaskDto(1L, "New task", "new description");
        Task task = new Task(1L, "New task", "new description");

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        when(taskMapper.mapToTask(ArgumentMatchers.any(TaskDto.class))).thenReturn(task);
        when(taskService.updateTask(ArgumentMatchers.any(Task.class))).thenThrow(new EntityNotFoundException("Task 1 not found!"));

        // When & Then
        mockMvc.perform(put("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Task 1 not found!"));
    }
}
