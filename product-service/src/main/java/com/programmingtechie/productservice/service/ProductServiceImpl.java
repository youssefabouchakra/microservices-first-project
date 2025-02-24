package com.programmingtechie.productservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.programmingtechie.productservice.dto.ProductRequestDTO;
import com.programmingtechie.productservice.dto.ProductResponseDTO;
import com.programmingtechie.productservice.model.Product;
import com.programmingtechie.productservice.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepository productRepository;
	
//	@Autowired
//	public ProductServiceImpl(final ProductRepository productRepository) {
//	    this.productRepository = productRepository;
//	  }
	@Override
	public void createProduct(ProductRequestDTO productRequestDTO) {
		Product product = Product.builder()
				.name(productRequestDTO.getName())
				.description(productRequestDTO.getDescription())
				.price(productRequestDTO.getPrice())
				.build();

		productRepository.save(product);
		log.info("Product {} is saved", product.getId());
	}

	@Override
	public List<ProductResponseDTO> getAllProducts() {
		List<Product> productList =  productRepository.findAll();
		//productList.stream().map(prod => mapToProductResponse(prod));
		return productList.stream().map(this::mapToProductResponse).toList();
	}

	private ProductResponseDTO mapToProductResponse(Product product) {
		return ProductResponseDTO.builder()
				.id(product.getId())
				.name(product.getName())
				.description(product.getDescription())
				.price(product.getPrice())
				.build();
	}
}
