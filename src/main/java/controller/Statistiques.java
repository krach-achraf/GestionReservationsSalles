package controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import service.ReservationService;

/**
 * Servlet implementation class Statistiques
 */
@WebServlet("/Statistiques")
public class Statistiques extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ReservationService rs = new ReservationService();

	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (request.getParameter("op") != null) {
			if (request.getParameter("op").equals("load1")) {
				response.setContentType("application/json");
				Map<String, Integer> nombreReservations = rs.nombreReservationsParSalle();
				Gson json = new Gson();
				response.getWriter().write(json.toJson(nombreReservations));
			}else if (request.getParameter("op").equals("load2")) {
				response.setContentType("application/json");
				Map<Integer, Integer> nombreReservations = rs.nombreReservationsParMois();
				Gson json = new Gson();
				response.getWriter().write(json.toJson(nombreReservations));
			}
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
