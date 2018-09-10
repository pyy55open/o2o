package com.csy.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csy.o2o.dao.ProductCategoryDao;
import com.csy.o2o.dto.ProductCategoryExcution;
import com.csy.o2o.entity.ProductCategory;
import com.csy.o2o.enu.ProductCategoryStateEnum;
import com.csy.o2o.exception.ProductCategoryOperationException;
import com.csy.o2o.service.ProductCategoryService;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService{

	@Autowired
	ProductCategoryDao productCategory;
	
	@Override
	public List<ProductCategory> getProductCategoryList(long shopId) {
		return productCategory.queryProductCategoryList(shopId);
	}

	@Override
	public ProductCategoryExcution batchInsertProductCategory(List<ProductCategory> pcList)
			throws ProductCategoryOperationException {
		if (pcList != null && pcList.size() > 0) {
			try {
				int effectedNum = productCategory.batchInsertProductCategory(pcList);
				if (effectedNum <= 0) {
					throw new ProductCategoryOperationException("店铺类别创建失败");
				} else {
					return new ProductCategoryExcution(ProductCategoryStateEnum.SUCCESS);
				}

			} catch (Exception e) {
				throw new ProductCategoryOperationException("batchAddProductCategory error: " + e.getMessage());
			}
		} else {
			return new ProductCategoryExcution(ProductCategoryStateEnum.EMPTY_LIST);
		}
	}

	@Override
	@Transactional//事务管理，删除该类别商品、删除商品类别 有两步
	public ProductCategoryExcution deleteProductCategory(long productCategoryID, long shopID) {
		// TODO Auto-generated method stub
		int i = productCategory.deleteProductCategory(productCategoryID, shopID);
		try{
			if(i>0){
				return new ProductCategoryExcution(ProductCategoryStateEnum.SUCCESS);
			}else{
				throw new ProductCategoryOperationException("删除失败。");
			}
		}catch(Exception e){
			throw new ProductCategoryOperationException("error:"+e.getMessage());
		}
	}

}
