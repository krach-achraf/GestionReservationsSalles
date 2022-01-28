package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import entities.SalleCreneau;
import service.CreneauService;
import service.SalleCreneauService;
import service.SalleService;

/**
 * Servlet implementation class Planning
 */
@WebServlet("/Planning")
public class Planning extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CreneauService cs = new CreneauService();
	private SalleService ss = new SalleService();
	private SalleCreneauService scs = new SalleCreneauService();
      
    
   private void load(HttpServletResponse response) throws IOException {
		List<SalleCreneau> salleCreneaux = scs.findAll();
		response.setContentType("application/json");		
		Gson json = new Gson();
		response.getWriter().write(json.toJson(salleCreneaux));
	}
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	if(request.getParameter("op") != null) {
    		if(request.getParameter("op").equals("add")) {
		    	if(!scs.create(new SalleCreneau(ss.findById(Integer.parseInt(request.getParameter("idSalle"))), cs.findById(Integer.parseInt(request.getParameter("idCreneau"))))))
		    		response.sendError(405);
	    	}else if(request.getParameter("op").equals("update")) {
	    		SalleCreneau salleCreneau = scs.findById(Integer.parseInt(request.getParameter("id")));
	    		salleCreneau.setSalle(ss.findById(Integer.parseInt(request.getParameter("idSalle"))));
	    		salleCreneau.setCreneau(cs.findById(Integer.parseInt(request.getParameter("idCreneau"))));
	    		scs.update(salleCreneau);
	    	}else if(request.getParameter("op").equals("delete")) {
				if(!scs.delete(scs.findById(Integer.parseInt(request.getParameter("id"))))) 
					response.sendError(406);	
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
