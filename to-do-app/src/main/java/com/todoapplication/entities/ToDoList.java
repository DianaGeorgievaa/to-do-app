package com.todoapplication.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "list")
public class ToDoList extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column
    private String name;

    @ManyToMany
    @JoinTable(
            name = "lists_categories",
            joinColumns = {@JoinColumn(name = "list_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id", referencedColumnName = "id")})
    private List<Category> categories;

    public ToDoList() {
    }

    public ToDoList(ToDoList toDoListDtoToList) {
        this.id = toDoListDtoToList.getId();
        this.name = toDoListDtoToList.getName();
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

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Category getCategory(Integer id) {
        for (Category category : categories) {
            if (category.getId() == id) {
                return category;
            }
        }

        return null;
    }

    public void removeCategory(int id) {
        categories.remove(getCategory(id));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToDoList toDoList = (ToDoList) o;
        return id == toDoList.id &&
                Objects.equals(name, toDoList.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
