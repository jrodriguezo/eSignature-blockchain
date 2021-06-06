<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title> File Sign Form</title>

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
		<a href="/App/" class="btn btn-secondary active btn-block"><i
			class="fa fa-angle-left "></i> &nbsp; Back</a>

		<div class="container p-3 mb-3 border" style="background-color: white;">

			<div class="row">
				<div class="col-12 text-center">
					<p class="text-center">Complete the following form fields to sign a file.</p>
				</div>
			</div>

			<form:form method="POST" action="uploadFile"
				enctype="multipart/form-data" modelAttribute="parameters">
				<div class="form-group">
					<div class="row">
						<div class="col-3">
							<label>Select format: </label>
						</div>
						<div class="col-9">
							<div class="form-check form-check-inline">
								<form:radiobutton class="form-check-input" path="format"
									id="inlineRadio1" value="PAdES" checked="checked"
									onclick="disable_buttons_PAdES()" />
								<label class="form-check-label" for="inlineRadio1">
									PAdES </label>
							</div>
							<!-- div class="form-check form-check-inline">
								<form:radiobutton class="form-check-input" path="format"
									id="inlineRadio2" value="CAdES"
									onclick="disable_buttons_CAdES()" />
								<label class="form-check-label" for="inlineRadio2">
									CAdES </label>
							</div -->
							<div class="form-check form-check-inline">
								<form:radiobutton class="form-check-input" path="format"
									id="inlineRadio3" value="XAdES"
									onclick="disable_buttons_XAdES()" />
								<label class="form-check-label" for="inlineRadio3">
									XAdES </label>
							</div>
						</div>
					</div>
					</div>
					<div class="form-group">
						<div class="row">
							<div class="col-3">
								<label>Select packaging: </label>
							</div>
							<div class="col-9">
								<div class="form-check form-check-inline">
									<form:radiobutton class="form-check-input" path="packaging"
										id="inlineRadio4" value="ENVELOPED" checked="checked" />
									<label class="form-check-label" for="inlineRadio4">
										Enveloped </label>
								</div>
								<div class="form-check form-check-inline">
									<form:radiobutton class="form-check-input" path="packaging"
										id="inlineRadio5" value="ENVELOPING" disabled="true"/>
									<label class="form-check-label" for="inlineRadio5">
										Enveloping </label>
								</div>
								<div class="form-check form-check-inline">
									<form:radiobutton class="form-check-input" path="packaging"
										id="inlineRadio6" value="DETACHED" disabled="true"/>
									<label class="form-check-label" for="inlineRadio6">
										Detached </label>
								</div>
							</div>
						</div>
						</div>

						
						<div class="form-group">
							<div class="row">
								<div class="col-3">
									<label><i class="fas fa-file"></i> Upload your file: </label>
								</div>
								<div class="col-9">
									<form:input class="w-100 border rounded" type="file" path="file" name="file2"/>
								</div>
							</div>
							<div class="row">
								<div class="col">
									<small class="form-text text-muted">Here you have to
										put your XML file to sign</small>
								</div>
							</div>
						</div>



				<div class="form-group">
					<div class="row">
						<div class="col-3">
							<label>Select level: </label>
						</div>
						<div class="col-9">
							<form:select path="level">
								<form:option value="BASELINE_B" label="baseline profile B" />
								<form:option value="BASELINE_T" label="baseline profile T" />
								<form:option value="BASELINE_LT" label="baseline profile LT" />
								<form:option value="BASELINE_LTA" label="baseline profile LTA" />
							</form:select>
						</div>
					</div>		
				</div>
				
				<div class="form-group">
					<div class="row">
						<div class="col-3">
							<label>Select Digest Algorithm: </label>
						</div>
						<div class="col-9">
							<form:select path="digest_algorithm">
								<form:option value="SHA1" label="SHA1" />
								<form:option value="SHA256" label="SHA256" />
								<form:option value="SHA384" label="SHA384" />
								<form:option value="SHA512" label="SHA512" />
							</form:select>
						</div>
					</div>		
				</div>

				<div class="form-group">
							<div class="row">
								<div class="col-3">
									<label>Select how
										you want to sign: </label>
								</div>
								<div class="col-9">
									<div class="form-check form-check-inline">
										<form:radiobutton class="form-check-input" path="device"
											value="pkc12" id="inlineRadio7" onclick="enable_input()" disabled="true"/>
										<label class="form-check-label" for="inlineRadio7" >
											Certificate PKCS#12 </label>
									</div>
									<div class="form-check form-check-inline">
										<form:radiobutton class="form-check-input" path="device"
											value="mscapi" id="inlineRadio8" onclick="disable_input()" checked="checked"/>
										<label class="form-check-label" for="inlineRadio8">
											SmartCard </label>
									</div>

								</div>
							</div>
						</div>
						
						<div id="show_input" style="display: none;" class="form-group">
							<div class="row">
								<div class="col-3">
									<label><i class="fas fa-lock"></i> Upload your key: </label>
								</div>
								<div class="col-9">
									<form:input class="w-100 border rounded" type="file" path="key" name="file1"/>
								</div>
							</div>
							<div class="row">
								<div class="col">
									<small class="form-text text-muted">Here you have to
										put your .p12 file</small>
								</div>
							</div>
						</div>
				<button type="button"
					style="all: unset; color: blue; -webkit-text-fill-color: blue; cursor: pointer;"
					data-container="body" data-toggle="popover" data-placement="right"
					data-content="Privacy information: data is processed on server, we do not store anything.">
					How we use your data?
				</button>
				<div class="row p-3">
							<div id="upload" style="display: block;" class="form-group">
								<input type="submit" value="Upload"
									class="btn btn-primary btn-sm" onclick="loading()">
							</div>
							<div id="loading" style="display: none;" class="form-group">
								<button class="btn btn-primary btn-sm" type="button" disabled>
								  <span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
								  Loading...
								</button>
							</div>		
						</div>
			</form:form>
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
		//uncheck packaging selected for security reasons and then just enable buttons that works with the format selected
		function disable_buttons_PAdES() {
			document.getElementById("inlineRadio4").checked = true; //just can enable and select this
			document.getElementById("inlineRadio5").checked = false;
			document.getElementById("inlineRadio6").checked = false;
			document.getElementById("inlineRadio4").disabled = false;
			document.getElementById("inlineRadio5").disabled = true;
			document.getElementById("inlineRadio6").disabled = true;
			document.getElementById("inlineRadio7").disabled = true;
		}
		/*
		NOTE: This button was not implemented	
		function disable_buttons_CAdES() {
			document.getElementById("inlineRadio4").checked = false;
			document.getElementById("inlineRadio5").checked = false;
			document.getElementById("inlineRadio6").checked = false;
			document.getElementById("inlineRadio4").disabled = true;
			document.getElementById("inlineRadio5").disabled = false;
			document.getElementById("inlineRadio6").disabled = false;
		}
		*/
		function disable_buttons_XAdES() {
			document.getElementById("inlineRadio4").checked = false;
			document.getElementById("inlineRadio5").checked = false;
			document.getElementById("inlineRadio6").checked = false;
			document.getElementById("inlineRadio4").disabled = false;
			document.getElementById("inlineRadio5").disabled = false;
			document.getElementById("inlineRadio6").disabled = false;
			document.getElementById("inlineRadio7").disabled = false;
		}
		function enable_input() {
			document.getElementById("show_input").style.display = "block";
		}
		function disable_input() {
			document.getElementById("show_input").style.display = "none";
		}
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