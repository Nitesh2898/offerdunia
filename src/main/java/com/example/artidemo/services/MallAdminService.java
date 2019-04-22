package com.example.artidemo.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.artidemo.model.MallAdmin;
import com.example.artidemo.repositories.MallAdminRepository;

@Service
public class MallAdminService {
	
	public final static Logger logger = Logger.getLogger(MallAdminService.class);
	@Autowired
	private MallAdminRepository mallAdminRepository;
	
	public String save(MallAdmin entity)
	{
		if(!mallAdminRepository.existsBymallname(entity.getMallname()))
		{
			logger.info("In add Admin function"+entity);
			mallAdminRepository.save(entity);
			return "You have been registered successfully.";
		}
		
		else
		{
			logger.info("Admin already exist");
			return "This Mall Admin is already registered";
		}
	}
	
	public MallAdmin getById(long id){
		return mallAdminRepository.findOne(id);
	}
	
	public List<MallAdmin> getAll(){
		return (List<MallAdmin>) mallAdminRepository.findAll();
	}
	
	public String delete(long id)
	{
		mallAdminRepository.delete(id);
		return "{ \"response\": \"deleted Successfully\"}";
	}
	
	public String login(MallAdmin input) {
		
		MallAdmin admin = mallAdminRepository.findOne(input.getId());
		if(admin!=null)
			return adminLogin(admin,input);
		else
			return "{ \"response\": \"Please Register\" }";
		
	}
	
	private String adminLogin(MallAdmin admin, MallAdmin input) {
		if(admin.getPassword().equals(input.getPassword()))
		{
			return "{ \"entity\": \"admin\", \"response\": \"success\"}";
		}
		else
			return "{ \"entity\": \"admin\", \"response\": \"wrong password\" }";
	}
	
	public String updateAdminProfile(MallAdmin input){
		MallAdmin admin= mallAdminRepository.findOne(input.getId());
		if(admin!=null){
			mallAdminRepository.delete(admin.getId());
			mallAdminRepository.save(input);
			return "{ \"response\": \"profile updated successfully\" }";	
		}
		else
		return "{ \"response\": \"failed to update\" }";	
	}

	public List<MallAdmin> getBycity(String city) {
		
		return mallAdminRepository.findBycity(city);
	}

}
