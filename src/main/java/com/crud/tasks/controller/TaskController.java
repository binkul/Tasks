package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/task")
public class TaskController {

    /**
     * can be @RequestMapping(method = RequestMethod.GET, value = "getTasks") or @GetMapping("getTasks")
     *  url --> http://localhost:8080/v1/task/getTasks
     * or @RequestMapping(method = RequestMethod.GET) or @GetMapping
     *  url --> http://localhost:8080/v1/task
     */
    @GetMapping("getTasks")
    List<TaskDto> getTasks() {
        return new ArrayList<>();
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
    @GetMapping("getTask")
    TaskDto getTask(@RequestParam("taskId") Long taskId) {
        return new TaskDto(taskId, "Test title", "test_content");
    }

    /**
     * can be @RequestMapping(method = RequestMethod.DELETE, value = "deleteTask") or @GetMapping("deleteTask")
     *  url --> http://localhost:8080/v1/task/deleteTask?taskId=1
     */
    @DeleteMapping("deleteTask")
    void deleteTask(@RequestParam("taskId") Long taskId) {

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
    @PutMapping("updateTask")
    TaskDto updateTask(@RequestBody TaskDto task) {
        return new TaskDto(task.getId(), task.getTitle(), task.getContent());
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
    @PostMapping("createTask")
    void createTask(@RequestBody TaskDto task) {

    }
}