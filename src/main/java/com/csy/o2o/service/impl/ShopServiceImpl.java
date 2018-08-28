package com.csy.o2o.service.impl;

import java.io.File;
import java.io.InputStream;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csy.o2o.dao.ShopDao;
import com.csy.o2o.dto.ShopException;
import com.csy.o2o.entity.Shop;
import com.csy.o2o.enu.ShopEnum;
import com.csy.o2o.exception.ShopOperationException;
import com.csy.o2o.service.ShopService;
import com.csy.o2o.util.ImgUtil;
import com.csy.o2o.util.PathUtil;

@Service
public class ShopServiceImpl implements ShopService{

	@Autowired
	private ShopDao shopDao;
	
	@Override
	@Transactional
	public ShopException addShop(Shop shop, InputStream fileIS,String fileName) {
		if(shop == null){
			return new ShopException(ShopEnum.NULL_SHOP);
		}
		try{
			shop.setCreateTime(new Date());
			shop.setUpdateTime(new Date());
			shop.setEnableStatus(0);
			int i = shopDao.insertShop(shop);
			if(i <= 0){
				throw new ShopOperationException("店铺创建失败。");
			}else{
				if(fileIS != null){
					try{
						String dest = PathUtil.getShopImgPath(shop.getShopid());
						String imgAddr = ImgUtil.thumbnailImg(fileIS, fileName,dest);
						shop.setPhoto(imgAddr);
					}catch(Exception e){
						throw new RuntimeException("====添加图片失败"+e.getMessage());
					}
					int j = shopDao.updateShop(shop);
					if(j <= 0){
						throw new ShopOperationException("====图片更新失败===");
					}
				}
			}
		}catch(Exception e){
			throw new ShopOperationException("=======addShopError   "+e.getMessage());
		}
		return new ShopException(ShopEnum.CHECK,shop);
	}
	
	
}
