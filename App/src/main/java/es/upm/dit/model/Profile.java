package es.upm.dit.model;

import org.springframework.web.multipart.MultipartFile;

public class Profile {
	private String password;
	private MultipartFile walletfile;

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public MultipartFile getWalletfile() {
		return walletfile;
	}
	public void setWalletfile(MultipartFile walletfile) {
		this.walletfile = walletfile;
	}
}
