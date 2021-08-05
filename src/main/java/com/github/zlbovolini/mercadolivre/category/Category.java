package com.github.zlbovolini.mercadolivre.category;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String name;

    /**
     * Dependendo do uso, pode ocorrer StackOverflow se houver recursão.
     */
    @ManyToOne(optional = true)
    private Category superCategory;

    Category() {}

    Category(String name) {
        this.name = name;
    }

    Category(String name, Category superCategory) {
        this.name = name;
        this.superCategory = superCategory;
    }
}