package com.todoapplication.services;

import com.todoapplication.constants.MessageConstants;
import com.todoapplication.dtos.CategoryDto;
import com.todoapplication.entities.Category;
import com.todoapplication.entities.ToDoList;
import com.todoapplication.exceptions.ResourceAlreadyExistsException;
import com.todoapplication.exceptions.ResourceNotFoundException;
import com.todoapplication.mapper.Mapper;
import com.todoapplication.repositories.CategoryRepository;
import com.todoapplication.repositories.ToDoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CategoryService {
    private CategoryRepository categoryRepository;
    private ToDoListRepository toDoListRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ToDoListRepository toDoListRepository) {
        this.categoryRepository = categoryRepository;
        this.toDoListRepository = toDoListRepository;
    }

    public void delete(int id) {
        Optional<Category> categoryById = categoryRepository.findById(id);
        if (!categoryById.isPresent()) {
            throw new ResourceNotFoundException(MessageConstants.RESOURCE_NOT_FOUND);
        }

        Category category = categoryById.get();
        for (ToDoList toDoList : category.getToDoLists()) {
            toDoList.removeCategory(id);
            toDoListRepository.save(toDoList);
        }
        categoryRepository.delete(category);
    }

    public CategoryDto add(CategoryDto categoryDto) {
        Category category = Mapper.INSTANCE.categoryDtoToCategory(categoryDto);
        if (isExistingCategory(category.getId())) {
            throw new ResourceAlreadyExistsException(MessageConstants.RESOURCE_ALREADY_EXISTS);
        }

        return Mapper.INSTANCE.categoryToCategoryDto(categoryRepository.save(category));
    }

    public CategoryDto get(int id) {
        Optional<Category> categoryById = categoryRepository.findById(id);
        if (!categoryById.isPresent()) {
            throw new ResourceNotFoundException(MessageConstants.RESOURCE_NOT_FOUND);
        }

        return Mapper.INSTANCE.categoryToCategoryDto(categoryById.get());
    }

    public CategoryDto partialUpdate(int id, String name) {
        Optional<Category> categoryById = categoryRepository.findById(id);
        if (!categoryById.isPresent()) {
            throw new ResourceNotFoundException(MessageConstants.RESOURCE_NOT_FOUND);
        }

        Category category = categoryById.get();
        category.setName(name);

        return Mapper.INSTANCE.categoryToCategoryDto(categoryRepository.save(category));
    }

    public CategoryDto fullyUpdate(int id, CategoryDto categoryDto) {
        if (!isExistingCategory(id)) {
            throw new ResourceNotFoundException(MessageConstants.RESOURCE_NOT_FOUND);
        }

        Category category = new Category();
        category.setId(id);
        category.setName(categoryDto.getName());

        return Mapper.INSTANCE.categoryToCategoryDto(categoryRepository.save(category));
    }

    public List<CategoryDto> getAll() {
        return Mapper.INSTANCE.mapListOfCategoriesToDtos(categoryRepository.findAll());
    }

    private boolean isExistingCategory(int id) {
        return categoryRepository.findById(id).isPresent();
    }
}
