package com.primeholding.todoapplication.controllers;

import com.primeholding.todoapplication.dtos.ToDoItemDto;
import com.primeholding.todoapplication.services.ToDoItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/note")
public class ToDoItemController {
    @Autowired
    private ToDoItemService toDoItemService;

    @GetMapping
    public ResponseEntity<List<ToDoItemDto>> getAll() {
        return toDoItemService.getAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ToDoItemDto> get(@RequestParam int id) {
        return toDoItemService.get(id);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@RequestParam int id) {
        return toDoItemService.delete(id);
    }

    @PostMapping
    public ResponseEntity<ToDoItemDto> add(@RequestBody ToDoItemDto toDoItemDto) {
        return toDoItemService.add(toDoItemDto);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<ToDoItemDto> partialUpdate(@RequestParam int id, @RequestParam boolean isCompleted) {
        return toDoItemService.partialUpdate(id, isCompleted);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ToDoItemDto> fullyUpdate(@RequestParam int id, @RequestBody ToDoItemDto toDoItemDto) {
        return toDoItemService.fullyUpdate(id, toDoItemDto);
    }
}
