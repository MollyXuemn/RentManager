package com.epf.rentmanager.ui;

import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.utils.IOUtils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class VehiculeInterface {

    public static void main(String[] args) throws ServiceException {

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        VehicleService vehicleService = context.getBean(VehicleService.class);

        Vehicle vehicule = new Vehicle();
        boolean output = true;
        while (output) {
            IOUtils.print("\nVeuillez choisir votre action :\n");
            IOUtils.print("1 : Lister toutes les voitures\n");
            IOUtils.print("2 : Trouver une voiture grâce à son id\n");
            IOUtils.print("3 : Créer une nouvelle voiture\n");
            IOUtils.print("4 : Supprimer une voiture à partir de son id\n");
            IOUtils.print("5 : Compter le nombre de véhicule présent dans la base donnée\n");
            IOUtils.print("6 : Changer les informations sur un véhicule\n");
            IOUtils.print("7 : Quitter\n");
            int src = IOUtils.readInt("");
            switch (src) {
                case 1:

                List<Vehicle> vehicules = vehicleService.findAll();
                for (Vehicle vehiculet : vehicules){
                     System.out.println(vehiculet);
                }
                    
                    break;
                case 2:
                       
                Optional<Vehicle> vehicule1 = vehicleService.findById(IOUtils.readInt("Veuillez saisir une id"));
                if (vehicule1.empty() != null) {
                    IOUtils.print("il n'y a pas de véhicule à l'id correspondant");
                }
                System.out.println(vehicule1.get());
                    break;

                case 3:
                int nb_place;
                do{
                    nb_place = IOUtils.readInt("Veuillez choisir le nombre de place");
                }while ( nb_place <= 1);
                    vehicule.setConstructeur(IOUtils.readString("Veuillez saisir la marque", true));
                    vehicule.setModele(IOUtils.readString("Veuillez saisir le modèle", true));
                    vehicule.setNb_places(nb_place);
                    vehicleService.create(vehicule);
                    break;

                case 4:
                    vehicule.setId(IOUtils.readInt("Veuillez choisir l'id du véhicule à supprimer"));
                    vehicleService.delete(vehicule);
                    break;

                case 5:
                    int nb_vehicule = vehicleService.count();
                    System.out.println(nb_vehicule);
                    break;
                    
                case 6:
                    IOUtils.print("sortie de l'interface");
                    output = false;
                default:
                    break;
            }
    }


    
    }
}
