package com.primeholding.todoapplication.mapper;

import com.primeholding.todoapplication.dtos.CategoryDto;
import com.primeholding.todoapplication.dtos.ToDoItemDto;
import com.primeholding.todoapplication.dtos.ToDoListDto;
import com.primeholding.todoapplication.entities.Category;
import com.primeholding.todoapplication.entities.ToDoItem;
import com.primeholding.todoapplication.entities.ToDoList;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {
    Mapper INSTANCE = Mappers.getMapper(Mapper.class);

    @Mapping(source = "listId", target = "toDoList.id")
    ToDoItem noteDtoToNote(ToDoItemDto toDoItemDto);

    @Mapping(source = "toDoList.id", target = "listId")
    ToDoItemDto noteToNoteDto(ToDoItem toDoItem);

    default List<ToDoItemDto> mapListOfToDoItemsToDto(List<ToDoItem> toDoItems) {
        List<ToDoItemDto> toDoItemDtos = new ArrayList<>();

        for (ToDoItem currentToDoItem : toDoItems) {
            toDoItemDtos.add(Mapper.INSTANCE.noteToNoteDto(currentToDoItem));
        }

        return toDoItemDtos;
    }

    @Mapping(source = "categoryIds", target = "categories")
    ToDoList toDoListDtoToList(ToDoListDto toDoListDto);

    @IterableMapping(elementTargetType = Integer.class)
    List<Integer> mapIntegerToList(List<Category> categories);

    Category mapIntegerToCategory(Integer id);

    default Integer mapCategoryToInteger(Category category) {
        return category.getId();
    }

    @Mapping(source = "categories", target = "categoryIds")
    ToDoListDto toDoListToListDto(ToDoList toDoList);

    default List<ToDoListDto> mapListOfToDoListsToDto(List<ToDoList> toDoLists) {
        List<ToDoListDto> toDoListDtos = new ArrayList<>();

        for (ToDoList currentToDoList : toDoLists) {
            toDoListDtos.add(Mapper.INSTANCE.toDoListToListDto(currentToDoList));
        }

        return toDoListDtos;
    }

    CategoryDto categoryToCategoryDto(Category category);

    Category categoryDtoToCategory(CategoryDto categoryDto);

    default List<CategoryDto> mapListOfCategoriesToDtos(List<Category> categories) {
        List<CategoryDto> categoryDtos = new ArrayList<>();

        for (Category currentCategory : categories) {
            categoryDtos.add(Mapper.INSTANCE.categoryToCategoryDto(currentCategory));
        }

        return categoryDtos;
    }
}
