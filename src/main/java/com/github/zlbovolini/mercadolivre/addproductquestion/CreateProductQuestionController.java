package com.github.zlbovolini.mercadolivre.addproductquestion;

import com.github.zlbovolini.mercadolivre.email.SendEmailService;
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
@RequestMapping("/api/v1/products/{id}/questions")
public class CreateProductQuestionController {

    @PersistenceContext
    private final EntityManager entityManager;

    private final SendEmailService sendEmailService;

    public CreateProductQuestionController(EntityManager entityManager, SendEmailService sendEmailService) {
        this.entityManager = entityManager;
        this.sendEmailService = sendEmailService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Void> save(@PathVariable Long id,
                                     @Valid @RequestBody CreateProductQuestionRequest question,
                                     @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {

        Product product = Optional.ofNullable(entityManager.find(Product.class, id))
                .orElseThrow(() -> new ResourceNotFoundException(format("Produto com id %d não encontrado", id)));

        User user = entityManager.find(User.class, authenticatedUser.getId());

        product.addQuestion(question, user);
        entityManager.merge(product);

        String productOwnerEmail = product.getOwnerEmail();
        String message = format("Nova pergunta para o produto %s do usuário %s:\n%s",
                product.getName(), user.getEmail(), question.getTitle());

        sendEmailService.send(productOwnerEmail, message);

        return ResponseEntity.ok().build();
    }
}
