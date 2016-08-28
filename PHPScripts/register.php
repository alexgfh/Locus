<?php

require "init.php";

$titulo = $_POST["titulo"];
$descricao = $_POST["descricao"];
$latitude = $_POST["latitude"];
$longitude = $_POST["longitude"];
$tipo = $_POST["tipo"];
$inicio = $_POST["inicio"];
$fim = $_POST["fim"];
$local = $_POST["local"];

$sql_query = "insert into eventos values(DEFAULT,'$titulo','$descricao','$local','$latitude','$longitude','$tipo', '$inicio', '$fim')";


if(mysqli_query($con,$sql_query))
{
echo "<h3> Data insertion success</h3>";
}
else
{
echo "Data insertion error...".mysqli_error($con);
}

mysqli_close($con);

?>
