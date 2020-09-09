package com.todoapplication.services;

import com.todoapplication.constants.MessageConstants;
import com.todoapplication.dtos.CategoryDto;
import com.todoapplication.dtos.ToDoItemDto;
import com.todoapplication.dtos.ToDoListDto;
import com.todoapplication.entities.Category;
import com.todoapplication.entities.ToDoList;
import com.todoapplication.exceptions.ResourceAlreadyExistsException;
import com.todoapplication.exceptions.ResourceNotFoundException;
import com.todoapplication.mapper.Mapper;
import com.todoapplication.repositories.CategoryRepository;
import com.todoapplication.repositories.ToDoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class ToDoListService {
    private ToDoListRepository toDoListRepository;
    private CategoryRepository categoryRepository;
    private ToDoItemService toDoItemService;

    @Autowired
    public ToDoListService(ToDoListRepository toDoListRepository,
                           CategoryRepository categoryRepository,
                           ToDoItemService toDoItemService) {
        this.toDoListRepository = toDoListRepository;
        this.categoryRepository = categoryRepository;
        this.toDoItemService = toDoItemService;
    }

    public void delete(int id) {
        if (!isToDoListExisting(id)) {
            throw new ResourceNotFoundException(MessageConstants.RESOURCE_NOT_FOUND);
        }

        toDoItemService.findToDoItemsByListId(id).forEach(toDoItemDto -> toDoItemService.delete(toDoItemDto.getId()));
        toDoListRepository.deleteById(id);
    }

    public ToDoListDto add(ToDoListDto toDoListDto) {
        ToDoList toDoList = Mapper.INSTANCE.toDoListDtoToList(toDoListDto);
        if (isToDoListExisting(toDoList.getId())) {
            throw new ResourceAlreadyExistsException(MessageConstants.RESOURCE_ALREADY_EXISTS);
        }

        List<Category> categories = new ArrayList<>();
        for (int id : toDoListDto.getCategoryIds()) {
            Optional<Category> category = categoryRepository.findById(id);
            if (!category.isPresent()) {
                throw new ResourceNotFoundException(MessageConstants.RESOURCE_NOT_FOUND);
            }

            categories.add(category.get());
        }

        toDoList.setCategories(categories);

        return Mapper.INSTANCE.toDoListToListDto(toDoListRepository.save(toDoList));
    }

    public ToDoListDto get(int id) {
        Optional<ToDoList> toDoListById = toDoListRepository.findById(id);
        if (!toDoListById.isPresent()) {
            throw new ResourceNotFoundException(MessageConstants.RESOURCE_NOT_FOUND);
        }

        return Mapper.INSTANCE.toDoListToListDto(toDoListById.get());
    }

    public ToDoListDto partialUpdate(int id, String name) {
        Optional<ToDoList> toDoListById = toDoListRepository.findById(id);
        if (!toDoListById.isPresent()) {
            throw new ResourceNotFoundException(MessageConstants.RESOURCE_NOT_FOUND);
        }

        ToDoList toDoList = toDoListById.get();
        toDoList.setName(name);

        return Mapper.INSTANCE.toDoListToListDto(toDoListRepository.save(toDoList));
    }

    public ToDoListDto fullyUpdate(int id, ToDoListDto toDoListDto) {
        if (!isToDoListExisting(id)) {
            throw new ResourceNotFoundException(MessageConstants.RESOURCE_NOT_FOUND);
        }

        ToDoList toDoList = new ToDoList(Mapper.INSTANCE.toDoListDtoToList(toDoListDto));
        toDoList.setId(id);
        Date date = new Date();
        toDoList.setDateOfCreation(date);

        List<Integer> categoryIds = toDoListDto.getCategoryIds();
        List<Category> categories = new ArrayList<>();
        for (int categoryId : categoryIds) {
            Optional<Category> category = categoryRepository.findById(categoryId);
            if (!category.isPresent()) {
                throw new ResourceNotFoundException(MessageConstants.RESOURCE_NOT_FOUND);
            }
            categories.add(category.get());
        }

        toDoList.setCategories(categories);

        return Mapper.INSTANCE.toDoListToListDto(toDoListRepository.save(toDoList));
    }

    public List<ToDoListDto> getAll() {
        return Mapper.INSTANCE.mapListOfToDoListsToDto(toDoListRepository.findAll());
    }

    public List<ToDoItemDto> getNotesByListId(int id) {
        if (!isToDoListExisting(id)) {
            throw new ResourceNotFoundException(MessageConstants.RESOURCE_NOT_FOUND);
        }

        return toDoItemService.findToDoItemsByListId(id);
    }

    public List<CategoryDto> getCategoriesByListId(int id) {
        Optional<ToDoList> toDoList = toDoListRepository.findById(id);
        if (!toDoList.isPresent()) {
            throw new ResourceNotFoundException(MessageConstants.RESOURCE_NOT_FOUND);
        }
        List<Category> categories = toDoList.get().getCategories();

        return Mapper.INSTANCE.mapListOfCategoriesToDtos(categories);
    }

    private boolean isToDoListExisting(int id) {
        return toDoListRepository.findById(id).isPresent();
    }
}