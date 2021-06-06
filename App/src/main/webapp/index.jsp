<html>
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<link href="resources/css/style.css" rel="stylesheet">
<!-- Fontawesome  -->
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet" type='text/css'>
</head>
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<!--
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
	integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
	integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
	integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
	crossorigin="anonymous"></script>
	
-->

<!-- script src="https://cdn.jsdelivr.net/gh/ethereum/web3.js@1.0.0-beta.37/dist/web3.min.js"></script -->

<body>

	<div class="container-fluid bg-dark py-5">
		<div class="container text-white text-center">
			<h1 class="display-3">
				<b>Web Application</b>
			</h1>
			<p class="lead">Sign as you wish, anywhere in Europe</p>
			<p class="small" style="letter-spacing: 3px;">Master's Thesis</p>
		</div>
	</div>


<div class="container">
	<div class="card-deck">
		<div class="card">
			<a href="uploadFileForm"><img class="card-img-top transparency" src="resources/imgs/sign.jpg" alt="sign"></a>
			<div class="card-body d-flex flex-column">
				<h5 class="card-title">Sign a file</h5>
				<p class="card-text">Sign different file formats with multiple combinations. Customize to your preferences.</p>
				<div class="row justify-content-center mt-auto">
					<a href="uploadFileForm" class="btn btn-outline-primary" role="button" aria-disabled="true">Explore <i class="text-center fas fa-arrow-right"></i></a>	
				</div>	
			</div>
		</div>
		<div class="card">
			<a href="uploadValidateFileForm"><img class="card-img-top transparency" src="resources/imgs/ok.jpg" alt="validate"></a>
			<div class="card-body d-flex flex-column">
				<h5 class="card-title">Validate a file</h5>
				<p class="card-text">Check the validity of the document in a couple of clicks.</p>
				<div class="row justify-content-center mt-auto">
					<a href="uploadValidateFileForm" class="btn btn-outline-primary" role="button" aria-disabled="true">Explore <i class="text-center fas fa-arrow-right"></i></a>	
				</div>	
			</div>
		</div>
		<div class="card">
			<a href="createDIDForm"><img class="card-img-top transparency" src="resources/imgs/blockchain.jpg" alt="blockchain"></a>
			
			<div class="card-body d-flex flex-column">
				<h5 class="card-title">Blockchain Self-Sovereign Identity</h5>
				<p class="card-text">Experiment the self-sovereign identity ecosystem on blockchain. </p>
				<div class="row justify-content-center mt-auto">
					<a href="createDIDForm" class="btn btn-outline-primary" role="button" aria-disabled="true">Explore <i class="text-center fas fa-arrow-right"></i></a>	
				</div>	
			</div>
		</div>
	</div>
</div>

	<!--div class="container"-->
	<!-- div class="row mx-2">
		<div class="col-sm">
			<div class="card my-3">
				<div class="row no-gutters">
					<div class="col-sm-5">
						<a href="createDIDForm"><img class="card-img transparency"
							src="resources/imgs/ethereum_blockchain.jpg" alt="blockchain"></a>
					</div>
					<div class="col-sm-7">
						<div class="card-body">
							<h5 class="card-title">Blockchain Self-Sovereign Identity</h5>
							<hr />
							<p class="card-text">In this section you will be able to
								experiment a Descentralized ID.</p>
							<a href="createDIDForm" class="btn btn-primary">Join</a>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-sm">
			<div class="card my-3">
				<div class="row no-gutters">
					<div class="col-sm-5">
						<a href="uploadFileForm"><img class="card-img transparency"
							src="resources/imgs/sign.jpg" alt="sign"></a>
					</div>
					<div class="col-sm-7">
						<div class="card-body">
							<h5 class="card-title">Sign a file</h5>
							<hr />
							<p class="card-text">In this section you will be able to sign
								different file formats with multiple combinations. Customize to
								your preferences.</p>
							<a href="uploadFileForm" class="btn btn-primary">Start</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row mx-2">
		<div class="col-sm">
			<div class="card my-3">
				<div class="row no-gutters">
					<div class="col-sm-5">
						<a href="uploadValidateFileForm"><img
							class="card-img transparency" src="resources/imgs/ok.jpg"
							alt="validate"></a>
					</div>
					<div class="col-sm-7">
						<div class="card-body">
							<h5 class="card-title">Validate a file</h5>
							<hr />
							<p class="card-text">Check the validity of the document in a
								couple of clicks.</p>
							<a href="uploadValidateFileForm" class="btn btn-primary">Start</a>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-sm">
			<div class="card my-3">
				<div class="row no-gutters">
					<div class="col-sm-5">
						<a href="create"><img class="card-img transparency"
							src="resources/imgs/credit_card.jpg" alt="credit_card"></a>
					</div>
					<div class="col-sm-7">
						<div class="card-body">
							<h5 class="card-title">Create a bank account</h5>
							<hr />
							<p class="card-text">Join our fintech located in Spain no
								matter where you are in Europe. Without photos and instantly.</p>
							<a href="create" class="btn btn-primary">Start</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div -->
	<!--  div class="card my-3">
			<div class="row no-gutters">
				<div class="col-sm-5">
					<img class="card-img transparency" src="resources/imgs/ss.jpg" alt="credit_card">
				</div>
				<div class="col-sm-7">
					<div class="card-body">
						<h5 class="card-title">Request social insurance</h5>
						<hr />
						<p class="card-text">Suresh Dasari is a founder and technical
							lead developer in tutlane.</p>
						<a href="" class="btn btn-primary">Create</a>
					</div>
				</div>
			</div>
		</div-->



	<!--  /div-->



	<footer class="bg-light text-center text-lg-start">

		<!-- Thanks -->
		<div class="text-center p-3 mt-3 bg-dark">
			<p class="text-white" style="letter-spacing: 3px;">Acknowledgements</p>
			<div class="row">
				<div class="col-sm text-center"></div>
				<div class="col-sm">
					<small class="text-muted">Departamento de Ingeniería de
						Sistemas Telemáticos </small><br> <small class="text-muted">Escuela
						Técnica Superior de Ingenieros de Telecomunicación </small></br> <img
						src="resources/imgs/upm.png" alt="upm" style="width: 30%">
				</div>
				<div class="col-sm center-block text-center"></div>
			</div>
		</div>
	</footer>

	<script type="text/javascript">
		
	</script>
</body>
</html>
