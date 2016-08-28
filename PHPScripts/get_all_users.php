<?php



$host = "mysql.dcc.ufmg.br";
$user = "fabricio.silva";
$password = "timef";
$db = "fabriciosilva";



$sql = "select * from usuarios;";

$con = mysqli_connect($host, $user, $password, $db);

mysqli_set_charset($con,"utf8");

$result = mysqli_query($con, $sql);

$response = array();

while($row = mysqli_fetch_array($result))
{
array_push($response, array("usuario"=>$row[2],"senha"=>$row[3]));
}

echo json_encode(array("server_response"=>$response));
mysqli_close($con);

?>
