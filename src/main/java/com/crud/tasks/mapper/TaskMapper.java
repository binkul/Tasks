package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskMapper {
    public Task mapToTask(final TaskDto taskDto) {
        return taskDto != null ? new Task(taskDto.getId(), taskDto.getTitle(), taskDto.getContent()) : null;
    }

    public TaskDto mapToTaskDto(final Task task) {
        return task != null ? new TaskDto(task.getId(), task.getTitle(), task.getContent()) : null;
    }

    public List<TaskDto> mapToTaskListDto(List<Task> taskList) {
        if (taskList != null) {
            return taskList.stream()
                    .map(t -> new TaskDto(t.getId(), t.getTitle(), t.getContent()))
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }
}
