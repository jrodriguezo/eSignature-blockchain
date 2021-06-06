package es.upm.dit.service;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.List;

import es.upm.dit.model.ParametersSelectedToSign;
import eu.europa.esig.dss.enumerations.DigestAlgorithm;
import eu.europa.esig.dss.enumerations.SignatureLevel;
import eu.europa.esig.dss.enumerations.SignaturePackaging;
import eu.europa.esig.dss.enumerations.VisualSignatureAlignmentHorizontal;
import eu.europa.esig.dss.enumerations.VisualSignatureAlignmentVertical;
import eu.europa.esig.dss.model.DSSDocument;
import eu.europa.esig.dss.model.InMemoryDocument;
import eu.europa.esig.dss.model.SignatureValue;
import eu.europa.esig.dss.model.ToBeSigned;
import eu.europa.esig.dss.model.x509.CertificateToken;
import eu.europa.esig.dss.pades.DSSFont;
import eu.europa.esig.dss.pades.DSSJavaFont;
import eu.europa.esig.dss.pades.PAdESSignatureParameters;
import eu.europa.esig.dss.pades.SignatureImageParameters;
import eu.europa.esig.dss.pades.SignatureImageTextParameters;
import eu.europa.esig.dss.pades.signature.PAdESService;
import eu.europa.esig.dss.service.crl.OnlineCRLSource;
import eu.europa.esig.dss.service.http.commons.CommonsDataLoader;
import eu.europa.esig.dss.service.http.commons.FileCacheDataLoader;
import eu.europa.esig.dss.service.http.commons.OCSPDataLoader;
import eu.europa.esig.dss.service.http.commons.TimestampDataLoader;
import eu.europa.esig.dss.service.ocsp.OnlineOCSPSource;
import eu.europa.esig.dss.service.tsp.OnlineTSPSource;
import eu.europa.esig.dss.spi.tsl.TrustedListsCertificateSource;
import eu.europa.esig.dss.spi.x509.CertificateSource;
import eu.europa.esig.dss.spi.x509.tsp.CompositeTSPSource;
import eu.europa.esig.dss.token.DSSPrivateKeyEntry;
import eu.europa.esig.dss.token.MSCAPISignatureToken;
import eu.europa.esig.dss.token.Pkcs12SignatureToken;
import eu.europa.esig.dss.token.SignatureTokenConnection;
import eu.europa.esig.dss.tsl.cache.CacheCleaner;
import eu.europa.esig.dss.tsl.job.TLValidationJob;
import eu.europa.esig.dss.tsl.source.LOTLSource;
import eu.europa.esig.dss.validation.CommonCertificateVerifier;
import eu.europa.esig.dss.xades.XAdESSignatureParameters;
import eu.europa.esig.dss.xades.signature.XAdESService;

public class SignWithPAdES {
	
	public void sign(ParametersSelectedToSign selected) throws IOException {
		// Preparing parameters for the PAdES signature
		PAdESSignatureParameters parameters = new PAdESSignatureParameters();
		// We choose the level of the signature (-B, -T, -LT, -LTA).
		parameters.setSignatureLevel(SignatureLevel.valueOf(selected.getLevel()));
		// PAdES only can be choose the enveloped option
		// We set the digest algorithm to use with the signature algorithm. You must use the
		// same parameter when you invoke the method sign on the token. The default value is SHA256
		parameters.setDigestAlgorithm(DigestAlgorithm.valueOf(selected.getDigest_algorithm()));
		
		DSSPrivateKeyEntry privateKey = null;
		SignatureTokenConnection signingToken = null;
		
		if(selected.getDevice().equals("mscapi")) {
			// This is to sign with a spain ID or whatever with a device in Microsoft
			signingToken = new MSCAPISignatureToken();
			List<DSSPrivateKeyEntry> keys = signingToken.getKeys();
			privateKey = keys.get(0);
		}else {
			// This is to sign with Pkcs12
			signingToken= new Pkcs12SignatureToken(new BufferedInputStream(selected.getKey().getInputStream()), new KeyStore.PasswordProtection(selected.getPassword().toCharArray()));
			List<DSSPrivateKeyEntry> keys = signingToken.getKeys();
			for (DSSPrivateKeyEntry entry : keys) {
			    privateKey = entry;
			    break;
			}
		}
		
		// We set the signing certificate
		parameters.setSigningCertificate(privateKey.getCertificate());
		// We set the certificate chain
		parameters.setCertificateChain(privateKey.getCertificateChain());

		// Create common certificate verifier
		CommonCertificateVerifier commonCertificateVerifier = new CommonCertificateVerifier();
		
		// Create PAdES service for signature
		PAdESService service = new PAdESService(commonCertificateVerifier);
		
		/// This is for -T, -LT, -LTA
		String tspServer = "http://dss.nowina.lu/pki-factory/tsa/good-tsa"; //fake
		OnlineTSPSource onlineTSPSource = new OnlineTSPSource(tspServer);
		
		if(selected.getLevel().equals("PAdES_BASELINE_T")) {
			// This is for -T
			onlineTSPSource.setDataLoader(new TimestampDataLoader()); // uses the specific content-type
			service.setTspSource(onlineTSPSource);
		}else if(selected.getLevel().equals("PAdES_BASELINE_LT") || selected.getLevel().equals("PAdES_BASELINE_LTA")) {
			// This section is for -LT and -LTA
			CommonsDataLoader commonsHttpDataLoader = new CommonsDataLoader();
			OCSPDataLoader ocspDataLoader = new OCSPDataLoader();

			LOTLSource lotlSource = new LOTLSource();
			lotlSource.setUrl("https://ec.europa.eu/tools/lotl/eu-lotl.xml");
			lotlSource.setPivotSupport(true);

			TrustedListsCertificateSource tslCertificateSource = new TrustedListsCertificateSource();

			FileCacheDataLoader onlineFileLoader = new FileCacheDataLoader(commonsHttpDataLoader);

			CacheCleaner cacheCleaner = new CacheCleaner();
			cacheCleaner.setCleanFileSystem(true);
			cacheCleaner.setDSSFileLoader(onlineFileLoader);

			TLValidationJob validationJob = new TLValidationJob();
			validationJob.setTrustedListCertificateSource(tslCertificateSource);
			validationJob.setOnlineDataLoader(onlineFileLoader);
			validationJob.setCacheCleaner(cacheCleaner);
			validationJob.setListOfTrustedListSources(lotlSource);
			validationJob.onlineRefresh();

			commonCertificateVerifier.setTrustedCertSources(tslCertificateSource);

			OnlineCRLSource onlineCRLSource = new OnlineCRLSource();
			onlineCRLSource.setDataLoader(commonsHttpDataLoader);
			commonCertificateVerifier.setCrlSource(onlineCRLSource);
			
			OnlineOCSPSource onlineOCSPSource = new OnlineOCSPSource();
			onlineOCSPSource.setDataLoader(ocspDataLoader);
			commonCertificateVerifier.setOcspSource(onlineOCSPSource);

			// For test purpose
			// Will request unknown OCSP responder / download untrusted CRL
			commonCertificateVerifier.setCheckRevocationForUntrustedChains(true);
			// This is for -T
			onlineTSPSource.setDataLoader(new TimestampDataLoader()); // uses the specific content-type
			service.setTspSource(onlineTSPSource);
		}
		
		DSSDocument toSignDocument = new InMemoryDocument(new BufferedInputStream(selected.getFile().getInputStream()));
		// Get the SignedInfo XML segment that need to be signed.
		ToBeSigned dataToSign = service.getDataToSign(toSignDocument, parameters);

		// This function obtains the signature value for signed information using the
		// private key and specified algorithm
		SignatureValue signatureValue = signingToken.sign(dataToSign, parameters.getDigestAlgorithm(), privateKey);

		// We invoke the service to sign the document with the signature value obtained in
		// the previous step.
		DSSDocument signedDocument = service.signDocument(toSignDocument, parameters, signatureValue);
		
        signedDocument.save(System.getProperty("user.home")+"/Downloads"+"/signedFile.pdf");
		signingToken.close();
	}
	
	public void signBankPDF(byte[] file) throws IOException {
	
	// Preparing parameters for the PAdES signature
	PAdESSignatureParameters parameters = new PAdESSignatureParameters();
	// We choose the level of the signature (-B, -T, -LT, -LTA).
	parameters.setSignatureLevel(SignatureLevel.PAdES_BASELINE_B);
	// We set the digest algorithm to use with the signature algorithm. You must use the
	// same parameter when you invoke the method sign on the token. The default value is
	// SHA256
	
	MSCAPISignatureToken signingToken = new MSCAPISignatureToken();
	List<DSSPrivateKeyEntry> keys = signingToken.getKeys();
	DSSPrivateKeyEntry privateKey = keys.get(0);
	
	// Initialize visual signature and configure
	SignatureImageParameters imageParameters = new SignatureImageParameters();
	//options selected
	imageParameters.setPage(2); //1 by default
	imageParameters.setAlignmentVertical(VisualSignatureAlignmentVertical.MIDDLE);
	imageParameters.setAlignmentHorizontal(VisualSignatureAlignmentHorizontal.CENTER);
	
	// Instantiates a SignatureImageTextParameters object
	SignatureImageTextParameters textParameters = new SignatureImageTextParameters();
	// Allows you to set a DSSFont object that defines the text style (see more information in the section "Fonts usage")
	// Defines the text content
	textParameters.setText(privateKey.getCertificate().getSubject().getPrettyPrintRFC2253() +
							"\n This is a legal document and may be charged as a felony, keep it in a safe place. ");
	// Defines the color of the characters
	textParameters.setTextColor(Color.BLUE);
	// Define the font and size
	DSSFont font = new DSSJavaFont(Font.SERIF);
	font.setSize(8);
	textParameters.setFont(font);
	
	// Defines the background color for the area filled out by the text
	textParameters.setBackgroundColor(Color.YELLOW);
	// Defines a padding between the text and a border of its bounding area
	textParameters.setPadding(20);
	// Set textParameters to a SignatureImageParameters object
	imageParameters.setTextParameters(textParameters);
	parameters.setImageParameters(imageParameters);
	
	
	parameters.setDigestAlgorithm(DigestAlgorithm.SHA256);
	// We set the signing certificate
	parameters.setSigningCertificate(privateKey.getCertificate());
	// We set the certificate chain
	parameters.setCertificateChain(privateKey.getCertificateChain());

	// Create common certificate verifier
	CommonCertificateVerifier commonCertificateVerifier = new CommonCertificateVerifier();
	// Create PAdESService for signature
	PAdESService service = new PAdESService(commonCertificateVerifier);

	DSSDocument toSignDocument = new InMemoryDocument(file);
	// Get the SignedInfo segment that need to be signed.
	ToBeSigned dataToSign = service.getDataToSign(toSignDocument, parameters);

	// This function obtains the signature value for signed information using the
	// private key and specified algorithm
	DigestAlgorithm digestAlgorithm = parameters.getDigestAlgorithm();
	SignatureValue signatureValue = signingToken.sign(dataToSign, digestAlgorithm, privateKey);

	// We invoke the xadesService to sign the document with the signature value obtained in
	// the previous step.
	DSSDocument signedDocument = service.signDocument(toSignDocument, parameters, signatureValue);

	signedDocument.save(System.getProperty("user.home")+"/Downloads"+"/Contract.pdf");
	signingToken.close();
	}
}
