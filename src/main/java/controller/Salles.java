package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import entities.Salle;
import service.SalleService;

/**
 * Servlet implementation class Salles
 */
@WebServlet("/Salles")
public class Salles extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SalleService ss = new SalleService();
	
	private void load(HttpServletResponse response) throws ServletException, IOException{
    	response.setContentType("application/json");
		Gson json = new Gson();
		List<Salle> salles = ss.findAll();
		response.getWriter().write(json.toJson(salles));
    }
	
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     	if(request.getParameter("op") != null) {
    		if(request.getParameter("op").equals("add")) {
    			if(!request.getParameter("code").equals("") && !request.getParameter("capacite").equals("")) {
    				String code = request.getParameter("code");
    				int capacite = Integer.parseInt(request.getParameter("capacite"));
    				String type = request.getParameter("type");
    				if(!ss.create(new Salle(code, capacite, type)))
    					response.sendError(405);
    			}else
    				response.sendError(406);
	    	}else if(request.getParameter("op").equals("update")) {
	    		if(!request.getParameter("code").equals("") && !request.getParameter("capacite").equals("")) {
	    			Salle salle = ss.findById(Integer.parseInt(request.getParameter("id")));
	    			String code = request.getParameter("code");
    				int capacite = Integer.parseInt(request.getParameter("capacite"));
    				String type = request.getParameter("type");
		    		salle.setCode(code);
					salle.setType(type);
					salle.setCapacite(capacite);
					ss.update(salle);
    			}else
    				response.sendError(406);
	    	}else if(request.getParameter("op").equals("delete")) {
				if(!ss.delete(ss.findById(Integer.parseInt(request.getParameter("id"))))) 
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
