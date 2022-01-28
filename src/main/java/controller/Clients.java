package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import encrypt.Encrypt;
import entities.Client;
import service.ClientService;

/**
 * Servlet implementation class Clients
 */
@WebServlet("/Clients")
public class Clients extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClientService cs = new ClientService();

	private void load(HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		Gson json = new Gson();
		List<Client> clients = cs.findAll();
		response.getWriter().write(json.toJson(clients));
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("op") != null) {
			if(request.getParameter("op").equals("delete")) {
				Client client = cs.findById(Integer.parseInt(request.getParameter("id")));
				if (!cs.delete(client))
					response.sendError(408);
			}else if(request.getParameter("op").equals("add")) {
				if(!request.getParameter("nom").equals("") && 
						!request.getParameter("prenom").equals("") && 
						!request.getParameter("login").equals("") &&
						!request.getParameter("email").equals("") &&
						!request.getParameter("password").equals("")) {
					
					String nom = request.getParameter("nom");
					String prenom = request.getParameter("prenom");
					String login = request.getParameter("login");
					String email = request.getParameter("email");
					String password = Encrypt.encrypt(request.getParameter("password"));
					Client client = new Client(nom, prenom, login, email, password);		
					if (!cs.create(client))
						response.sendError(405);
				}else
					response.sendError(406);
			}
		}
		load(response);
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
