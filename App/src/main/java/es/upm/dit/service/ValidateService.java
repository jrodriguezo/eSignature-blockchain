package es.upm.dit.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;

import es.upm.dit.model.ParametersSelectedToValidate;
import eu.europa.esig.dss.detailedreport.DetailedReport;
import eu.europa.esig.dss.detailedreport.DetailedReportFacade;
import eu.europa.esig.dss.diagnostic.DiagnosticData;
import eu.europa.esig.dss.model.DSSDocument;
import eu.europa.esig.dss.model.InMemoryDocument;
import eu.europa.esig.dss.service.crl.OnlineCRLSource;
import eu.europa.esig.dss.service.http.commons.CommonsDataLoader;
import eu.europa.esig.dss.service.ocsp.OnlineOCSPSource;
import eu.europa.esig.dss.simplereport.SimpleReport;
import eu.europa.esig.dss.simplereport.SimpleReportFacade;
import eu.europa.esig.dss.simplereport.jaxb.XmlSimpleReport;
import eu.europa.esig.dss.spi.DSSUtils;
import eu.europa.esig.dss.spi.x509.CertificateSource;
import eu.europa.esig.dss.spi.x509.CommonTrustedCertificateSource;
import eu.europa.esig.dss.spi.x509.KeyStoreCertificateSource;
import eu.europa.esig.dss.token.DSSPrivateKeyEntry;
import eu.europa.esig.dss.token.MSCAPISignatureToken;
import eu.europa.esig.dss.tsl.job.TLValidationJob;
import eu.europa.esig.dss.tsl.source.TLSource;
import eu.europa.esig.dss.validation.CertificateVerifier;
import eu.europa.esig.dss.validation.CommonCertificateVerifier;
import eu.europa.esig.dss.validation.SignedDocumentValidator;
import eu.europa.esig.dss.validation.reports.Reports;

public class ValidateService {

	public String validate(ParametersSelectedToValidate parameters) throws IOException, TransformerException, JAXBException{

		//No se puede validar correctamente porque no tiene activado el LTV el signedDocumentValidator
		// First, we need a Certificate verifier
		CertificateVerifier cv = new CommonCertificateVerifier();

		// We can inject several sources. eg: OCSP, CRL, AIA, trusted lists

		// Capability to download resources from AIA
		cv.setDataLoader(new CommonsDataLoader());

		// Capability to request OCSP Responders
		cv.setOcspSource(new OnlineOCSPSource());

		// Capability to download CRL
		cv.setCrlSource(new OnlineCRLSource());
		
		// Here is the document to be validated (any kind of signature file)
		DSSDocument document = new InMemoryDocument(new BufferedInputStream(parameters.getFile().getInputStream()));

		// We create an instance of DocumentValidator
		// It will automatically select the supported validator from the classpath
		SignedDocumentValidator documentValidator = SignedDocumentValidator.fromDocument(document);

		// We add the certificate verifier (which allows to verify and trust certificates)
		documentValidator.setCertificateVerifier(cv);

		// Here, everything is ready. We can execute the validation (for the example, we use the default and embedded
		// validation policy)
		Reports reports = documentValidator.validateDocument();
		
		//DetailedReportFacade detailedReportFacade = DetailedReportFacade.newFacade();

		// We have 3 reports
		// The diagnostic data which contains all used and static data
		//DiagnosticData diagnosticData = reports.getDiagnosticData();

		// The detailed report which is the result of the process of the diagnostic data and the validation policy
		//DetailedReport detailedReport = reports.getDetailedReport();

		// The simple report is a summary of the detailed report (more user-friendly)
		//String simpleReport = reports.getXmlSimpleReport();
	
		
		String simpleBootstrap3Report = SimpleReportFacade.newFacade().generateHtmlBootstrap3Report(reports.getXmlSimpleReport());
		//String detailedBootstrap3Report = DetailedReportFacade.newFacade().generateHtmlBootstrap3Report(reports.getXmlDetailedReport());
		//String htmlDetailedReport = detailedReportFacade.generateHtmlReport(reports.getDetailedReportJaxb());
		reports.print();
		//System.out.println(htmlDetailedReport);
		return simpleBootstrap3Report;
		
		
	}

}
