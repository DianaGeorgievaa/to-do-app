package com.todoapplication.controllers;

import com.todoapplication.constants.ToDoAppConstants;
import com.todoapplication.dtos.ToDoItemDto;
import com.todoapplication.services.ToDoItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ToDoAppConstants.V1_API_PATH + ToDoAppConstants.NOTES_PATH)
public class ToDoItemController {
    private ToDoItemService toDoItemService;

    @Autowired
    public ToDoItemController(ToDoItemService toDoItemService) {
        this.toDoItemService = toDoItemService;
    }

    @GetMapping
    public ResponseEntity<List<ToDoItemDto>> getAll() {
        return new ResponseEntity<>(toDoItemService.getAll(), HttpStatus.OK);
    }

    @GetMapping(value = ToDoAppConstants.ID_PATH_PARAM)
    public ResponseEntity<ToDoItemDto> get(@PathVariable int id) {
        return new ResponseEntity<>(toDoItemService.get(id), HttpStatus.OK);
    }

    @DeleteMapping(value = ToDoAppConstants.ID_PATH_PARAM)
    public ResponseEntity<Void> delete(@PathVariable int id) {
        toDoItemService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<ToDoItemDto> add(@RequestBody ToDoItemDto toDoItemDto) {
        return new ResponseEntity<>(toDoItemService.add(toDoItemDto), HttpStatus.CREATED);
    }

    @PatchMapping(value = ToDoAppConstants.ID_PATH_PARAM)
    public ResponseEntity<ToDoItemDto> partialUpdate(@PathVariable int id, @RequestParam boolean isCompleted) {
        return new ResponseEntity<>(toDoItemService.partialUpdate(id, isCompleted), HttpStatus.OK);
    }

    @PutMapping(value = ToDoAppConstants.ID_PATH_PARAM)
    public ResponseEntity<ToDoItemDto> fullyUpdate(@PathVariable int id, @RequestBody ToDoItemDto toDoItemDto) {
        return new ResponseEntity<>(toDoItemService.fullyUpdate(id, toDoItemDto), HttpStatus.OK);
    }
}
