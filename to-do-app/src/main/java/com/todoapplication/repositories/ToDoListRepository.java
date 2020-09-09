package com.todoapplication.repositories;

import com.todoapplication.entities.ToDoList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoListRepository extends JpaRepository<ToDoList, Integer> {
}
