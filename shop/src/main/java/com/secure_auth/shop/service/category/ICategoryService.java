package com.secure_auth.shop.service.category;

import com.secure_auth.shop.model.Category;

import java.util.List;

/**
 * Interface for Category Service.
 */
public interface ICategoryService {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category updateCategory(Category category, Long id);
    void deleteCategoryById(Long id);
}
