<?php
include('funcionapi.php');
$valor=$_GET["Dato"];
if($result=obtenerResultado("SELECT*FROM `Numeros` WHERE Dato='$valor'")){
    while($fila=$result->fetch_array(MYSQLI_NUM)){
        echo json_encode($fila);
    }
}




?>