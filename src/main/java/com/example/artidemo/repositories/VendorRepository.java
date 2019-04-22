package com.example.artidemo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.artidemo.model.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {

	List<Vendor> findBycity(String city);

}
