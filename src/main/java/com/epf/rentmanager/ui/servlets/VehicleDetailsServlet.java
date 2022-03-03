package com.epf.rentmanager.ui.servlets;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet ("/cars/detail")
public class VehicleDetailsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    
    @Autowired
    private ClientService clientService;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private ReservationService reservationService;
  
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        }


    protected void doGet (final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException{
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/vehicles/detail.jsp");
        List<Reservation> resalist = new ArrayList<>();
        List<Client> clientlist = new ArrayList<>();

        try {
            int idvehicle = Integer.parseInt(request.getParameter("id"));
            request.setAttribute("vehicle", vehicleService.findById(idvehicle).get());
            request.setAttribute("nbresa", reservationService.findResaByVehicleId(idvehicle).size());
            request.setAttribute("nbclient", reservationService.NbClientIdByVehicle(idvehicle).size());

            for(Reservation resa : reservationService.findResaByVehicleId(idvehicle)){
                Client client = clientService.findById(resa.getClient_id()).get();
                resa.setNomprenom(client.getNom() +" "+ client.getPrenom());
                resalist.add(resa);
            }

            for(int idclient : reservationService.NbClientIdByVehicle(idvehicle)){
                clientlist.add(clientService.findById(idclient).get());
            }
            request.setAttribute("clients", clientlist);
            request.setAttribute("reservations", resalist);

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
