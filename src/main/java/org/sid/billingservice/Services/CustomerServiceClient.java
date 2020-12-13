package org.sid.billingservice.Services;

import org.sid.billingservice.Model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@FeignClient(name="CUSTOMER-SERVICE")
public interface CustomerServiceClient {
    @GetMapping(path = "/customers/{id}?projection=fullCustomer")
    Customer findCustomerById(@PathVariable("id") Long id);

}
