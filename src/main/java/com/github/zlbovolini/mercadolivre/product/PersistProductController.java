package com.github.zlbovolini.mercadolivre.product;

import com.github.zlbovolini.mercadolivre.user.AuthenticatedUser;
import com.github.zlbovolini.mercadolivre.user.User;
import com.github.zlbovolini.mercadolivre.validation.constraint.NotEmptyElement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/products")
public class PersistProductController {

    @PersistenceContext
    private final EntityManager entityManager;

    private final RemoteStorageService storageService;

    public PersistProductController(EntityManager entityManager, RemoteStorageService storageService) {
        this.entityManager = entityManager;
        this.storageService = storageService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Void> save(@Valid @RequestBody CreateProductRequest createProductRequest, @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {

        User owner = entityManager.find(User.class, authenticatedUser.getId());
        Product product = createProductRequest.toModel(entityManager::find, owner);

        entityManager.persist(product);

        return ResponseEntity.ok().build();
    }

    @Transactional
    @PostMapping("/{id}/images")
    public ResponseEntity<Void> addProductImage(@Valid Identifier identifier,
                                                @Valid @Size(min = 1) @NotEmptyElement List<MultipartFile> images,
                                                @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {

        Long ownerId = authenticatedUser.getId();

        List<Resource> resources = storageService.upload(ownerId, images);

        Product product = entityManager.find(Product.class, identifier.getId());
        product.addProductImages(resources);

        entityManager.merge(product);

        return ResponseEntity.ok().build();
    }
}
