package com.epf.rentmanager.ui;

import java.sql.Date;
import java.util.List;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.utils.IOUtils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ReservationInterface {

    public static void main (String[] arg) throws ServiceException{
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        ReservationService reservationService = context.getBean(ReservationService.class);

        boolean output = true ;
        while(output){
            IOUtils.print("Veuillez choisir votre action : \n");
            IOUtils.print("1 : Afficher toutes les réservations \n");
            IOUtils.print("2 : Afficher toutes les réservations en relation avec un véhicule \n");
            IOUtils.print("3 : Afficher toutes les réservations en relation avec un client \n");
            IOUtils.print("4 : Créer une réservation \n");
            IOUtils.print("5 : Supprimer une réservation \n");
            IOUtils.print("6 : Quitter \n");
            switch(IOUtils.readInt("")){
                case 1:
                    List<Reservation> reservations = reservationService.findAll();
                    for (Reservation reservationt : reservations){
                        System.out.println(reservationt);
                    }

                break;
                case 2:
                    List<Reservation> reservations2 = reservationService.findResaByVehicleId(IOUtils.readInt("Veuillez selectionner l'id du véhicule"));
                    for (Reservation reservationt : reservations2){
                        System.out.println(reservationt);
                    }
                break;
                case 3:
                    List<Reservation> reservations3 = reservationService.findResaByClientId(IOUtils.readInt("Veuillez sélectionner l'id du client"));
                    for (Reservation reservationt : reservations3){
                        System.out.println(reservationt);
                    }

                break;
                case 4:
                    Reservation newreservation = new Reservation();
                    newreservation.setClient_id(IOUtils.readInt("Veuillez renseigner l'id du client"));
                    newreservation.setVehicle_id(IOUtils.readInt("Veuillez renseinger l'id du véhicule"));
                    newreservation.setDebut(Date.valueOf(IOUtils.readDate("Veuillez renseigner la date de début de réservation", true)));
                    newreservation.setFin(Date.valueOf(IOUtils.readDate("Veuillez renseigner la date de fin de réservation", true)));
                    reservationService.create(newreservation);

                break;
                case 5:
                    Reservation delreservation = new Reservation();
                    delreservation.setId(IOUtils.readInt("Veuillez renseigner l'id de la réservation à supprimer"));
                    reservationService.delete(delreservation);
                break;
                case 6:
                    IOUtils.print("Vous quitter l'interface des réservations");
                    output = false;
                break;
                default:

                break;
            }
        }
    }   
}
