/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 27/10/2011
 * Project: UnaCloudEntities
 * Package: com.manuelvieda.unacloud.repository.impl
 * File:    JpaMonitoringlogDao.java
 */
package com.manuelvieda.unacloud.repository.impl;

import javax.ejb.Stateless;

import com.manuelvieda.unacloud.repository.dao.MonitoringLogDao;

/**
 * Session Bean implementation class MonitoringLogDao
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version	1.0
 * @since	1.0
 */
@Stateless(mappedName="monitoringLogDao")
public class JpaMonitoringlogDao extends JpaGeneric implements MonitoringLogDao {

}
