package com.lzj.health.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.lzj.health.bean.Account;
import com.lzj.health.dao.UserDao;

@Repository
public class UserDaoImpl extends BaseDaoImpl<Account>implements UserDao{
	
	@Override
	public Account findByName(String name) {
		final String query = "select a from Account a where a.userName =?1";
		Query createQuery = super.getEm().createQuery(query);
		createQuery.setParameter(1, name);
		List resultList = createQuery.getResultList();
		return resultList.size()>0?(Account) resultList.get(0):null;
	}
	
	@Override
	public Account findByPhone(String phone) {
		final String query = "select a from Account a where a.phone =?1";
		Query createQuery = getEm().createQuery(query);
		createQuery.setParameter(1, phone);
		List resultList = createQuery.getResultList();
		return resultList.size()>0?(Account) resultList.get(0):null;
	}

	@Override
	public Account findByEmail(String email) {
		final String query = "select a from Account a where a.email =?1";
		Query createQuery = getEm().createQuery(query);
		createQuery.setParameter(1, email);
		List resultList = createQuery.getResultList();
		return resultList.size()>0?(Account) resultList.get(0):null;
	}
	


}
