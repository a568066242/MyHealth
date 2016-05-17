package com.lzj.health.controller;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.lzj.health.bean.Account;
import com.lzj.health.bean.Accountbasic;
import com.lzj.health.bean.Accounthealth;
import com.lzj.health.service.AccountService;

@Controller
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	AccountService accountService;
	
	private ObjectMapper mapper= new ObjectMapper();
	
	@RequestMapping("/test")
	public String test(){
		return "test";
	}
	
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public String loginRequest(String id,String pwd,Integer type){
		System.out.println("AccountController loginRequest");
		System.out.println(id+pwd);
		if (type == null)
			if (id.contains("@"))
				type = AccountService.EMAIL_TYPE;
			else if (id.matches("\\d{11}"))
				type = AccountService.PHONE_TYPE;
			else
				type = AccountService.NAME_TYPE;
		Account a = accountService.login(id, pwd, type );
		if(a == null)
			return "-1";
		return String.valueOf(a.getId());
	}
	
	@RequestMapping(value="/loginByName",method = RequestMethod.POST)
	@ResponseBody
	public String loginByName(String userName,String pwd){
		System.out.println("loginByName");
		Account account = accountService.login(userName, pwd, AccountService.NAME_TYPE);
		if(account != null)
			return String.valueOf(account.getId());
		else
			return "-1";
	}
	
	@RequestMapping(value="/register",method = RequestMethod.POST)
	@ResponseBody
	public Boolean register(Account a,Accountbasic accountbasic) {
		System.out.println("register");
		boolean canRegister = accountService.canRegister(a);
		System.out.println("canRegister:"+canRegister);
		if(!canRegister){
			System.out.println("can't register and return false");
			return new Boolean(false);
		}
		boolean success = false;
		try{
			success = accountService.register(a,accountbasic);
		}catch(Exception e){
			return new Boolean(false);
		}
		return new Boolean(success);
	}
	
	
	@RequestMapping(value="updatehealth",method=RequestMethod.POST)
	@ResponseBody
	public Boolean updateHealth(Accounthealth ah){
		System.out.println("update "+ah);
		return accountService.updateHealth(ah);
	}
	
	@RequestMapping(value="updatebasic",method=RequestMethod.POST)
	@ResponseBody
	public Boolean updateBasic(Accountbasic ah){
		System.out.println("update "+ah);
		return accountService.updateBasic(ah);
	}
	
	@RequestMapping(value="getbasic",produces="text/json;charset=UTF-8")
	@ResponseBody
	public String getBasicInfo(int id){
		Accountbasic basic = accountService.getBasic(id);
		String json = null;
		try {
			json = mapper.writeValueAsString(basic);
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
	
	@RequestMapping(value="gethealth")
	@ResponseBody
	public String getHealthInfo(int id){
		Accounthealth basic = accountService.getHealth(id);
		String json = null;
		try {
			json = mapper.writeValueAsString(basic);
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
		System.out.println(json);
		return json;
	}
	
}
