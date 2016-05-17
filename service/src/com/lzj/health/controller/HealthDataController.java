package com.lzj.health.controller;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lzj.health.bean.Account;
import com.lzj.health.bean.Bloodpressure;
import com.lzj.health.bean.Bloodsugar;
import com.lzj.health.bean.Oxygensaturation;
import com.lzj.health.bean.Pulse;
import com.lzj.health.service.AccountService;
import com.lzj.health.service.BloodPressureService;
import com.lzj.health.service.BloodSugarService;
import com.lzj.health.service.OxygenSaturationService;
import com.lzj.health.service.PluseService;

@Controller
@RequestMapping("/health")
public class HealthDataController {
	@Autowired
	BloodPressureService bloodPressureService;
	@Autowired
	BloodSugarService bloodSugarService;
	
	@Autowired
	OxygenSaturationService oxygenSaturationService;
	
	@Autowired
	PluseService pluseService;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	
	@RequestMapping(value = "/addOneBP")
	@ResponseBody
	public String addOneBP(Bloodpressure bp,Account a){
		bp.setId(null);
		System.out.println(bp);
		System.out.println(a);
		return String.valueOf(bloodPressureService.addOneData(a, bp));
	}
	
	@RequestMapping(value = "/addOneBSS")
	@ResponseBody
	public String addOneBP(Bloodsugar bs,Account a){
		bs.setId(0);
		System.out.println(bs);
		System.out.println(a);
		return String.valueOf(bloodSugarService.addOneData(a, bs));
	}
	
	@RequestMapping(value = "/addOneOS")
	@ResponseBody
	public String addOneOS(Oxygensaturation os,Account a){
		os.setId(0);
		System.out.println(os);
		System.out.println(a);
		return String.valueOf(oxygenSaturationService.addOneData(a, os));
	}
	
	@RequestMapping(value = "/addOnePulse")
	@ResponseBody
	public String addOnePulse(Pulse p,Account a){
		p.setId(0);
		System.out.println(p);
		System.out.println(a);
		return String.valueOf(pluseService.addOneData(a, p));
	}
	
	@RequestMapping(value="queryBP")
	@ResponseBody
	public String getBPById(int userid,String time){
		
		List<Bloodpressure> data = bloodPressureService.queryBloodpressure(userid, time);
		String string = "[]";
		try {
			string = objectMapper.writeValueAsString(data);
		} catch ( IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return string;
	}
	
	@RequestMapping(value="queryPluse")
	@ResponseBody
	public String getPluseById(int userid,String time){
		
		List<Pulse> data = pluseService.queryPluse(userid, time);
		String string = "[]";
		try {
			string = objectMapper.writeValueAsString(data);
		} catch ( IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return string;
	}
	
	@RequestMapping(value="queryBS")
	@ResponseBody
	public String getBSById(int userid,String time){
		
		List<Bloodsugar> data = bloodSugarService.queryBloodSugar(userid, time);
		String string = "[]";
		try {
			string = objectMapper.writeValueAsString(data);
		} catch ( IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return string;
	}
	
	@RequestMapping(value="queryOS")
	@ResponseBody
	public String getOSById(int userid,String time){
		List<Oxygensaturation> data = oxygenSaturationService.queryOxygensaturation(userid, time);
		String string = "[]";
		try {
			string = objectMapper.writeValueAsString(data);
		} catch ( IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return string;
	}
}
