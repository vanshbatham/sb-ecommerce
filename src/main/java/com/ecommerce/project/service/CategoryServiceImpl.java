package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.repositories.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException
                        (HttpStatus.NOT_FOUND, "Resource Not Found"));
        categoryRepository.delete(existingCategory);
        return "Category with categoryId: " + categoryId + " deleted successfully.";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        Optional<Category> savedCategoryOptional = categoryRepository.findById(categoryId);

        Category savedCategory = savedCategoryOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resourse not found."));
        category.setCategoryId(categoryId);
        savedCategory = categoryRepository.save(category);
        return savedCategory;
    }
}
