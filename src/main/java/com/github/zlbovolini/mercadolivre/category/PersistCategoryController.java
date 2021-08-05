package com.github.zlbovolini.mercadolivre.category;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/categories")
public class PersistCategoryController {

    @PersistenceContext
    private final EntityManager entityManager;

    public PersistCategoryController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Void> save(@Valid @RequestBody CreateCategoryRequest createCategoryRequest) {
        Category category = createCategoryRequest.toModel(entityManager::find);

        entityManager.persist(category);

        return ResponseEntity.ok().build();
    }
}
