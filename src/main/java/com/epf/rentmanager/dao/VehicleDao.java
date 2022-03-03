package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleDao {
	
	private static VehicleDao instance = null;
	private VehicleDao() {}
	public static VehicleDao getInstance() {
		if(instance == null) {
			instance = new VehicleDao();
		}
		return instance;
	}
	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, modele, nb_places) VALUES(?, ?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle;";
	private static final String COUNT_VEHICLE_QUERY = "SELECT COUNT(*) FROM Vehicle;";
	private static final String CHANGE_VEHICLE_QUERY = "UPDATE Vehicle SET constructeur=?, modele=?, nb_places=? WHERE id=?;";

	/**
	* returns the number of vehicles in the database
	* @return the number of vehicles in the database
	* @throws DaoException if there is an error connecting to the database or in the query
	*/ 
	public int count() throws DaoException {

		try (

			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(COUNT_VEHICLE_QUERY);

			){

			ResultSet resultset = ps.executeQuery();
			resultset.next();
			return resultset.getInt(1);
			
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		
	}
	/**
	* Create a vehicle in the database
	* @param vehicle the information relating to the vehicle to be created
	* @return the generated key for the PreparedStatement or <code>null</code> if there is no generated key
	* @throws DaoException if there is an error connecting to the database or in the query
	*/ 
	
	public int create(Vehicle vehicle) throws DaoException {

		try (

			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(CREATE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS);

			){
			ps.setString(1, vehicle.getConstructeur());
			ps.setString(2, vehicle.getModele());
			ps.setInt(3, vehicle.getNb_places());
			ps.execute();
			ResultSet resultSet = ps.getGeneratedKeys();

			if(resultSet.next()){
				return resultSet.getInt(1);
			}else {
				throw new DaoException();
			}
			
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		
	}

	/**
	* Deletes a vehicle in the database
	* @param vehicle the information relating to the vehicle to be deleted
	* @return the generated key for the PreparedStatement or <code>null</code> if there is no generated key
	* @throws DaoException if there is an error connecting to the database or in the query
	*/ 
	public int delete(Vehicle vehicule) throws DaoException {

		try (

			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(DELETE_VEHICLE_QUERY);
		){

			ps.setInt(1, vehicule.getId());
			return ps.executeUpdate();
			
		} catch (SQLException e) {
			throw new DaoException();
		}
		
	}

	/**
	* Returns a vehicle based on id
	* @param id the id of the vehicle to search for
	* @return an Optional containing a vehicle if there is a vehicle with the corresponding id, and null if there is no vehicle
	* @throws DaoException if there is an error connecting to the database or in the query
	*/ 
	public Optional<Vehicle> findById(int id) throws DaoException {
		
		Vehicle vehicule = new Vehicle();
		try (
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_VEHICLE_QUERY);
		
		){

			ps.setInt(1, id);
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()){
				vehicule.setId(resultSet.getInt("id"));
				vehicule.setConstructeur(resultSet.getString("constructeur"));
				vehicule.setModele(resultSet.getString("modele"));
				vehicule.setNb_places(resultSet.getInt("nb_places"));
				return Optional.of(vehicule);

			}else {
				return Optional.empty();
			}


		} catch (SQLException e) {
			throw new DaoException();
		}

	}

	/**
	* Returns all vehicles in the database
	* @return A list containing all the vehicles in the database
	* @throws DaoException if there is an error connecting to the database or in the query
	*/ 
	public List<Vehicle> findAll() throws DaoException {

		List<Vehicle> result = new ArrayList<>();
		try (

			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_VEHICLES_QUERY);
		){
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()){
				Vehicle vehicule = new Vehicle();
				vehicule.setId(resultSet.getInt("id"));
				vehicule.setConstructeur(resultSet.getString("constructeur"));
				vehicule.setNb_places(resultSet.getInt("nb_places"));
				vehicule.setModele(resultSet.getString("modele"));
				result.add(vehicule);
			}
			

		} catch (SQLException e) {
			throw new DaoException();
		}

		return result;

	}
	/**
	* Change vehicle information
	* @param vehicle the new vehicle information
	* @return the generated key for the PreparedStatement or <code>null</code> if there is no generated key
	* @throws DaoException if there is an error connecting to the database or in the query
	*/ 
	public int changeinfo (Vehicle vehicle) throws DaoException{

		try(
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(CHANGE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS);
		) 
		{
			ps.setString(1, vehicle.getConstructeur());
			ps.setString(2, vehicle.getModele());
			ps.setInt(3, vehicle.getNb_places());
			ps.setInt(4, vehicle.getId());

			ps.executeUpdate();
			ResultSet resultSet = ps.getGeneratedKeys();

			if (resultSet.next()) {
				return resultSet.getInt(1);
			} else {
				throw new DaoException();
			}
			
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

}
