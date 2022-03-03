package com.epf.rentmanager.ui;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.utils.IOUtils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ClientInterface {

    public static void main(String[] args) throws ServiceException {

        boolean output = true;
        Client clientcr = new Client();
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        ClientService clientService = context.getBean(ClientService.class);
        
        while (output) {
            IOUtils.print("Veuillez choisir votre action : \n"); 
            IOUtils.print("1 : Lister tous les clients \n");
            IOUtils.print("2 : Trouver un client grâce à son id \n");
            IOUtils.print("3 : Créer un nouveau client \n");
            IOUtils.print("4 : Supprimer un client à partir de son id \n");
            IOUtils.print("5 : Changer des informations \n");
            IOUtils.print("6 : Quitter \n");
            int src=IOUtils.readInt("");

            switch (src) {
                case 1:
                    List<Client> clients = clientService.findAll();
                    for (Client client : clients){
                        System.out.println(client);
                    }
                    break;

                case 2:
                        Optional<Client> client = clientService.findById(IOUtils.readInt("Veuillez saisir une id \n"));
                        if (client.empty() != null){
                            IOUtils.print("il n'y a pas de client correpondant à l'id \n");

                        }else System.out.println(client);
                        
                    break;

                case 3:
                    clientcr.setPrenom(IOUtils.readString("Veuillez saisir votre prénom \n ", true));
                    clientcr.setNom(IOUtils.readString("Veuillez saisir votre nom \n", true));
                    clientcr.setNaissance(Date.valueOf(IOUtils.readDate("Veuillez saisir votre date de naissance \n", true)));
                    String email = null;
                    do{
                       email = IOUtils.readString("Veuillez saisir votre email \n", true);
                       if (!email.contains("@")){
                           IOUtils.print("l'email n'est pas valide \n");
                       }
                    }while(!email.contains("@"));
                    clientcr.setEmail(email);
                    clientService.create(clientcr);
                    break;

                case 4:
                    Client clientdele;
                    do{
                        clientdele = clientService.findById(IOUtils.readInt("Veuillez rentrer l'id de l'utilisateur à supprimer \n")).get();
                    }while(clientdele == null);
                    clientService.delete(clientcr);
                    break;
                case 5:
                boolean output2 = true;
                Optional <Client> clientchange2 = clientService.findById(IOUtils.readInt("Veuillez sélectionner votre id"));
                Client clientchange;

                if (clientchange2.empty() != null){
                do {
                   clientchange2 = clientService.findById(IOUtils.readInt("L'id sélectionnée n'existe pas"));
                } while (clientchange2.empty() != null);
                clientchange = clientchange2.get();
            }else {
                clientchange = clientchange2.get();
            }

                while (output2){
                    System.out.println(clientchange);
                    IOUtils.print("\nQuelles informations souhaitez-vous changer");
                    IOUtils.print("1 : Nom");
                    IOUtils.print("2 : Prenom");
                    IOUtils.print("3 : Email");
                    IOUtils.print("4 : Naissance");
                    IOUtils.print("5 : Quitter");
        
                    switch (IOUtils.readInt("")) {
                        case 1:
                            clientchange.setNom(IOUtils.readString("Veuillez sélectionner votre nom", true).toUpperCase());
                            break;
                        case 2:
                            clientchange.setPrenom(IOUtils.readString("Veuillez selectionner votre prénom", true));
                            break;
                        case 3:
                            String email2 = clientchange.getEmail();
                            do{
                                email2 = IOUtils.readString("Veuillez saisir votre email \n", true);
                                if (!email2.contains("@")){
                                     IOUtils.print("l'email n'est pas valide \n");
                                }
                            }while(!email2.contains("@"));
                                clientchange.setEmail(email2);
                            break;
                        case 4:
                             clientchange.setNaissance(Date.valueOf(IOUtils.readDate("Veuillez selectionner votre date de naissance", true)));
                            break;
                        case 5:
                             output2 = false;
                            break;
                        default:
                            break;
                    }
                    clientService.changeinfo(clientchange);  
                }
                break;
                case 6:
                    IOUtils.print("sortie de l'interface \n");
                    output = false;
                default:
                    break;
            }
    }

    }


}