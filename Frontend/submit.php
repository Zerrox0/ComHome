<?php
//Submit to shit
$email = $_POST['email'];
$conn = mysqli_connect ( '192.168.2.102', 'data', '123456', 'comHome' );
if(!empty($email)){
$result = mysqli_query($conn, "UPDATE `Config` SET `Value`='" . $email . "' WHERE `Key`='Email'");
mysqli_close($conn);
}
?>