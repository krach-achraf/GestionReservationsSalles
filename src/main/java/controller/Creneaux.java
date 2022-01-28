package controller;

import java.io.IOException;
import java.sql.Time;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import entities.Creneau;
import service.CreneauService;

/**
 * Servlet implementation class Creneaux
 */
@WebServlet("/Creneaux")
public class Creneaux extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CreneauService cs = new CreneauService();
    
    private void load(HttpServletResponse response) throws IOException {
		List<Creneau> creneaux = cs.findAll();
		response.setContentType("application/json");		
		Gson json = new Gson();
		response.getWriter().write(json.toJson(creneaux));
	}

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	if(request.getParameter("op") != null) {
    		if(request.getParameter("op").equals("add")) {
    			if(!request.getParameter("heureDebut").equals("") && !request.getParameter("heureFin").equals("")) {
    				Time heureDebut = Time.valueOf(request.getParameter("heureDebut") + ":00");
    				Time heureFin = Time.valueOf(request.getParameter("heureFin") + ":00");
    				if(!cs.create(new Creneau(heureDebut, heureFin)))
    					response.sendError(405); 
    			}else
    				response.sendError(406); 
    		}else if(request.getParameter("op").equals("update")) {
    			if(!request.getParameter("heureDebut").equals("") && !request.getParameter("heureFin").equals("")) {
    				Time heureDebut = Time.valueOf(request.getParameter("heureDebut") + ":00");
    				Time heureFin = Time.valueOf(request.getParameter("heureFin") + ":00");
    				Creneau creneau = cs.findById(Integer.parseInt(request.getParameter("id")));
        			creneau.setHeureDebut(heureDebut);
        			creneau.setHeureFin(heureFin);
        			cs.update(creneau);
    			}else
    				response.sendError(406); 
    		}else if(request.getParameter("op").equals("delete")) {
    			int id = Integer.parseInt(request.getParameter("id"));
    			if(!cs.delete(cs.findById(id))) 
    				response.sendError(408);	
    		}
    	}
    	load(response);
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
