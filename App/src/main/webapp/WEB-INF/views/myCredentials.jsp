<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>myCredentials</title>
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

			
			
			<c:if test="${storageEmpty}">
				<div class="alert alert-warning mt-3" role="alert">
					You do not have any <strong>credential</strong>. Please, click <a href="createCredentials" class="alert-link">here</a> to get one.
				</div>
			</c:if>
			<c:forEach items="${list}" var="map" varStatus="loop">
				<c:choose>
					<c:when test="${empty map}">  <!-- this is the same that ${fn:length(map) > 0} -->
		 				<!-- don't do anything -->
					</c:when>
					<c:otherwise>
					<c:choose>
						<c:when test="${credentialAvailable && loop.index == 0}">
							<div class="alert alert-warning mt-3" role="alert">
								You do not have any <strong>credential</strong>. Please, click <a href="createCredentials" class="alert-link">here</a> to get one.
							</div>
						</c:when>
						<c:otherwise>
							<c:if test="${bankAvailable && loop.index == 0}">
								<div class="alert alert-warning mt-3" role="alert">
									Do you want to create a bank account? Click <a href="bankCreate" class="alert-link">here</a> to get one.
								</div>
							</c:if>	
							<c:if test="${socialSecurityAvailable && loop.index == 0}">
								<div class="alert alert-warning mt-3" role="alert">
									You do not have any <strong>social security identification</strong>. Please, click <a href="socialSecurity" class="alert-link">here</a> to get one.
								</div>
							</c:if>
						</c:otherwise>
					</c:choose>	
					<div class="card mb-3 mt-3">
						<div class="row no-gutters">
							<div class="col-md-2 d-flex justify-content-center align-self-center">
								<img class="card-img transparency" src="resources/imgs/ethereum.png" alt="sign">
							</div>
								<div class="col-md-10">
									<div class="card-body">
										<br>
										<form:form method="POST" action="/App/deleteCredentials" modelAttribute="parameters">
											<p class="card-title" style="padding-left: 20px; border-left: 2px solid #ccc;">DID # did:${identifiers.get(loop.index)} </p>
											<input type="hidden" name="index" value="${loop.index}">
											<input type="hidden" name="did" value="${identifiers.get(loop.index)}">
											<div id="clickToDelete" style="display: block;">
												<button type="submit" class="close" onclick="deleting()"><i class="fa fa-trash"></i> </button>
											</div>
											<div id="deleting" style="display: none;">
												<button type="submit" class="close" disabled><i class="fa fa-trash"></i> </button>
											</div>
												<br>		
												<c:forEach items="${map}" var="entry">
													<p class="card-text" style="line-height:0px"><strong>${entry}</strong></p>
												</c:forEach>
										</form:form>
									</div>
								</div>
							</div>
						</div>	
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</div>
	</div>

	

	<script type="text/javascript">
		function deleting() {
			document.getElementById("clickToDelete").style.display = "none";
			document.getElementById("deleting").style.display = "block";
		}
	</script>
</body>
</html>