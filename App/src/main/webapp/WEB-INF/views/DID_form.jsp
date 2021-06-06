<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>DID form</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">

<!--  link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"-->
<link rel="stylesheet"
	href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" />
	
<style type="text/css">
    .popover{
        max-width:100%;
    }
</style>
</head>
<body>


	<div class="container mt-3">
		<c:if test="${empty w.address}">
			<a href="/App/" class="btn btn-secondary active btn-block"><i
				class="fa fa-angle-left "></i> &nbsp; Back</a>

			<div class="container p-3 mb-3 border">
				<form:form method="POST" action="addedDID"
					enctype="multipart/form-data" modelAttribute="profile">

					<div class="form-group">
						<div class="row">
							<div class="col-3">
								<label><i class="fas fa-lock"></i> Import keystore: </label>
							</div>
							<div class="col-9">
								<div class="row">
									<form:input class="w-100 border rounded" type="file"
										path="walletfile" />
								</div>
							</div>

						</div>
					</div>

					<div class="form-group">
						<div class="row">
							<div class="col-3">
								<label> Introduce password: </label>
							</div>
							<div class="col-9">
								<div class="row">
									<form:input name="password" type="password" path="password" />
									<div id="upload" style="display: block;">
										<input type="submit" value="Load"
										class="btn btn-primary btn-sm ml-3" onclick="loading()">
									</div>
									<div id="loading" style="display: none;">
										<button class="btn btn-primary btn-sm ml-3" type="button" disabled>
											<span class="spinner-border spinner-border-sm" role="status"
												aria-hidden="true"></span> Loading...
										</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</form:form>
				<c:if test="${not empty incorrectPassword}">
					<div class="alert alert-danger" role="alert">
						${incorrectPassword}
					</div>
				</c:if>
				<button type="button"
					style="all: unset; color: blue; -webkit-text-fill-color: blue; cursor: pointer;"
					data-container="body" data-toggle="popover" data-placement="right"
					data-content="Privacy information: data is processed on server, we do not store anything.">
					How we use your data?
				</button>
			</div>
		</c:if>
	</div>

	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
		integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
		integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
		crossorigin="anonymous"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
		integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
		crossorigin="anonymous"></script>
	<script type="text/javascript">
		function loading() {
			document.getElementById("upload").style.display = "none";
			document.getElementById("loading").style.display = "block";
		}
		
		// Popover privacy information
		$(function () {
			  $('[data-toggle="popover"]').popover()
			})
	</script>
</body>
</html>