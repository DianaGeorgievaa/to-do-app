package com.todoapplication.dtos;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class ToDoListDto {
    private int id;
    private String name;
    private LocalDate dateOfCreation;
    private LocalDate lastUpdate;
    private List<Integer> categoryIds;

    public ToDoListDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<Integer> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Integer> categoryIds) {
        this.categoryIds = categoryIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToDoListDto toDoList = (ToDoListDto) o;
        return id == toDoList.id &&
                Objects.equals(name, toDoList.name) &&
                Objects.equals(dateOfCreation, toDoList.dateOfCreation) &&
                Objects.equals(lastUpdate, toDoList.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, dateOfCreation, lastUpdate);
    }

    @Override
    public String toString() {
        return "ToDoListDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateOfCreation=" + dateOfCreation +
                ", lastUpdate=" + lastUpdate +
                ", categoryIds=" + categoryIds +
                '}';
    }
}