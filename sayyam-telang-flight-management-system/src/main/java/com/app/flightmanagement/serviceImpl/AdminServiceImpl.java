package com.app.flightmanagement.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.flightmanagement.dao.AdminDao;
import com.app.flightmanagement.dao.BookingDetailsDao;
import com.app.flightmanagement.dao.FlightDetailsDao;
import com.app.flightmanagement.dao.PassengerDao;
import com.app.flightmanagement.entity.Admin;
import com.app.flightmanagement.entity.BookingDetails;
import com.app.flightmanagement.entity.FlightDetails;
import com.app.flightmanagement.entity.Passenger;
import com.app.flightmanagement.exception.AdminAlreadyExistException;
import com.app.flightmanagement.exception.AdminDoesnotExistException;
import com.app.flightmanagement.exception.BookingDoesNotFoundException;
import com.app.flightmanagement.exception.FlightDetailsNotFoundException;
import com.app.flightmanagement.exception.NullAdminException;
import com.app.flightmanagement.exception.NullFlightDetailsException;
import com.app.flightmanagement.exception.NullUserException;
import com.app.flightmanagement.exception.UserDoesnotExistException;
import com.app.flightmanagement.service.AdminService;
import com.app.flightmanagement.utils.AdminAuth;


@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminDao adminDao;

	@Autowired
	FlightDetailsDao flightDao;
	
	@Autowired
	PassengerDao passengerDao;
	
	@Autowired
	BookingDetailsDao bookingDao;

	
	@Override
	public Admin addAdmin(Admin admin) {
		if (admin == null)
			throw new NullAdminException("no data provided");
		Integer adminId = (int) ((Math.random() * 900) + 100);
		
		admin.setAdminId(adminId);
		Optional<Admin> checkAdmin = adminDao.findById(admin.getAdminId());
		if (checkAdmin.isPresent()) {
			throw new AdminAlreadyExistException("admin already exist exception");
		} else {
			adminDao.save(admin);
			System.out.println(adminId);
			return admin;
		}
	}

	@Override
	public Admin getAdmin(Integer adminId) {
		if (adminId == null)
			throw new NullAdminException("no data provided");
		Optional<Admin> admin = adminDao.findById(adminId);
		if (!admin.isPresent()) {
			throw new AdminDoesnotExistException("admin does not exist ");
		}
		return admin.get();
	}
	
	
	@Override
	public void deleteAdmin(Integer adminId) {
		if (adminId == null)
			throw new NullAdminException("no data provided");
		Optional<Admin> admin = adminDao.findById(adminId);
		if (!admin.isPresent()) {
			throw new AdminDoesnotExistException("admin Doesnot Exist Exception");
		}
		adminDao.deleteById(adminId);
	}

	@Override
	public Admin adminLogin(AdminAuth auth) {
		if (auth == null) {
			throw new NullAdminException("no data provided");
		}
		Optional<Admin> admin = adminDao.findById(auth.getAdminId());
		if (admin.isPresent()) {
			if (admin.get().getAdminId() == auth.getAdminId() && admin.get().getPassword().equals(auth.getPassword())) {
				return admin.get();
			} else {
				throw new AdminDoesnotExistException("invalid login id or password");
			}
			
		} else
			throw new AdminDoesnotExistException("admin doesnot exist");
	}

	@Override
	public List<FlightDetails> getAllFlightDetails() {
		return flightDao.findAll();
	}

	@Override
	public FlightDetails addFlightDetails(FlightDetails details) {
		if (details == null) {
			throw new NullFlightDetailsException("no data provided");
		}
		Integer flightNumber = (int) ((Math.random() * 9000) + 1000);
		details.setFlightNumber(flightNumber);
		flightDao.save(details);
		return details;
	}

	@Override
	public void deleteFlight(Integer flightNumber) {
		if (flightNumber == null)
			throw new NullFlightDetailsException("No data recieved..");
		Optional<FlightDetails> details = flightDao.findById(flightNumber);
		if (!details.isPresent()) {
			throw new FlightDetailsNotFoundException("Flight Details not found");
		}
		flightDao.deleteById(flightNumber);
	}

	@Override
	public FlightDetails updateFlight(FlightDetails details) {
		if (details == null)
			throw new NullFlightDetailsException("No data recieved..");
		Optional<FlightDetails> flightDetails = flightDao.findById(details.getFlightNumber());
		if (!flightDetails.isPresent()) {
			throw new FlightDetailsNotFoundException("Flight with flightNumber: " + details.getFlightNumber() + " not exists..");
		}
		flightDao.save(details);
		return details;
	}
	
	public List<Passenger> getAllPassengers(){
		return passengerDao.findAll();
	}
	
	public List<Passenger> getPassengersByBooking(Integer id){
		if (id == null) throw new BookingDoesNotFoundException("no data provided");
		Optional<BookingDetails> details = bookingDao.findById(id);
		if (!details.isPresent())
			throw new BookingDoesNotFoundException("booking not found");
		return details.get().getPassengers();
	}

}
