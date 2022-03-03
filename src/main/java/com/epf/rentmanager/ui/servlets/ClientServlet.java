package com.epf.rentmanager.ui.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.service.ClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;


@WebServlet ("/users")
public class ClientServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    @Autowired
    private ClientService clientService;
  
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        }

        
    protected void doGet (final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException{
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/users/list.jsp");
        try {
            request.setAttribute("clients", clientService.findAll());
        } catch (final Exception e) {
            request.setAttribute("ErrorMessage", e.getMessage());
            System.out.println("Une erreur est survenue : "+ e.getMessage());
        }
        dispatcher.forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        doGet(request, response);
    }
    
}
