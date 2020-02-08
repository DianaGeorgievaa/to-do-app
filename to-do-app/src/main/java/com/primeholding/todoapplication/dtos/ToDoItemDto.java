package com.primeholding.todoapplication.dtos;

import java.time.LocalDate;
import java.util.Objects;

public class ToDoItemDto {
    private int id;
    private String title;
    private String description;
    private boolean isCompleted;
    private LocalDate dateOfCreation;
    private LocalDate lastUpdate;
    private int listId;

    public ToDoItemDto(int id, String title, String description, boolean isCompleted, LocalDate dateOfCreation, LocalDate lastUpdate, int listId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isCompleted = isCompleted;
        this.dateOfCreation = dateOfCreation;
        this.lastUpdate = lastUpdate;
        this.listId = listId;
    }

    public ToDoItemDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDate dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDate lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToDoItemDto that = (ToDoItemDto) o;
        return id == that.id &&
                isCompleted == that.isCompleted &&
                listId == that.listId &&
                Objects.equals(title, that.title) &&
                Objects.equals(description, that.description) &&
                Objects.equals(dateOfCreation, that.dateOfCreation) &&
                Objects.equals(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, isCompleted, dateOfCreation, lastUpdate, listId);
    }
}