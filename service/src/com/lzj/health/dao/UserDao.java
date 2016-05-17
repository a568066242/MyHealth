package com.lzj.health.dao;

import com.lzj.health.bean.Account;

public interface UserDao extends BaseDao<Account>{
	 Account findByPhone(String phone);
	 Account findByEmail(String email);
	Account findByName(String name);
	 
}
