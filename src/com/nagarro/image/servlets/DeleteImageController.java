package com.nagarro.image.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nagarro.image.dao.UserDAO;
import com.nagarro.image.data.UserModel;

/**
 * Servlet implementation class DeleteImageController
 * used to delete the image.
 */

@WebServlet("/DeleteImage")
public class DeleteImageController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteImageController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (request.getSession().getAttribute("user") == null) {
			response.sendRedirect("login.jsp");
		} else {
			String imageId = request.getParameter("imageId").toString();
			UserDAO.deleteImage(imageId);
			try {

				UserModel userUpdated = UserDAO
						.getDetails(((UserModel) request.getSession().getAttribute("user")).getUser_name());
				request.getSession().setAttribute("user", userUpdated);
				response.sendRedirect("displayData.jsp");
			} catch (Exception e) {
				System.out.println("Unable to set user to session");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
