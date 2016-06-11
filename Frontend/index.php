<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" href="icon.png" />
<title>comHome</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
					aria-expanded="false">
					<span class="sr-only">Navigation</span> <span class="icon-bar"></span>
					<span class="icon-bar"></span> <span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">comHome</a>
			</div>
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li class="active"><a href="#">Dashboard <span class="sr-only">(current)</span></a></li>

				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a data-toggle="modal" data-target="#myModal">Settings</a></li>
				</ul>
			</div>
		</div>
	</nav>
	<!-- START OF SETTINGS -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Settings</h4>
				</div>
				<form class="form-control input-group" method="POST"
					action="submit.php">
					<div class="modal-body">
						<label for="recipient-name" class="control-label">Account settings</label>
						<div class="input-group">
							<!-- Email start -->
							<span class="input-group-addon" id="basic-addon3">Email:</span> <input
								type="email" class="form-control" name="email" autocomplete="off"
								aria-describedby="basic-addon3"
								placeholder="<?php
								$conn = mysqli_connect ( '192.168.2.102', 'data', '123456', 'comHome' );
								$result = mysqli_query ( $conn, "SELECT `Value` FROM `Config` WHERE `Key`='Email'" );
								$res = mysqli_fetch_array ( $result );
								echo $res [0];
								?>" />
						</div>
						<div class="input-group">
							<!-- IP Start -->
							<span class="input-group-addon" id="basic-addon3">Your static IP
								Address:</span> <input type="text" class="form-control"
								name="ip" aria-describedby="basic-addon3" autocomplete="off"
								placeholder="<?php
								$conn = mysqli_connect ( '192.168.2.102', 'data', '123456', 'comHome' );
								$result = mysqli_query ( $conn, "SELECT `Value` FROM `Config` WHERE `Key`='userIP'" );
								$res = mysqli_fetch_array ( $result );
								echo $res [0];
								?>" />
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						<button type="submit" class="btn btn-primary">Save changes</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="col-md-8 text-center">
				<h1>Welcome to your comHome dashboard!</h1>
				<h2>Here's an overview over your sensors</h2>
				<table class="table table-hover table-bordered" id="tableupdate"
					data-toggle="table">

				</table>

			</div>
		</div>
	</div>
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script>
						$(document).ready(function(){
							window.setInterval(function(){
								$.ajax("http://localhost/Eclipse/Projects/comHome/update.php", {
										method:"GET",
										success:function(data){
											
											$('#tableupdate').html(data);
										}
									});
							}, 300);
							});

	</script>



</body>
</html>