package es.upm.dit.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;

import es.upm.dit.blockchain.Customer;
import es.upm.dit.blockchain.Wallet;
import es.upm.dit.model.Profile;
import es.upm.dit.model.User;
import es.upm.dit.service.CreatePDF;
import es.upm.dit.service.SignWithPAdES;

@Controller
public class BankAccountController {

	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(HttpServletRequest request, Model model) {
		User user = new User();
		model.addAttribute("user", user);
		// Parameters loaded from Credentials authorized
		model.addAttribute("name", request.getParameter("valueEntry0"));
		model.addAttribute("surname", request.getParameter("valueEntry1"));
		model.addAttribute("gender", request.getParameter("valueEntry2"));

		String[] checkboxList = request.getParameterValues("checkboxList");
		if(checkboxList != null){
			return "bank_account_form";
		}else
			// It was not accepted, then return back
			model.addAttribute("profile", new Profile());
			return "DID_form";
		
	}
	
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public void addStudent(@ModelAttribute("user") User user, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		try {
			
			CreatePDF pdf = new CreatePDF(user);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			//String FILE = System.getProperty("user.home").replace("\\", "/")+"/Downloads"+"/first.pdf";

			Document document = new Document();
			PdfWriter.getInstance(document, baos);
			// Open PDF
			document.open();		
			// Add content to the PDF: first page, second page and metadata
			pdf.addMetaData(document);
			pdf.addTitlePage(document);
			pdf.addContent(document);
			// Close PDF
			document.close();

			//this pdf generated is sended to sign PDF controller to put a sign. 
			// Sign with your smart card reader + eID
			SignWithPAdES s = new SignWithPAdES();
			s.signBankPDF(baos.toByteArray());
			
			// Integration with Blockchain
			RequestDispatcher rd = request.getRequestDispatcher("modal_success");
		    rd.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
