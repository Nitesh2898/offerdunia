package com.example.artidemo.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.artidemo.model.Vendor;
import com.example.artidemo.repositories.VendorRepository;

@Service
public class VendorService {
	
	public final static Logger logger = Logger.getLogger(VendorService.class);
	@Autowired
	private VendorRepository vendorRepository;
	
	public String save(Vendor entity)
	{
		if(!vendorRepository.exists(entity.getContactNo()))
		{
			logger.info("In adduser function"+entity);
			vendorRepository.save(entity);
			return "You have been registered successfully.";
		}
		else
		{
			logger.info("user already exist");
			return "This user_id is already registered";
		}
	}
	
	public Vendor getById(long id){
		return vendorRepository.findOne(id);
	}
	
	public List<Vendor> getAll(){
		return vendorRepository.findAll();
	}
	
	public String delete(long id)
	{
		vendorRepository.delete(id);
		return "{ \"response\": \"deleted Successfully\"}";
	}
	
	public String login(Vendor input) {
		
		Vendor vendor = vendorRepository.findOne(input.getId());
		if(vendor!=null)
			return vendorLogin(vendor,input);
		else
			return "{ \"response\": \"Please Register\" }";
		
	}
	
	private String vendorLogin(Vendor vendor, Vendor input) {
		if(vendor.getPassword().equals(input.getPassword()))
		{
			return "{ \"entity\": \"admin\", \"response\": \"success\"}";
		}
		else
			return "{ \"entity\": \"admin\", \"response\": \"wrong password\" }";
	}
	
	public String updateVendorProfile(Vendor input){
		Vendor vendor= vendorRepository.findOne(input.getId());
		if(vendor!=null){
			vendorRepository.delete(vendor.getId());
			vendorRepository.save(input);
			return "{ \"response\": \"profile updated successfully\" }";	
		}
		else
		return "{ \"response\": \"failed to update\" }";	
	}

	public List<Vendor> getBycity(String city) {
		
		return vendorRepository.findBycity(city);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
