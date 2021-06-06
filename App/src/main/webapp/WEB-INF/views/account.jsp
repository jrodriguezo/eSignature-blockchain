
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Account Creation</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<!--  link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"-->
<link rel="stylesheet"
	href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" />
</head>
<body>

	<div class="container bg-faded py-4">
		<div class="alert alert-success" role="alert">
			<div class="row">
				<h4 class="alert-heading">Your bank account has been created
					with success.</h4>
				<div class="col text-right">
					<h4 class="alert-heading">
						<i class="fa fa-check"></i>
					</h4>
				</div>
			</div>

			<hr />
			<p class="mb-0">The path where PDF was saved is: ${path}</p>
			<div class="row">
				<div class="col text-center">
					<a href="/App/" class="btn btn-success">Accept</a>
					<div class="row">
						<div class="col text-center"></div>
					</div>
				</div>
			</div>
		</div>
	</div>


			<!--  div class="container p-3 my-3 border">
				<ul>
					<li>First name: <strong>${user.firstname}</strong></li>
					<li>Last name: <strong>${user.lastname}</strong></li>
					<li>Gender: <strong>${user.gender}</strong></li>
					<li>E-mail: <strong>${user.email}</strong></li>
					<li>Country: <strong>${user.country}</strong></li>
					<li>Location: <strong>${user.streetname}</strong></li>
					<li>Phone: <strong>${user.phone}</strong></li>
					<li>Married: <strong>${user.married}</strong></li>
				</ul>
			</div-->
</body>
</html>