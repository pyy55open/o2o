package com.csy.o2o.service;

import java.io.File;
import java.io.InputStream;

import com.csy.o2o.dto.ImgHolder;
import com.csy.o2o.dto.ShopException;
import com.csy.o2o.entity.Shop;

public interface ShopService {
	
	public ShopException getShopList(Shop shop,int pageIndex,int pageSize);

	Shop getByShopId(Long shopid);
	
	ShopException modifyShop(Shop shop,ImgHolder ih);
	
	ShopException addShop(Shop shop,ImgHolder ih);
}
