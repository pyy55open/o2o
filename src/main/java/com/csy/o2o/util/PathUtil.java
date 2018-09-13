package com.csy.o2o.util;

public class PathUtil {

	private static String separator = System.getProperty("file.separator");
	
	public static String getImgBasePath() {
		String os = System.getProperty("os.name");
		String basePath = "";
		if(os.toLowerCase().startsWith("win")){
			basePath = "D:/img";
		}else{
			basePath = "/home/img";
		}
		basePath.replace("/", separator);
		return basePath;
	}
	
	public static String getShopImgPath(Long shopid){
		String imgPath = "/upload/" + shopid +"/";
		return imgPath.replace(separator,"/");
		
	}
}
