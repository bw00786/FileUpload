package com.atlantic.FileUpload.repositories;

import com.atlantic.FileUpload.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository  extends CrudRepository<Product, String> {
    @Override
    void delete(Product deleted);
}

