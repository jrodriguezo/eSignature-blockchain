package es.upm.dit.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.security.KeyStore;
import java.util.List;

import es.upm.dit.model.ParametersSelectedToSign;
import eu.europa.esig.dss.enumerations.DigestAlgorithm;
import eu.europa.esig.dss.enumerations.SignatureLevel;
import eu.europa.esig.dss.enumerations.SignaturePackaging;
import eu.europa.esig.dss.model.DSSDocument;
import eu.europa.esig.dss.model.InMemoryDocument;
import eu.europa.esig.dss.model.SignatureValue;
import eu.europa.esig.dss.model.ToBeSigned;
import eu.europa.esig.dss.service.crl.OnlineCRLSource;
import eu.europa.esig.dss.service.http.commons.CommonsDataLoader;
import eu.europa.esig.dss.service.http.commons.FileCacheDataLoader;
import eu.europa.esig.dss.service.http.commons.OCSPDataLoader;
import eu.europa.esig.dss.service.http.commons.TimestampDataLoader;
import eu.europa.esig.dss.service.ocsp.OnlineOCSPSource;
import eu.europa.esig.dss.service.tsp.OnlineTSPSource;
import eu.europa.esig.dss.spi.tsl.TrustedListsCertificateSource;
import eu.europa.esig.dss.spi.x509.KeyStoreCertificateSource;
import eu.europa.esig.dss.spi.x509.tsp.CompositeTSPSource;
import eu.europa.esig.dss.token.DSSPrivateKeyEntry;
import eu.europa.esig.dss.token.KSPrivateKeyEntry;
import eu.europa.esig.dss.token.MSCAPISignatureToken;
import eu.europa.esig.dss.token.Pkcs12SignatureToken;
import eu.europa.esig.dss.token.SignatureTokenConnection;
import eu.europa.esig.dss.tsl.cache.CacheCleaner;
import eu.europa.esig.dss.tsl.job.TLValidationJob;
import eu.europa.esig.dss.tsl.source.LOTLSource;
import eu.europa.esig.dss.validation.CommonCertificateVerifier;
import eu.europa.esig.dss.xades.XAdESSignatureParameters;
import eu.europa.esig.dss.xades.signature.XAdESService;


public class SignWithXAdES {
	
	// Sign file with BASIC level.
	public void sign(ParametersSelectedToSign selected) throws IOException {
		// Preparing parameters for the XAdES signature
		XAdESSignatureParameters parameters = new XAdESSignatureParameters();
		// We choose the level of the signature (-B, -T, -LT, -LTA).
		parameters.setSignatureLevel(SignatureLevel.valueOf(selected.getLevel()));
		// We choose the type of the signature packaging (ENVELOPED, ENVELOPING, DETACHED).
		parameters.setSignaturePackaging(SignaturePackaging.valueOf(selected.getPackaging()));
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

		// Create XAdES service for signature
		XAdESService service = new XAdESService(commonCertificateVerifier);
		
		// This is for -T, -LT, -LTA
		String tspServer = "http://dss.nowina.lu/pki-factory/tsa/good-tsa"; //fake
		OnlineTSPSource onlineTSPSource = new OnlineTSPSource(tspServer);
		
		if(selected.getLevel().equals("XAdES_BASELINE_T")) {
			// This is for -T
			onlineTSPSource.setDataLoader(new TimestampDataLoader()); // uses the specific content-type
			service.setTspSource(onlineTSPSource);
			System.out.println("T");
		}else if(selected.getLevel().equals("XAdES_BASELINE_LT") || selected.getLevel().equals("XAdES_BASELINE_LTA")) {
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
		
        signedDocument.save(System.getProperty("user.home")+"/Downloads"+"/signedFile.xml");
		String nSigned = signedDocument.getName();
		signingToken.close();
	}
	
	public void signLT(ParametersSelectedToSign selected) throws IOException {
		// Preparing parameters for the XAdES signature
		XAdESSignatureParameters parameters = new XAdESSignatureParameters();
		// We choose the level of the signature (-B, -T, -LT, -LTA).
		parameters.setSignatureLevel(SignatureLevel.valueOf(selected.getLevel()));
		// We choose the type of the signature packaging (ENVELOPED, ENVELOPING, DETACHED).
		parameters.setSignaturePackaging(SignaturePackaging.valueOf(selected.getPackaging()));
		// We set the digest algorithm to use with the signature algorithm. You must use the
		// same parameter when you invoke the method sign on the token. The default value is SHA256
		parameters.setDigestAlgorithm(DigestAlgorithm.valueOf(selected.getDigest_algorithm()));
		
		DSSPrivateKeyEntry privateKey = null;
		SignatureTokenConnection signingToken = null;
		KeyStoreCertificateSource keyStoreCertificateSource = null;
		
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
			keyStoreCertificateSource = new KeyStoreCertificateSource(new File("D:\\TFM\\PKCS12\\identity.p12"), "PKCS12", "password");
		}
		
		// We set the signing certificate
		parameters.setSigningCertificate(privateKey.getCertificate());
		// We set the certificate chain
		parameters.setCertificateChain(privateKey.getCertificateChain());

		// Create common certificate verifier
		CommonCertificateVerifier commonCertificateVerifier = new CommonCertificateVerifier();
		
		CommonsDataLoader commonsHttpDataLoader = new CommonsDataLoader();
		OCSPDataLoader ocspDataLoader = new OCSPDataLoader();
		
		LOTLSource lotlSource = new LOTLSource();
		lotlSource.setUrl("https://ec.europa.eu/tools/lotl/eu-lotl.xml");
		lotlSource.setCertificateSource(keyStoreCertificateSource);
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
		
		// Create XAdES service for signature
		XAdESService service = new XAdESService(commonCertificateVerifier);
		service.setTspSource(new OnlineTSPSource());
		
		
		CompositeTSPSource mockTSPSource= new CompositeTSPSource();
		service.setTspSource(mockTSPSource);
		//end TSP
		
		DSSDocument toSignDocument = new InMemoryDocument(new BufferedInputStream(selected.getFile().getInputStream()));
		// Get the SignedInfo XML segment that need to be signed.
		ToBeSigned dataToSign = service.getDataToSign(toSignDocument, parameters);

		// This function obtains the signature value for signed information using the
		// private key and specified algorithm
		SignatureValue signatureValue = signingToken.sign(dataToSign, parameters.getDigestAlgorithm(), privateKey);

		// We invoke the service to sign the document with the signature value obtained in
		// the previous step.
		DSSDocument signedDocument = service.signDocument(toSignDocument, parameters, signatureValue);
		
        signedDocument.save(System.getProperty("user.home")+"/Downloads"+"/signedFile.xml");
		String nSigned = signedDocument.getName();
		signingToken.close();
	}
}
