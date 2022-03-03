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
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.persistence.ConnectionManager;

import org.springframework.stereotype.Repository;

@Repository
public class ClientDao {
	private ClientDao() {}

	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	private static final String CHANGE_CLIENT_INFO = "UPDATE Client SET nom=?, prenom=?, email=?, naissance=? WHERE id = ?;";

	/**
	* Change a customer's information in the database
	* @param clientchange The new client information
	* @return the generated key for the PreparedStatement or <code>null</code> if there is no generated key
	* @throws DaoException if there is an error connecting to the database or in the query
	*/ 
	public int changeinfo(Client clientchange) throws DaoException{
		try(
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(CHANGE_CLIENT_INFO, Statement.RETURN_GENERATED_KEYS);
		) 
		{
			ps.setInt(5, clientchange.getId());
			ps.setString(1, clientchange.getNom());
			ps.setString(2, clientchange.getPrenom());
			ps.setString(3, clientchange.getEmail());
			ps.setDate(4, clientchange.getNaissance());

			ps.executeUpdate();
			ResultSet resultSet = ps.getGeneratedKeys();

			if (resultSet.next()) {
				return resultSet.getInt(1);
			} else {
				throw new DaoException();
			}

		}catch (SQLException e) {
			throw new DaoException();
		}
	}
	/**
	* returns all customers in the database
	* @return A list containing all the clients in the database
	* @throws DaoException if there is an error connecting to the database or in the query
	*/ 
	
	public List<Client> findAll() throws DaoException {

		List<Client> result = new ArrayList<>();

		try (			
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_CLIENTS_QUERY);
		)
		{
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()){
				Client client = new Client();
				client.setId(resultSet.getInt("id"));
				client.setNom(resultSet.getString("nom"));
				client.setEmail(resultSet.getString("email"));
				client.setPrenom(resultSet.getString("prenom"));
				client.setNaissance(resultSet.getDate("naissance"));
				result.add(client);
			}

		} catch (SQLException e) {
			throw new DaoException();
		}
		return result;
	}

	/**
	* returns a client from the database based on the id
	* @param id id of the client sought
	* @return an Optional containing a customer if there is a customer with the corresponding id, and null if there is no customer
	* @throws DaoException if there is an error connecting to the database or in the query
	*/ 
	
	public Optional<Client> findById(int id) throws DaoException {
		try (
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_CLIENT_QUERY);
		){
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()){

				Client client = new Client();
				client.setId(id);
				client.setNom(resultSet.getString("nom"));
				client.setPrenom(resultSet.getString("prenom"));
				client.setEmail(resultSet.getString("email"));
				client.setNaissance(resultSet.getDate("naissance"));

				return Optional.of(client);
			}else {
				return Optional.empty();
			}

		} catch (SQLException e) {
			throw new DaoException();
			
		}
	}

	/**
	* delete the client from the database
	* @param client the new client to add in the database
	* @return the generated key for the PreparedStatement or <code>null</code> if there is no generated key
	* @throws DaoException if there is an error connecting to the database or in the query
	*/ 

	public int create(Client client) throws DaoException {

		try (
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS);
		){
			
			preparedStatement.setString(1, client.getNom().toUpperCase());
			preparedStatement.setString(2, client.getPrenom());
			preparedStatement.setString(3, client.getEmail());
			preparedStatement.setDate(4, client.getNaissance());
			preparedStatement.executeUpdate();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();

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
	* delete the client from the database
	* @param client the client to delete in the database
	* @return the generated key for the PreparedStatement or <code>null</code> if there is no generated key
	* @throws DaoException if there is an error connecting to the database or in the query
	*/ 
	public int delete(Client client) throws DaoException {

		try (
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(DELETE_CLIENT_QUERY);
		){

			ps.setInt(1, client.getId());
			return ps.executeUpdate();
	
		} catch (SQLException e) {
			throw new DaoException();
		}

	}

}