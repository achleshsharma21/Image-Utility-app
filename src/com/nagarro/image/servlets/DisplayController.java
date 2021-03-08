package com.nagarro.image.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nagarro.image.dao.UserDAO;
import com.nagarro.image.data.FilesModel;

/**
 * Servlet implementation class DisplayController
 */
@WebServlet("/DisplayImage")
public class DisplayController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DisplayController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getSession().getAttribute("user") == null) {
			response.sendRedirect("login.jsp");
		} else {
			String imageId = request.getParameter("imageId");
			FilesModel image = UserDAO.getImage(imageId);
			if (image != null) {
				response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
				try {
					response.getOutputStream().flush();
					response.getOutputStream().write(image.getFileData());
					response.getOutputStream().close();
				} catch (IOException e) {
					request.setAttribute("message", "Image retrieval failed due to " + e);
				}
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
