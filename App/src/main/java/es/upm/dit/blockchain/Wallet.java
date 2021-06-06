package es.upm.dit.blockchain;

import java.io.File;
import java.math.BigInteger;

import org.apache.commons.io.FileUtils;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;

public class Wallet {

	private String address;
	private BigInteger balance;
	private Credentials credentials;
	//private BigInteger identifier;
	private String[] attributes; 
	
	public String[] getAttributes() {
		return attributes;
	}

	public void setAttributes(String[] attributes) {
		this.attributes = attributes;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigInteger getBalance() {
		return balance;
	}

	public void setBalance(BigInteger wei) {
		this.balance = wei;
	}

	public void importWallet(String password, File walletfile  ) {
			try {
				credentials = WalletUtils.loadCredentials(password, walletfile);
				this.setCredentials(credentials); // It will be necessary for loading a smartcontract
				String address = credentials.getAddress();
		        this.setAddress(address);
		        
				// To get balance from an address specified.
				/*
				Admin web3j = Admin.build(new HttpService());
		        EthGetBalance ethGetBalance = web3j
						.ethGetBalance(address, DefaultBlockParameterName.LATEST)
						.sendAsync()
						.get();
		        BigInteger wei = ethGetBalance.getBalance();
		        this.setBalance(wei);
		        */
				
		        //Remove temporary file in Downloads
		        FileUtils.forceDelete(walletfile);

		    } catch(Exception e) {
		        System.err.println("Error: " + e.getMessage());
		    }
			
		}
	
	
}
