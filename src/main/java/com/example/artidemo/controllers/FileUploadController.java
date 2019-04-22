package com.example.artidemo.controllers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {
	
	@RequestMapping(value ="/upload",method = RequestMethod.POST, consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> uploadFile(@RequestParam ("file") MultipartFile file) throws IOException
	{
		File convertfile = new File("C:\\Users\\Nitesh\\Desktop\\fileuploaddemo\\"+file.getOriginalFilename());
		convertfile.createNewFile();
		FileOutputStream fout = new FileOutputStream(convertfile);
		fout.write(file.getBytes());
		fout.close();
		return new ResponseEntity<>("File Uploaded Successfully",HttpStatus.OK);
		
		
	}
		
	private static final String EXTERNAL_FILE_PATH = "C:\\Users\\Nitesh\\Desktop\\fileuploaddemo\\";

	@RequestMapping("/download/file/{fileName:.+}")
	public void downloadPDFResource(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("fileName") String fileName) throws IOException {

		File file = new File(EXTERNAL_FILE_PATH + fileName);
		if (file.exists()) {

			//get the mimetype
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				//unknown mimetype so set the mimetype to application/octet-stream
				mimeType = "application/octet-stream";
			}

			response.setContentType(mimeType);

			//response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));  //inline to show on webpage
			
			response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));  //attachment to download file

			response.setContentLength((int) file.length());

			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

			FileCopyUtils.copy(inputStream, response.getOutputStream());

		}
	}
	
	
	
	
	
	
	
	
	
	}