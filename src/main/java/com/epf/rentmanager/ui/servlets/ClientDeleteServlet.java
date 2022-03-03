package com.epf.rentmanager.ui.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet("/users/delete")
public class ClientDeleteServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    @Autowired
    ClientService clientService;
    @Autowired
    ReservationService reservationService;

    @Override
    public void init() throws ServletException {
    super.init();
    SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException,IOException {
        try {
            Client client = new Client();
            client.setId(Integer.parseInt(request.getParameter("id")));
            List<Reservation> reservations = reservationService.findResaByClientId(Integer.parseInt(request.getParameter("id")));
            for (Reservation reservation : reservations){
                reservationService.delete(reservation);
            }
            clientService.delete(client); 
            response.sendRedirect("http://localhost:8080/rentmanager/users");
        } catch(final Exception e) {
            request.setAttribute("ErrorMessage", e.getMessage());
            e.printStackTrace();
            System.out.println("Une erreur est survenue : " + e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        doGet(request, response);
    }
}