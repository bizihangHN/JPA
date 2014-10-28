package com.micmiu.hibernate.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author <a href="http://www.micmiu.com">Michael</a>
 * @time Create on 2013-9-17 下午11:27:06
 * @version 1.0
 */
@Entity
@Table(name = "T_DEMO_USER_INFO")
public class UserInfo implements Serializable {

	private static final long serialVersionUID = 4943396187256597131L;

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;

	@Column(name = "USER_NAME", length = 20)
	private String userName;

	@Column(name = "EMAIL", length = 64)
	private String email;

	@Column(name = "BLOG_URL", length = 64)
	private String blogURL;

	public Integer getId() {
		System.out.println("!");
		int i = 0;
		return id;
	}
	 
	
	public String getUserName() {
		return userName;
	}

	public String getEmail() {
		return email;
	}

	public String getBlogURL() {
		return blogURL;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setBlogURL(String blogURL) {
		this.blogURL = blogURL;
	}

	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", userName=" + userName + ", email="
				+ email + ", blogURL=" + blogURL + "]";
	}

}