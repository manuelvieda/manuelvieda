/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 14/10/2011
 * Project: UnaCloudWebPortal
 * Package: com.manuelvieda.unacloud.user
 * File:    CreateUserBean.java
 */
package com.manuelvieda.unacloud.portal.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.manuelvieda.unacloud.beans.user.UserManagerBeanLocal;

/**
 * This backing beans defines how the systems create a new user.
 * 
 * @author Manuel Eduardo Vieda
 * @version 1.0
 * @since	1.0
 */
@ViewScoped
@ManagedBean (name="createUserBean")
public class CreateUserBean {
	
	/**
	 * The username of the user. Used by the system to identify each user.
	 */
	@NotEmpty
	@NotBlank
	@Length(min=3,max=50)
	private String username;
	
	/**
	 * The password of the user. 
	 */
	@NotEmpty
	@NotBlank
	@Length(min=3,max=20)
	private String password;
	
	/**
	 * The confrmation of the password. 
	 */
	@NotEmpty
	@NotBlank
	@Length(min=3,max=20)
	private String confirmPassword;
	
	/**
	 * The first name of the user
	 */
	@NotEmpty
	@NotBlank
	@Length(min=3,max=50)
	private String firstName;
	
	/**
	 * The last name of the user
	 */
	@NotEmpty
	@NotBlank
	@Length(min=3,max=50)
	private String lastName;
	
	/**
	 * The email address of the user
	 */
	@Email
	@NotEmpty
	@NotBlank
	@Length(min=3,max=100)
	private String email;
	
	
	/**
	 * The confirmation of the email address
	 */
	@Email
	@NotEmpty
	@NotBlank
	@Length(min=3,max=100)
	private String emailConfirmation;
	
	// -----------------------------------------------------------
	
	@EJB
	private UserManagerBeanLocal userManagerBean;
	
	// -----------------------------------------------------------
	
	/**
	 * Default constructor
	 */
	public CreateUserBean(){
		super();
	}

	// -----------------------------------------------------------
	
	/**
	 * 
	 */
	public void create(){
		
		FacesContext context = FacesContext.getCurrentInstance();
		
		// USERNAME 
		boolean usernameAssert = validateString(firstName, 3, 50, true, true) && StringUtils.isAlphanumericSpace(username);
		if(!usernameAssert)
			context.addMessage("createUserFormBean:username", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Password", null));
		else{
			
		}
		// PASSWORD
		boolean passwordAssert = validateString(firstName, 3, 50, true, true) && StringUtils.isAlphanumericSpace(password);
		if(!passwordAssert)
			context.addMessage("createUserFormBean:password", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Password", null));
		
		// PASSWORD CONFIRMATION
		boolean passwordConfirmationAssert = password.equals(confirmPassword);
		if(!passwordConfirmationAssert)
			context.addMessage("createUserFormBean:confirmPassword", new FacesMessage(FacesMessage.SEVERITY_ERROR, "The password confirmation doesn't match", null));
		
		// FIRSTNAME
		boolean firstNameAssert = validateString(firstName, 3, 50, true, true) && StringUtils.isAlphanumericSpace(firstName);
		if(!firstNameAssert)
			context.addMessage("createUserFormBean:firstName", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid First Name", null));
		
		// LASTNAME
		boolean lastNameAssert = validateString(lastName, 3, 50, true, true) && StringUtils.isAlphanumericSpace(lastName);
		if(!lastNameAssert)
			context.addMessage("createUserFormBean:lastName", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Last Name", null));
		
		// EMAIL
		boolean emailAssert = validateString(email, 3, 100, true, true) && validateEmail(email);
		if(!emailAssert)
			context.addMessage("createUserFormBean:eamil", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Email", null));
		
		// EMAIL CONFIRMATION
		boolean emailConfirmationAssert = email.equals(emailConfirmation);
		if(!emailConfirmationAssert)
			context.addMessage("createUserFormBean:emailConfirmation", new FacesMessage(FacesMessage.SEVERITY_ERROR, "The email confirmation doesn't match", null));
		
		
		if(usernameAssert && passwordAssert && passwordConfirmationAssert && firstNameAssert && lastNameAssert && emailAssert && emailConfirmationAssert){
			userManagerBean.setUser(username, firstName, lastName, password);
		}
		
	}
	
	
	// ----------------------------------------------------------------------------------------------------
	
	
	/**
	 * This method is a heper validating the composition of an email address
	 * 
	 * @param email - The email address (String) to validate
	 * @return True if the email has an email well formed format, false otherwise
	 */
	private boolean validateEmail(String email) {
		String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern = Pattern.compile(emailPattern,Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(email); 
		return  matcher.matches();
	}

	/**
	 * This method is a helper validating any String on any form.
	 * 
	 * @param str - The String (char secuence) to validate
	 * @param min - The minium length of the String (Negative if dont care)
	 * @param max - The Maxium length of the String (Negative if dont care)
	 * @param notNull - True if the String cant be null
	 * @param notBlank - True if the String cant be only whitespaces
	 * @return True if the String meets with all the features, false otherwise
	 */
	private boolean validateString(String str, int min, int max, boolean notNull, boolean notBlank){
		
		boolean lengthAssert = min>=0 && max>=min? (str.length()>=min && str.length()<=max) : true;
		return lengthAssert && StringUtils.isNotBlank(str) && StringUtils.isNotEmpty(str);

	}
	
	// -----------------------------------------------------------
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the emailConfirmation
	 */
	public String getEmailConfirmation() {
		return emailConfirmation;
	}

	/**
	 * @param emailConfirmation the emailConfirmation to set
	 */
	public void setEmailConfirmation(String emailConfirmation) {
		this.emailConfirmation = emailConfirmation;
	}

	/**
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * @param confirmPassword the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
}
