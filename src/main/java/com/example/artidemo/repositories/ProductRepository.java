package com.example.artidemo.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.artidemo.model.Product;


@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

	List<Product> findBymallname(String mallname);

	List<Product> findBycategory(String category);

	List<Product> findBycategory2(String category);

	List<Product> findBybrandname(String brandname);

	List<Product> findByofferpriceLessThanEqual(Long offerprice);

	List<Product> findByofferpriceGreaterThanEqual(Long offerprice);

	List<Product> findBycity(String city);

	List<Product> findByOfferDateEndGreaterThanEqualAndOfferDateLessThanEqual(Timestamp end, Timestamp start);

	List<Product> findByOfferDateAfter(Timestamp start);

	boolean existsByproductname(String productname);

	boolean existsBymallname(String mallname);

	@Query("SELECT DISTINCT city from Product")
	List<String> findDistinctcity();

	@Query("SELECT DISTINCT mallname from Product")
	List<String> findDistinctmallname();
	
	@Query("SELECT DISTINCT p.shopname from Product p WHERE p.mallname = :mallname")
	List<String> findDistinctshopnameBymallname(@Param("mallname") String mallname);

	List<Product> findByshopname(String shopname);
	
	@Query("SELECT DISTINCT p.productname FROM Product p WHERE p.mallname = :mallname AND p.shopname = :shopname ")
	List<Product> findProductBymallnameAndshopname(@Param("mallname") String mallname, @Param("shopname") String shopname);

}
