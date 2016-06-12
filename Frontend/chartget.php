<?php
$conn = mysqli_connect("192.168.2.102", "data", "123456", "comHome");
$result = mysqli_query($conn, "SELECT * FROM `Data`");
$row = mysqli_fetch_array($result);
echo json_encode($row);
?>