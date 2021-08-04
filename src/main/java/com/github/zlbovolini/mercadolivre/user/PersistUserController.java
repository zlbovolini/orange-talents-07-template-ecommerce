package com.github.zlbovolini.mercadolivre.user;

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
@RequestMapping("/api/v1/users")
public class PersistUserController {

    @PersistenceContext
    private final EntityManager entityManager;

    public PersistUserController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Void> save(@Valid @RequestBody CreateUserRequest createUserRequest) {
        User user = createUserRequest.toModel();

        entityManager.persist(user);

        return ResponseEntity.ok().build();
    }
}
