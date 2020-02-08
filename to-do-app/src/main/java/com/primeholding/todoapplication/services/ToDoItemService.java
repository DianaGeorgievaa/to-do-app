package com.primeholding.todoapplication.services;

import com.primeholding.todoapplication.dtos.ToDoItemDto;
import com.primeholding.todoapplication.entities.ToDoItem;
import com.primeholding.todoapplication.entities.ToDoList;
import com.primeholding.todoapplication.mapper.Mapper;
import com.primeholding.todoapplication.repositories.ToDoItemRepository;
import com.primeholding.todoapplication.repositories.ToDoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class ToDoItemService {
    @Autowired
    private ToDoItemRepository toDoItemRepository;
    @Autowired
    private ToDoListRepository toDoListRepository;

    public ToDoItemService() {
    }

    public ResponseEntity<Void> delete(int id) {
        if (!isToDoItemExisting(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        toDoItemRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<ToDoItemDto> add(ToDoItemDto toDoItemDto) {
        Optional<ToDoList> todoList = toDoListRepository.findById(toDoItemDto.getListId());
        if (!todoList.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ToDoItem note = Mapper.INSTANCE.noteDtoToNote(toDoItemDto);
        if (isToDoItemExisting(note.getId())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        ToDoItemDto createdToDoItemDto = Mapper.INSTANCE.noteToNoteDto(toDoItemRepository.save(note));

        return new ResponseEntity<>(createdToDoItemDto, HttpStatus.CREATED);
    }

    public ResponseEntity<ToDoItemDto> get(int id) {
        if (!isToDoItemExisting(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ToDoItemDto toDoItemDto = Mapper.INSTANCE.noteToNoteDto(toDoItemRepository.findById(id).get());

        return new ResponseEntity<>(toDoItemDto, HttpStatus.OK);


    }

    public ResponseEntity<ToDoItemDto> partialUpdate(int id, boolean isCompleted) {
        if (!isToDoItemExisting(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<ToDoItem> toDoItem = toDoItemRepository.findById(id);
        toDoItem.get().setIsCompleted(isCompleted);
        ToDoItemDto toDoItemDto = Mapper.INSTANCE.noteToNoteDto(toDoItemRepository.save(toDoItem.get()));

        return new ResponseEntity<>(toDoItemDto, HttpStatus.OK);
    }

    public ResponseEntity<ToDoItemDto> fullyUpdate(int id, ToDoItemDto toDoItemDto) {
        if (!isToDoItemExisting(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ToDoItem toDoItem = new ToDoItem(Mapper.INSTANCE.noteDtoToNote(toDoItemDto));
        toDoItem.setId(id);
        toDoItem.setDateOfCreation(new Date());

        ToDoItemDto savedToDoItemDto = Mapper.INSTANCE.noteToNoteDto(toDoItemRepository.save(toDoItem));

        return new ResponseEntity<>(savedToDoItemDto, HttpStatus.OK);
    }

    public ResponseEntity<List<ToDoItemDto>> getAll() {
        return new ResponseEntity<>(Mapper.INSTANCE.mapListOfToDoItemsToDto(toDoItemRepository.findAll()), HttpStatus.OK);
    }

    public List<ToDoItemDto> findToDoItemsByListId(int id) {
        List<ToDoItem> toDoItems = toDoItemRepository.findByToDoListId(id);

        return Mapper.INSTANCE.mapListOfToDoItemsToDto(toDoItems);
    }

    private boolean isToDoItemExisting(int id) {
        return toDoItemRepository.findById(id).isPresent();
    }
}

