package com.programmingtechie.productservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.programmingtechie.productservice.dto.ProductRequestDTO;
import com.programmingtechie.productservice.dto.ProductResponseDTO;
import com.programmingtechie.productservice.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {
	
	@org.springframework.beans.factory.annotation.Autowired
    private ProductService productService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createProduct(@RequestBody ProductRequestDTO productRequest) {
		productService.createProduct(productRequest);
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<ProductResponseDTO> getAllProducts(){
		return productService.getAllProducts();
	}

}
