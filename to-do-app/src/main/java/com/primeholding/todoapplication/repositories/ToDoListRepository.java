package com.primeholding.todoapplication.repositories;

import com.primeholding.todoapplication.entities.ToDoList;
import org.springframework.data.jpa.repositories.JpaRepository;

public interface ToDoListRepository extends JpaRepository<ToDoList, Integer> {
}
