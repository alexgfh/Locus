<?php



$host = "mysql.dcc.ufmg.br";
$user = "fabricio.silva";
$password = "timef";
$db = "fabriciosilva";


$sql = "select * from eventos;";

$con = mysqli_connect($host, $user, $password, $db);

mysqli_set_charset($con,"utf8");

$result = mysqli_query($con, $sql);

$response = array();

while($row = mysqli_fetch_array($result))
{
array_push($response, array("id"=>$row[0],"titulo"=>$row[1],"descricao"=>"$row[2]","local"=>$row[3],"latitude"=>$row[4],"longitude"=>$row[5],"tipo"=>$row[6],"inicio"=>$row[7],"fim"=>$row[8]));
}

echo json_encode(array("server_response"=>$response));
mysqli_close($con);

?>
