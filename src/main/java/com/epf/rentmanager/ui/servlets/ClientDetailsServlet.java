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
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet ("/users/detail")
public class ClientDetailsServlet extends HttpServlet{
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
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/users/details.jsp");
        List<Reservation> resalist = new ArrayList<>();
        List<Vehicle> vehiclist = new ArrayList<>();
       
        try {
            int idclient = Integer.parseInt(request.getParameter("id"));

            if (clientService.findById(idclient).isPresent()){

                request.setAttribute("client", clientService.findById(idclient).get());
                request.setAttribute("nbressa", reservationService.findResaByClientId(idclient).size());
                request.setAttribute("nbvehi", reservationService.vehicleidByClient(idclient).size());

                for(Reservation resa : reservationService.findResaByClientId(idclient)){
                    Vehicle vehicle = vehicleService.findById(resa.getVehicle_id()).get();
                    resa.setModeleconstructeur(vehicle.getConstructeur() + " " + vehicle.getModele());
                    resalist.add(resa);
                }

                for(int idvehic : reservationService.vehicleidByClient(idclient)){
                    vehiclist.add(vehicleService.findById(idvehic).get());
                }

                request.setAttribute("reservations", resalist);
                request.setAttribute("vehicules", vehiclist);
                
            }
            
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
