package com.github.zlbovolini.mercadolivre.product;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FakeRemoteStorageService implements RemoteStorageService {

    @Override
    public Resource upload(@NotNull Long owner, @NotNull MultipartFile multipartFile)  {
        URI uri = URI.create("http://localhost/" + owner + "/products/images/" + multipartFile.getOriginalFilename());

        return new Resource(multipartFile.getOriginalFilename(), uri,
                multipartFile.getContentType(), multipartFile.getSize());
    }

    @Override
    public List<Resource> upload(@NotNull Long owner, @NotNull List<MultipartFile> multipartFileList) {
        return multipartFileList.stream()
                .map(file -> upload(owner, file))
                .collect(Collectors.toList());
    }
}
