package com.lzj.health.dao.impl;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.lzj.health.bean.Bloodpressure;
import com.lzj.health.dao.BloodPressureDao;

@Repository
public class BloodPressureDaoImpl 
					extends BaseDataDaoImpl<Bloodpressure>
						implements BloodPressureDao{


}
