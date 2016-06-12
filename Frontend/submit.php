<?php
//Submit to shit
$email = $_POST['email'];
$ip = $_POST['ip'];
$s1 = $_POST['sensor1'];
$s2 = $_POST['sensor2'];
$conn = mysqli_connect ( '192.168.2.102', 'data', '123456', 'comHome' );
//MAIL
if(!empty($email)){
$result = mysqli_query($conn, "UPDATE `Config` SET `Value`='" . $email . "' WHERE `Key`='Email'");
echo "email";
}else {
	echo "emailerror";
}
//IP
if(!empty($ip)){
	$result = mysqli_query($conn, "UPDATE `Config` SET `Value`='" . $ip . "' WHERE `Key`='userIP'");
	echo "ip";
}else {
	echo "iperror";
}
//s1
if(!empty($s1)){
	$result = mysqli_query($conn, "UPDATE `Sensors` SET `Name`='" . $s1 . "' WHERE `ID`='1'");
	echo "s1";
}else {
	echo "s1error";
}
//s2
if(!empty($s2)){
	$result = mysqli_query($conn, "UPDATE `Sensors` SET `Name`='" . $s2 . "' WHERE `ID`='2'");
	echo "s2";
}else {
	echo "s2error";
}
?>