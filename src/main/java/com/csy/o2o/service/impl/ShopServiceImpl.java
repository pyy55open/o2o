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

	@Override
	public Shop getByShopId(Long shopid) {
		return shopDao.queryByShopid(shopid);
	}

	@Override
	public ShopException modifyShop(Shop shop,InputStream is,String fileName) {
		if(shop==null||shop.getShopid()==null){
			return new ShopException(ShopEnum.NULL_SHOP);
		}else{
			//1是否需要处理图片
			if(is!=null){
				Shop newShop = shopDao.queryByShopid(shop.getShopid());
				if(newShop.getPhoto()!=null){
					ImgUtil.deleteFile(shop.getPhoto());
				}
				//工具类处理图片，获取图片保存地址
				String dest = PathUtil.getShopImgPath(shop.getShopid());
				String imgAddr = ImgUtil.thumbnailImg(is, fileName,dest);
				newShop.setPhoto(imgAddr);
			}
			//2更新店铺信息
			shop.setUpdateTime(new Date());
			int i = shopDao.updateShop(shop);
			if(i>0){
				shop = shopDao.queryByShopid(shop.getShopid());
				return new ShopException(ShopEnum.SUCCESS);
			}else{
				return new ShopException(ShopEnum.INNER_ERROR);
			}
		}
	}
	
	
}
