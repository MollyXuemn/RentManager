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
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDao {
	private ReservationDao() {} 
	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
	private static final String FIND_RESERVATION_BY_ID = "SELECT client_id, vehicle_id, debut, fin FROM Reservation WHERE id=?;";
	private static final String NB_VEHICLEID_BY_CLIENT = "SELECT DISTINCT vehicle_id FROM Reservation WHERE client_id=?;";
	private static final String NB_CLIENTID_BY_VEHICLE = "SELECT DISTINCT client_id FROM Reservation WHERE vehicle_id=?;";
	private static final String DATEBEFORE_VEHICLEID_QUERY = "SELECT debut, fin FROM Reservation WHERE vehicle_id=? ORDER BY debut DESC;";
	private static final String DATEAFTER_VEHICLEID_QUERY = "SELECT debut, fin FROM Reservation WHERE vehicle_id=? ORDER BY debut;";
	private static final String UPDATE_RESA_QUERY = "UPDATE Vehicule SET vehicle_id = ?, debut = ?, fin = ? WHERE id=?;";
	
	/**
	* Change a customer's information in the database
	* @param reservation The new customer information
	* @return the generated key for the PreparedStatement or <code>null</code> if there is no generated key
	* @throws DaoException if there is an error connecting to the database or in the query
	*/ 
	public long update(Reservation reservation) throws DaoException{
		try (
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(UPDATE_RESA_QUERY, Statement.RETURN_GENERATED_KEYS);
		){
			ps.setInt(4, reservation.getId());
			ps.setInt(1, reservation.getVehicle_id());
			ps.setDate(2, reservation.getDebut());
			ps.setDate(3, reservation.getFin());
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
	
	
	/**
	* Create a reservation
	* @param reservation the new reservation to add to the database
	* @return the generated key for the PreparedStatement or <code>null</code> if there is no generated key
	* @throws DaoException if there is an error connecting to the database or in the query
	*/ 
	public long create(Reservation reservation) throws DaoException {

		try (
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(CREATE_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS);
		)
		{

			ps.setInt(1, reservation.getClient_id());
			ps.setInt(2, reservation.getVehicle_id());
			ps.setDate(3, reservation.getDebut());
			ps.setDate(4, reservation.getFin());
			ps.executeUpdate();
			ResultSet resultset = ps.getGeneratedKeys();

			if (resultset.next()){
				return resultset.getInt(1);
			}else {
				throw new DaoException();
			}


		} catch (Exception e) {
			throw new DaoException();
		}
		
	}
	
	/**
	* Deletes a reservation
	* @param reservation the reservation to delete in the database
	* @return the generated key for the PreparedStatement or <code>null</code> if there is no generated key
	* @throws DaoException if there is an error connecting to the database or in the query
	*/ 
	
	public long delete(Reservation reservation) throws DaoException {
		try(
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(DELETE_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS);
		) 
		
		{
			ps.setInt(1, reservation.getId());
			ps.executeUpdate();
			ResultSet resultset = ps.getGeneratedKeys();
			if (resultset.next()){
				return resultset.getInt(1);
			}else {
				throw new DaoException();
			}

		} catch (SQLException e) {
			throw new DaoException();
		}
		
	}

	/**
	* returns all reservations associated with a customer
	* @param clientId the client id to find reservations
	* @return a list containing the reservations associated with the customer's id
	* @throws DaoException if there is an error connecting to the database or in the query
	*/ 
	public List<Reservation> findResaByClientId(int clientId) throws DaoException {

		List<Reservation> resavid = new ArrayList<>();
		
		try(
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);

		 ) 
		 {
			ps.setInt(1, clientId);
			ResultSet resultset = ps.executeQuery();
			

			while(resultset.next()){
				Reservation reservation = new Reservation();
				reservation.setClient_id(clientId);
				reservation.setVehicle_id(resultset.getInt("vehicle_id"));
				reservation.setDebut(resultset.getDate("debut"));
				reservation.setFin(resultset.getDate("fin"));
				reservation.setId(resultset.getInt("id"));
				resavid.add(reservation);

			}

			 
		 } catch (SQLException e) {
			 throw new DaoException();
		 }
		 return resavid;
		
	}
	/**
	* returns all reservations associated with a vehicle
	* @param clientId the client id to find reservations
	* @return a list containing the reservations associated with the customer's id
	* @throws DaoException if there is an error connecting to the database or in the query
	*/ 
	
	public List<Reservation> findResaByVehicleId(int vehicleId) throws DaoException {
		 List<Reservation> resavid = new ArrayList<>();
		
		try(
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);

		 ) 
		 {
			ps.setInt(1, vehicleId);
			ResultSet resultset = ps.executeQuery();
			

			while(resultset.next()){
				Reservation reservation = new Reservation();
				reservation.setClient_id(resultset.getInt("client_id"));
				reservation.setVehicle_id(vehicleId);
				reservation.setDebut(resultset.getDate("debut"));
				reservation.setFin(resultset.getDate("fin"));
				reservation.setId(resultset.getInt("id"));
				resavid.add(reservation);

			}

			 
		 } catch (SQLException e) {
			 throw new DaoException();
		 }
		 return resavid;
	}

	/**
	 * retourne l'ensemble des réservation
	 * @return une liste contenant l'ensemble des réservations de la base de données
	 * @throws DaoException en cas d'erreur lors de la connection à la base de données ou dans la requête
	 */
	public List<Reservation> findAll() throws DaoException {
		List<Reservation> result = new ArrayList<>();
		try(
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_QUERY);			
		) 
		
		{
			
			ResultSet resultset = ps.executeQuery();
			

			while (resultset.next()){
				Reservation reservation = new Reservation();
				reservation.setClient_id(resultset.getInt("client_id"));
				reservation.setVehicle_id(resultset.getInt("vehicle_id"));
				reservation.setDebut(resultset.getDate("debut"));
				reservation.setFin(resultset.getDate("fin"));
				reservation.setId(resultset.getInt("id"));
				result.add(reservation);
			}

			
			
		} catch (SQLException e) {
			throw new DaoException();
		}
		return result;
		 
	}
	/**
	* Returns the reservation number associated with a customer
	* @param id the customer id
	* @return a list corresponding to the ids of the vehicles associated with the client (without duplicates)
	* @throws DaoException if there is an error connecting to the database or in the query
	*/ 
	
	public List<Integer> vehicleidByClient(int id) throws DaoException{
		List<Integer> vehicidlist = new ArrayList<>();

		try (
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(NB_VEHICLEID_BY_CLIENT);
		)
		{
			ps.setInt(1, id);
			ResultSet resultset = ps.executeQuery();
			while(resultset.next()){
				vehicidlist.add(resultset.getInt("vehicle_id"));
			}
			return vehicidlist;
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	/**
	* Returns the reservation number associated with a customer
	* @param id the customer id
	* @return a list corresponding to the ids of the customers associated with the vehicle (without duplicates)
	* @throws DaoException if there is an error connecting to the database or in the query
	*/ 
	public List<Integer> NbClientIdByVehicle(int id) throws DaoException{
		List<Integer> clientid = new ArrayList<>();
		try (
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(NB_CLIENTID_BY_VEHICLE);
		){
	
			ps.setInt(1, id);
			ResultSet resultset = ps.executeQuery();
			while(resultset.next()){
				clientid.add(resultset.getInt("client_id"));
			}
			return clientid;
		} catch (SQLException e) {
			throw new DaoException();
		}
	}
	
	/**
	* Returns the reservation number associated with a customer
	* @param id the booking id
	* @return an Optional containing a reservation if there is a reservation at the corresponding id, and null if there is no reservation
	* @throws DaoException if there is an error connecting to the database or in the query
	*/ 
	public Optional<Reservation> findById(int id) throws DaoException{
		
		try(
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_RESERVATION_BY_ID);
		) 
		{
			ps.setInt(1, id);
			ResultSet resultSet = ps.executeQuery();
			
			if (resultSet.next()){

				Reservation resa = new Reservation();
				resa.setId(id);
				resa.setClient_id(resultSet.getInt("client_id"));
				resa.setVehicle_id(resultSet.getInt("vehicle_id"));
				resa.setDebut(resultSet.getDate("debut"));
				resa.setFin(resultSet.getDate("fin"));

				return Optional.of(resa);
			}else {
				return Optional.empty();
			}
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	public List<Reservation> listVehicleDateBefore (Reservation reservation) throws DaoException{
		List<Reservation> reservations = new ArrayList<>();
		try(
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(DATEBEFORE_VEHICLEID_QUERY);
		)  
		{
			ps.setInt(1, reservation.getVehicle_id());
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()){
				Reservation reservationtemp = new Reservation();
				reservationtemp.setDebut(resultSet.getDate("debut"));
				reservationtemp.setFin(resultSet.getDate("fin"));
				reservations.add(reservationtemp);
			}
			System.out.println();
			return reservations;
		} catch (SQLException e) {
			throw new DaoException();
		}
	}
	public List<Reservation> listVehicleDateAfter (Reservation reservation) throws DaoException{
		List<Reservation> reservations = new ArrayList<>();
		try(
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(DATEAFTER_VEHICLEID_QUERY);
		)  
		{
			ps.setInt(1, reservation.getVehicle_id());
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()){
				Reservation reservationtemp = new Reservation();
				reservationtemp.setDebut(resultSet.getDate("debut"));
				reservationtemp.setFin(resultSet.getDate("fin"));
				reservations.add(reservationtemp);
			}	
			return reservations;
		} catch (SQLException e) {
			throw new DaoException();
		}
	}
}
