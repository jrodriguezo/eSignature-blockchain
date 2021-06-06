package es.upm.dit.blockchain;

import java.io.IOException;
import java.math.BigInteger;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;


// Run as Java application to deploy a new contract, indicating the address (or walletfile) desired
public class DeployNewContracts {

	// Gas Limit and gas price parameters of the deployed node
	private final static BigInteger GAS_LIMIT = BigInteger.valueOf(0x47b760L);
	private final static BigInteger GAS_PRICE = BigInteger.valueOf(1000000000L);

	public static void main(String[] args) throws Exception {
		DeployNewContracts contracts = new DeployNewContracts();
		contracts.deployIssuerContract();
		contracts.deployVerifierContract();
		contracts.deployBankContract();

		// In our case we got the following output from console:
		/*
		
		----------------------------------------------------------
		The address of the Issuer contract is at: 0x406cb623ce36e12155b49be161d2b029a92d5715
		----------------------------------------------------------
		The address of the Verifier contract is at: 0x124d2e69668be271808cc5a22534172a28faa09c
		----------------------------------------------------------
		The address of the Bank contract is at: 0xfac417c8c22cf4e0267c9a234686be0786410270

		*/
	}

	public void deployIssuerContract() throws Exception {
		// Web3j client initialization
		// Use the following line when the port used is not the default port (8545)
		// Web3j web3 = Web3j.build(new HttpService("http://127.0.0.1:XXXX"));
		// Otherwise use the following (http://localhost:8545/)
		Web3j web3 = Web3j.build(new HttpService()); 
		ContractGasProvider gasProvider = new StaticGasProvider(GAS_PRICE, GAS_LIMIT);

		// HERE YOU WILL NEED INDICATE YOUR WALLET DIRECTORY (#1 IMPORTANT!)
		String walletfile = "D:\\TFM\\Blockchain\\Private\\node\\keystore\\ENTITY.807071100Z--c44b4bde512868859c0b83de3257d03964cd2972";;
		Credentials credentials = WalletUtils.loadCredentials("pass", walletfile);
		// If you have just the private key, use the following
		//private final static String PRIVATE_KEY = "<YOURPRIVATEKEYGOESHERE>";
		//Credentials credentials = Credentials.create(PRIVATE_KEY);

		// HERE YOU WILL NEED INDICATE WHAT CONTRACT YOU WANT TO DEPLOY (#2 IMPORTANT!) It takes some time, wait until then.
		// We are going to simulate that the "Police" are going to emit new credentials about our Name, Surname and Gender.
		Issuer contract= Issuer.deploy(web3, credentials, gasProvider,"Police").send();
		String contractAddress = contract.getContractAddress();
		System.out.println("----------------------------------------------------------");
		System.out.println("The address of the Issuer contract is at: "+contractAddress);
	}
	
	public void deployVerifierContract() throws Exception {
		Web3j web3 = Web3j.build(new HttpService()); 
		ContractGasProvider gasProvider = new StaticGasProvider(GAS_PRICE, GAS_LIMIT);
		String walletfile = "D:\\TFM\\Blockchain\\Private\\node\\keystore\\VERIFIER.409679300Z--979c356dbd67404e870e673d8ea1c16b7071d26c";
		Credentials credentials = WalletUtils.loadCredentials("pass", walletfile);
		Verifier contract= Verifier.deploy(web3, credentials, gasProvider,"Social Security").send();
		String contractAddress = contract.getContractAddress();
		System.out.println("----------------------------------------------------------");
		System.out.println("The address of the Verifier contract is at: "+contractAddress);
	}
	
	public void deployBankContract() throws Exception {
		Web3j web3 = Web3j.build(new HttpService()); 
		ContractGasProvider gasProvider = new StaticGasProvider(GAS_PRICE, GAS_LIMIT);
		String walletfile = "D:\\TFM\\Blockchain\\Private\\node\\keystore\\BANK.266833900Z--aec8a4b0743ac0984878bcb923e6f81c7b0ec5bf";
		Credentials credentials = WalletUtils.loadCredentials("pass", walletfile);
		Bank contract= Bank.deploy(web3, credentials, gasProvider).send();
		String contractAddress = contract.getContractAddress();
		System.out.println("----------------------------------------------------------");
		System.out.println("The address of the Bank contract is at: "+contractAddress);
	}
}
