package com.primeholding.todoapplication.repositories;

import com.primeholding.todoapplication.entities.ToDoItem;
import org.springframework.data.jpa.repositories.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoItemRepository extends JpaRepository<ToDoItem, Integer> {

    List<ToDoItem> findByToDoListId(int id);
}
