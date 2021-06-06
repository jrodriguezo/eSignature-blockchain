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
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<!--  link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"-->
<link rel="stylesheet"
	href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" />

</head>
<body>

	<div class="container mt-3">
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<ul class="navbar-nav">
				<li>
					<form action="/App/myWallet" method="POST">
						<!-- Write all studentdata Properties as Hidden inputs  -->
						<input type="hidden" name="property1" value="${w.address}">
						<c:choose>
							<c:when test="${empty w.address}">
								<input type="submit"
									style="border: none; background-color: rgb(249, 249, 249);"
									value="MyWallet" disabled>
							</c:when>
							<c:otherwise>
								<input type="submit"
									style="border: none; background-color: rgb(249, 249, 249);"
									value="MyWallet">
							</c:otherwise>
						</c:choose>
					</form>
				</li>
				<li>
					<form action="/App/userCredentials" method="GET">
						<c:choose>
							<c:when test="${empty w.address}">
								<input type="hidden" name="property1" value="${w.address}">
								<input type="submit"
									style="border: none; background-color: rgb(249, 249, 249);"
									value="MyCredentials" disabled>
							</c:when>
							<c:otherwise>
								<input type="hidden" name="property1" value="${w.address}">
								<input type="submit"
									style="border: none; background-color: rgb(249, 249, 249);"
									value="MyCredentials">
							</c:otherwise>
						</c:choose>
					</form>
				</li>
				<li>
					<form action="/App/verifyCredentialsForm" method="GET">
						<c:choose>
							<c:when test="${empty w.address}">

								<input type="hidden" name="property1" value="${w.address}">
								<input type="submit"
									style="border: none; background-color: rgb(249, 249, 249);"
									value="Verify" disabled>
							</c:when>
							<c:otherwise>
								<input type="hidden" name="property1" value="${w.address}">
								<input type="submit"
									style="border: none; background-color: rgb(249, 249, 249);"
									value="Verify">
							</c:otherwise>
						</c:choose>
					</form>
				</li>
				<li>
					<form action="/App/createDIDForm" method="GET">
						<input type="submit"
							style="border: none; background-color: rgb(249, 249, 249);"
							value="Exit">
					</form>
				</li>
			</ul>
			
		</nav>
		<div class="container border">
		
			<c:if test="${empty w.address}">
					<p id="noWallet" style="display: block;" class="mt-2">Your session has expired. Click <a href="createDIDForm" class="alert-link">here</a> to Log-in again</p>
				</c:if>

			<div class="container p-3 mb-3 mt-3 border">

				<div class="row">
					<div class="col-12 text-center">
						<p class="text-center">This is to get new credentials to
							issuer.</p>
					</div>
				</div>

				<form:form method="POST" action="sendedCredentials"
					modelAttribute="parameters">

					<div class="form-group">
						<div class="row">
							<div class="col-2">
								<form:label path="name">First name:</form:label>
							</div>
							<div class="col-4">
								<form:input path="name" />
							</div>
						</div>
					</div>


					<div class="form-group">
						<div class="row">
							<div class="col-2">
								<form:label path="surname">Last name:</form:label>
							</div>
							<div class="col-4">
								<form:input path="surname" />
							</div>
						</div>
					</div>

					<div class="form-group">
						<div class="row">
							<div class="col-2">
								<form:label path="gender">Select your gender: </form:label>
							</div>
							<div class="col-4">
								<form:select path="gender">
									<form:option value="Male" label="Male" />
									<form:option value="Female" label="Female" />
								</form:select>
							</div>
						</div>
					</div>

					<div class="row p-3">
						<input type="submit" value="Update" class="btn btn-primary btn-sm">
					</div>
					<input type="hidden" name="property1" value="${w.address}">

				</form:form>
			</div>
		</div>

		<div class="modal" tabindex="-1" role="dialog" id="modal">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title">Modal title</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<p>Modal body text goes here.</p>
					</div>
					<div class="modal-footer">
						<form:form method="POST" action="sendedCredentialsPassword"
							modelAttribute="parameters">
							<div class="form-group">
								<div class="row">
									<div class="col-2">
										<form:label path="password">Wallet password:</form:label>
									</div>
									<div class="col-4">
										<form:input path="password" />
									</div>
								</div>
							</div>
							<input type="submit" value="upload"
								class="btn btn-primary btn-sm">
							<input type="hidden" name="property1" value="${w.address}">
							<input type="hidden" name="property2" value="${p.name}">
							<input type="hidden" name="property3" value="${p.surname}">
							<input type="hidden" name="property4" value="${p.gender}">
						</form:form>
						<input type="submit" value="Close"
							class="btn btn-secondary btn-sm" data-dismiss="modal">
					</div>
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
</body>
</html>