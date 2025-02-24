package com.programmingtechie.productservice.service;

import java.util.List;

import com.programmingtechie.productservice.dto.ProductRequestDTO;
import com.programmingtechie.productservice.dto.ProductResponseDTO;

public interface ProductService {
	public void createProduct(ProductRequestDTO productRequestDTO);
	public List<ProductResponseDTO> getAllProducts();
}
