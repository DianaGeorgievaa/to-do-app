package com.primeholding.todoapplication.services;

import com.primeholding.todoapplication.dtos.CategoryDto;
import com.primeholding.todoapplication.entities.Category;
import com.primeholding.todoapplication.entities.ToDoList;
import com.primeholding.todoapplication.mapper.Mapper;
import com.primeholding.todoapplication.repositories.CategoryRepository;
import com.primeholding.todoapplication.repositories.ToDoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ToDoListRepository toDoListRepository;

    public CategoryService() {
    }

    public ResponseEntity<Void> delete(int id) {
        if (!isCategoryExisting(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<Category> category = categoryRepository.findById(id);
        for (ToDoList toDoList : category.get().getToDoLists()) {
            toDoList.removeCategory(id);
            toDoListRepository.save(toDoList);
        }

        categoryRepository.delete(category.get());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<CategoryDto> add(CategoryDto categoryDto) {
        Category category = Mapper.INSTANCE.categoryDtoToCategory(categoryDto);
        if (isCategoryExisting(category.getId())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        CategoryDto createdToDoItemDto = Mapper.INSTANCE.categoryToCategoryDto(categoryRepository.save(category));

        return new ResponseEntity<>(createdToDoItemDto, HttpStatus.CREATED);
    }

    public ResponseEntity<CategoryDto> get(int id) {
        if (!isCategoryExisting(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CategoryDto categoryDto = Mapper.INSTANCE.categoryToCategoryDto(categoryRepository.findById(id).get());

        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    public ResponseEntity<CategoryDto> partialUpdate(int id, String name) {
        if (!isCategoryExisting(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<Category> category = categoryRepository.findById(id);
        category.get().setName(name);
        CategoryDto categoryDto = Mapper.INSTANCE.categoryToCategoryDto(categoryRepository.save(category.get()));

        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    public ResponseEntity<CategoryDto> fullyUpdate(int id, CategoryDto categoryDto) {
        if (!isCategoryExisting(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Category category = new Category();
        category.setId(id);
        category.setName(categoryDto.getName());
        CategoryDto savedToDoItemDto = Mapper.INSTANCE.categoryToCategoryDto(categoryRepository.save(category));

        return new ResponseEntity<>(savedToDoItemDto, HttpStatus.OK);
    }

    public ResponseEntity<List<CategoryDto>> getAll() {
        return new ResponseEntity<>(Mapper.INSTANCE.mapListOfCategoriesToDtos(categoryRepository.findAll()), HttpStatus.OK);
    }

    private boolean isCategoryExisting(int id) {
        return categoryRepository.findById(id).isPresent();
    }
}
