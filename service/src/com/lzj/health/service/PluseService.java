package com.lzj.health.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lzj.health.bean.Account;
import com.lzj.health.bean.Bloodpressure;
import com.lzj.health.bean.Pulse;
import com.lzj.health.dao.BaseDataDao;
import com.lzj.health.dao.PulseDao;

@Service
@Transactional
public class PluseService extends BaseDataService<Pulse>{

	@Autowired
	private PulseDao dao;
	
	@Override
	protected void setUser(Account a, Pulse data) {
		data.setUser(a);
	}

	@Override
	protected BaseDataDao getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	@Override
	protected Class<Pulse> getClas() {
		// TODO Auto-generated method stub
		return Pulse.class;
	}

	public List<Pulse> queryPluse(int userid, String time) {
		return dao.queryReturnList(userid, time, Pulse.class);
	}

}
