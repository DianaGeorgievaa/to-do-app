package com.primeholding.todoapplication.controllers;

import com.primeholding.todoapplication.dtos.CategoryDto;
import com.primeholding.todoapplication.dtos.ToDoItemDto;
import com.primeholding.todoapplication.dtos.ToDoListDto;
import com.primeholding.todoapplication.services.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/list")
public class ToDoListController {
    @Autowired
    private ToDoListService toDoListService;

    @GetMapping()
    public ResponseEntity<List<ToDoListDto>> getAll() {
        return toDoListService.getAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ToDoListDto> get(@RequestParam int id) {
        return toDoListService.get(id);
    }

    @GetMapping(value = "/{id}/notes")
    public ResponseEntity<List<ToDoItemDto>> getNotesByListId(@PathVariable int id) {
        return toDoListService.getNotesByListId(id);
    }

    @GetMapping(value = "/{id}/categories")
    public ResponseEntity<List<CategoryDto>> getCategoriesByListId(@PathVariable int id) {
        return toDoListService.getCategoriesByListId(id);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@RequestParam int id) {
        return toDoListService.delete(id);
    }

    @PostMapping
    public ResponseEntity<ToDoListDto> add(@RequestBody ToDoListDto toDoListDto) {
        return toDoListService.add(toDoListDto);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<ToDoListDto> partialUpdate(@RequestParam int id, @RequestParam String name) {
        return toDoListService.partialUpdate(id, name);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ToDoListDto> fullyUpdate(@RequestParam int id, @RequestBody ToDoListDto toDoListDto) {
        return toDoListService.fullyUpdate(id, toDoListDto);
    }
}