package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/task")
public class TaskController {
    @Autowired
    private DbService service;
    @Autowired
    private TaskMapper taskMapper;

    /**
     * can be @RequestMapping(method = RequestMethod.GET, value = "getTasks") or @GetMapping("getTasks")
     *  url --> http://localhost:8080/v1/task/getTasks
     * or @RequestMapping(method = RequestMethod.GET) or @GetMapping
     *  url --> http://localhost:8080/v1/task
     */
    @GetMapping("/getTasks")
    List<TaskDto> getTasks() {
        return taskMapper.mapToTaskListDto(service.getAllTasks());
    }

    /**
     * FOR @RequestParam:
     * @RequestMapping(method = RequestMethod.GET, value = "getTask")
     * TaskDto getTask(@RequestParam("taskId") Long taskId) --> url: http://localhost:8080/v1/task/getTask?taskId=1
     * in url you can use http://localhost:8080/v1/task/getTask?taskId=1+2 it gives ...=12
     *
     * FOR @PathVariable:
     * @RequestMapping(method = RequestMethod.GET, value = "getTask/{taskId}")
     * TaskDto getTask(@PathVariable("taskId") Long taskId) --> url: http://localhost:8080/v1/task/getTask/1
     *
     * NEW in java 8
     * @GetMapping("getTask")
     * TaskDto getTask(@RequestParam("taskId") Long taskId) --> url: http://localhost:8080/v1/task/getTask?taskId=1
     *
     * @GetMapping("getTask/{taskId}")
     * TaskDto getTask(@PathVariable("taskId") Long taskId) --> url: http://localhost:8080/v1/task/getTask/1
     */
    @GetMapping("/getTask/{taskId}")
    TaskDto getTask(@PathVariable Long taskId) throws EntityNotFoundException {
        return taskMapper.mapToTaskDto(service.getTaskById(taskId).orElseThrow(() -> new EntityNotFoundException("Task " + taskId + " not found!")));
    }

    /**
     *  the best is @DeleteMapping("deleteTask/{taskId}") --> void deleteTask(@PathVariable Long taskId) because
     *  url --> http://localhost:8080/v1/task/deleteTask/1 hide the name of the variable taskId - this url identifies the resource
     *  without the name of the variable
     *  can be @RequestMapping(method = RequestMethod.DELETE, value = "deleteTask") or @GetMapping("deleteTask")
     *  deleteTask(@RequestParam("taskId") Long taskId)
     *  url --> http://localhost:8080/v1/task/deleteTask?taskId=1 - this is acceptable, but not professional
     */
    @DeleteMapping("deleteTask/{taskId}")
    void deleteTask(@PathVariable Long taskId) {
        service.deleteTask(taskId);
    }

    /**
     * can be @RequestMapping(method = RequestMethod.PUT, value = "updateTask") or @PutMapping("updateTask")
     * in Postman you need to add Body in raw format:
     * {
     *     "id": "3",
     *     "title": "eq. tmp"
     *     "content": "eg. done"
     * }
     * and set Headers (next left) to key:Content-type | value:application/json;charset=utf-8
     * url should be --> http://localhost:8080/v1/task/updateTask
     */
    @PutMapping(value = "/updateTask", consumes = MediaType.APPLICATION_JSON_VALUE)
    TaskDto updateTask(@RequestBody TaskDto taskDto) {
        return taskMapper.mapToTaskDto(service.saveTask(taskMapper.mapToTask(taskDto)));
    }

    /**
     * can be @RequestMapping(method = RequestMethod.POST, value = "createTask") or @PostMapping("createTask")
     * in Postman you need to add Body in raw format:
     * {
     *     "id": "3",
     *     "title": "eq. tmp"
     *     "content": "eg. done"
     * }
     * and set Headers (next left) to key:Content-type | value:application/json;charset=utf-8
     * url should be --> http://localhost:8080/v1/task/createTask
     */
    @PostMapping(value = "/createTask", consumes = MediaType.APPLICATION_JSON_VALUE)
    void createTask(@RequestBody TaskDto taskDto) {
        service.saveTask(taskMapper.mapToTask(taskDto));
    }
}