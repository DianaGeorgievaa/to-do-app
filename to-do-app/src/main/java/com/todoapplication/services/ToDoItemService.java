package com.todoapplication.services;

import com.todoapplication.constants.MessageConstants;
import com.todoapplication.dtos.ToDoItemDto;
import com.todoapplication.entities.ToDoItem;
import com.todoapplication.entities.ToDoList;
import com.todoapplication.exceptions.ResourceAlreadyExistsException;
import com.todoapplication.exceptions.ResourceNotFoundException;
import com.todoapplication.mapper.Mapper;
import com.todoapplication.repositories.ToDoItemRepository;
import com.todoapplication.repositories.ToDoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class ToDoItemService {
    private ToDoItemRepository toDoItemRepository;
    private ToDoListRepository toDoListRepository;

    @Autowired
    public ToDoItemService(ToDoItemRepository toDoItemRepository, ToDoListRepository toDoListRepository) {
        this.toDoItemRepository = toDoItemRepository;
        this.toDoListRepository = toDoListRepository;
    }

    public void delete(int id) {
        if (!isExistingToDoItem(id)) {
            throw new ResourceNotFoundException(MessageConstants.RESOURCE_NOT_FOUND);
        }

        toDoItemRepository.deleteById(id);
    }

    public ToDoItemDto add(ToDoItemDto toDoItemDto) {
        Optional<ToDoList> todoList = toDoListRepository.findById(toDoItemDto.getListId());
        if (!todoList.isPresent()) {
            throw new ResourceNotFoundException(MessageConstants.RESOURCE_NOT_FOUND);
        }

        ToDoItem note = Mapper.INSTANCE.noteDtoToNote(toDoItemDto);
        if (isExistingToDoItem(note.getId())) {
            throw new ResourceAlreadyExistsException(MessageConstants.RESOURCE_ALREADY_EXISTS);
        }

        return Mapper.INSTANCE.noteToNoteDto(toDoItemRepository.save(note));
    }

    public ToDoItemDto get(int id) {
        Optional<ToDoItem> toDoItem = toDoItemRepository.findById(id);
        if (!toDoItem.isPresent()) {
            throw new ResourceNotFoundException(MessageConstants.RESOURCE_NOT_FOUND);
        }

        return Mapper.INSTANCE.noteToNoteDto(toDoItem.get());
    }

    public ToDoItemDto partialUpdate(int id, boolean isCompleted) {
        Optional<ToDoItem> toDoItemById = toDoItemRepository.findById(id);
        if (!toDoItemById.isPresent()) {
            throw new ResourceNotFoundException(MessageConstants.RESOURCE_NOT_FOUND);
        }

        ToDoItem toDoItem = toDoItemById.get();
        toDoItem.setIsCompleted(isCompleted);

        return Mapper.INSTANCE.noteToNoteDto(toDoItemRepository.save(toDoItem));
    }

    public ToDoItemDto fullyUpdate(int id, ToDoItemDto toDoItemDto) {
        if (!isExistingToDoItem(id)) {
            throw new ResourceNotFoundException(MessageConstants.RESOURCE_NOT_FOUND);
        }

        ToDoItem toDoItem = new ToDoItem(Mapper.INSTANCE.noteDtoToNote(toDoItemDto));
        toDoItem.setId(id);
        toDoItem.setDateOfCreation(new Date());

        return Mapper.INSTANCE.noteToNoteDto(toDoItemRepository.save(toDoItem));
    }

    public List<ToDoItemDto> getAll() {
        return Mapper.INSTANCE.mapListOfToDoItemsToDto(toDoItemRepository.findAll());
    }

    public List<ToDoItemDto> findToDoItemsByListId(int id) {
        List<ToDoItem> toDoItems = toDoItemRepository.findByToDoListId(id);

        return Mapper.INSTANCE.mapListOfToDoItemsToDto(toDoItems);
    }

    private boolean isExistingToDoItem(int id) {
        return toDoItemRepository.findById(id).isPresent();
    }
}
