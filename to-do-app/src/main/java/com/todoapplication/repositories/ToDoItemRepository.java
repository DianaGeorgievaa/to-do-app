package com.todoapplication.repositories;

import com.todoapplication.entities.ToDoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoItemRepository extends JpaRepository<ToDoItem, Integer> {

    List<ToDoItem> findByToDoListId(int id);
}
