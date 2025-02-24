package com.programmingtechie.product_service;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.assertions.Assertions;
import com.programmingtechie.productservice.ProductServiceApplication;
import com.programmingtechie.productservice.dto.ProductRequestDTO;
import com.programmingtechie.productservice.repository.ProductRepository;


@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@ContextConfiguration(classes = ProductServiceApplication.class)
class ProductServiceApplicationTests {

	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));
	
	@Autowired
	private MockMvc mockMVC;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private ProductRepository productRepo;
	/**
	 *   Add this property to Spring context dynamically before performing the test
	 * */
	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
		dynamicPropertyRegistry.add("sprng.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
		
	}

	@Test
	void testCreateProduct() throws Exception {
		ProductRequestDTO product = getProductRequest();
		//convert object to JSON
		String productRequestStirng = objectMapper.writeValueAsString(product);
		mockMVC.perform(MockMvcRequestBuilders.post("/api/product")
				.contentType(MediaType.APPLICATION_JSON)
				.content(productRequestStirng)
				).andExpect(MockMvcResultMatchers.status().isCreated());
		//mongoDBContainer.start();
		System.out.println("productRepo.findAll().size()=" + productRepo.findAll().size());
		Assertions.assertTrue(productRepo.findAll().size()==1);
		
	}

	@Test
	void testGetProduct() throws Exception {
		//convert object to JSON
		mockMVC.perform(MockMvcRequestBuilders.get("/api/product"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Iphone 14"));
//		        andExpect(MockMvcResultMatchers.content().string("[{\"id\":\"67b86043f1dfdc3db46eae3e\""
//		        		+ ",\"name\":\"Iphone 14\""
//		        		+ ",\"description\":\"Iphone 14 description\""
//		        		+ ",\"price\":1300}]"));
		
	}

	private ProductRequestDTO getProductRequest() {
		return ProductRequestDTO.builder().name("Iphone 14")
				.description("Iphone 14 description")
				.price(BigDecimal.valueOf(1300))
				.build();
	}
}
