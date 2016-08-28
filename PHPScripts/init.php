<?php

$db_name = "fabriciosilva";
$mysql_user = "fabricio.silva";
$mysql_pass = "timef";
$server_name = "mysql.dcc.ufmg.br";
$con = mysqli_connect($server_name, $mysql_user, $mysql_pass, $db_name);

if(!$con)
{
  echo "Connection Error...".mysqli_connect_error();
}
else
{
  //echo "<h3>Database connection success!</h3>";
}

?>
