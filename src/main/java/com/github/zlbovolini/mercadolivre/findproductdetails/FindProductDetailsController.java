package com.github.zlbovolini.mercadolivre.findproductdetails;

import com.github.zlbovolini.mercadolivre.exception.ResourceNotFoundException;
import com.github.zlbovolini.mercadolivre.product.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products/{id}/details")
public class FindProductDetailsController {

    @PersistenceContext
    private final EntityManager entityManager;

    public FindProductDetailsController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @GetMapping
    public ResponseEntity<ProductDetailsResponse> find(@PathVariable Long id) {
        Product product = Optional.ofNullable(entityManager.find(Product.class, id))
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        return ResponseEntity.ok(new ProductDetailsResponse(product));
    }
}
