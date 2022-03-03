package com.epf.rentmanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VehicleServiceTest {
    
    @InjectMocks
    private VehicleService vehicleService;

    @Mock
    private VehicleDao vehicleDao;

    @Test
    public void should_return_true_when_modele_is_present() throws ServiceException{
        Vehicle vehicle = new Vehicle(1, "Toyota", "GTI12", 4);
        assertTrue(vehicleService.modelePresence(vehicle));
    }

    @Test
    public void should_return_true_when_constructeur_is_present() throws ServiceException{
        Vehicle vehicle = new Vehicle(1, "Toyota", "GTI12", 4);
        assertTrue(vehicleService.constructeurPresence(vehicle));
    }

    @Test
    public void should_return_true_when_nbpalce_is_between_9and2() throws ServiceException{
        Vehicle vehicle = new Vehicle(1, "Toyota", "GTI12", 4);
        assertTrue(vehicleService.nbPlace(vehicle));
    }

    @Test(expected=ServiceException.class) //Ici on vérifie que l'appel de la fonction findall quand elle ne marche pas renvoie une DaoException
public void findAll_should_fail_when_dao_throws_exception() throws DaoException, ServiceException {

// When
when(this.vehicleDao.findAll()).thenThrow(new DaoException());

// Then
vehicleService.findAll();
}

@Test // Ici on vérifie que la findall fonctionne, on crée deux liste, celle de référence et celle envoyée à la fonction
public void findAll_fonctionne() throws DaoException, ServiceException {
List<Vehicle> listevehicle = new ArrayList<>();
List<Vehicle> listevehicleexpected = new ArrayList<>();
Vehicle vehicle1 = new Vehicle(1, "constructeur", "modele", 4);
Vehicle vehicle2 = new Vehicle(1, "constructeur", "modele", 5);
listevehicle.add(vehicle1);
listevehicle.add(vehicle2);
listevehicleexpected.add(vehicle1);
listevehicleexpected.add(vehicle2);

// When
when(this.vehicleDao.findAll()).thenReturn(listevehicle); // on définit le retour de la fonction findall

// Then
assertEquals(listevehicleexpected, vehicleService.findAll()); //on vérifie que les deux listes sont égales
}
    
}
