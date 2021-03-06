package com.nagarro.image.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.nagarro.image.dao.UserDAO;
import com.nagarro.image.model.FilesModel;
import com.nagarro.image.model.UserModel;

/**
 * Servlet implementation class EditImageController
 */
@WebServlet("/UpdateImage")
public class EditImageController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditImageController() {
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserModel user = (UserModel) request.getSession().getAttribute("user");
		if (request.getSession().getAttribute("user") == null) {
			response.sendRedirect("login.jsp");
		} else {
			String imageId = request.getParameter("imageId");
			FilesModel ir = UserDAO.getImage(imageId);

			double imageSize = 0;
			byte[] image = null;
			String imageName = null;
			try {
				if (!ServletFileUpload.isMultipartContent(request))
					return;
				List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
				for (FileItem item : multiparts) {
					if (item.isFormField()) {
						imageName = item.getString();
						if (!imageName.isEmpty()) {
							ir.setFileName(imageName);
						}
					} else {
						imageSize = item.getSize() / 1024;
						if (imageSize != 0) {
							image = item.get();
							ir.setFileSize(imageSize);
							ir.setFileData(image);
						}
					}
				}
			} catch (Exception e) {
				System.out.println("Error in Image update");
			}
			if (ir.getFileSize() > 1024 || UserDAO.getImagesSize(user.getUser_name()) + imageSize > 10 * 1024) {
				System.out.println("Image size is greater than 1MB or Total Image Size is greater than 10MB");
				request.getSession().setAttribute("message",
						"Updated image is greater than 1MB or Total size is greater than 10Mb");

			} else {
				UserDAO.editImage(ir);
				request.setAttribute("message", "Your Image has been updated successfully!");

			}
			try {
				UserModel userUpdated = UserDAO
						.getDetails(((UserModel ) request.getSession().getAttribute("user")).getUser_name());
				request.getSession().setAttribute("user", userUpdated);
				response.sendRedirect("displaydata.jsp");
			} catch (Exception e) {
				System.out.println("Unable to set user to session");
			}
		}
	}

}
