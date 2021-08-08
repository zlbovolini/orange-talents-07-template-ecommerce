package com.github.zlbovolini.mercadolivre.product;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

interface RemoteStorageService {

    Resource upload(Long owner, MultipartFile multipartFile);

    List<Resource> upload(Long owner, List<MultipartFile> multipartFileList);
}
