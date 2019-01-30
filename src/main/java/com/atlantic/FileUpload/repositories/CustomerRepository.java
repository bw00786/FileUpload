package com.atlantic.FileUpload.repositories;


import com.atlantic.FileUpload.model.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerRepository  extends CrudRepository<Customer, String> {
    @Override
    void delete(Customer deleted);

}
