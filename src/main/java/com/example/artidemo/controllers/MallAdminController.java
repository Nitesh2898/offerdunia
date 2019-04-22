package com.example.artidemo.controllers;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.artidemo.JsonResponse;
import com.example.artidemo.model.MallAdmin;
import com.example.artidemo.services.MallAdminService;

@RestController
@RequestMapping("/Admin")
public class MallAdminController {
	
	final static Logger logger = Logger.getLogger(MallAdminController.class);

	@Autowired
	MallAdminService malladminService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addAdmin(@RequestBody MallAdmin admin) {
		String str = malladminService.save(admin);
		logger.debug("Added:: " + admin);
		return new ResponseEntity<>(new JsonResponse().convertToJson(str), HttpStatus.OK);
	}
	
	@RequestMapping(path="/updateAdminById/{id}",method = RequestMethod.PUT)
	public ResponseEntity<String> updateAdmin(@RequestBody MallAdmin admin) {
		MallAdmin existingAdmin = malladminService.getById(admin.getId());
		if (existingAdmin == null) {
			logger.debug("admin with id " + admin.getId() + " does not exists");
			String str=  "{ \"response\": \"does not exist\" }";	
			return new ResponseEntity<String>(str,HttpStatus.NOT_FOUND);
		} else {
			String str = malladminService.updateAdminProfile(admin);
			return new ResponseEntity<String>(str,HttpStatus.OK);
		}
	}
	
	@RequestMapping(path="/getAdminById/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getAdmin(@PathVariable("id") Long id) {
		MallAdmin admin = malladminService.getById(id);
		if (admin == null) {
			logger.debug("admin with id " + id + " does not exists");
			return new ResponseEntity<MallAdmin>(HttpStatus.NOT_FOUND);
			}
		return new ResponseEntity<>(admin, HttpStatus.OK);
		}


	@RequestMapping(path="/getAllAdmin",method = RequestMethod.GET)
	public ResponseEntity<List<MallAdmin>> getAllVendors() {
		List<MallAdmin> admin = malladminService.getAll();
		if (admin.isEmpty()) {
			logger.debug("Admin does not exists");
			return new ResponseEntity<List<MallAdmin>>(HttpStatus.NO_CONTENT);
		}
		logger.debug("Found " + admin.size() + " Vendors");
		logger.debug(admin);
		logger.debug(Arrays.toString(admin.toArray()));
		return new ResponseEntity<List<MallAdmin>>(admin, HttpStatus.OK);
	}


	@RequestMapping(path = "/deleteAdminById/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteAdmin(@PathVariable("id") Long id) {
		MallAdmin admin = malladminService.getById(id);
		if (admin == null) {
			logger.debug("MallAdmin with id " + id + " does not exists");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			String str = malladminService.delete(id);
			logger.debug("MallAdmin with id " + id + " deleted");
			return new ResponseEntity<>(str,HttpStatus.GONE);
		}
	}
	
	@RequestMapping(method= RequestMethod.POST,value="/login")
	public ResponseEntity<String> Login(@RequestBody MallAdmin input){
		String str="";
		try{
			str=malladminService.login(input);
		}catch (HttpMessageNotReadableException e) 
		{
			logger.error("Could not authenticate. Required request body is missing");
			return new ResponseEntity<>(new JsonResponse().convertToJson("Could not authenticate. Required request body is missing"),HttpStatus.BAD_REQUEST);
		}catch (java.lang.NullPointerException e) {
			logger.error("Could not authenticate. Required request body is incomplete");
			return new ResponseEntity<>(new JsonResponse().convertToJson("Could not authenticate. Required request body is incomplete"),HttpStatus.BAD_REQUEST);
		}catch (DataAccessException e) {
			logger.error("Username does not exist.");
			return new ResponseEntity<>(new JsonResponse().convertToJson("Could not authenticate."),HttpStatus.BAD_REQUEST);
		}
		if(str==null)
		{
			logger.error("Could not authenticate. Insufficient data sent.");
			return new ResponseEntity<>(new JsonResponse().convertToJson("Could not authenticate Admin. Insufficient data sent."),HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(str,HttpStatus.OK);			
	}
	
	@RequestMapping(path="/getMallNameBycity/{city}", method = RequestMethod.GET)
	public ResponseEntity<List<MallAdmin>> getMallNameBycity(@PathVariable("city") String city) {
		List<MallAdmin> mall = malladminService.getBycity(city);
			if (mall.isEmpty()) {
				logger.debug("Malls does not exists");
				return new ResponseEntity<List<MallAdmin>>(HttpStatus.NO_CONTENT);
			}
			logger.debug("Found " + mall.size() + " Malls");
			logger.debug(mall);
			logger.debug(Arrays.toString(mall.toArray()));
			return new ResponseEntity<List<MallAdmin>>(mall, HttpStatus.OK);
		}	

}
