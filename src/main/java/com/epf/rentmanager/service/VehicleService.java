package com.epf.rentmanager.service;

import java.util.List;
import java.util.Optional;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.epf.rentmanager.dao.VehicleDao;

@Service
public class VehicleService {

	private VehicleDao vehicleDao;

	@Autowired
	public VehicleService(VehicleDao vehicleDao){
		this.vehicleDao = vehicleDao;
	}

	public int create(Vehicle vehicle) throws ServiceException {
		
		try {
			modelePresence(vehicle);
			constructeurPresence(vehicle);
			nbPlace(vehicle);
			return vehicleDao.create(vehicle);
		} catch (DaoException e) {
			throw new ServiceException("Il y a eu une erreur dans la Dao."+ e.getMessage());
		} catch (ServiceException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public Optional<Vehicle> findById(int id) throws ServiceException {
		
		try {

			if (vehicleDao.findById(id).isPresent()) {
				return vehicleDao.findById(id);
			}else {
				throw new DaoException();
			}

		} catch (DaoException e) {
			throw new ServiceException("Il y a eu une erreur dans la Dao"+ e.getMessage());
		}
		
	}

	public List<Vehicle> findAll() throws ServiceException {
		try {

			return vehicleDao.findAll();
			
		}catch (DaoException e) {
			throw new ServiceException("Il y a eu une erreur dans la Dao"+ e.getMessage());
		}
		
	}
	
	public int delete (Vehicle vehicule) throws ServiceException {

		try {

			if (vehicule !=null) {
				return vehicleDao.delete(vehicule);
			}
			throw new ServiceException();
			
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
	public int count() throws ServiceException {
		try {
			return vehicleDao.count();
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}
	public int changeinfo(Vehicle vehicle) throws ServiceException{
		try {
			return vehicleDao.changeinfo(vehicle);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public boolean modelePresence (Vehicle vehicle) throws ServiceException {
		if(vehicle.getModele() == null){
			throw new ServiceException("Vous n'avez pas rentré de modèle pour le véhicule");
		}
		return true;
	}

	public boolean constructeurPresence (Vehicle vehicle) throws ServiceException{
		if(vehicle.getConstructeur() == null){
			throw new ServiceException("Vous n'avez pas rentré de modèle pour le véhicule");
		}
		return true;
	}

	public boolean nbPlace (Vehicle vehicle) throws ServiceException{
		if(vehicle.getNb_places() > 9 || vehicle.getNb_places() < 2){
			throw new ServiceException("Vous n'avez pas rentré de modèle pour le véhicule");
		}
		return true;
	}

	public static VehicleService getInstance() {
		// TODO Auto-generated method stub
		return null;
	}
}
