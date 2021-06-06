package es.upm.dit.controller;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.upm.dit.model.ParametersSelectedToSign;
import es.upm.dit.service.SignWithCAdES;
import es.upm.dit.service.SignWithPAdES;
import es.upm.dit.service.SignWithXAdES;

/**
 * Handles requests for the application file upload requests
 */
@Controller
public class SignFileController {
	
	private ParametersSelectedToSign parametersselectedtosign;

	@RequestMapping(value = "/uploadFileForm", method = RequestMethod.GET)
	public String uploadFileHandlerForm(Model model) {
		ParametersSelectedToSign parameters = new ParametersSelectedToSign();
		model.addAttribute("parameters", parameters);
		return "sign_form";

	}
	
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public  String submitParameters(@ModelAttribute("parameters") ParametersSelectedToSign parameters,
			HttpServletRequest request, 
			Model model) {
		
		try {
			parametersselectedtosign = parameters;
			if(parametersselectedtosign.getDevice().equals("mscapi")) {
				signFormatSelected();
				model.addAttribute("reason", "The path where the signed file is located in: " + System.getProperty("user.home").replace("\\", "/")+"/Downloads"+"/signedFile");
				model.addAttribute("backToTheView", "/App/");
				return "modal_success";
			}else {
				model.addAttribute("goBack", "uploadFileForm");
				model.addAttribute("titleMessage", "PKCS12 file password");
				model.addAttribute("bodyMessage", "We need the password to sign");
				model.addAttribute("buttonMessage", "Accept");
				model.addAttribute("view", "results");
			}
		}catch (Exception e) {
			e.printStackTrace();
			// Modal advertising something was wrong
			model.addAttribute("backToTheView", "/App/uploadFileForm");
			return "modal_error";
			//return "failed";
		}
		return "modal_password";
	}
	
	@RequestMapping(value = "/results", method = RequestMethod.POST)
	public  String passwordHandler( @RequestParam("password") String password,
			HttpServletRequest request, 
			Model model) {
		
		try {
			parametersselectedtosign.setPassword(password);
			signFormatSelected();
			
		} catch (Exception e) {
			e.printStackTrace();
			// Modal advertising something was wrong
			model.addAttribute("backToTheView", "/App/uploadFileForm");
			return "modal_error";
			//return "failed";
		}
		// Modal advertising it was generated successfully
		// send path to the model where the file is stored, it will appear in /Downloads
		model.addAttribute("reason", "The path where the signed file is located in: " + System.getProperty("user.home").replace("\\", "/")+"/Downloads"+"/signedFile");
		model.addAttribute("backToTheView", "/App/");
		return "modal_success";
	}
	
	private void signFormatSelected() throws IOException {
        if(parametersselectedtosign.getFormat().equals("PAdES")){
        	System.out.println("PAdES");
        	parametersselectedtosign.setLevel("PAdES_"+parametersselectedtosign.getLevel());
            SignWithPAdES sPAdES = new SignWithPAdES();
            sPAdES.sign(parametersselectedtosign);
        }else {
        	System.out.println("XAdES");
            parametersselectedtosign.setLevel("XAdES_"+parametersselectedtosign.getLevel());
            SignWithXAdES sXAdES = new SignWithXAdES();
            sXAdES.sign(parametersselectedtosign);	
        }
        
	}

}
