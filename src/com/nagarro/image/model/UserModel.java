package com.nagarro.image.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "userdata")
public class UserModel {

	/**
	 * 
	 */

	@Id
	@GeneratedValue
	private int id;
	
	@Column(name="user_name")
	private String user_name;

	@Column(name="password")
	private String password;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<FilesModel> images = new HashSet<>();
	

	public Set<FilesModel> getImages() {
		return images;
	}

	public void setImages(Set<FilesModel> images) {
		this.images = images;
	}


	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserModel(int id, String user_name, String password) {
		super();
		this.id = id;
		this.user_name = user_name;
		this.password = password;
	}

	public UserModel() {
		// TODO Auto-generated constructor stub
	}
	
	
}
