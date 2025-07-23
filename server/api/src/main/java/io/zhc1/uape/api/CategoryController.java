package io.zhc1.uape.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import io.zhc1.uape.api.request.CategoryRequest;
import io.zhc1.uape.api.response.CategoryResponse;
import io.zhc1.uape.config.AuthToken;
import io.zhc1.uape.model.BusinessUnit;
import io.zhc1.uape.model.Category;
import io.zhc1.uape.model.TransactionType;
import io.zhc1.uape.persistence.TransactionTypeJpaRepository;
import io.zhc1.uape.service.BusinessUnitService;
import io.zhc1.uape.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
class CategoryController {
    private final CategoryService categoryService;
    private final BusinessUnitService businessUnitService;
    private final TransactionTypeJpaRepository transactionTypeJpaRepository;

    /** GET /api/categories - Listar todas as categorias de uma unidade de negócio */
    @GetMapping
    CategoryResponse getCategories(
            AuthToken authToken, @RequestParam(value = "businessUnit", required = false) Long businessUnitId) {

        // Se não especificar businessUnit, usa a do usuário logado
        if (businessUnitId == null) {
            businessUnitId = authToken.businessUnitId();
        }

        BusinessUnit businessUnit = businessUnitService
                .findById(businessUnitId)
                .orElseThrow(() -> new RuntimeException("BusinessUnit não encontrada"));

        var categories = categoryService.getCategoriesByBusinessUnit(
                businessUnit, authToken.businessUnitId(), authToken.isAdmin());

        return new CategoryResponse(categories);
    }

    /** GET /api/categories/{id} - Obter uma categoria específica */
    @GetMapping("/{id}")
    CategoryResponse getCategory(AuthToken authToken, @PathVariable Integer id) {
        Category category = categoryService.getCategory(id, authToken.businessUnitId(), authToken.isAdmin());
        return new CategoryResponse(category);
    }

    /** GET /api/categories/by-transaction-type - Listar categorias por tipo de transação */
    @GetMapping("/by-transaction-type")
    CategoryResponse getCategoriesByTransactionType(
            AuthToken authToken,
            @RequestParam(value = "businessUnit", required = false) Long businessUnitId,
            @RequestParam("transactionTypeId") Integer transactionTypeId) {

        // Se não especificar businessUnit, usa a do usuário logado
        if (businessUnitId == null) {
            businessUnitId = authToken.businessUnitId();
        }

        BusinessUnit businessUnit = businessUnitService
                .findById(businessUnitId)
                .orElseThrow(() -> new RuntimeException("BusinessUnit não encontrada"));

        TransactionType transactionType = transactionTypeJpaRepository
                .findById(transactionTypeId)
                .orElseThrow(() -> new RuntimeException("TransactionType não encontrado"));

        var categories = categoryService.getCategoriesByBusinessUnitAndTransactionType(
                businessUnit, transactionType, authToken.businessUnitId(), authToken.isAdmin());

        return new CategoryResponse(categories);
    }

    /** POST /api/categories - Criar uma nova categoria */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CategoryResponse createCategory(AuthToken authToken, @RequestBody CategoryRequest request) {

        TransactionType transactionType = transactionTypeJpaRepository
                .findById(request.transactionTypeId())
                .orElseThrow(() -> new RuntimeException("TransactionType não encontrado"));

        // Se não especificar businessUnit, usa a do usuário logado
        Long businessUnitId = request.businessUnitId() != null ? request.businessUnitId() : authToken.businessUnitId();

        BusinessUnit businessUnit = businessUnitService
                .findById(businessUnitId)
                .orElseThrow(() -> new RuntimeException("BusinessUnit não encontrada"));

        Category category = categoryService.createCategory(
                request.name(), transactionType, businessUnit, authToken.businessUnitId(), authToken.isAdmin());

        return new CategoryResponse(category);
    }

    /** PUT /api/categories/{id} - Atualizar uma categoria */
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            AuthToken authToken, @PathVariable Integer id, @RequestBody CategoryRequest request) {

        TransactionType transactionType = transactionTypeJpaRepository
                .findById(request.transactionTypeId())
                .orElseThrow(() -> new RuntimeException("TransactionType não encontrado"));

        Category category = categoryService.updateCategory(
                id, request.name(), transactionType, authToken.businessUnitId(), authToken.isAdmin());

        return ResponseEntity.ok(new CategoryResponse(category));
    }

    /** DELETE /api/categories/{id} - Deletar uma categoria */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(AuthToken authToken, @PathVariable Integer id) {
        categoryService.deleteCategory(id, authToken.businessUnitId(), authToken.isAdmin());
        return ResponseEntity.noContent().build();
    }
}
