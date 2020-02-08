package com.primeholding.todoapplication.services;

import com.primeholding.todoapplication.dtos.CategoryDto;
import com.primeholding.todoapplication.dtos.ToDoItemDto;
import com.primeholding.todoapplication.dtos.ToDoListDto;
import com.primeholding.todoapplication.entities.Category;
import com.primeholding.todoapplication.entities.ToDoList;
import com.primeholding.todoapplication.mapper.Mapper;
import com.primeholding.todoapplication.repositories.CategoryRepository;
import com.primeholding.todoapplication.repositories.ToDoListRepository;
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
    @Autowired
    private ToDoListRepository toDoListRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ToDoItemService toDoItemService;

    public ToDoListService() {
    }

    public ResponseEntity<Void> delete(int id) {
        if (!isToDoListExisting(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        toDoItemService.findToDoItemsByListId(id).forEach(toDoItemDto -> toDoItemService.delete(toDoItemDto.getId()));
        toDoListRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<ToDoListDto> add(ToDoListDto toDoListDto) {
        ToDoList toDoList = Mapper.INSTANCE.toDoListDtoToList(toDoListDto);
        if (isToDoListExisting(toDoList.getId())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        List<Category> categories = new ArrayList<>();
        Optional<Category> category;

        for (int id : toDoListDto.getCategoryIds()) {
            category = categoryRepository.findById(id);
            if (!category.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            categories.add(category.get());
        }

        toDoList.setCategories(categories);
        ToDoListDto createdToDoListDto = Mapper.INSTANCE.toDoListToListDto(toDoListRepository.save(toDoList));

        return new ResponseEntity<>(createdToDoListDto, HttpStatus.CREATED);
    }

    public ResponseEntity<ToDoListDto> get(int id) {
        if (!isToDoListExisting(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ToDoListDto toDoListDto = Mapper.INSTANCE.toDoListToListDto(toDoListRepository.findById(id).get());
        return new ResponseEntity<>(toDoListDto, HttpStatus.OK);
    }


    public ResponseEntity<ToDoListDto> partialUpdate(int id, String name) {
        if (!isToDoListExisting(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<ToDoList> toDoList = toDoListRepository.findById(id);
        toDoList.get().setName(name);
        ToDoListDto toDoListDto = Mapper.INSTANCE.toDoListToListDto(toDoListRepository.save(toDoList.get()));

        return new ResponseEntity<>(toDoListDto, HttpStatus.OK);
    }

    public ResponseEntity<ToDoListDto> fullyUpdate(int id, ToDoListDto toDoListDto) {
        if (!isToDoListExisting(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ToDoList toDoList = new ToDoList(Mapper.INSTANCE.toDoListDtoToList(toDoListDto));
        toDoList.setId(id);
        Date date = new Date();
        toDoList.setDateOfCreation(date);

        List<Integer> categoryIds = toDoListDto.getCategoryIds();
        List<Category> categories = new ArrayList<>();
        Optional<Category> category;
        for (int categoryId : categoryIds) {
            category = categoryRepository.findById(categoryId);
            if (!category.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            categories.add(category.get());
        }

        toDoList.setCategories(categories);

        ToDoListDto savedToDoListDto = Mapper.INSTANCE.toDoListToListDto(toDoListRepository.save(toDoList));
        return new ResponseEntity<>(savedToDoListDto, HttpStatus.OK);

    }

    public ResponseEntity<List<ToDoListDto>> getAll() {
        List<ToDoListDto> allToDoListDto = Mapper.INSTANCE.mapListOfToDoListsToDto(toDoListRepository.findAll());

        return new ResponseEntity<>(allToDoListDto, HttpStatus.OK);
    }

    public ResponseEntity<List<ToDoItemDto>> getNotesByListId(int id) {
        if (!isToDoListExisting(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(toDoItemService.findToDoItemsByListId(id), HttpStatus.OK);
    }

    public ResponseEntity<List<CategoryDto>> getCategoriesByListId(int id) {
        if (!isToDoListExisting(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<ToDoList> toDoList = toDoListRepository.findById(id);
        List<Category> categories = toDoList.get().getCategories();

        return new ResponseEntity<>(Mapper.INSTANCE.mapListOfCategoriesToDtos(categories), HttpStatus.OK);
    }

    private boolean isToDoListExisting(int id) {
        return toDoListRepository.findById(id).isPresent();
    }
}