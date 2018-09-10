package com.csy.o2o.service;

import java.util.List;

import com.csy.o2o.dto.ImgHolder;
import com.csy.o2o.dto.ProductExcution;
import com.csy.o2o.entity.Product;
import com.csy.o2o.exception.ProductOperationException;

public interface ProductService {

	ProductExcution addProduct(Product product,ImgHolder ih,List<ImgHolder> ihList) throws ProductOperationException;
}
