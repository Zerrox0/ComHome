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
				<form class="form-control input-group" id="settingsform">
					<div class="modal-body" id="settingsbody">
						<label for="recipient-name" class="control-label">Account settings</label>
						<div class="input-group">
							<!-- Email start -->
							<span class="input-group-addon" id="basic-addon3">Email:</span> <input
								id="email" type="email" class="form-control" name="email"
								autocomplete="off" aria-describedby="basic-addon3"
								value="<?php
								$conn = mysqli_connect ( '192.168.2.102', 'data', '123456', 'comHome' );
								$result = mysqli_query ( $conn, "SELECT `Value` FROM `Config` WHERE `Key`='Email'" );
								$res = mysqli_fetch_array ( $result );
								echo $res [0];
								?>" />
						</div>
						<div class="input-group">
							<!-- IP Start -->
							<span class="input-group-addon" id="basic-addon3">Your static IP
								Address:</span> <input type="text" class="form-control" id="ip"
								name="ip" aria-describedby="basic-addon3" autocomplete="off"
								value="<?php
								$conn = mysqli_connect ( '192.168.2.102', 'data', '123456', 'comHome' );
								$result = mysqli_query ( $conn, "SELECT `Value` FROM `Config` WHERE `Key`='userIP'" );
								$res = mysqli_fetch_array ( $result );
								echo $res [0];
								?>" />
						</div>
						<br />
						<!-- Notification -->
						<label for="recipient-name" class="control-label">Sensor settings</label>
						<!-- Sensor settings -->
						<div class="input-group">
							<span class="input-group-addon" id="basic-addon3">Name for sensor
								1:</span> <input id="sensor1" type="text" class="form-control"
								name="sensor1" autocomplete="off"
								aria-describedby="basic-addon3"
								value="<?php
								$conn = mysqli_connect ( '192.168.2.102', 'data', '123456', 'comHome' );
								$result = mysqli_query ( $conn, "SELECT `Name` FROM `Sensors` WHERE `ID`='1'" );
								$res = mysqli_fetch_array ( $result );
								echo $res [0];
								?>" />
						</div>
						<div class="input-group">
							<span class="input-group-addon" id="basic-addon3">Name for sensor
								2:</span> <input type="text" class="form-control" id="sensor2"
								name="sensor2" aria-describedby="basic-addon3"
								autocomplete="off"
								value="<?php
								$conn = mysqli_connect ( '192.168.2.102', 'data', '123456', 'comHome' );
								$result = mysqli_query ( $conn, "SELECT `Name` FROM `Sensors` WHERE `ID`='2'" );
								$res = mysqli_fetch_array ( $result );
								echo $res [0];
								?>" />
						</div>
						<div id="settingsnotification"></div>
						<br />

					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						<button type="button" id="apply" class="btn btn-primary">Save
							changes</button>
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
				<br />
				<h2>Statistics:</h2>
				<script src="js/chart.js"></script>
<canvas id="myChart" width="400" height="400"></canvas>
<script>
var ctx = document.getElementById("myChart");
var myChart = new Chart(ctx, {
    type: 'bar',
    data: {
        labels: ["Red", "Blue", "Yellow", "Green", "Purple", "Orange"],
        datasets: [{
            label: '# of Votes',
            data: [12, 19, 3, 5, 2, 3],
            backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)',
                'rgba(255, 206, 86, 0.2)',
                'rgba(75, 192, 192, 0.2)',
                'rgba(153, 102, 255, 0.2)',
                'rgba(255, 159, 64, 0.2)'
            ],
            borderColor: [
                'rgba(255,99,132,1)',
                'rgba(54, 162, 235, 1)',
                'rgba(255, 206, 86, 1)',
                'rgba(75, 192, 192, 1)',
                'rgba(153, 102, 255, 1)',
                'rgba(255, 159, 64, 1)'
            ],
            borderWidth: 1
        }]
    },
    options: {
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero:true
                }
            }]
        }
    }
});
</script>

			</div>
		</div>
	</div>
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script><script>
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
	<script type="text/javascript">
							$("#apply").click(function() {
									//BTNCLCK
								 var values = $("#settingsform").serialize();
								 $.ajax({
								        url: "submit.php",
								        type: "post",
								        data: values ,
								        success: function (response) {
								          if(response == "emailips1s2"){
								        	$("#settingsnotification").html('<div class="alert alert-success" role="alert">Your settings were updated successfully.</div>');
								        	}else {
									        	$("#settingsnotification").html('<div class="alert alert-warning" role="alert">Don\'t leave any fields empty!</div>');
													
									        	}
									        
								        },
								        error: function(jqXHR, textStatus, errorThrown) {
								        	$("#settingsnotification").html('<div class="alert alert-danger" role="alert">An error occoured while updating your settings!' + textStatus +'</div>');
								        }


								    });

								
								});
	</script>


</body>
</html>