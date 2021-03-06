<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.nagarro.image.model.FilesModel"%>
<%@ page import="com.nagarro.image.model.UserModel"%>
<%@ page import="com.nagarro.image.dao.UserDAO"%>
<%@ page import="com.nagarro.image.controller.FileController"%>
<%@ page import="com.nagarro.image.controller.LoginController"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
<body>
	<%
	if (session.getAttribute("user") == null) {
	%>
	<jsp:forward page="index.jsp" />
	<%
	}
	String username = "";
	UserModel user = (UserModel) session.getAttribute("user");
	try {
	username = user.getUser_name();
	} catch (Exception e) {
	response.sendRedirect("index.jsp");
	}
	%>
	<form action="<%=request.getContextPath()%>/filesData" method="post"
		enctype="multipart/form-data">



		<div class="row">
			<div class="col-sm-4">
				<input type="text" name="imagename" placeholder="Name of image"
					class="form-control" required>
			</div>
			<div class="col-sm-4">

				<input type="file" name="image" accept="image/*" required
					class="form-control">
			</div>

			<div class="custom-file col-sm-4">
				<button type="submit" class="btn btn-success">Submit</button>
				<button type="reset" class="btn btn-danger">Cancel</button>
			</div>
		</div>
	</form>
	<div class="container">
		<h4 class=" pt-5">Uploaded Images</h4>
		<div class="row text-center">
			<div class="col-1 border border-secondary">S.no</div>
			<div class="col-4 border border-secondary">Name</div>
			<div class="col-2 border border-secondary">Size</div>
			<div class="col-3 border border-secondary">Preview</div>
			<div class="col-2 border border-secondary">Actions</div>
		</div>
		<%
		Set<FilesModel> images = user.getImages();
		int i = 0;
		for (FilesModel image : images) {
			i++;
		%>
		<div class="row text-center">
			<div class="col-1 border border-secondary py-1"><%=i%></div>
			<div class="col-4 border border-secondary py-1"><%=image.getFileName()%></div>
			<div class="col-2 border border-secondary py-1"><%=image.getFileSize()%>
				KB
			</div>
			<div class="col-3 border border-secondary py-1">
				<img
					src="<%=request.getContextPath()%>/DisplayImage?imageId=<%=image.getId()%>"
					alt="Image" height="100" width="100">
			</div>
			<div class="col-2 border border-secondary py-1">
				<button class="btn">
					<i class="fas fa-edit" data-toggle="modal" data-target="#editImage"></i>
				</button>
				<div class="modal fade" id="editImage" tabindex="-1" role="dialog"
					aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
					<div class="modal-dialog modal-dialog-centered" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="exampleModalLongTitle">
									Edit Image
									<%=image.getFileName()%></h5>
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>
							<div class="modal-body">
								<form
									action="<%=request.getContextPath()%>/UpdateImage?imageId=<%=image.getId()%>"
									method="post" enctype="multipart/form-data">
									<div class="row">
										<div class="col-12">
											<input type="text" name="imagename"
												placeholder="Name of image" class="form-control" required>
										</div>
										<div class="col-12 pt-2">
											<input type="file" name="image" accept="image/*" required
												class="form-control">
										</div>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-secondary"
											data-dismiss="modal">Close</button>
										<button type="submit" value="update" class="btn btn-primary">Save
											changes</button>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
				<a
					href="<%=request.getContextPath()%>/DeleteImage?imageId=<%=image.getId()%>"><button
						class="btn">
						<i class="fas fa-trash-alt"></i>
					</button></a>
			</div>
		</div>
		<%
		}
		%>
	</div>
<script src="https://use.fontawesome.com/releases/v5.15.2/js/all.js" data-auto-replace-svg="nest"></script>
	
</body>
</html>