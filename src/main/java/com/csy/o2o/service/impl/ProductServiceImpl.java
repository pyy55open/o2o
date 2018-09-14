package com.csy.o2o.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.csy.o2o.util.PageCalculate;
import com.csy.o2o.util.PathUtil;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	ProductDao productDao;
	
	@Autowired
	ProductImgDao productImgDao;
	
	Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
	
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
				log.error("添加商品详情图异常:"+e.getMessage());
				throw new ProductOperationException("添加商品详情图失败:"+e.getMessage());
			}
		}
	}

	@Override
	public Product queryByProductID(Long productID) {
		return productDao.queryByProductID(productID);
	}

	@Override
	public ProductExcution queryProductOfShop(Product product,int pageIndex,int pageSize) {
		int rowIndex = PageCalculate.calculateRowIndex(pageIndex, pageSize);
		List<Product> productsList = productDao.queryProductList(product, rowIndex, pageSize);
		int count =productDao.quertCountOfProduct(product);
		ProductExcution productExcution = new ProductExcution();
		productExcution.setProductlist(productsList);
		productExcution.setCount(count);
		return productExcution;
	}

	@Override
	public ProductExcution modifyProduct(Product product, ImgHolder ih, List<ImgHolder> ihList)
			throws ProductOperationException {
		if(product != null){
			product.setUpdateTime(new Date());
			if(ih!= null){//处理缩略图
				if(product.getImgAddr()!=null){//商品原缩略图删除
					ImgUtil.deleteFile(product.getImgAddr());
				}
				addProductImg(product,ih);
			}
			//处理商品详情图  1删  2换新
			List<ProductImg> piList = productImgDao.queryProductImgList(product.getProductid());
			if(piList!=null&&piList.size()>0){//如果有详情图记录，循环删
				for (int i = 0; i < piList.size(); i++) {
					ImgUtil.deleteFile(piList.get(i).getImgAddr());
					productImgDao.deleteProductImgByProductId(piList.get(i).getProductid());
				}
			}
			if(ihList!=null && ihList.size()>0){//如果有图片
				addProductImgList(product, ihList);
			}
			int num = productDao.modifyProduct(product);
			if(num <=0){
				return new ProductExcution(ProductStateEnum.INNER_ERROR);
			}
			return new ProductExcution(ProductStateEnum.SUCCESS, product);
		}else{
			return new ProductExcution(ProductStateEnum.EMPTY_LIST);
		}
	}

}
