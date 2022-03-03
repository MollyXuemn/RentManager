package com.epf.rentmanager.ui.servlets;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;


@WebServlet ("/users/update")
public class ClientUpdateServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private ClientService clientService;
  
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        }

    protected void doGet (final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException{
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/users/update.jsp");
        try {
            request.setAttribute("client", clientService.findById(Integer.parseInt(request.getParameter("id"))).get());    
        } catch (final Exception e) {
             System.out.println("Une erreur est survenue : "+ e.getMessage());
        } 
        dispatcher.forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        
        try {
            Client client = clientService.findById(Integer.parseInt(request.getParameter("id"))).get();
            client.setNom(request.getParameter("nom"));
            client.setPrenom(request.getParameter("prenom"));
            client.setEmail(request.getParameter("email"));
            client.setNaissance(Date.valueOf(request.getParameter("naissance")));
            clientService.changeinfo(client);
        } catch (final Exception e) {
            request.setAttribute("ErrorMessage", e.getMessage());
            System.out.println("Une erreur est survenue : "+ e.getMessage());
        }
        response.sendRedirect("http://localhost:8080/rentmanager/users");
    }
    
}
