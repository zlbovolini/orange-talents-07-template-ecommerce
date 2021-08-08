package com.github.zlbovolini.mercadolivre.product;

import com.github.zlbovolini.mercadolivre.validation.constraint.NotEmptyElement;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.List;

class CreateProductImagesRequest {
    
    @Size(min = 1)
    @NotEmptyElement
    private final List<MultipartFile> images;

    public CreateProductImagesRequest(@NotEmptyElement @Size(min = 1) List<MultipartFile> images) {
        this.images = images;
    }

    public List<MultipartFile> getImages() {
        return Collections.unmodifiableList(images);
    }
}
