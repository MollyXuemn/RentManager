package com.epf.rentmanager.ui.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;


@WebServlet ("/cars/create")
public class VehicleCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private VehicleService vehicleService;
  
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        }
    

    protected void doGet (final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException{
    	// allows you to retrieve an html file (or web page layout) 
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp");
        dispatcher.forward(request, response);
    }
    
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try {
            Vehicle vehicle = new Vehicle();
            vehicle.setNb_places(Integer.parseInt(request.getParameter("nb_places")));
            vehicle.setModele(request.getParameter("modele"));
            vehicle.setConstructeur(request.getParameter("constructeur"));
            vehicleService.create(vehicle);
            response.sendRedirect("http://localhost:8080/rentmanager/cars");
        } catch (final Exception e) {
            System.out.println("Une erreur est survenue : "+ e.getMessage());
            request.setAttribute("ErrorMessage", e.getMessage());
        }
    }

}
