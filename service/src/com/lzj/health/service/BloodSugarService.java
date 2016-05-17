package com.lzj.health.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lzj.health.bean.Account;
import com.lzj.health.bean.Bloodsugar;
import com.lzj.health.dao.BaseDao;
import com.lzj.health.dao.BaseDataDao;
import com.lzj.health.dao.BloodSugarDao;

@Service
@Transactional
public class BloodSugarService extends BaseDataService<Bloodsugar>{
	
	@Autowired
	private BloodSugarDao dao;
	
	@Override
	protected void setUser(Account a, Bloodsugar data) {
		data.setUser(a);
	}

	@Override
	protected BaseDataDao getDao() {
		return dao;
	}

	@Override
	protected Class<Bloodsugar> getClas() {
		// TODO Auto-generated method stub
		return Bloodsugar.class;
	}


	public List<Bloodsugar> queryBloodSugar(int userid, String time) {
		return dao.queryReturnList(userid, time, Bloodsugar.class);
	}
	
}
