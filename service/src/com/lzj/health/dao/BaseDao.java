package com.lzj.health.dao;

import java.util.List;

import javax.persistence.EntityManager;

/**
 * 通用DAO接口
 * @author lzj
 *
 * @param <T>
 */
public interface BaseDao<T> {
	void save(T entity);
	void update(T newEntity);
	void delete(T entity);
	T getById(Class<T> clas, Object id);
	
}
