package es.upm.dit.model;

import org.springframework.web.multipart.MultipartFile;

public class ParametersSelectedToSign {
	private MultipartFile key;
	private MultipartFile file;
	private String format;
	private String packaging;
	private String level;
	private String digest_algorithm;
	private String device;
	private String password;

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public MultipartFile getKey() {
		return key;
	}
	public void setKey(MultipartFile key) {
		this.key = key;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getPackaging() {
		return packaging;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getDigest_algorithm() {
		return digest_algorithm;
	}
	public void setDigest_algorithm(String digest_algorithm) {
		this.digest_algorithm = digest_algorithm;
	}
	

}
