package com.epf.rentmanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationDao reservationDao;

    @Test
    public void should_return_true_when_vehicle_is_not_used_over_7_day() throws ServiceException{
        Reservation reservation = new Reservation();
        Date debut = Date.valueOf("2010-10-01");
        Date fin = Date.valueOf("2010-10-06");
        reservation.setDebut(debut);
        reservation.setFin(fin);
        assertTrue(reservationService.reservationOver7day(reservation));
    }

    @Test
    public void should_return_6() throws ServiceException{
        Reservation reservation = new Reservation();
        Date debut = Date.valueOf("2010-10-01");
        Date fin = Date.valueOf("2010-10-06");
        reservation.setDebut(debut);
        reservation.setFin(fin);
        assertEquals(6, reservationService.vehicleReservationAfterDate(reservation));
    }
    
    @Test
    public void should_return_5() throws ServiceException{
        Reservation reservation = new Reservation();
        Date debut = Date.valueOf("2010-10-01");
        Date fin = Date.valueOf("2010-10-05");
        reservation.setDebut(debut);
        reservation.setFin(fin);
        assertEquals(5, reservationService.vehicleReservationAfterDate(reservation));
    }

@Test(expected=ServiceException.class) //Ici on vérifie que l'appel de la fonction findall quand elle ne marche pas renvoie une DaoException
public void findAll_should_fail_when_dao_throws_exception() throws DaoException, ServiceException {

// When
when(this.reservationDao.findAll()).thenThrow(new DaoException());

// Then
reservationService.findAll();
}

@Test // Ici on vérifie que la findall fonctionne, on crée deux liste, celle de référence et celle envoyée à la fonction
public void findAll_fonctionne() throws DaoException, ServiceException {
List<Reservation> listeresa = new ArrayList<>();
List<Reservation> listeresaexpected = new ArrayList<>();
Date debut = Date.valueOf("2010-10-01");
Date fin = Date.valueOf("2010-10-05");
Reservation resa1 = new Reservation(1, 1, 1, debut, fin, "modeleconstructeur", "nomprenom");
Reservation resa2 = new Reservation(1, 1, 1, debut, fin, "modeleconstructeur", "nomprenom");
listeresa.add(resa1);
listeresa.add(resa2);
listeresaexpected.add(resa1);
listeresaexpected.add(resa2);

// When
when(this.reservationDao.findAll()).thenReturn(listeresa); // on définit le retour de la fonction findall

// Then
assertEquals(listeresaexpected, reservationService.findAll()); //on vérifie que les deux listes sont égales
}
}
