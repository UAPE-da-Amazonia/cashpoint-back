package io.zhc1.realworld.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import io.zhc1.realworld.model.Category;

public interface CategoryJpaRepository extends JpaRepository<Category, Integer> {}
