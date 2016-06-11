
<thead>
	<tr>
		<th class="text-center">Time</th>
		<th class="text-center">Sensor</th>
		<th class="text-center">State</th>
		<th class="text-center">Sensortype</th>
	</tr>
</thead>

<?php
$id = 1;
$switch = true;
$conn = mysqli_connect ( '192.168.2.102', 'data', '123456', 'comHome' );
$query = "SELECT * FROM Data WHERE SensorID='" . $id . "' ORDER BY Zeit DESC LIMIT 1";
$result = mysqli_query ( $conn, $query );
$row = mysqli_fetch_array ( $result );

$query1 = "SELECT * FROM Sensors WHERE ID='" . $id . "'";
$result1 = mysqli_query ( $conn, $query1 );
$row1 = mysqli_fetch_array ( $result1 );
// 1 = open
// 0 = close
if ($row [1] == 0) {
	// Sensor 1 is closed
	
	echo '<tr><td>' . $row [0] . '</td><td>' . utf8_encode ( $row1 [1] ) . '</td><td>Closed</td><td>' . $row1 [2] . '</td></tr>';
} elseif ($row [1] == 1) {
	// SENSOR 1 open
	echo '<tr><td>' . $row [0] . '</td><td>' . utf8_encode ( $row1 [1] ) . '</td><td class="active">Open</td><td>' . $row1 [2] . '</td></tr>';
}
$switch = ! $switch;


$id20= 2;
$switch20 = true;
$conn20 = mysqli_connect ( '192.168.2.102', 'data', '123456', 'comHome' );
$query20 = "SELECT * FROM Data WHERE SensorID='" . $id20 . "' ORDER BY Zeit DESC LIMIT 1";
$result20 = mysqli_query ( $conn20, $query20 );
$row20 = mysqli_fetch_array ( $result20 );

$query120 = "SELECT * FROM Sensors WHERE ID='" . $id20 . "'";
$result120 = mysqli_query ( $conn20, $query120 );
$row120 = mysqli_fetch_array ( $result120 );
// 1 = open
// 0 = close
if ($row20 [1] == 0) {
	// Sensor 1 is closed

	echo '<tr><td>' . $row20 [0] . '</td><td>' . utf8_encode ( $row120 [1] ) . '</td><td>Closed</td><td>' . $row120 [2] . '</td></tr>';
} elseif ($row20 [1] == 1) {
	// SENSOR 1 open
	echo '<tr><td>' . $row20 [0] . '</td><td>' . utf8_encode ( $row120 [1] ) . '</td><td class="active">Open</td><td>' . $row120 [2] . '</td></tr>';
}
$switch = ! $switch;

?>
					