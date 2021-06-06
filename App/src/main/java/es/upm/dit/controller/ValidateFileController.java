package es.upm.dit.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.upm.dit.model.ParametersSelectedToSign;
import es.upm.dit.model.ParametersSelectedToValidate;
import es.upm.dit.service.ValidateService;

@Controller
public class ValidateFileController {

	@RequestMapping(value = "/uploadValidateFileForm", method = RequestMethod.GET)
	public String uploadFileHandlerForm(Model model) {
		ParametersSelectedToValidate parameters = new ParametersSelectedToValidate();
		model.addAttribute("parameters", parameters);
		return "validate_form";

	}

	@RequestMapping(value = "/uploadValidateFile", method = RequestMethod.POST)
	public  String uploadFileHandler( @ModelAttribute("parameters") ParametersSelectedToValidate parameters,
			HttpServletRequest request, 
			Model model) {
		try
		{
			ValidateService v = new ValidateService();
			String s = v.validate(parameters);
			model.addAttribute("example", s);
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("reason", "Please update a file to generate the report.");
			model.addAttribute("backToTheView", "/App/uploadValidateFileForm");
			return "modal_error";
		}
		return "report";

	}

}
