package com.epf.rentmanager.ui.servlets;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;


@WebServlet ("/rents/create")
public class ReservationCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private ClientService clientService;
  
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        }
    
    protected void doGet (final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException{
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/rents/create.jsp");
        try {
        	request.setAttribute("clients", clientService.findAll());  
            request.setAttribute("vehicles", vehicleService.findAll());
               
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        dispatcher.forward(request, response);
    }
    
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try {
            Reservation resa = new Reservation();           
            resa.setClient_id(Integer.parseInt(request.getParameter("clientid")));
            resa.setVehicle_id(Integer.parseInt(request.getParameter("vehicleid")));
            resa.setDebut(Date.valueOf(request.getParameter("debut")));
            resa.setFin(Date.valueOf(request.getParameter("fin")));
            reservationService.create(resa);
            response.sendRedirect("http://localhost:8080/rentmanager/rents");
        } catch (final Exception e) {
            e.printStackTrace();
            System.out.println("Une erreur est survenue : "+ e.getMessage());
            request.setAttribute("ErrorMessage", e.getMessage());
            doGet(request, response);
        }
    }

    
}
