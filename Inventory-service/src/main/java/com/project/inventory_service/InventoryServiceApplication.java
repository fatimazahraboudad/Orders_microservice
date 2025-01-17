package com.project.inventory_service;

import com.project.inventory_service.model.Inventory;
import com.project.inventory_service.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loaddata(InventoryRepository inventoryRepository) {

		return  args ->{
			Inventory inventory1 = new Inventory();
			inventory1.setSkuCode("iphone 13");
			inventory1.setQuantity(10);

			Inventory inventory2 = new Inventory();
			inventory2.setSkuCode("iphone 15");
			inventory2.setQuantity(5);
			Inventory inventory3 = new Inventory();
			inventory2.setSkuCode("iphone 11");
			inventory2.setQuantity(0);

			inventoryRepository.save(inventory1);
			inventoryRepository.save(inventory2);
		};
	}

}
