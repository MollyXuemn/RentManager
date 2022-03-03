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


@WebServlet ("/cars/update")
public class VehicleUpdateServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private VehicleService vehicleService;
  
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        }

    protected void doGet (final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException{
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/vehicles/update.jsp");
        try {
            request.setAttribute("vehicle", vehicleService.findById(Integer.parseInt(request.getParameter("id"))).get());    
        } catch (final Exception e) {
             System.out.println("Une erreur est survenue : "+ e.getMessage());
        } 
        dispatcher.forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        
        try {
            Vehicle vehicle = vehicleService.findById(Integer.parseInt(request.getParameter("id"))).get();
            vehicle.setNb_places(Integer.parseInt(request.getParameter("nb_places")));
            vehicle.setConstructeur(request.getParameter("constructeur"));
            vehicle.setModele(request.getParameter("modele"));
            vehicleService.changeinfo(vehicle);
        } catch (final Exception e) {
            request.setAttribute("ErrorMessage", e.getMessage());
            System.out.println("Une erreur est survenue : "+ e.getMessage());
        }
        response.sendRedirect("http://localhost:8080/rentmanager/cars");
    }
}
