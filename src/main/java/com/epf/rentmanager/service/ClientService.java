package com.epf.rentmanager.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.epf.rentmanager.dao.ClientDao;

@Service
public class ClientService {

	private ClientDao clientDao;
	@Autowired
	public ClientService(ClientDao clientDao){
		this.clientDao = clientDao;
	}

	public int create(Client client) throws ServiceException {
		try {
			tailleNom(client);
			taillePrenom(client);
			emailNonExistant(client);
			testAge(client);
			return clientDao.create(client);
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la création de l'utilisateur"+ e.getMessage());
		} catch (ServiceException e){
			throw new ServiceException(e.getMessage());
		}
		
	}

	public Optional<Client> findById(int id) throws ServiceException {
		
		try {
			
			if (clientDao.findById(id).isPresent()){
				
				return clientDao.findById(id);
			}else {
				return Optional.empty();
			}

		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la récupération de l'utilisateur"+ e.getMessage());
		}
		
	}

	public List<Client> findAll() throws ServiceException {
		
		try {

			return clientDao.findAll();

		}catch (DaoException e) {
			throw new ServiceException();
		}
	}
	
	public int delete(Client client) throws ServiceException {
		try {

			return clientDao.delete(client);
			
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}

	public int changeinfo(Client client) throws ServiceException {
		try {
			return clientDao.changeinfo(client);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public boolean testAge(Client client) throws ServiceException {
		if (Period.between(client.getNaissance().toLocalDate(), LocalDate.now()).getYears() >= 18){
			return true;
		}
		throw new ServiceException("L'age doit être supérieur à 18 ans");
	}

	public boolean emailNonExistant(Client client) throws ServiceException {
		List<Client> clients = findAll();
		for (Client client1 : clients){
			if (client.getEmail().equals(client1.getEmail())){
				throw new ServiceException("L'email que vous avez choisi est déjà existant");
			}
		}
		return true;
	}
	
	public boolean tailleNom (Client client) throws ServiceException {
		if (client.getNom().length() < 3){
			throw new ServiceException("Votre nom doit contenir 3 caractères ou plus");
		}
		return true;
	}

	public boolean taillePrenom (Client client) throws ServiceException {
		if (client.getPrenom().length() < 3){
			throw new ServiceException("Votre nom doit contenir 3 caractères ou plus");
		}
		return true;
	}
}
