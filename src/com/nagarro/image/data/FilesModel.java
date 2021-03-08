package com.nagarro.image.data;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "filedata")

public class FilesModel {

	@Override
	public String toString() {
		return "FilesModel [fileid=" + fileid + ", fileData=" + Arrays.toString(fileData) + ", fileSize=" + fileSize
				+ ", fileName=" + fileName + ", user=" + user + "]";
	}

	public FilesModel() {
		super();
	}

	@Id
	@GeneratedValue
	private long fileid;

	@Column(name = "file_data", columnDefinition = "mediumblob")
	private byte[] fileData;

	@Column(name = "file_size")
	private double fileSize;

	@Column(name = "file_name")
	private String fileName;

	@ManyToOne
	@JoinColumn(name = "id", nullable = false)
	private UserModel user;

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getId() {
		return fileid;
	}

	public void setId(int id) {
		this.fileid = id;
	}

	public byte[] getFileData() {
		return fileData;
	}

	public FilesModel(byte[] fileData, double fileSize, String fileName) {
		super();
		this.fileData = fileData;
		this.fileSize = fileSize;
		this.fileName = fileName;
	}

	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}

	public double getFileSize() {
		return fileSize;
	}

	public void setFileSize(double fileSize) {
		this.fileSize = fileSize;
	}

}
