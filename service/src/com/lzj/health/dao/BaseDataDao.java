package com.lzj.health.dao;

import java.util.List;

public interface BaseDataDao<T> extends BaseDao<T>{
	
	public int queryCounts(int userid,Class<T> clas);
	
	public List<T> queryReturnList(int id, String time, Class<T> clas);
}
