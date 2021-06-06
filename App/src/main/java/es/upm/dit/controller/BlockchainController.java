package es.upm.dit.controller;


import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import es.upm.dit.blockchain.Credentials;
import es.upm.dit.blockchain.Customer;
import es.upm.dit.blockchain.Document;
import es.upm.dit.blockchain.Wallet;
import es.upm.dit.model.ParametersSendedToIssuer;
import es.upm.dit.model.Profile;
import es.upm.dit.model.User;


@Controller
public class BlockchainController {

	//private boolean reload = false;
	//private Date date;
	private static Wallet wallet;
	ParametersSendedToIssuer storedParameters;
	private Credentials credentials;
	private Customer customer;
	private Document document;
	private List<String[]> storage;
	private List<BigInteger> identifiers;
	
	// User who request attributes
	private static final String userVerifier = "0x979c356dbd67404e870e673d8ea1c16b7071d26c";
	private static final String userBank = "0xaec8a4b0743ac0984878bcb923e6f81c7b0ec5bf";

	// Launching for first time DID_form
	@RequestMapping(value = "/createDIDForm", method = RequestMethod.GET)
	public ModelAndView showDIDForm() {
		//reload = false;
		return new ModelAndView("DID_form", "profile", new Profile());
	}

	
	// Import wallet
	@RequestMapping(value = "/addedDID", method = RequestMethod.POST)
	public  String submitProfile( @ModelAttribute("profile") Profile profile, HttpServletRequest request, Model model) throws IOException {
		try {
			wallet = new Wallet();
			File file = new File(System.getProperty("user.home")+"/Downloads/"+profile.getWalletfile().getOriginalFilename());
			Files.copy(profile.getWalletfile().getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
			wallet.importWallet(profile.getPassword(),file);
			if(wallet.getAddress() == null) {
				model.addAttribute("incorrectPassword", "<strong>Incorrect Password</strong>. Please, checks that the password introduced is correct.");
				model.addAttribute("profile", new Profile());
				return "DID_form";
			}
			model.addAttribute("w", wallet);

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("reason","Something was wrong! Check out if your password is introduced properly.");
			model.addAttribute("backToTheView", "/App/createDIDForm");
			return "modal_error";
		}
		return "myWallet";
	}
	
	// To retrieve to DID_form
	@RequestMapping(value = "/myWallet", method = RequestMethod.POST)
	public static String logout(HttpServletRequest request, final Model model) {
		model.addAttribute("w", wallet);
		return "myWallet";
	}
	
	// Load myCredentials
	@RequestMapping(value = "/userCredentials", method = RequestMethod.GET)
	public  String getUserCrendentials(HttpServletRequest request, Model model) throws IOException {
		try {
			model.addAttribute("w", wallet);
			getUserCredentialsMethod(model);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("backToTheView", "/App/createDIDForm");
			return "modal_error";
		}
		return "myCredentials";
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@RequestMapping(value = "/deleteCredentials", method = RequestMethod.POST)
	public  String deleteUserCrendentials(HttpServletRequest request, Model model) throws IOException {
		try {
			model.addAttribute("w", wallet);
			BigInteger bdid = new BigInteger(request.getParameter("did"));
			if (credentials.newID(wallet).equals(bdid)) {
				// It is a DID corresponding to Credentials
				credentials.deleteCredential(bdid, wallet);
			} else if(customer.newID(wallet).equals(bdid)) {
				// It is a DID corresponding to Customer
				customer.deleteCustomer(bdid, wallet);
			}else {
				// It is a DID corresponding to Document
				document.deleteDocument(bdid, wallet);
			}
			getUserCredentialsMethod(model);
				
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("backToTheView", "/App/createDIDForm");
			return "modal_error";
		}
		return "myCredentials";
	}

	// Go to Blockchain, and create new Credentials in Issuer.sol 
	@RequestMapping(value = "/createCredentials", method = RequestMethod.GET)
	public ModelAndView createCredentialsForm(Model model, HttpServletRequest request) {
		model.addAttribute("w", wallet);
		return new ModelAndView("credentials_form", "parameters", new ParametersSendedToIssuer());
	}

	// Verify form 
	@RequestMapping(value = "/verifyCredentialsForm", method = RequestMethod.GET)
	public ModelAndView verifyCredentialsForm(Model model, HttpServletRequest request) {
		model.addAttribute("w", wallet);
		return  new ModelAndView("verify_form");
	}

	// Go to Blockchain, and verify Credentials in Issuer.sol 
	@RequestMapping(value = "/verifyCredentials", method = RequestMethod.POST)
	public String verifiedCredentials(@RequestParam("DID") String did, Model model, HttpServletRequest request) {
		model.addAttribute("w", wallet);
		storage = new ArrayList<String[]>(); //create List
		model.addAttribute("identifier", did);
		// Eliminate spaces, tabs from did
		String didCleaned = did.replaceAll("\\s+","");
		try {
			document = new Document();
			if(document.verify(new BigInteger(didCleaned), wallet).length != 0) {
			storage.add(document.verify(new BigInteger(didCleaned), wallet));
			model.addAttribute("list", storage);
			return "verify_form";
			}else
				throw new Exception("Check if it is a Credential");
		}catch(Exception e1) {
			try {
				credentials = new Credentials();
				if(credentials.verify(new BigInteger(didCleaned), wallet).length != 0) {
					storage.add(credentials.verify(new BigInteger(didCleaned), wallet));
					model.addAttribute("list", storage);
					return "verify_form";
				}else
					throw new Exception("Check if it is a Customer");
			}catch(Exception e2) {
				try {
					customer = new Customer();
					if(customer.verify(new BigInteger(didCleaned), wallet).length != 0) {
					storage.add(customer.verify(new BigInteger(didCleaned), wallet));
					model.addAttribute("list", storage);
					return "verify_form";
					}else
						throw new Exception("Failed");
				}catch(Exception e3) {
						model.addAttribute("failed", true);
						model.addAttribute("alert", "You do not have access to the requested DID or it may not exist.");
						return "verify_form";
				}
				
			}
			
		}
		
	}


	@RequestMapping(value = "/sendedCredentials", method = RequestMethod.POST)
	public  String submitParameters( @ModelAttribute("parameters") ParametersSendedToIssuer parameters, HttpServletRequest request, Model model) throws IOException {
		model.addAttribute("w", wallet);
		storedParameters = new ParametersSendedToIssuer();
		storedParameters = parameters;
		model.addAttribute("p", storedParameters);
		model.addAttribute("goBack", "createCredentials");
		model.addAttribute("titleMessage", "Authorize transaction");
		model.addAttribute("bodyMessage", "You agree to sign the transaction.");
		model.addAttribute("buttonMessage", "Authorize");
		model.addAttribute("view", "sendedCredentialsPassword");
		return "modal_password";
	}

	@RequestMapping(value = "/sendedCredentialsPassword", method = RequestMethod.POST)
	public  String passwordHandler( @RequestParam("password") String password, HttpServletRequest request, Model model) throws IOException {
		try {
			
			//When click on authorize after passing password.
			storedParameters.setPassword(password);
			model.addAttribute("w", wallet);
			model.addAttribute("p", storedParameters);
			credentials = new Credentials();
			credentials.newCredential(wallet, storedParameters);
			identifiers = new ArrayList<BigInteger>();
			identifiers.add(credentials.newID(wallet));
			getUserCredentialsMethod(model);
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("backToTheView", "/verifyCredentials");
			return "modal_error";
		}
		return "myCredentials";
	}
	
	// Social Security Part
	
	@RequestMapping(value = "/socialSecurity", method = RequestMethod.GET)
	public String ssAttributesHandler(Model model, HttpServletRequest request){
		
		try {
			credentials = new Credentials();
			identifiers = new ArrayList<BigInteger>();
			storage = new ArrayList<String[]>();
			if(credentials.verify(credentials.newID(wallet), wallet).length != 0) {
				identifiers.add(credentials.newID(wallet));
				storage.add(credentials.verify(credentials.newID(wallet), wallet));
			}
			model.addAttribute("list", storage);
			model.addAttribute("identifiers", identifiers);
			model.addAttribute("user", userVerifier);
			//auxiliar list with raw values from verify
			
		} catch (Exception e) {
			e.printStackTrace();
			//Pending ...
		}
		model.addAttribute("action", "submitAttributes");
		return "modal_attributes";
	}
	
	@RequestMapping(value = "/submitAttributes", method = RequestMethod.POST)
	public String submitProfile(HttpServletRequest request, Model model) throws IOException {
		model.addAttribute("w", wallet);
		model.addAttribute("goBack", "/socialSecurity");
		try {
			String[] checkboxList = request.getParameterValues("checkboxList");
			model.addAttribute("identifier", credentials.newID(wallet));
			if(checkboxList[0].equals("true")) {
				credentials.authorize(userVerifier, wallet);
				document = new Document();
				document.newDocument(wallet);
			}else
				System.out.println("It was not selected");
			getUserCredentialsMethod(model);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("backToTheView", "/myCredentials");
			return "modal_error";
		}
		return "myCredentials";
	}
	
	// Create a online bank and linked in blockchain
	
	
	@RequestMapping(value = "/bankCreate", method = RequestMethod.GET)
	public String bankAttributesHandler(Model model, HttpServletRequest request, HttpServletResponse response){
		
		try {
			credentials = new Credentials();
			identifiers = new ArrayList<BigInteger>();
			storage = new ArrayList<String[]>();
			if(credentials.verify(credentials.newID(wallet), wallet).length != 0) {
				identifiers.add(credentials.newID(wallet));
				storage.add(credentials.verify(credentials.newID(wallet), wallet));
				List<String[]> rawList = new ArrayList<String[]>();
				rawList.add(credentials.rawVerify(credentials.newID(wallet), wallet));
				model.addAttribute("rawList", rawList);
			}
			model.addAttribute("list", storage);
			model.addAttribute("identifiers", identifiers);
			model.addAttribute("user", userBank);

		} catch (Exception e) {
			e.printStackTrace();
			//Pending ...
		}
		model.addAttribute("action", "create");
		model.addAttribute("goBack", "/App/createDIDForm");
		return "modal_attributes";
	}
	
	
	@RequestMapping(value = "/modal_success", method = RequestMethod.POST)
	public String customerAdded(HttpServletRequest request, Model model) {
		try {
			
			model.addAttribute("w", wallet);
			// When we got authorized, because user say yes!
			credentials.authorize(userBank, wallet);
			// Then new customer is added to Blockchain
			customer = new Customer();
			customer.newCustomer(wallet);
		
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("backToTheView", "/myCredentials");
			return "modal_error";
		}
		model.addAttribute("reason", "The path where the proof of request is located in: " + System.getProperty("user.home").replace("\\", "/") + "/Downloads"+"/Contract");
		model.addAttribute("backToTheView", "/App/userCredentials");
		return "modal_success";
	}
	
	// Repetitive operations
	
	public void getUserCredentialsMethod(Model model) throws Exception {
		credentials = new Credentials();
		document = new Document();
		customer = new Customer();
		// It should be a List
		identifiers = new ArrayList<BigInteger>();
		storage = new ArrayList<String[]>();
		if(credentials.verify(credentials.newID(wallet), wallet).length != 0) {
			identifiers.add(credentials.newID(wallet));
			storage.add(credentials.verify(credentials.newID(wallet), wallet));
		}else{	
			model.addAttribute("credentialAvailable",true);
		}
		if(customer.verify(customer.newID(wallet), wallet).length != 0) {
			identifiers.add(customer.newID(wallet));		
			storage.add(customer.verify(customer.newID(wallet), wallet));
		}else {
			model.addAttribute("bankAvailable",true);
		}
		if(document.verify(document.newID(wallet), wallet).length != 0) {
			identifiers.add(document.newID(wallet));		
			storage.add(document.verify(document.newID(wallet), wallet));
		}else {
			model.addAttribute("socialSecurityAvailable",true);
		}
		
		model.addAttribute("identifiers", identifiers);
		model.addAttribute("list", storage);
		if(storage.size() == 0) {
			model.addAttribute("storageEmpty", true);
		}else
			model.addAttribute("storageEmpty", false);
	}
}
