package com.primeholding.todoapplication.controllers;

import com.primeholding.todoapplication.dtos.CategoryDto;
import com.primeholding.todoapplication.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAll() {
        return categoryService.getAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryDto> get(@RequestParam int id) {
        return categoryService.get(id);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@RequestParam int id) {
        return categoryService.delete(id);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> add(@RequestBody CategoryDto categoryDto) {
        return categoryService.add(categoryDto);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<CategoryDto> partialUpdate(@RequestParam int id, @RequestParam String name) {
        return categoryService.partialUpdate(id, name);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoryDto> fullyUpdate(@RequestParam int id, @RequestBody CategoryDto categoryDto) {
        return categoryService.fullyUpdate(id, categoryDto);
    }
}
