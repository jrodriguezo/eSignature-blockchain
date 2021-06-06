<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>MyWallet</title>
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
			<c:choose>
				<c:when test="${empty w.address}">
					<p id="noWallet" style="display: block;" class="mt-2">Your session has expired. Click <a href="createDIDForm" class="alert-link">here</a> to Log-in again</p>
				</c:when>
				<c:otherwise>
					<div class="card-body">
						<h5 class="card-title">Wallet imported</h5>
						<p class="card-text">Content:</p>
						<ul>
							<li>DID: <strong>${w.address}.</strong></li>
							<!-- li>Balance: <strong>${w.balance}</strong> wei. </li-->
						</ul>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>

</body>
</html>