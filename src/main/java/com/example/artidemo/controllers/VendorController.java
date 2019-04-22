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
import com.example.artidemo.model.Vendor;
import com.example.artidemo.services.VendorService;

@RestController
@RequestMapping("/vendor")
public class VendorController {
	
	final static Logger logger = Logger.getLogger(VendorController.class);

	@Autowired
	VendorService vendorService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addVendor(@RequestBody Vendor vendor) {
		String str = vendorService.save(vendor);
		logger.debug("Added:: " + vendor);
		return new ResponseEntity<>(new JsonResponse().convertToJson(str), HttpStatus.OK);
	}
	
	@RequestMapping(path="/updateVendorById/{id}",method = RequestMethod.PUT)
	public ResponseEntity<String> updateVendor(@RequestBody Vendor vendor) {
		Vendor existingVendor = vendorService.getById(vendor.getId());
		if (existingVendor == null) {
			logger.debug("vendor with id " + vendor.getId() + " does not exists");
			String str=  "{ \"response\": \"does not exist\" }";	
			return new ResponseEntity<String>(str,HttpStatus.NOT_FOUND);
		} else {
			String str = vendorService.updateVendorProfile(vendor);
			return new ResponseEntity<String>(str,HttpStatus.OK);
		}
	}
	
	@RequestMapping(path="/getVendorById/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getVendor(@PathVariable("id") Long id) {
		Vendor vendor = vendorService.getById(id);
		if (vendor == null) {
			logger.debug("Vendor with id " + id + " does not exists");
			return new ResponseEntity<Vendor>(HttpStatus.NOT_FOUND);
			}
		return new ResponseEntity<>(vendor, HttpStatus.OK);
		}


	@RequestMapping(path="/getAllVendors",method = RequestMethod.GET)
	public ResponseEntity<List<Vendor>> getAllVendors() {
		List<Vendor> vendors = vendorService.getAll();
		if (vendors.isEmpty()) {
			logger.debug("Vendors does not exists");
			return new ResponseEntity<List<Vendor>>(HttpStatus.NO_CONTENT);
		}
		logger.debug("Found " + vendors.size() + " Vendors");
		logger.debug(vendors);
		logger.debug(Arrays.toString(vendors.toArray()));
		return new ResponseEntity<List<Vendor>>(vendors, HttpStatus.OK);
	}


	@RequestMapping(path = "/deleteVendorById/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteVendor(@PathVariable("id") Long id) {
		Vendor vendor = vendorService.getById(id);
		if (vendor == null) {
			logger.debug("Vendor with id " + id + " does not exists");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			String str = vendorService.delete(id);
			logger.debug("Vendor with id " + id + " deleted");
			return new ResponseEntity<>(str,HttpStatus.GONE);
		}
	}
	
	@RequestMapping(method= RequestMethod.POST,value="/login")
	public ResponseEntity<String> Login(@RequestBody Vendor input){
		String str="";
		try{
			str=vendorService.login(input);
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
	public ResponseEntity<List<Vendor>> getMallNameBycity(@PathVariable("city") String city) {
		List<Vendor> mall = vendorService.getBycity(city);
			if (mall.isEmpty()) {
				logger.debug("Malls does not exists");
				return new ResponseEntity<List<Vendor>>(HttpStatus.NO_CONTENT);
			}
			logger.debug("Found " + mall.size() + " Malls");
			logger.debug(mall);
			logger.debug(Arrays.toString(mall.toArray()));
			return new ResponseEntity<List<Vendor>>(mall, HttpStatus.OK);
		}	
	
	
	
	

}
