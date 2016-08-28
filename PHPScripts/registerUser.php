<?php

require "init.php";



$nome = $_POST["nome"];
$usuario = $_POST["usuario"];
$senha = $_POST["senha"];


$sql_query = "insert into usuarios values(DEFAULT,'$nome','$usuario','$senha')";



if(mysqli_query($con,$sql_query))
{

}
else
{
//echo "Data insertion error...".mysqli_error($con);
}




mysqli_close($con);
?>
