package com.lzj.health.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lzj.health.bean.Account;
import com.lzj.health.dao.BaseDao;
import com.lzj.health.dao.BaseDataDao;

@Service
@Transactional
public abstract class BaseDataService<DATA> {
	
	/**
	 * 添加一条健康数据
	 * @param a
	 * @param data
	 * @return
	 */
	public boolean addOneData(Account a
			,DATA data) {
	setUser(a,data);
	try {
		getDao().save(data);
	} catch (Exception e) {
		e.printStackTrace();
		return false;
	}
	
	return true;
	}
	
	public int queryCounts(int userid) {
		return getDao().queryCounts(userid,getClas());
		
	}
	
	/**
	 * 获取最新的N条数据
	 * @return
	 */
	public List<DATA> getDatasLatestN(int n) {
		
		return null;
		
	}
	
	
	
	protected abstract void setUser(Account a,DATA data);
	
	protected abstract BaseDataDao getDao();
	
	protected abstract Class<DATA> getClas();
}
