package com.edu.ruse.studypal;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/loginServlet")
public class StudyPalLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public StudyPalLoginServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		// Validate user credentials 
		if (username.equals("user") && password.equals("password")) {
			response.sendRedirect("loginSuccesful.html");
		} else {
			response.sendRedirect("loginError.html");
		}
	}
}
