package controller;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import encrypt.Encrypt;
import entities.Client;
import service.ClientService;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClientService cs = new ClientService();
	
    private void sendMail(String recepient, int code) throws Exception {
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		String myAccount = "krachkr101@gmail.com";
		String password = "gmai@124";
		Session session = Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(myAccount, password);
			}
		});
		Message message = prepareMessage(session, myAccount, recepient, code);
		Transport.send(message);
	}

	private static Message prepareMessage(Session session, String myAccount, String recepient, int code) {
		try {
			Message message;
			message = new MimeMessage(session);
			message.setFrom(new InternetAddress(myAccount));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			message.setSubject("Récupération du mot de passe");
			message.setText("Le code de vérification est : " + code);
			return message;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
    	if(request.getParameter("login") != null && request.getParameter("password") != null) {
    		String login = request.getParameter("login");
    		String hashedPassword = Encrypt.encrypt(request.getParameter("password"));
    		HttpSession session = request.getSession();
    		if ((login.equals("admin"))) {
    			if(hashedPassword.equals(Encrypt.encrypt("admin"))) {
    				session.setAttribute("idAmin", 1);
					session.setAttribute("nom", "Krach");
					session.setAttribute("prenom", "Achraf");
					response.sendRedirect("statistiques.jsp");
    			}else {
    				request.setAttribute("mdpError", "error");
					request.getRequestDispatcher("login.jsp").forward(request, response);
    			}
			} else {
				Client client = cs.findByLogin(login);
				if (client != null) {
					if (hashedPassword.equals(client.getPassword())) {
						session.setAttribute("idClient", client.getId());
						session.setAttribute("nom", client.getNom());
						session.setAttribute("prenom", client.getPrenom());
						response.sendRedirect("reservations-client.jsp");
					} else {
						request.setAttribute("mdpError", "error");
						request.getRequestDispatcher("login.jsp").forward(request, response);
					}
				} else {
					request.setAttribute("loginError", "error");
					request.getRequestDispatcher("login.jsp").forward(request, response);
				}
			}
    	}else if (request.getParameter("email") != null) {
			String email = request.getParameter("email");
			Client client = cs.findByEmail(email);
			if (client == null) {
				request.setAttribute("emailError", "notexist");
				request.getRequestDispatcher("recuperationMdp.jsp").forward(request, response);
			} else {
				int code = (int) (999999 * Math.random());
				HttpSession session = request.getSession();
				session.setAttribute("code", code);
				session.setAttribute("idClient", client.getId());
				response.sendRedirect("sasirCode.jsp");
				try {
					this.sendMail(email, code);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}else if (request.getParameter("code") != null) {
			int codeEntre = Integer.parseInt(request.getParameter("code"));		
			HttpSession session = request.getSession();
			int code = Integer.parseInt(session.getAttribute("code").toString());
			if (code != codeEntre) {
				request.setAttribute("codeError", "error");
				request.setAttribute("code", code);
				request.getRequestDispatcher("sasirCode.jsp").forward(request, response);			
			}else 
				request.getRequestDispatcher("changerMdp.jsp").forward(request, response);
		}else if(request.getParameter("password") != null && request.getParameter("passwordConfirm") != null){
			String password = Encrypt.encrypt(request.getParameter("password"));
			String passwordConfirm = Encrypt.encrypt(request.getParameter("passwordConfirm"));
			if(password.equals(passwordConfirm)) {
				HttpSession session = request.getSession();
				int id = Integer.parseInt(session.getAttribute("idClient").toString());
				Client client = cs.findById(id);
				client.setPassword(password);
				cs.update(client);
				session.invalidate();
				request.setAttribute("mdpChanged", "success");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}else {
				request.setAttribute("mdpConfirmError", "error");
				request.getRequestDispatcher("changerMdp.jsp").forward(request, response);
			}
		}
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
