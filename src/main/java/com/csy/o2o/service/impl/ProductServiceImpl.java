package com.csy.o2o.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csy.o2o.dao.ProductDao;
import com.csy.o2o.dao.ProductImgDao;
import com.csy.o2o.dto.ImgHolder;
import com.csy.o2o.dto.ProductExcution;
import com.csy.o2o.entity.Product;
import com.csy.o2o.entity.ProductImg;
import com.csy.o2o.enu.ProductStateEnum;
import com.csy.o2o.exception.ProductOperationException;
import com.csy.o2o.service.ProductService;
import com.csy.o2o.util.ImgUtil;
import com.csy.o2o.util.PathUtil;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	ProductDao productDao;
	
	@Autowired
	ProductImgDao productImgDao;
	
	@Override
	@Transactional
	public ProductExcution addProduct(Product product, ImgHolder ih, List<ImgHolder> ihList) throws ProductOperationException{
		if(product!=null&&product.getShop()!=null&&product.getShop().getShopid()!=null){
			product.setCreateTime(new Date());
			product.setUpdateTime(new Date());
			product.setEnableStatus(1);
			if(ih!=null){
				//添加商品缩略图
				addProductImg(product, ih);
			}
			try{
				int i = productDao.addProduct(product);
				if(i <= 0){
					throw new ProductOperationException("商品创建失败。");
				}
			}catch(Exception e){
				throw new ProductOperationException("创建商品失败:"+e.getMessage());
			}
			if(ihList != null && ihList.size() > 0){
				//批量添加商品详情图
				addProductImgList(product, ihList);
			}
			return new ProductExcution(ProductStateEnum.SUCCESS,product);
		}else{
			return new ProductExcution(ProductStateEnum.EMPTY_LIST);
		}
	}
	
	private void addProductImg(Product product, ImgHolder thumbnail) {
		// 获取shop图片目录的相对值路径
		String dest = PathUtil.getShopImgPath(product.getShop().getShopid());
		String shopImgAddr = ImgUtil.thumbnailImg(thumbnail, dest);
		product.setImgAddr(shopImgAddr);
	}
	
	/**
	 * 批量添加商品详情图
	 * @param product
	 * @param ihList
	 */
	private void addProductImgList(Product product, List<ImgHolder> ihList) {
		String dest = PathUtil.getShopImgPath(product.getShop().getShopid());
		List<ProductImg> piList = new ArrayList<ProductImg>();
		for (ImgHolder imgHolder : ihList) {
			String imgAddr = ImgUtil.thumbnailDetailImg(imgHolder,dest);
			ProductImg pi = new ProductImg();
			pi.setCreateTime(new Date());
			pi.setProductid(product.getProductid());
			pi.setImgAddr(imgAddr);
			piList.add(pi);
		}
		if(piList.size()>0){
			try{
				//批量添加商品详情图
				int i = productImgDao.batchInsertProductImg(piList);
				if(i <= 0){
					throw new ProductOperationException("添加商品详情图失败。");
				}
			}catch(Exception e){
				throw new ProductOperationException("添加商品详情图失败:"+e.getMessage());
			}
		}
	}

}
