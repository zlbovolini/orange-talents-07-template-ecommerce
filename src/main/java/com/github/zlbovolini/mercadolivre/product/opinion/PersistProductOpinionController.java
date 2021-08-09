package com.github.zlbovolini.mercadolivre.product.opinion;

import com.github.zlbovolini.mercadolivre.exception.ResourceNotFoundException;
import com.github.zlbovolini.mercadolivre.product.Product;
import com.github.zlbovolini.mercadolivre.user.AuthenticatedUser;
import com.github.zlbovolini.mercadolivre.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

import static java.lang.String.format;

@RestController
@RequestMapping("/api/v1/products/{id}/opinions")
public class PersistProductOpinionController {

    @PersistenceContext
    private final EntityManager entityManager;

    public PersistProductOpinionController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Void> save(@PathVariable Long id,
                                     @Valid @RequestBody CreateProductOpinionRequest opinion,
                                     @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {

        Product product = Optional.ofNullable(entityManager.find(Product.class, id))
                .orElseThrow(() -> new ResourceNotFoundException(format("Produto com id %d não encontrado", id)));

        User user = entityManager.find(User.class, authenticatedUser.getId());

        product.addOpinion(opinion, user);
        entityManager.merge(product);

        return ResponseEntity.ok().build();
    }
}
