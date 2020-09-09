package com.todoapplication.controllers;

import com.todoapplication.constants.ToDoAppConstants;
import com.todoapplication.dtos.CategoryDto;
import com.todoapplication.dtos.ToDoItemDto;
import com.todoapplication.dtos.ToDoListDto;
import com.todoapplication.services.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ToDoAppConstants.V1_API_PATH + ToDoAppConstants.LISTS_PATH)
public class ToDoListController {
    private ToDoListService toDoListService;

    @Autowired
    public ToDoListController(ToDoListService toDoListService) {
        this.toDoListService = toDoListService;
    }

    @GetMapping()
    public ResponseEntity<List<ToDoListDto>> getAll() {
        return new ResponseEntity<>(toDoListService.getAll(), HttpStatus.OK);
    }

    @GetMapping(value = ToDoAppConstants.ID_PATH_PARAM)
    public ResponseEntity<ToDoListDto> get(@PathVariable int id) {
        return new ResponseEntity<>(toDoListService.get(id), HttpStatus.OK);
    }

    @GetMapping(value = ToDoAppConstants.ID_PATH_PARAM + ToDoAppConstants.NOTES_PATH)
    public ResponseEntity<List<ToDoItemDto>> getNotesByListId(@PathVariable int id) {
        return new ResponseEntity<>(toDoListService.getNotesByListId(id), HttpStatus.OK);
    }

    @GetMapping(value = ToDoAppConstants.ID_PATH_PARAM + ToDoAppConstants.CATEGORIES_PATH)
    public ResponseEntity<List<CategoryDto>> getCategoriesByListId(@PathVariable int id) {
        return new ResponseEntity<>(toDoListService.getCategoriesByListId(id), HttpStatus.OK);
    }

    @DeleteMapping(value = ToDoAppConstants.ID_PATH_PARAM)
    public ResponseEntity<Void> delete(@PathVariable int id) {
        toDoListService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<ToDoListDto> add(@RequestBody ToDoListDto toDoListDto) {
        return new ResponseEntity<>(toDoListService.add(toDoListDto), HttpStatus.OK);
    }

    @PatchMapping(value = ToDoAppConstants.ID_PATH_PARAM)
    public ResponseEntity<ToDoListDto> partialUpdate(@PathVariable int id, @RequestParam String name) {
        return new ResponseEntity<>(toDoListService.partialUpdate(id, name), HttpStatus.OK);
    }

    @PutMapping(value = ToDoAppConstants.ID_PATH_PARAM)
    public ResponseEntity<ToDoListDto> fullyUpdate(@PathVariable int id, @RequestBody ToDoListDto toDoListDto) {
        return new ResponseEntity<>(toDoListService.fullyUpdate(id, toDoListDto), HttpStatus.OK);
    }
}