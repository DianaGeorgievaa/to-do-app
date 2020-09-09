package com.todoapplication.controllers;

import com.todoapplication.constants.ToDoAppConstants;
import com.todoapplication.dtos.CategoryDto;
import com.todoapplication.exceptions.ResourceNotFoundException;
import com.todoapplication.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ToDoAppConstants.V1_API_PATH + ToDoAppConstants.CATEGORIES_PATH)
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAll() {
        return new ResponseEntity<>(categoryService.getAll(), HttpStatus.OK);
    }

    @GetMapping(value = ToDoAppConstants.ID_PATH_PARAM)
    public ResponseEntity<CategoryDto> get(@PathVariable int id) {
        return new ResponseEntity<>(categoryService.get(id), HttpStatus.OK);
    }

    @DeleteMapping(value = ToDoAppConstants.ID_PATH_PARAM)
    public ResponseEntity<Void> delete(@PathVariable int id) throws ResourceNotFoundException {
        categoryService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> add(@RequestBody CategoryDto categoryDto) {
        return new ResponseEntity<>(categoryService.add(categoryDto), HttpStatus.CREATED);
    }

    @PatchMapping(value = ToDoAppConstants.ID_PATH_PARAM)
    public ResponseEntity<CategoryDto> partialUpdate(@PathVariable int id, @RequestParam String name) {
        return new ResponseEntity<>(categoryService.partialUpdate(id, name), HttpStatus.OK);
    }

    @PutMapping(value = ToDoAppConstants.ID_PATH_PARAM)
    public ResponseEntity<CategoryDto> fullyUpdate(@PathVariable int id, @RequestBody CategoryDto categoryDto) {
        return new ResponseEntity<>(categoryService.fullyUpdate(id, categoryDto), HttpStatus.OK);
    }
}
