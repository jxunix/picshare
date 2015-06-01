/*
 * Filename: UserBean.java
 * Author: Jun Xu
 * Email: jxu.vimacs@gmail.com
 * Created Time: Thu 23 Apr 2015 01:13:14 AM EDT
 */

package photoshare;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;

/*
 * A bean that handles new user data
 */
public class UserBean {
	private int id;
	private String firstName = "";
	private String lastName = "";
  private String email = "";
	private Date dob = null;
	private String gender = "";
  private String password = "";
	private int hmtnLocId;
	private int currLocId;
	private String education = "";

	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

  public String getEmail() {
    return email;
  }
	
	public Date getDob() {
		return dob;
	}

	public String getGender() {
		return gender;
	}

  public String getPassword() {
    return password;
  }

	public int getHmtnLocId() {
		return hmtnLocId;
	}

	public int getCurrLocId() {
		return currLocId;
	}

	public String getEducation() {
		return education;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

  public void setEmail(String email) {
    this.email = email;
  }

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

  public void setPassword(String password) {
    this.password = password;
  }

	public void setHmtnLocId(int hmtnLocId) {
		this.hmtnLocId = hmtnLocId;
	}

	public void setCurrLocId(int currLocId) {
		this.currLocId = currLocId;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public UserBean create(HttpServletRequest request) {
		UserBean user = new UserBean();

		String firstName;
		String lastName;
		String email;
		String dobStr;
		String gender;
		String password;
		String hmtnLocIdStr;
		String currLocIdStr;
		String educationStr;

		firstName = request.getParameter("firstName");
		lastName = request.getParameter("lastName"); 
		email = request.getParameter("email");
		dobStr = request.getParameter("dob"); 
		gender = request.getParameter("gender");
		password = request.getParameter("password1");
		hmtnLocIdStr = request.getParameter("hmtnLocId");
		currLocIdStr = request.getParameter("currLocId");
		educationStr = request.getParameter("education");

		if (firstName == null || firstName.equals("") ||
				lastName == null || lastName.equals("") ||
				email == null || email.equals("") ||
				dobStr == null || dobStr.equals("") ||
				gender == null ||
				password == null || password.equals("") ||
				hmtnLocIdStr == null ||
				currLocIdStr == null ||
				education == null) {
			return null;
		}

		Date dob = null;
		try {
			java.util.Date dobUtil = new SimpleDateFormat("yyyy-MM-dd").parse(dobStr);
			dob = new Date(dobUtil.getTime());
		} catch (ParseException e) {
			return null;
		}
		int hmtnLocId = Integer.parseInt(hmtnLocIdStr);
		int currLocId = Integer.parseInt(currLocIdStr);

		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setDob(dob);
		user.setGender(gender);
		user.setPassword(password);
		user.setHmtnLocId(hmtnLocId);
		user.setCurrLocId(currLocId);

		return user;
	}
}
