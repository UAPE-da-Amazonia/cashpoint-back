package io.zhc1.realworld.model;

import java.util.Optional;

public interface CategoryRepository {
    Optional<Category> findById(Integer id);
}
