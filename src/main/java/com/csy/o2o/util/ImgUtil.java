package com.csy.o2o.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.csy.o2o.dto.ImgHolder;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImgUtil {
	
	private static String basePath =Thread.currentThread().getContextClassLoader().getResource("").getPath();
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyymmdd");
	private static Random random = new Random();
	private static Logger log = LoggerFactory.getLogger(ImgUtil.class); 
	
	/**
	 * 将CommonsMultipartFile转成file
	 * @param cfile
	 * @return
	 */
	public static File transferThumbnailtoFile(CommonsMultipartFile cfile){
		File file = new File(cfile.getOriginalFilename());
		try {
			cfile.transferTo(file);
		} catch (IllegalStateException | IOException e) {
			log.error(e.toString());
			e.printStackTrace();
		}
		return file;
	}
	
	public static String thumbnailImg(ImgHolder ih,String target){
		//新文件随机名称
		String realFilename = getRealFileName();
		//新文件的扩展名
		String extension = getExtension(ih.getName());
		//路径可能不存在 创建文件路径   
		makeDirPath(target);
		//文件保存路径
		String filePath = target + realFilename + extension;
		log.debug("======文件保存路径 ："+ filePath +"=========");
		File targetFile = new File(PathUtil.getImgBasePath()+filePath);
		log.debug("======文件全路径："+ PathUtil.getImgBasePath()+filePath+"==========");
		try{
		Thumbnails.of(ih.getInputStream()).size(200, 200)
		.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath+"/timg.jpg")), 0.25f)
		.outputQuality(0.8f).toFile(targetFile);
		}catch(IOException e){
			log.error(e.toString());
			e.printStackTrace();
		}
		return filePath;
	}
	
	public static String thumbnailDetailImg(ImgHolder ih,String target){
		//新文件随机名称
		String realFilename = getRealFileName();
		//新文件的扩展名
		String extension = getExtension(ih.getName());
		//路径可能不存在 创建文件路径   
		makeDirPath(target);
		//文件保存路径
		String filePath = target + realFilename + extension;
		log.debug("======文件保存路径 ："+ filePath +"=========");
		File targetFile = new File(PathUtil.getImgBasePath()+filePath);
		log.debug("======文件全路径："+ PathUtil.getImgBasePath()+filePath+"==========");
		try{
		Thumbnails.of(ih.getInputStream()).size(337, 640)
		.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath+"/timg.jpg")), 0.25f)
		.outputQuality(0.8f).toFile(targetFile);
		}catch(IOException e){
			log.error(e.toString());
			e.printStackTrace();
		}
		return filePath;
	}
	
	/**
	 * 创建文件保存路径
	 * @param target
	 */
	private static void makeDirPath(String target) {
		String filePath = PathUtil.getImgBasePath() + target;
		File file = new File(filePath);
		if(!file.exists()){
			file.mkdirs();
		}
	}

	private static String getExtension(String fileName) {
		log.info(fileName);
		return fileName.substring(fileName.lastIndexOf("."));
	}

	/**
	 * 生成随机文件名
	 * @return
	 */
	public static String getRealFileName() {
		int i = random.nextInt(89999)+10000;
		String filename = simpleDateFormat.format(new Date());
		
		return filename+i;
	}
	
	public static void deleteFile(String filePath){
		File fileOrpath = new File(PathUtil.getImgBasePath()+filePath);
		if(!fileOrpath.exists()){
			if(fileOrpath.isDirectory()){
				File file[] = fileOrpath.listFiles();
				for (int i = 0; i < file.length; i++) {
					file[i].delete();
				}
			}
			fileOrpath.delete();
		}
	}
}
