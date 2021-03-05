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
 * Servlet implementation class FileController
 */
@WebServlet("/files")
public class FileController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String imageName = null;
        byte bytes[] = null;
        double imageSize = 0;
        if(ServletFileUpload.isMultipartContent(request))
        {
        	try
        	{
        		List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
        		 for (FileItem item : multiparts) {
                     if (item.isFormField()) {
                         imageName = item.getString();
                     } else {
                         imageSize = item.getSize() / 1024;
                         bytes = item.get();
                     }
                 }
        		 UserModel user = (UserModel) request.getSession().getAttribute("user");
        		 FilesModel image = new FilesModel(bytes,imageSize,imageName);
                 image.setUser(user);
                 if (user != null) {
                     if (image.getFileSize() < 1024) {
                         if (UserDAO.getImagesSize(user.getUser_name())
                                 + image.getFileSize() < 1024*10) {
                             UserDAO.saveFile(image);
                             try {



                                 UserModel userUpdated = UserDAO.getDetails(
                                         ((UserModel) request.getSession().getAttribute("user")).getUser_name());
                                 request.getSession().setAttribute("user", userUpdated);
                                 response.sendRedirect("displaydata.jsp");
                             } catch (Exception e) {
                                 System.out.println("Unable to add user to session");
                             }
                         } else {
                             System.out.println("Images size greater than 10 MB is not allowed");
                             request.getSession().setAttribute("message", "Images size greater than 10 MB is not allowed");
                             response.sendRedirect("displaydata.jsp");
                         }
                     } else {
                         System.out.println("Image size greater than 1 MB is not allowed    ");
                         request.getSession().setAttribute("message", "Image size greater than 1 MB is not allowed");
                         response.sendRedirect("displaydata.jsp");
                     }
                 }

        	}
        	
        	catch (Exception e) {
				// TODO: handle exception
			}
        }
        
	}

}
