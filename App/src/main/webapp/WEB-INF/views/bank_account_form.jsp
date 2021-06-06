<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Bank Account Form</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<!--  link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"-->
<link rel="stylesheet"
	href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" />
</head>
<body>


	<div class="container mt-3 ">
		<a href="/App/userCredentials" class="btn btn-secondary active btn-block"><i
			class="fa fa-angle-left "></i> &nbsp; Back</a>

		<div class="container p-3 mb-3 border">

			<div class="row">
				<div class="col-12 text-center">
					<p class="text-center">This is to create a bank account.</p>
				</div>
			</div>

			<form:form action="addUser" modelAttribute="user" method="POST">
				<div class="form-group">
					<div class="row">
						<div class="col-2">
							<form:label path="firstname">First name:</form:label>
						</div>
						<div class="col-4">
							<input readonly="true" disabled="true" placeholder="${name}"/>
							<form:hidden path="firstname" value="${name}"/>
						</div>
					</div>
				</div>


				<div class="form-group">
					<div class="row">
						<div class="col-2">
							<form:label path="lastname">Last name:</form:label>
						</div>
						<div class="col-4">
							<input readonly="true" disabled="true" placeholder="${surname}"/>
							<form:hidden path="lastname" value="${surname}"/>
						</div>
					</div>
				</div>

				<!--  
			<div class="form-group">
				<div class="row">
					<div class="col-2">
						<form:label path="birthday">Birthday:</form:label>
					</div>
					<div class="col-4">
						<form:input type="date" path="birthday" />
					</div>
				</div>
			</div>
	-->
				<div class="form-group">
					<div class="row">
						<div class="col-2">
							<form:label path="gender">Select your gender: </form:label>
						</div>
						<div class="col-4">
							<form:select path="gender" readonly="true" disabled="true">
								<form:option value="${gender}" label="${gender}" />
								<!--  form:option value="male" label="Male" /-->
								<!--  form:option value="female" label="Female" /-->
							</form:select>
							<form:hidden path="gender" value="${gender}"/>
						</div>
					</div>
				</div>

				<div class="form-group">
					<div class="row">
						<div class="col-2">
							<form:label path="email">E-mail:</form:label>
						</div>
						<div class="col-10">
							<form:input path="email" style="width:50%;" />
						</div>
					</div>
				</div>

				<div class="form-group">
					<div class="row">
						<div class="col-2">
							<form:label path="country">Country:</form:label>
						</div>
						<div class="col-10">
							<form:input path="country" />
						</div>
					</div>
				</div>

				<div class="form-group">
					<div class="row">
						<div class="col-2">
							<form:label path="streetname">Location:</form:label>
						</div>
						<div class="col-10">
							<form:input path="streetname" />
						</div>
					</div>
				</div>

				<div class="form-group">
					<div class="row">
						<div class="col-2">
							<form:label path="phone">Phone:</form:label>
						</div>
						<div class="col-10">
							<form:input path="phone" />
						</div>
					</div>
				</div>

				<div class="form-group">
					<div class="row">
						<div class="col-2">
							<form:label path="married">Are you married?</form:label>
						</div>
						<div class="col-10">
							<form:checkbox path="married"/>
						</div>
					</div>
				</div>

				<div class="row p-3">
					<div id="upload" style="display: block;" class="form-group">
						<input type="submit" value="upload" class="btn btn-primary btn-sm"
							onclick="loading()">
					</div>
					<div id="loading" style="display: none;" class="form-group">
						<button class="btn btn-primary btn-sm" type="button" disabled>
							<span class="spinner-border spinner-border-sm" role="status"
								aria-hidden="true"></span> Creating account, please wait...
						</button>
					</div>
				</div>
				<input type="hidden" name="checkboxValue" value="${checkboxValue}">
			</form:form>
		</div>
	</div>



	<script type="text/javascript">
		function loading() {
			document.getElementById("upload").style.display = "none";
			document.getElementById("loading").style.display = "block";
		}
	</script>


</body>
</html>