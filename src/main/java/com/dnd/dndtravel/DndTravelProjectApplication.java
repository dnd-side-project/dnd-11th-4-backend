package com.dnd.dndtravel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class DndTravelProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(DndTravelProjectApplication.class, args);
	}

}
