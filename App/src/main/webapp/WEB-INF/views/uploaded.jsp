<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
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
				<h4 class="alert-heading">File signed with success</h4>
				<div class="col text-right">
					<h4 class="alert-heading">
						<i class="fa fa-check"></i>
					</h4>
				</div>
			</div>

			<hr />
			<p class="mb-0">The path was saved in: ${path}</p>
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
	
</body>
</html>