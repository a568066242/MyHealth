package com.lzj.health.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lzj.health.bean.Account;
import com.lzj.health.bean.Bloodpressure;
import com.lzj.health.dao.BaseDataDao;
import com.lzj.health.dao.BloodPressureDao;

@Service
@Transactional
public class BloodPressureService extends BaseDataService<Bloodpressure>{
	
	@Autowired
	private BloodPressureDao bloodPressureDao;

	@Override
	protected void setUser(Account a, Bloodpressure data) {
		data.setUser(a);
	}

	@Override
	protected BaseDataDao getDao() {
		return bloodPressureDao;
	}

	@Override
	protected Class<Bloodpressure> getClas() {
		// TODO Auto-generated method stub
		return Bloodpressure.class;
	}
	
	public List<Bloodpressure> queryBloodpressure(int userid,String time){
		return bloodPressureDao.queryReturnList(userid, time, Bloodpressure.class);
	}
	
}
