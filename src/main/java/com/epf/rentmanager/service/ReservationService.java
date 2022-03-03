package com.epf.rentmanager.service;

import java.sql.Date;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private ReservationDao reservationdao;
    
    @Autowired
    public ReservationService (ReservationDao reservationdao){
        this.reservationdao = reservationdao;
    }

    public long udpate(Reservation reservation) throws ServiceException{
        try {
            int tempsResa = Period.between(reservation.getDebut().toLocalDate(), reservation.getFin().toLocalDate()).getDays();
            vehicleDispo(reservation);
            reservationOver7day(reservation);
            if ((vehicleReservationAfterDate(reservation) + vehicleReservationBeforeDate(reservation) - tempsResa) > 30){
                throw new ServiceException("Le véhicule est déjà réservé et le nombre de jours de réservation d'affilée de ce véhicule > 30 jours");
            }
            return reservationdao.update(reservation);
        } catch (DaoException e) {
            throw new ServiceException("il y a eu un problème dans la dao "+ e.getMessage());
        }
    }

    public long create (Reservation reservation) throws ServiceException {
        try {
            int tempsResa = Period.between(reservation.getDebut().toLocalDate(), reservation.getFin().toLocalDate()).getDays();
            vehicleDispo(reservation);
            reservationOver7day(reservation);
            if ((vehicleReservationAfterDate(reservation) + vehicleReservationBeforeDate(reservation) - tempsResa) > 30){
                throw new ServiceException("Le véhicule est déjà réservé et le nombre de jours de réservation d'affilée de ce véhicule > 30 jours");
            }
            return reservationdao.create(reservation);
            
        } catch (DaoException e) {
            throw new ServiceException("il y à eu un problème dans la dao"+ e.getMessage());
        } catch (ServiceException e) {
			throw new ServiceException(e.getMessage());
		}
    }
    public long delete (Reservation reservation) throws ServiceException {
        try {
            if (reservation.getClient_id() != 0){
                return reservationdao.delete(reservation);
            }
            throw new ServiceException("il y a eu un problème dans la dao ");
        } catch (DaoException e) {
            throw new ServiceException("il y a eu un problème dans la dao "+ e.getMessage());
        }
    }

    public List<Reservation> findResaByVehicleId(int vehicleId) throws ServiceException {

        try {
            return reservationdao.findResaByVehicleId(vehicleId); 

        } catch (DaoException e) {
            throw new ServiceException("Il y a eu un problème dans la dao"+ e.getMessage());
        }
    }

    public List<Reservation> findResaByClientId(int clientId) throws ServiceException {

        try {
            return reservationdao.findResaByClientId(clientId);
        } catch (DaoException e) {
            throw new ServiceException("Il y a eu un problème dans la dao"+ e.getMessage());
        }
    }

    public List<Reservation> findAll() throws ServiceException {

        try {
            return reservationdao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Il y a eu un problème dans la dao"+ e.getMessage());   
        }
    }

    public List<Integer> vehicleidByClient(int id) throws ServiceException{
        try {
            return reservationdao.vehicleidByClient(id);
        } catch (DaoException e) {
            throw new ServiceException("Il y a eu un problème dans la dao "+ e.getMessage());
        }
    }
    
    public List<Integer> NbClientIdByVehicle(int id) throws ServiceException{
        try {
            return reservationdao.NbClientIdByVehicle(id);
        } catch (DaoException e) {
            throw new ServiceException("Il y a eu un problème dans la dao "+ e.getMessage());
        }
    }

    public Optional<Reservation> findById(int id) throws ServiceException{

        try {
            if (reservationdao.findById(id).isPresent()){
				
				return reservationdao.findById(id);
			}else {
				return Optional.empty();
			}
        } catch (DaoException e) {
            throw new ServiceException("Il y a eu un problème dans la dao "+ e.getMessage());
        }
    }

    public boolean vehicleDispo(Reservation reservation) throws ServiceException {
        List<Reservation> reservations = findResaByVehicleId(reservation.getVehicle_id());
        Date datefin = reservation.getFin();
        Date datedebut = reservation.getDebut();
        for(Reservation reservationbdd : reservations) {
            Date datedebutBDD = reservationbdd.getDebut();
            Date datefinBDD = reservationbdd.getFin();
            if ((datedebut.compareTo(datedebutBDD) >= 0) && (datedebut.compareTo(datefinBDD) <= 0)){
                throw new ServiceException("Le véhicule est déjà loué sur la période du "+ reservationbdd.getDebut() + " au " + reservationbdd.getFin());
            }
            if ((datefin.compareTo(datefinBDD) <= 0) && (datefin.compareTo(datedebutBDD) >= 0)){    
                throw new ServiceException("Le véhicule est déjà loué sur la période du "+ reservationbdd.getDebut() + " au " + reservationbdd.getFin());
            }
            if ((datedebutBDD.compareTo(datedebut) >= 0) && (datedebutBDD.compareTo(datefin) <= 0)){
                throw new ServiceException("Le véhicule est déjà loué sur la période du "+ reservationbdd.getDebut() + " au " + reservationbdd.getFin());
            }
        }
        return true;
    }

    public List<Reservation> listVehicleDateBefore (Reservation reservation) throws ServiceException{
        try {
            return reservationdao.listVehicleDateBefore(reservation);
        } catch (DaoException e) {
            throw new ServiceException();    
        }
    }

    public List<Reservation> listVehicleDateAfter (Reservation reservation) throws ServiceException{
        try {
            return reservationdao.listVehicleDateAfter(reservation);
        } catch (DaoException e) {
            throw new ServiceException();    
        }
    }

    public int vehicleReservationBeforeDate(Reservation reservationclient) throws ServiceException{
        List<Reservation> reservations = listVehicleDateBefore(reservationclient);
        Date dateresadebut = reservationclient.getDebut();
        Date dateresafin = reservationclient.getFin();
        int nombre_jours = Period.between(dateresadebut.toLocalDate(), dateresafin.toLocalDate()).getDays() + 1;
        for(Reservation reservation : reservations){
            if (Period.between(reservation.getFin().toLocalDate(), dateresadebut.toLocalDate()).getDays() == 1){
                nombre_jours += Period.between(reservation.getDebut().toLocalDate(), reservation.getFin().toLocalDate()).getDays() + 1;
                dateresadebut = reservation.getDebut();
                dateresafin = reservation.getFin();
                if (nombre_jours>=30){
                    throw new ServiceException("Le véhicule est utilisé juste avant vous et la durée de réservation cumulée est supérieur à 30 jours");
                }
            }
            if (Period.between(reservationclient.getDebut().toLocalDate(), reservation.getDebut().toLocalDate()).getDays() > 30){
                return nombre_jours;
            } 
        }
        return nombre_jours;
    }

    public int vehicleReservationAfterDate(Reservation reservationclient) throws ServiceException{
        List<Reservation> reservations = listVehicleDateAfter(reservationclient);
        Date dateresadebut = reservationclient.getDebut();
        Date dateresafin = reservationclient.getFin();
        int nombre_jours = Period.between(dateresadebut.toLocalDate(), dateresafin.toLocalDate()).getDays() + 1;
        for(Reservation reservation : reservations){
            if(Period.between(dateresafin.toLocalDate(), reservation.getDebut().toLocalDate()).getDays() == 1){
                nombre_jours += Period.between(reservation.getDebut().toLocalDate(), reservation.getFin().toLocalDate()).getDays() + 1;
                dateresadebut = reservation.getDebut();
                dateresafin = reservation.getFin();
            }
            if (nombre_jours >= 30){
                throw new ServiceException("Le véhicule est déjà utilisé juste après vous et la durée de réservation cumulée est supérieur à 30 jours");
            }
            if (Period.between(reservation.getDebut().toLocalDate(), reservationclient.getDebut().toLocalDate()).getDays() > 30){
                return nombre_jours;
            }
        }   
        return nombre_jours;
    }

    public boolean reservationOver7day(Reservation reservation) throws ServiceException{
        if (Period.between(reservation.getDebut().toLocalDate(), reservation.getFin().toLocalDate()).getDays() > 7 ){
            throw new ServiceException("Vous ne pouvez pas réserver le véhicule plus de 7 jours d'affilés");
        }
        return true;
    }
}



