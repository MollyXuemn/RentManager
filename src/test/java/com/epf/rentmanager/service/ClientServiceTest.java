package com.epf.rentmanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientDao clientDao;
    
    @Test
    public void testNom_should_return_true_when_name_is_over_3_letter() throws ServiceException{
        Date date = Date.valueOf("1999-10-12");
        Client clientTest = new Client(1,"John", "Dohd", "john.dohd@gmail.com", date);
        assertTrue(clientService.tailleNom(clientTest));
    }

    @Test
    public void testAge_should_return_true_when_age_is_over_18() throws ServiceException{
        Date date = Date.valueOf("1999-10-12");
        Client clientTest = new Client(1,"John", "Dohd", "john.dohd@gmail.com", date);
        assertTrue(clientService.testAge(clientTest));
    }
    

    @Test
    public void testPrenom_should_return_true_when_name_is_over_3_letter() throws ServiceException{
        Date date = Date.valueOf("1999-10-12");
        Client clientTest = new Client(1,"John", "Dohd", "john.dohd@gmail.com", date);
        assertTrue(clientService.taillePrenom(clientTest));
    }

    @Test
    public void testEmail_should_return_true_when_mail_is_not_used() throws ServiceException{
        Date date = Date.valueOf("1999-10-12");
        Client clientTest = new Client(1,"John", "Dohd", "john.dohd@gmail.com", date);
        assertTrue(clientService.emailNonExistant(clientTest));
    }

    @Test(expected = ServiceException.class)
    public void testAge_should_throws_serviceException_when_age_is_over_18() throws ServiceException{
        Date date = Date.valueOf("2010-10-12");
        Client clientTest = new Client(1,"Jo", "Dohn", "john.dohd@gmail.com", date);
        clientService.testAge(clientTest);
    }

    @Test(expected = ServiceException.class)
    public void testNom_should_throws_serviceException_when_name_is_under_3_letter() throws ServiceException{
        Date date = Date.valueOf("1999-10-12");
        Client clientTest = new Client(1,"Jo", "Dohn", "john.dohd@gmail.com", date);
        clientService.tailleNom(clientTest);
    }

    @Test(expected = ServiceException.class)
    public void testPrenom_should_throws_serviceException_when_name_is_under_3_letter() throws ServiceException{
        Date date = Date.valueOf("1999-10-12");
        Client clientTest = new Client(1,"John", "Do", "john.dohd@gmail.com", date);
        clientService.taillePrenom(clientTest);
    }


@Test(expected=ServiceException.class) //Ici on vérifie que l'appel de la fonction findall quand elle ne marche pas renvoie une DaoException
public void findAll_should_fail_when_dao_throws_exception() throws DaoException, ServiceException {

// When
when(this.clientDao.findAll()).thenThrow(new DaoException());

// Then
clientService.findAll();
}

@Test // Ici on vérifie que la findall fonctionne, on crée deux liste, celle de référence et celle envoyée à la fonction
public void findAll_fonctionne() throws DaoException, ServiceException {
List<Client> listeclient = new ArrayList<>();
List<Client> listeclientexpected = new ArrayList<>();
Date date = Date.valueOf("2000-06-19");
Client client1 = new Client(1,"nom","prenom", "email", date);
Client client2 = new Client(2,"nom","prenom", "email", date);
listeclient.add(client1);
listeclient.add(client2);
listeclientexpected.add(client1);
listeclientexpected.add(client2);

// When
when(this.clientDao.findAll()).thenReturn(listeclient); // on définit le retour de la fonction findall

// Then
assertEquals(listeclientexpected, clientService.findAll()); //on vérifie que les deux listes sont égales
}
}
