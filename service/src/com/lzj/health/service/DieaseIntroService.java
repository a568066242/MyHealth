package com.lzj.health.service;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lzj.health.bean.DieaseCategory;
import com.lzj.health.bean.DieaseInfo;

@Service
@Transactional
public class DieaseIntroService {

	@PersistenceContext
	private EntityManager em;
	
	public List<DieaseCategory> getBigCategory(){
		Query query = em.createNamedQuery("DieaseCategory.getBigCategory");
		List<DieaseCategory> resultList = query.getResultList();
		
		return resultList;
	}
	
	public List<DieaseCategory> getSubCategory(int pid) {
//		Query query = em.createNativeQuery("subcategory");
		Query query = em.createNamedQuery("DieaseCategory.getSubCategory");
		query.setParameter(1, pid);
		List<DieaseCategory> resultList = query.getResultList();
		return resultList;
	}
	
	public List<DieaseCategory> getDieases(int pid) {
//		Query query = em.createNativeQuery("subcategory");
		Query query = em.createNamedQuery("DieaseCategory.getSubCategory");
		query.setParameter(1, pid);
		List<DieaseCategory> resultList = query.getResultList();
		return resultList;
	}
	
	public DieaseInfo getDieaseInfo(int id){
		Query createNamedQuery = em.createNamedQuery("DieaseInfo.find");
		createNamedQuery.setParameter(1, id);
		return (DieaseInfo) createNamedQuery.getSingleResult();
	}
	
}
