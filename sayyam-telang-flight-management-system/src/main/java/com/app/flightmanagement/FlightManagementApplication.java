package com.app.flightmanagement;




import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.app.flightmanagement.entity.Admin;
import com.app.flightmanagement.serviceImpl.AdminServiceImpl;



@SpringBootApplication
public class FlightManagementApplication {
	
//	@Autowired
//	AdminServiceImpl adminService;
//	
//	@PostConstruct
//	public void initAdmin() {
//		Admin admin = new Admin();
//		admin.setAdminName("rahul");
//		admin.setPassword("12345678");
//		adminService.addAdmin(admin);
//	}
	
	public static void main(String[] args) {
		SpringApplication.run(FlightManagementApplication.class, args);
	}

}
