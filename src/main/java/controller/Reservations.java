package controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import entities.Client;
import entities.Reservation;
import entities.SalleCreneau;
import service.ClientService;
import service.ReservationService;
import service.SalleCreneauService;

/**
 * Servlet implementation class Reservations
 */
@WebServlet("/Reservations")
public class Reservations extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ReservationService rs = new ReservationService();
	private SalleCreneauService scs = new SalleCreneauService();
	private ClientService cs = new ClientService();

	private void load(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		int id = Integer.parseInt(session.getAttribute("idClient").toString());
		List<Reservation>  reservations = rs.findByIdClient(id);
		response.setContentType("application/json");		
		Gson json = new Gson();
		response.getWriter().write(json.toJson(reservations));
	}
	
	private void loadDataV(HttpServletResponse response) throws IOException {
		List<Reservation> reservations = rs.findReservationsValide();
		response.setContentType("application/json");		
		Gson json = new Gson();
		response.getWriter().write(json.toJson(reservations));
	}
	
	private void loadDataEV(HttpServletResponse response) throws IOException {
		List<Reservation> reservations = rs.findReservationsEnAttend();
		response.setContentType("application/json");		
		Gson json = new Gson();
		response.getWriter().write(json.toJson(reservations));
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
	 	if(request.getParameter("op") != null) {
	 		switch(request.getParameter("op")) {
	 			case "load" : 
	 				load(request, response); 
	 				break;
	 			case "loadDisponibilite" : 
	 				List<SalleCreneau> salleCreneaux = scs.findDispos(new Date(request.getParameter("date").replace("-", "/")));
	    			response.setContentType("application/json");		
	    			Gson json = new Gson();
	    			response.getWriter().write(json.toJson(salleCreneaux));
	 				break;
	 			case "delete" : 
	 				Reservation reservation = rs.findById(Integer.parseInt(request.getParameter("id")));
					if(reservation.getEtatReservation().equals("valide"))
						response.sendError(400);
					else {
						rs.delete(reservation);
						load(request, response);
					}		
	 				break;
	 			case "loadResValid" : 
	 				loadDataV(response); 
	 				break;
	 			case "loadResEnAtt" : 
	 				loadDataEV(response); 
	 				break;
	 			case "invalide" : 
					rs.delete(rs.findById(Integer.parseInt(request.getParameter("id"))));
					response.sendRedirect("validation-reservation.jsp");
	 				break;
	 			case "valide" : 
	 				rs.valider(Integer.parseInt(request.getParameter("id")));
					response.sendRedirect("validation-reservation.jsp");
	 				break;
	 		}
    	}else {
    		HttpSession session = request.getSession();
    		Client client = cs.findById(Integer.parseInt(session.getAttribute("idClient").toString()));
    		SalleCreneau salleCreneau = scs.findById(Integer.parseInt(request.getParameter("id")));
    		Date date = new Date(request.getParameter("date").replace("-", "/"));
    		rs.create(new Reservation(salleCreneau, client, date, "en attend"));
    		load(request, response);
    	}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}
}
