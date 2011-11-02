package com.manuelvieda.unacloud.utils.ldap;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

/**
 * Session Bean implementation class LogBean
 */
@Stateless(mappedName = "logBean")
@TransactionManagement(TransactionManagementType.BEAN)
@LocalBean
public class LogBean {

    /**
     * Default constructor. 
     */
    public LogBean() {
        // TODO Auto-generated constructor stub
    }

}
