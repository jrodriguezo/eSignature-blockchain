package es.upm.dit.model;

import org.springframework.web.multipart.MultipartFile;

public class ParametersSelectedToValidate {
	private MultipartFile certificate;
	private String password;
	private MultipartFile file;
	
	public MultipartFile getCertificate() {
		return certificate;
	}
	public void setCertificate(MultipartFile certificate) {
		this.certificate = certificate;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}

}
