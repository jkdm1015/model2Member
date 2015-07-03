package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({"/jquery/example01_01.do","/jquery/example01_02.do","/jquery/example01_03.do"})
public class JqueryController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
	protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath().substring(+1);
		String url = "";
		switch (path) {
		case "jquery/example01_01.do":	url = "ch01/example01_01.html";	break;
		case "jquery/example01_02.do":	url = "ch01/example01_02.html";	break;
		case "jquery/example01_03.do":	url = "ch01/example01_03.html";	break;
		default:
			break;
		}
		if (path.substring(0, 5).equals("jquer")){
			RequestDispatcher dispatcher
				= request.getRequestDispatcher("/jquery/"+url);
			dispatcher.forward(request, response);
		}
	}

}
