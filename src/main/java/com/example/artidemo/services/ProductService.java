package com.example.artidemo.services;

import java.util.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.artidemo.model.Product;
import com.example.artidemo.repositories.ProductRepository;

@Service
public class ProductService {
	
	public final static Logger logger = Logger.getLogger(ProductService.class);
	
	@Autowired
	ProductRepository productRepository;
	
	public String save(Product entity)
	{
		if(!productRepository.existsByproductname(entity.getProductname()))
		{
			logger.info("In addProduct function"+entity);
			productRepository.save(entity);
			return "Product Added successfully.";
		}
		else
		{
			logger.info("Product already exist");
			return "This Product is already registered";
		}
	}
	
	public Product getById(long id){
		return productRepository.findOne(id);
	}
	
	public List<Product> getAll(){
		return (List<Product>) productRepository.findAll();
	}
	
	public String delete(long id)
	{
		productRepository.delete(id);
		return "{ \"response\": \"deleted Successfully\"}";
	}
	
	public String updateProductProfile(Product input){
		Product product= productRepository.findOne(input.getId());
		if(product!=null){
			productRepository.delete(product.getId());
			productRepository.save(input);
			return "{ \"response\": \"product details updated successfully\" }";	
		}
		else
		return "{ \"response\": \"failed to update product details\" }";	
	}

	public List<Product> getByMallname(String mallname) {
		
		return productRepository.findBymallname(mallname);
	}

	public List<Product> getByCategory(String category) {
		
		return productRepository.findBycategory(category);

	}

	public List<Product> getByCategory2(String category) {
		
		return productRepository.findBycategory2(category);
	}

	public List<Product> getBybrandname(String brandname) {
		
		return productRepository.findBybrandname(brandname);
	}

	public List<Product> getByPricebelow(Long offerprice) {

		return productRepository.findByofferpriceLessThanEqual(offerprice);
	}

	public List<Product> getByPriceabove(Long offerprice) {
		
		return productRepository.findByofferpriceGreaterThanEqual(offerprice);
	}

	public List<Product> getBycity(String city) {
		
		return productRepository.findBycity(city);
	}

	public Timestamp convertToTimestamp(LocalDate localDate) {
		Date date = Date.from(localDate.atStartOfDay()
				.atZone(ZoneId.systemDefault()).toInstant());
		Timestamp timeStamp = new Timestamp(date.getTime());
		logger.info("timestamp from convertToTimestamp method"+timeStamp+" date"+date);
		return timeStamp;
	}

	public List<Product> findByOfferDateEndGreaterThanAndOfferDateLessThan(Timestamp end, Timestamp start) {
		logger.info("In findByOfferDateGreaterThanAndOfferDateLessThan function");
		List<Product>  offerForPastMonth = productRepository.findByOfferDateEndGreaterThanEqualAndOfferDateLessThanEqual(end, start);
		return offerForPastMonth;
	}

	public List<Product> findByOfferDateGreaterThanCurrent(Timestamp start) {
		logger.info("In findByOfferDateGreaterThanAndOfferDateLessThan function");
		List<Product>  offerForFuture = productRepository.findByOfferDateAfter(start);
		return offerForFuture;
	}

	public List<String> getAllCities() {
		return productRepository.findDistinctcity();
	}

	public List<String> getAllMalls() {
		return productRepository.findDistinctmallname();
	}

	public List<Product> getByShopname(String shopname) {
		return productRepository.findByshopname(shopname);
	}

	public List<String> getAllShopsByMallname(String mallname) {
		return productRepository.findDistinctshopnameBymallname(mallname);
	}

	public List<Product> getByMallnameAndShopname(String mallname, String shopname) {
		return productRepository.findProductBymallnameAndshopname(mallname,shopname);
	}

}
