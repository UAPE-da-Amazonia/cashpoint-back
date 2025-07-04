package io.zhc1.realworld.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import io.zhc1.realworld.model.BusinessUnit;
import io.zhc1.realworld.model.Category;
import io.zhc1.realworld.model.CategoryRepository;
import io.zhc1.realworld.model.TransactionType;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    /**
     * Get category by id with access control.
     *
     * @param id category id
     * @param userBusinessUnitId user's business unit id
     * @param isAdmin if user is admin
     * @return Returns category
     */
    public Category getCategory(Integer id, Long userBusinessUnitId, boolean isAdmin) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("category not found."));

        // Admin can access any category, USER only from their business unit
        if (!isAdmin && !category.getBusinessUnit().getId().equals(userBusinessUnitId)) {
            throw new SecurityException("Access denied. You can only access categories from your business unit.");
        }

        return category;
    }

    /**
     * Get category by id.
     *
     * @param id category id
     * @return Returns category
     */
    public Category getCategory(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("category not found."));
    }

    /**
     * Get all categories by business unit with access control.
     *
     * @param businessUnit business unit
     * @param userBusinessUnitId user's business unit id
     * @param isAdmin if user is admin
     * @return Returns list of categories
     */
    public List<Category> getCategoriesByBusinessUnit(BusinessUnit businessUnit, Long userBusinessUnitId, boolean isAdmin) {
        if (businessUnit == null) {
            throw new IllegalArgumentException("business unit is required.");
        }

        // Admin can access any business unit, USER only their own
        if (!isAdmin && !businessUnit.getId().equals(userBusinessUnitId)) {
            throw new SecurityException("Access denied. You can only access categories from your business unit.");
        }

        return categoryRepository.findByBusinessUnit(businessUnit);
    }

    /**
     * Get all categories by business unit.
     *
     * @param businessUnit business unit
     * @return Returns list of categories
     */
    public List<Category> getCategoriesByBusinessUnit(BusinessUnit businessUnit) {
        if (businessUnit == null) {
            throw new IllegalArgumentException("business unit is required.");
        }
        return categoryRepository.findByBusinessUnit(businessUnit);
    }

    /**
     * Get categories by business unit and transaction type with access control.
     *
     * @param businessUnit business unit
     * @param transactionType transaction type
     * @param userBusinessUnitId user's business unit id
     * @param isAdmin if user is admin
     * @return Returns list of categories
     */
    public List<Category> getCategoriesByBusinessUnitAndTransactionType(
            BusinessUnit businessUnit, TransactionType transactionType, Long userBusinessUnitId, boolean isAdmin) {
        if (businessUnit == null) {
            throw new IllegalArgumentException("business unit is required.");
        }
        if (transactionType == null) {
            throw new IllegalArgumentException("transaction type is required.");
        }

        // Admin can access any business unit, USER only their own
        if (!isAdmin && !businessUnit.getId().equals(userBusinessUnitId)) {
            throw new SecurityException("Access denied. You can only access categories from your business unit.");
        }

        return categoryRepository.findByBusinessUnitAndTransactionType(businessUnit, transactionType);
    }

    /**
     * Get categories by business unit and transaction type.
     *
     * @param businessUnit business unit
     * @param transactionType transaction type
     * @return Returns list of categories
     */
    public List<Category> getCategoriesByBusinessUnitAndTransactionType(BusinessUnit businessUnit, TransactionType transactionType) {
        if (businessUnit == null) {
            throw new IllegalArgumentException("business unit is required.");
        }
        if (transactionType == null) {
            throw new IllegalArgumentException("transaction type is required.");
        }

        return categoryRepository.findByBusinessUnitAndTransactionType(businessUnit, transactionType);
    }

    /**
     * Create a new category with access control.
     *
     * @param name category name
     * @param transactionType transaction type
     * @param businessUnit business unit
     * @param userBusinessUnitId user's business unit id
     * @param isAdmin if user is admin
     * @return Returns the created category
     */
    public Category createCategory(String name, TransactionType transactionType, BusinessUnit businessUnit, Long userBusinessUnitId, boolean isAdmin) {
        // Admin can create in any business unit, USER only in their own
        if (!isAdmin && !businessUnit.getId().equals(userBusinessUnitId)) {
            throw new SecurityException("Access denied. You can only create categories in your business unit.");
        }

        var category = new Category(name, transactionType, businessUnit);
        return categoryRepository.save(category);
    }

    /**
     * Create a new category.
     *
     * @param name category name
     * @param transactionType transaction type
     * @param businessUnit business unit
     * @return Returns the created category
     */
    public Category createCategory(String name, TransactionType transactionType, BusinessUnit businessUnit) {
        var category = new Category(name, transactionType, businessUnit);
        return categoryRepository.save(category);
    }

    /**
     * Update a category with access control.
     *
     * @param id category id
     * @param name category name
     * @param transactionType transaction type
     * @param userBusinessUnitId user's business unit id
     * @param isAdmin if user is admin
     * @return Returns the updated category
     */
    public Category updateCategory(Integer id, String name, TransactionType transactionType, Long userBusinessUnitId, boolean isAdmin) {
        Category category = getCategory(id, userBusinessUnitId, isAdmin);

        // Admin can update any category, USER only from their business unit
        if (!isAdmin && !category.getBusinessUnit().getId().equals(userBusinessUnitId)) {
            throw new SecurityException("Access denied. You can only update categories from your business unit.");
        }

        // Create new category with updated data
        var updatedCategory = new Category(name, transactionType, category.getBusinessUnit());
        updatedCategory = categoryRepository.save(updatedCategory);

        // Delete the old category
        categoryRepository.delete(category);

        return updatedCategory;
    }

    /**
     * Update a category.
     *
     * @param id category id
     * @param name category name
     * @param transactionType transaction type
     * @return Returns the updated category
     */
    public Category updateCategory(Integer id, String name, TransactionType transactionType) {
        Category category = getCategory(id);

        // Create new category with updated data
        var updatedCategory = new Category(name, transactionType, category.getBusinessUnit());
        updatedCategory = categoryRepository.save(updatedCategory);

        // Delete the old category
        categoryRepository.delete(category);

        return updatedCategory;
    }

    /**
     * Delete a category with access control.
     *
     * @param id category id
     * @param userBusinessUnitId user's business unit id
     * @param isAdmin if user is admin
     */
    public void deleteCategory(Integer id, Long userBusinessUnitId, boolean isAdmin) {
        Category category = getCategory(id, userBusinessUnitId, isAdmin);

        // Admin can delete any category, USER only from their business unit
        if (!isAdmin && !category.getBusinessUnit().getId().equals(userBusinessUnitId)) {
            throw new SecurityException("Access denied. You can only delete categories from your business unit.");
        }

        categoryRepository.delete(category);
    }

    /**
     * Delete a category.
     *
     * @param id category id
     */
    public void deleteCategory(Integer id) {
        Category category = getCategory(id);
        categoryRepository.delete(category);
    }
} 