package com.example.artidemo.controllers;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.artidemo.JsonResponse;
import com.example.artidemo.model.Product;
import com.example.artidemo.services.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	final static Logger logger = Logger.getLogger(ProductController.class);

	@Autowired
	ProductService productService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addProduct(@RequestBody Product product) {
		String str = productService.save(product);
		logger.debug("Added:: " + product);
		return new ResponseEntity<>(new JsonResponse().convertToJson(str), HttpStatus.OK);
	}
	
	@RequestMapping(path="/getProductById/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getProduct(@PathVariable("id") Long id) {
		Product product = productService.getById(id);
		if (product == null) {
			logger.debug("Product with id " + id + " does not exists");
			return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
			}
		return new ResponseEntity<>(product, HttpStatus.OK);
		}


	@RequestMapping(path="/getAllProducts",method = RequestMethod.GET)
	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> product = productService.getAll();
		if (product.isEmpty()) {
			logger.debug("Product does not exists");
			return new ResponseEntity<List<Product>>(HttpStatus.NO_CONTENT);
		}
		logger.debug("Found " + product.size() + " Products");
		logger.debug(product);
		logger.debug(Arrays.toString(product.toArray()));
		return new ResponseEntity<List<Product>>(product, HttpStatus.OK);
	}
	
	@RequestMapping(path="/getAllCities",method = RequestMethod.GET)
	public ResponseEntity<List<String>> getAllCities() {
		List<String> city = productService.getAllCities();
		if (city.isEmpty()) {
			logger.debug("Cities does not exists");
			return new ResponseEntity<List<String>>(HttpStatus.NO_CONTENT);
		}
		logger.debug("Found " + city.size() + " Products");
		logger.debug(city);
		//logger.debug(Arrays.toString(city));
		return new ResponseEntity<List<String>>(city, HttpStatus.OK);
	}

	@RequestMapping(path="/getAllMalls",method = RequestMethod.GET)
	public ResponseEntity<List<String>> getAllMalls() {
		List<String> mall = productService.getAllMalls();
		if (mall.isEmpty()) {
			logger.debug("Malls does not exists");
			return new ResponseEntity<List<String>>(HttpStatus.NO_CONTENT);
		}
		logger.debug("Found " + mall.size() + " Products");
		logger.debug(mall);
		//logger.debug(Arrays.toString(city));
		return new ResponseEntity<List<String>>(mall, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/deleteproductById/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id) {
		Product product = productService.getById(id);
		if (product == null) {
			logger.debug("Product with id " + id + " does not exists");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			String str = productService.delete(id);
			logger.debug("Product with id " + id + " deleted");
			return new ResponseEntity<>(str,HttpStatus.GONE);
		}
	}
	
	@RequestMapping(path="/updateProductById/{id}",method = RequestMethod.PUT)
	public ResponseEntity<String> updateProduct(@RequestBody Product product) {
		Product existingProduct = productService.getById(product.getId());
		if (existingProduct == null) {
			logger.debug("product with id " + product.getId() + " does not exists");
			String str=  "{ \"response\": \"does not exist\" }";	
			return new ResponseEntity<String>(str,HttpStatus.NOT_FOUND);
		} else {
			String str = productService.updateProductProfile(product);
			return new ResponseEntity<String>(str,HttpStatus.OK);
		}
	}
	
	@RequestMapping(path="/getProductByMallname/{mallname}", method = RequestMethod.GET)
	public ResponseEntity<List<Product>> getProductByMallname(@PathVariable("mallname") String mallname) {
		List<Product> product = productService.getByMallname(mallname);
			if (product.isEmpty()) {
				logger.debug("Product does not exists");
				return new ResponseEntity<List<Product>>(HttpStatus.NO_CONTENT);
			}
			logger.debug("Found " + product.size() + " Products");
			logger.debug(product);
			logger.debug(Arrays.toString(product.toArray()));
			return new ResponseEntity<List<Product>>(product, HttpStatus.OK);
		}
	
	@RequestMapping(path="/getProductByShopname/{shopname}", method = RequestMethod.GET)
	public ResponseEntity<List<Product>> getProductByShopname(@PathVariable("shopname") String shopname) {
		List<Product> product = productService.getByShopname(shopname);
			if (product.isEmpty()) {
				logger.debug("Product does not exists");
				return new ResponseEntity<List<Product>>(HttpStatus.NO_CONTENT);
			}
			logger.debug("Found " + product.size() + " Products");
			logger.debug(product);
			logger.debug(Arrays.toString(product.toArray()));
			return new ResponseEntity<List<Product>>(product, HttpStatus.OK);
		}
	
	@RequestMapping(path="/getProductByMallnameAndShopname/{mallname}/{shopname}", method = RequestMethod.GET)
	public ResponseEntity<List<Product>> getProductByMallnameAndShopname(@PathVariable("shopname") String shopname, @PathVariable("mallname") String mallname) {
		List<Product> product = productService.getByMallnameAndShopname(mallname,shopname);
			if (product.isEmpty()) {
				logger.debug("Product does not exists");
				return new ResponseEntity<List<Product>>(HttpStatus.NO_CONTENT);
			}
			logger.debug("Found " + product.size() + " Products");
			logger.debug(product);
			logger.debug(Arrays.toString(product.toArray()));
			return new ResponseEntity<List<Product>>(product, HttpStatus.OK);
		}
	
	
	
	
	@RequestMapping(path="/getAllShopsByMallname/{mallname}",method = RequestMethod.GET)
	public ResponseEntity<List<String>> getAllShopsByMallname(@PathVariable("mallname") String mallname) {
		List<String> shop = productService.getAllShopsByMallname(mallname);
		if (shop.isEmpty()) {
			logger.debug("Shops does not exists");
			return new ResponseEntity<List<String>>(HttpStatus.NO_CONTENT);
		}
		logger.debug("Found " + shop.size() + " Products");
		logger.debug(shop);
		logger.debug(Arrays.toString(shop.toArray()));
		return new ResponseEntity<List<String>>(shop, HttpStatus.OK);
	}
	
	
	
	
	
	
	@RequestMapping(path="/getProductByCategory/{Category}", method = RequestMethod.GET)
	public ResponseEntity<List<Product>> getProductByCategory(@PathVariable("Category") String Category) {
		List<Product> product = productService.getByCategory(Category);
			if (product.isEmpty()) {
				logger.debug("Product does not exists");
				return new ResponseEntity<List<Product>>(HttpStatus.NO_CONTENT);
			}
			logger.debug("Found " + product.size() + " Products");
			logger.debug(product);
			logger.debug(Arrays.toString(product.toArray()));
			return new ResponseEntity<List<Product>>(product, HttpStatus.OK);
		}
	
	@RequestMapping(path="/getProductByCategory2/{Category2}", method = RequestMethod.GET)
	public ResponseEntity<List<Product>> getProductByCategory2(@PathVariable("Category2") String Category) {
		List<Product> product = productService.getByCategory2(Category);
			if (product.isEmpty()) {
				logger.debug("Product does not exists");
				return new ResponseEntity<List<Product>>(HttpStatus.NO_CONTENT);
			}
			logger.debug("Found " + product.size() + " Products");
			logger.debug(product);
			logger.debug(Arrays.toString(product.toArray()));
			return new ResponseEntity<List<Product>>(product, HttpStatus.OK);
		}
	
	@RequestMapping(path="/getProductByBrandname/{brandname}", method = RequestMethod.GET)
	public ResponseEntity<List<Product>> getProductByBrandname(@PathVariable("brandname") String brandname) {
		List<Product> product = productService.getBybrandname(brandname);
			if (product.isEmpty()) {
				logger.debug("Product does not exists");
				return new ResponseEntity<List<Product>>(HttpStatus.NO_CONTENT);
			}
			logger.debug("Found " + product.size() + " Products");
			logger.debug(product);
			logger.debug(Arrays.toString(product.toArray()));
			return new ResponseEntity<List<Product>>(product, HttpStatus.OK);
		}
	
	
	@RequestMapping(path="/getProductByPricebelow/{offerprice}", method = RequestMethod.GET)
	public ResponseEntity<List<Product>> getProductByPricebelow(@PathVariable("offerprice") Long offerprice) {
		List<Product> product = productService.getByPricebelow(offerprice);
			if (product.isEmpty()) {
				logger.debug("Product does not exists");
				return new ResponseEntity<List<Product>>(HttpStatus.NO_CONTENT);
			}
			logger.debug("Found " + product.size() + " Products");
			logger.debug(product);
			logger.debug(Arrays.toString(product.toArray()));
			return new ResponseEntity<List<Product>>(product, HttpStatus.OK);
		}
	
	@RequestMapping(path="/getProductByPriceabove/{offerprice}", method = RequestMethod.GET)
	public ResponseEntity<List<Product>> getProductByPriceabove(@PathVariable("offerprice") Long offerprice) {
		List<Product> product = productService.getByPriceabove(offerprice);
			if (product.isEmpty()) {
				logger.debug("Product does not exists");
				return new ResponseEntity<List<Product>>(HttpStatus.NO_CONTENT);
			}
			logger.debug("Found " + product.size() + " Products");
			logger.debug(product);
			logger.debug(Arrays.toString(product.toArray()));
			return new ResponseEntity<List<Product>>(product, HttpStatus.OK);
		}
	
	
	@RequestMapping(path="/getProductBycity/{city}", method = RequestMethod.GET)
	public ResponseEntity<List<Product>> getProductBycity(@PathVariable("city") String city) {
		List<Product> product = productService.getBycity(city);
			if (product.isEmpty()) {
				logger.debug("Product does not exists");
				return new ResponseEntity<List<Product>>(HttpStatus.NO_CONTENT);
			}
			logger.debug("Found " + product.size() + " Products");
			logger.debug(product);
			logger.debug(Arrays.toString(product.toArray()));
			return new ResponseEntity<List<Product>>(product, HttpStatus.OK);
		}
	
	@SuppressWarnings("static-access")
	@RequestMapping(method= RequestMethod.GET,value="/getOfferforToday")
	public ResponseEntity<List<Product>> findByOfferForToday(){
		logger.info("In FindByOffersforToday method");
		Timestamp start = productService.convertToTimestamp(LocalDate.ofEpochDay(System.currentTimeMillis() / (24 * 60 * 60 * 1000) ).now().plusDays(1));
		Timestamp end = productService.convertToTimestamp (LocalDate.ofEpochDay(System.currentTimeMillis() / (24 * 60 * 60 * 1000) ).now());		
		logger.info("Fetching all Offer details of the last one month");
		List<Product> offerFortoday = productService.findByOfferDateEndGreaterThanAndOfferDateLessThan(end,start);
		logger.info("All Offer details of the last one month: "+offerFortoday+"Timestamp end:"+end+"Timestamp start:"+start);
		return new ResponseEntity<>(offerFortoday, HttpStatus.OK);
	}
	
	
	@SuppressWarnings("static-access")
	@RequestMapping(method= RequestMethod.GET,value="/getOfferforFuture")
	public ResponseEntity<List<Product>> findByOfferDate(){
		logger.info("Fetching all Offer details of Future");
		Timestamp start = productService.convertToTimestamp(LocalDate.ofEpochDay(System.currentTimeMillis() / (24 * 60 * 60 * 1000) ).now().plusDays(1));
		List<Product> offerForFuture = productService.findByOfferDateGreaterThanCurrent(start);
		logger.info("All Offer details of Future: "+offerForFuture);
		return new ResponseEntity<>(offerForFuture, HttpStatus.OK);
	}
	
	
	
	
	
	
	
		
		
}

