package com.lzj.health.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lzj.health.bean.DieaseCategory;
import com.lzj.health.bean.DieaseInfo;
import com.lzj.health.service.DieaseIntroService;


@Controller
@RequestMapping(value="/diease")
public class DieaseIntroController {
	
	@Autowired
	DieaseIntroService dieaseIntroService;
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@RequestMapping(value="/bigcategory",produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String getBigCategory(){
		List<DieaseCategory> bigCategory = dieaseIntroService.getBigCategory();
		Collections.sort(bigCategory);
		System.out.println(Arrays.toString(bigCategory.toArray()));
		String json = null;
		try {
			json = objectMapper .writeValueAsString(bigCategory);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	
	@RequestMapping(value="/subcategory",produces="text/json;charset=UTF-8")
	@ResponseBody
	public String getSubCate(int pid) {
		List<DieaseCategory> subCategory = dieaseIntroService.getSubCategory(pid);
		System.out.println(Arrays.toString(subCategory.toArray()));
		String json = null;
		try {
			json = objectMapper.writeValueAsString(subCategory);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	
	
	@RequestMapping(value="/getdiease",produces="text/json;charset=UTF-8")
	@ResponseBody
	public String getDiease(int pid){
		List<DieaseCategory> subCategory = dieaseIntroService.getSubCategory(pid);
		System.out.println(Arrays.toString(subCategory.toArray()));
		String json = null;
		try {
			json = objectMapper.writeValueAsString(subCategory);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	
	@RequestMapping(value="/dieaseinfo",produces="text/json;charset=UTF-8")
	@ResponseBody
	public String getDieaseInfo(int id){
		DieaseInfo dieaseInfo = dieaseIntroService.getDieaseInfo(id);
		String json = null;
		try {
			json = objectMapper.writeValueAsString(dieaseInfo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
}
