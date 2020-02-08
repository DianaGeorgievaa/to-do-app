package com.primeholding.todoapplication.repositories;

import com.primeholding.todoapplication.entities.Category;
import org.springframework.data.jpa.repositories.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
