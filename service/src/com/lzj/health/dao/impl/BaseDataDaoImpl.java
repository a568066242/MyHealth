package com.lzj.health.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.lzj.health.dao.BaseDataDao;

@Repository
public  class BaseDataDaoImpl<T> extends BaseDaoImpl<T>
				implements BaseDataDao<T>{

	@Override
	public int queryCounts(int userid,Class<T> clas) {
		EntityManager em = getEm();
		String stringQuery = "select count(data.id) from "
				+ clas.getSimpleName() + " data where data.userid =?1";
		Query createQuery = em.createQuery(stringQuery);
		createQuery.setParameter(1, userid);
		Object singleResult = createQuery.getSingleResult();
		return (int) singleResult;
	}
	
	
	
	@Override
	public List<T> queryReturnList(int userid, String time,Class<T> clas) {
		System.out.println(clas.getSimpleName());
		EntityManager em = getEm();
		String stringQuery = "select data from "
				+ clas.getSimpleName() + " data where data.user.id =?1 and data.date >= ?2 order by data.date desc";
		Query createQuery = em.createQuery(stringQuery);
		createQuery.setParameter(1, userid);
		try {
			createQuery.setParameter(2, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return createQuery.getResultList();
	}


}
