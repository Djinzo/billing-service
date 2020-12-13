package org.sid.billingservice;


import org.sid.billingservice.Model.Bill;
import org.sid.billingservice.Model.Customer;
import org.sid.billingservice.Model.ProductItem;
import org.sid.billingservice.Repository.BillRepository;
import org.sid.billingservice.Repository.ProductItemRepository;
import org.sid.billingservice.Services.CustomerServiceClient;
import org.sid.billingservice.Services.InventoryServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.util.Date;




@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {
    @Autowired
    InventoryServiceClient customerServiceClient;

    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }




    @Bean
    CommandLineRunner commandLineRunner(InventoryServiceClient inventoryServiceClient,CustomerServiceClient customerServiceClient,BillRepository billRepository, ProductItemRepository productItemRepository){
        return args -> {
            Bill bill=new Bill();
            bill.setBillingDate(new Date());
            Customer customer=customerServiceClient.findCustomerById(1L);
            bill.setCustomerID(1L);
            billRepository.save(bill);
            inventoryServiceClient.findAll().getContent().forEach(p->{
                productItemRepository.save(new ProductItem(null,null,p.getId(),p.getPrice(),(int)(1+Math.random()*1000),bill));
            });
        };
    }
}
