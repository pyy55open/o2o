package com.csy.o2o.service;

import java.io.File;
import java.io.InputStream;

import com.csy.o2o.dto.ShopException;
import com.csy.o2o.entity.Shop;

public interface ShopService {

	ShopException addShop(Shop shop,InputStream fileIS,String fileName);
}
