package com.lzj.health.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lzj.health.bean.Account;
import com.lzj.health.bean.Accountbasic;
import com.lzj.health.bean.Accounthealth;
import com.lzj.health.dao.UserBasicDao;
import com.lzj.health.dao.UserDao;
import com.lzj.health.dao.UserHealthDao;

@Service
@Transactional
public class AccountService {
	@Autowired
	UserDao userDao;
	@Autowired
	UserBasicDao userBasicDao;
	
	@Autowired
	UserHealthDao userHealthDao;
	/**
	 * ���˺ŵ�½
	 */
	public static final int NAME_TYPE = 1;
	/**
	 * �Ե绰��½
	 */
	public static final int PHONE_TYPE = 2;
	/**
	 * �������½
	 */
	public static final int EMAIL_TYPE = 3;
	
	/**
	 * �û���½�߼�
	 * @param accout
	 * @param pwd
	 * @param type
	 * @return Account
	 */
	public Account login(String accout,String pwd,int type){
		Account a = null;
		switch (type) {
		case NAME_TYPE:
			a = userDao.findByName(accout);
			break;
		case PHONE_TYPE:
			a = userDao.findByPhone(accout);
			break;
		case EMAIL_TYPE:
			a = userDao.findByEmail(accout);
			break;
		default:
			break;
		}
		
		if(a!=null && a.getPwd().equals(pwd))
			return a;
		a = null;
		return a;
		
	}
	
	/**
	 * �жϴ��û��Ƿ���Ե�½
	 * @param accout
	 * @param pwd
	 * @param type
	 * @return
	 */
	public boolean canLogin(String accout,String pwd,int type){
		return login(accout, pwd, type)==null?false:true;
	}
	
	/**
	 * ע��
	 * @param account
	 * @return
	 */
	public boolean register(Account account
			,Accountbasic accountbasic){
		try {
			userDao.save(account);
			System.out.println(account.getId());
			accountbasic.setId(account.getId());
			userBasicDao.save(accountbasic);
			Accounthealth accounthealth = new Accounthealth();
			accounthealth.setId(account.getId());
			userHealthDao.save(accounthealth);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * �Ƿ��ע��
	 * @param account
	 * @return
	 */
	public boolean canRegister(Account account){
		Account user = userDao.findByName(account.getUserName());
		if(user == null)
			return true;
		else
			return false;
	}
	
	/**
	 * ͨ��ID��ѯAccount
	 * @param id
	 * @return
	 */
	public Account findById(Integer id){
		return userDao.getById(Account.class, id);
	}
	
	
	
	public boolean saveHealth(Accounthealth entity){
		try{
			userHealthDao.save(entity);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	public boolean updateHealth(Accounthealth newEntity){
		try{
			userHealthDao.update(newEntity);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public Boolean updateBasic(Accountbasic ah) {
		try{
			userBasicDao.update(ah);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	
	public Accountbasic getBasic(int id){
		return userBasicDao.getById(Accountbasic.class, id);
	}
	
	public Accounthealth getHealth(int id){
		return userHealthDao.getById(Accounthealth.class, id);
	}
	
}
