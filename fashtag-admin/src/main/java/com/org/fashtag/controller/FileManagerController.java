package com.org.fashtag.controller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.swing.ImageIcon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.org.fashtag.controller.BaseController;
import com.org.fashtag.util.CommonUtils;


@Controller
public class FileManagerController extends BaseController {

	
	@RequestMapping(value = "/fileManager")
	public  ModelAndView filemanager(HttpServletRequest req) 
	{
	
		String field=req.getParameter("field");
		String thumb=req.getParameter("thumb");
		
		ModelAndView mv=new ModelAndView("fileManager");
	mv.addObject("field", field);
	mv.addObject("thumb", thumb);
		return mv;
	}
	
	private String getImageDir(HttpServletRequest req)
	{
		String imgDir=getAppSystemProperties(PRODUCT_IMAGE_DIR);
		return imgDir; 
	}
	
	@RequestMapping(value = "/getFiles" , method = RequestMethod.POST)
	public  @ResponseBody String getFiles(HttpServletRequest request)
	{
		

		String imgDir=getImageDir(request);
				
		File dir = new File(imgDir+request.getParameter("directory"));
File[] imageFiles = dir.listFiles(new FilenameFilter() {
	public boolean accept(File dir, String name) {
		return (name.toUpperCase().endsWith("PNG") || name
				.toUpperCase().endsWith("JPEG") 
				|| name
				.toUpperCase().endsWith("JPG")|| name
				.toUpperCase().endsWith("svg") );
	}
});
JSONArray jsonArr = new JSONArray();

try {
for (int fileIndex = 0; fileIndex < imageFiles.length; fileIndex++) {
	JSONObject jsonObj = new JSONObject();
	
	jsonObj.put("file", imageFiles[fileIndex]);
	jsonObj.put("filename", imageFiles[fileIndex].getName());
	jsonObj.put("size", 0);
	jsonArr.put(jsonObj);
}

	}
catch (JSONException e) {
	e.printStackTrace();
}

return jsonArr.toString();
	}
	
	
	
	
	@RequestMapping(value = "/getDirectories" , method = RequestMethod.POST)
	public  @ResponseBody String getDirectories(HttpServletRequest request)
	{
		
		String imgDir=getImageDir(request);
		
		File dir = new File(imgDir);
	
		File[] directories = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return new File(dir,name).isDirectory();
			}
		});
		
		
		
		JSONArray jsonArr = new JSONArray();

		try {
		for (int fileIndex = 0; fileIndex < directories.length; fileIndex++) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("data", directories[fileIndex].getName());
			JSONObject jsonObj2 = new JSONObject();
			jsonObj2.put("directory", directories[fileIndex].getName());
			jsonObj.put("attributes",jsonObj2);
			jsonObj.put("children"," ");
			jsonArr.put(jsonObj);
		}

			}
		catch (JSONException e) {
			e.printStackTrace();
		}
		
		return jsonArr.toString();
		
		}
	
	
	@RequestMapping(value = "/getActualImage")
	public @ResponseBody String getActualImage(HttpServletRequest req)
	{
		
		JSONObject json=new JSONObject();
		String imgUrl="";
		try{
			String selDir=req.getParameter("selDir");
			String url=getAppSystemProperties(PRODUCT_IMAGE_URL);
			
			if(!CommonUtils.isEmpty(selDir))
			{
				url+=selDir+"/";
			}
						
			
		imgUrl=url+req.getParameter("image");
		json.put("imgUrl",imgUrl);
		json.put("imgName",req.getParameter("image"));
		}
		catch(Exception e)
		{
			
		}
		return json.toString();
	}
	
	
	@RequestMapping(value = "/fileManagerUpload")
	public @ResponseBody String fileUpload(MultipartHttpServletRequest request)
	{
		
		
		String imgDir=getImageDir(request);
		JSONObject json=new JSONObject();
		
		try{
		MultipartFile mFile=request.getFile("image");
		File file=new File(imgDir+mFile.getOriginalFilename());
		file.setExecutable(true,false);
		byte[] bytes;
		bytes=mFile.getBytes();
		BufferedOutputStream buffStream =new BufferedOutputStream(new FileOutputStream(file));
		buffStream.write(bytes);
		buffStream.close();
		json.put("success", "Success: File uploaded succesfully");
		}
		catch(Exception e)
		{

			e.printStackTrace();
		}
		
		return json.toString();
	}
	
	
}
