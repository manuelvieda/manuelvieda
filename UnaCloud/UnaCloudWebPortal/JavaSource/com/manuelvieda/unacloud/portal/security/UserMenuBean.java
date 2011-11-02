/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 * University of Los Andes
 *
 * Creation: 23/10/2011
 * Project: UnaCloudWebPortal
 * Package: com.manuelvieda.unacloud.portal.security
 * File:    UserMenuBean.java
 */
package com.manuelvieda.unacloud.portal.security;

import java.security.Principal;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version	1.0
 * @since	1.0
 */
@ManagedBean (name="userMenuBean")
@ViewScoped
public class UserMenuBean {
	
	
	/**
	 * This method includes the principal menu for the general information about the system
	 * @return - The HTML with the info menu
	 */
	public String getGeneralMenu(){
		
		ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
		
		String menu = "";
		
		menu += "		<div id=\"menuItem\">\n" +
				"			<p>Help - FAQs</p>\n" +
				"			<img src=\"/"+ectx.getContextName()+"/data/imgs/icons/faq.png\" alt=\"Help - FAQs\" />\n" +
				"		</div>\n" +
				"		<div id=\"menuDetail\">\n" +
				"			<ul>\n" +
				"				<li>About UnaCloudp</li>\n" +
				"				<li>About COMIT</li>\n" +
				"			</ul>\n" +
				"		</div>";
		
		return menu;
	}
	
	
	/**
	 * This method includes the principal menu for the account manager functions.
	 * @return - The HTML with the account menu
	 */
	public String getAccountMenu(){
		
		ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
		
		Principal user = ectx.getUserPrincipal();
		
		
		String menu = "<div id=\"menuItem\"><p>Account Manager</p><img src=\"/"+ectx.getContextName()+"/data/imgs/icons/user.png\" alt=\"Administration\" /></div>";
		
		if(user==null){
			// Not logged user in the system
			menu += "\n		<div id=\"menuDetail\">\n" +
					"			<ul>\n" +
					"				<li>You're not logged in. <br />Please, insert your credentials:<br /><br /></li>\n" +
					"			<form action=\"j_security_check\" method=\"post\"></li>\n" +
					"				<li>Username:</li>\n" +
					"				<li><input type=\"text\" name=\"j_username\" tabindex=\"1\" /></li>\n" +
					"				<li>Password:</li>\n" +
					"				<li><input type=\"password\" name=\"j_password\" tabindex=\"2\" /></li>\n" +
					"				<li><input type=\"submit\" value=\"Login\" align=\"middle\" tabindex=\"3\" /></li>\n" +
					"			</form>\n" +
					"			</ul>\n" +
					"			<ul>\n" +
					"				<li><b><a href=\"/"+ectx.getContextName()+"/pages/user/join.xhtml\" title=\"Join UnaCloud\">Join us now!</a></b></li>" +
					"			</ul>\n" +
					"		</div>";	
		}else{
			// Logged user in the system
			menu += "\n		<div id=\"menuDetail\">\n" +
					"			<ul>\n" +
					"				<li><a href=\"/"+ectx.getContextName()+"/pages/user/account/details.xhtml\" title=\"Account information\">Account Information</a></li>\n" +
					"			</ul>\n" +
					"			<ul>\n" +
					"				<li><h:form><h:commandLink value=\"Logout\" action=\"#{loginBean.doLogout}\" /></h:form></li>\n" +
					"			</ul>\n" +
					"		</div>";
			
		}
		
		return menu;
	}
	
	/**
	 * This method construct a returns the HTML code of the user workspace menu.
	 * @return HMTL code of the workpsace menu
	 */
	public String getUserMenu(){
		
		ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
		Principal user = ectx.getUserPrincipal();
		
		return user==null ? "" :		
		"		<div id=\"menuItem\">\n" +
		"		<p>Workspace</p>\n" +
		"			<img src=\"/"+ectx.getContextName()+"/data/imgs/icons/cluster.png\" alt=\"Workspace\" />\n" +
		"		</div>\n" +
		"		<div id=\"menuDetail\">\n" +
		"		<ul>\n" +
		"			<li><a href=\"/"+ectx.getContextName()+"/pages/workspace/clusters/admin.xhtml\">Manage Clusters</a></li>\n" +
		"			<li><a href=\"/"+ectx.getContextName()+"/pages/workspace/clusters/create.xhtml\">Create Clusters</a></li>\n" +
		"			<li><a href=\"/"+ectx.getContextName()+"/pages/workspace/monitor/cluster.xhtml\">Monitoring</a></li>\n" +
		"			<li><a href=\"/"+ectx.getContextName()+"/pages/workspace/jobs/admin.xhtml\">Job Administration</a></li>\n" +
		"			<li><a href=\"/"+ectx.getContextName()+"/pages/workspace/jobs/create.xhtml\">Create Job</a></li>\n" +
		"		</ul>\n" +
		"	</div>";
		
	}
	
	/**
	 * This method construct a returns the HTML code of the admninistrator zone menu.
	 * @returnHMTL code of the admin menu
	 */
	public String getAdminMenu(){
		
		ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
		Principal user = ectx.getUserPrincipal();
		
		return user==null ? "" : !ectx.isUserInRole("Administrators")? "" :		
				"	<div id=\"menuItem\">\n" +
				"		<p>System Configuration</p>\n" +
				"		<img src=\"/"+ectx.getContextName()+"/data/imgs/icons/admin_tools.png\" alt=\"Administration\" />\n" +
				"	</div>\n" +
				"	<div id=\"menuDetail\">\n" +
				"		<ul>\n" +
				"			<li>Users and ACLs</li>\n" +
				"			<li>System Status</li>\n" +
				"			<li>Amazon Manager</li>\n" +
				"		</ul>\n" +
				"	</div>";
	}
	
	
}
