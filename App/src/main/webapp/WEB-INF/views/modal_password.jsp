<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Modal password</title>
<!-- link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">

<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">

<!--  link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"-->
<link rel="stylesheet"
	href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" />

</head>
<body style="">

	<div class="modal" tabindex="-1" role="dialog" id="modal">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">${titleMessage}</h5>
						<form:form method="GET" action="${goBack}" modelAttribute="parameters">
						<input type="submit" value="&times;" class="close" style="border: none; background-color: white;">
					</form:form>
				</div>
				<div class="modal-body">
					<form:form method="POST" action="${view}">
						<p>${bodyMessage}</p>

						<div class="form-group">
							<div class="row">
								<div class="col-4">
									<label>Your password:</label>
								</div>
								<div class="col-8">
									<input type="password" id="password" name="password">
								</div>
							</div>
						</div>
						
						<div id="upload" style="display: block;" class="form-group">
							<input type="submit" value="${buttonMessage}" class="btn btn-primary btn-sm float-right" onclick="loading()">
						</div>
						<div id="loading" style="display: none;" class="form-group">
							<button class="btn btn-primary btn-sm float-right" type="button" disabled>
							  <span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
							  Loading...
							</button>
						</div>
					</form:form>
				</div>
			</div>
		</div>
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
		$(window).on('load', function() {
			$('#modal').modal({backdrop: 'static', keyboard: false});
		});
		
		function loading() {
			document.getElementById("upload").style.display = "none";
			document.getElementById("loading").style.display = "block";
		}
	</script>
</body>
</html>