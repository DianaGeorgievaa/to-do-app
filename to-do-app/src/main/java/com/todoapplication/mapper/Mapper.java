package com.todoapplication.mapper;

import com.todoapplication.dtos.CategoryDto;
import com.todoapplication.dtos.ToDoItemDto;
import com.todoapplication.dtos.ToDoListDto;
import com.todoapplication.entities.Category;
import com.todoapplication.entities.ToDoItem;
import com.todoapplication.entities.ToDoList;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {
    Mapper INSTANCE = Mappers.getMapper(Mapper.class);

    @Mapping(source = "listId", target = "toDoList.id")
    ToDoItem noteDtoToNote(ToDoItemDto toDoItemDto);

    @Mapping(source = "toDoList.id", target = "listId")
    ToDoItemDto noteToNoteDto(ToDoItem toDoItem);

   List<ToDoItemDto> mapListOfToDoItemsToDto(List<ToDoItem> toDoItems);

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

    List<ToDoListDto> mapListOfToDoListsToDto(List<ToDoList> toDoLists);

    CategoryDto categoryToCategoryDto(Category category);

    Category categoryDtoToCategory(CategoryDto categoryDto);

    List<CategoryDto> mapListOfCategoriesToDtos(List<Category> categories);
}
