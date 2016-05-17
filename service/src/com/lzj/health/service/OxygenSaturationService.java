package com.lzj.health.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lzj.health.bean.Account;
import com.lzj.health.bean.Oxygensaturation;
import com.lzj.health.dao.BaseDataDao;
import com.lzj.health.dao.OxygenSaturationDao;

@Service
@Transactional
public class OxygenSaturationService extends BaseDataService<Oxygensaturation> {
	
	@Autowired
	private OxygenSaturationDao dao;
	
	@Override
	protected void setUser(Account a, Oxygensaturation data) {
		data.setUser(a);
	}

	@Override
	protected BaseDataDao getDao() {
		return dao;
	}

	@Override
	protected Class<Oxygensaturation> getClas() {
		// TODO Auto-generated method stub
		return Oxygensaturation.class;
	}

	public List<Oxygensaturation> queryOxygensaturation(int userid, String time) {
		return dao.queryReturnList(userid, time, Oxygensaturation.class);
	}

}
