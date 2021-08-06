package com.github.zlbovolini.mercadolivre.product;

import com.github.zlbovolini.mercadolivre.user.AuthenticatedUser;
import com.github.zlbovolini.mercadolivre.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/products")
public class PersistProductController {

    @PersistenceContext
    private final EntityManager entityManager;

    public PersistProductController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Void> save(@Valid @RequestBody CreateProductRequest createProductRequest, @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {

        User owner = entityManager.find(User.class, authenticatedUser.getId());
        Product product = createProductRequest.toModel(entityManager::find, owner);

        entityManager.persist(product);

        return ResponseEntity.ok().build();
    }
}
