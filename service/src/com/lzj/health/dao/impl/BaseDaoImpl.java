package com.lzj.health.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.lzj.health.dao.BaseDao;

@Repository
public class BaseDaoImpl<T> implements BaseDao<T> {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void save(T entity) {
		em.persist(entity);
		
	}

	@Override
	public T getById(Class<T> clas, Object id) {
		return em.find(clas, id);
	}

	@Override
	public void update(T newEntity) {
		em.merge(newEntity);
	//	em.refresh(newEntity);
	}

	@Override
	public void delete(T entity) {
		em.remove(entity);
	}
	
	public EntityManager getEm() {
		return em;
	}

	
	
		
}
