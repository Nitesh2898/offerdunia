package com.example.artidemo.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.artidemo.model.MallAdmin;

@Repository
public interface MallAdminRepository extends CrudRepository<MallAdmin, Long>{

	List<MallAdmin> findBycity(String city);
	boolean existsBymallname(String mallname);
	//boolean existsBycity(String city);

}
