<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
</head>
<body>
<form action="<%=request.getContextPath()%>/files"
            method="post" enctype="multipart/form-data">

 

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
                    <button type="reset" class="btn btn-danger" >Cancel</button>
                </div>
            </div>
        </form>
</body>
</html>