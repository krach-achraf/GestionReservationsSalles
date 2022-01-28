package controller;

import java.io.IOException;
import java.util.Date;
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
 * Servlet implementation class Disponibilites
 */
@WebServlet("/Disponibilites")
public class Disponibilites extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SalleService ss = new SalleService();
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");	
		Gson json = new Gson();
		List<Salle> salles = ss.findSallesDispo(new Date(request.getParameter("date").replace("-", "/")));
		response.getWriter().write(json.toJson(salles));
	}

}
