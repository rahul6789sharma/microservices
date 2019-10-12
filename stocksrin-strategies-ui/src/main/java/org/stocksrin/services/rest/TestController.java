package org.stocksrin.services.rest;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

	@RequestMapping(value = "/getpdf", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getPDF() throws IOException {

		Path pdfPath = Paths.get("C:\\Users\\sharmarah\\Desktop\\mavn\\GuideGeneral.pdf");
		byte[] pdf = Files.readAllBytes(pdfPath);
		// retrieve contents of "C:/tmp/report.pdf" that were written in showHelp
		// byte[] contents = (...);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		// Here you have to set the actual filename of your pdf
		String filename = "output.pdf";
		headers.setContentDispositionFormData(filename, filename);
		ResponseEntity<byte[]> response = new ResponseEntity<>(pdf, headers, HttpStatus.OK);
		return response;
	}

/*	@RequestMapping(value = "/getpdf1", method = RequestMethod.GET)
	public ResponseEntity<FileInputStream> getPDF1() throws IOException {
		Path pdfPath = Paths.get("C:\\Users\\sharmarah\\Desktop\\mavn\\GuideGeneral.pdf");
		byte[] pdf = Files.readAllBytes(pdfPath);
		// retrieve contents of "C:/tmp/report.pdf" that were written in showHelp
		// byte[] contents = (...);
		try (ByteArrayInputStream stream = new ByteArrayInputStream(pdf);
				OutputStream outputStream = response.getOutputStream()) {

		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		// Here you have to set the actual filename of your pdf
		String filename = "output.pdf";
		headers.setContentDispositionFormData(filename, filename);

		ResponseEntity<byte[]> response = new ResponseEntity<>(pdf, headers, HttpStatus.OK);
		return response;
	}*/

	@RequestMapping(value = "/getpdf2", method = RequestMethod.GET)
	public javax.ws.rs.core.Response getPdf() throws Exception {
		File file = new File("C:\\\\Users\\\\sharmarah\\\\Desktop\\\\mavn\\\\GuideGeneral.pdf");
		FileInputStream fileInputStream = new FileInputStream(file);
		javax.ws.rs.core.Response.ResponseBuilder responseBuilder = javax.ws.rs.core.Response
				.ok((Object) fileInputStream);
		responseBuilder.type("application/pdf");
		responseBuilder.header("Content-Disposition", "filename=test.pdf");
		return responseBuilder.build();
	}
}
