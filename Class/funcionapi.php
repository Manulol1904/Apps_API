<?php
header('Content-Type: text/html;charset=utf-8');
function obtenerResultado($qwery){
    $mysqli=new mysqli("localhost","root","","Myapi");
    if($mysqli->connect_errno){
        printf("Malandro programador hijueputa");
        exit();
    }
    if($mysqli->multi_query($qwery)){
        return $mysqli->store_result();

    }
    $mysqli->close();
}
?>


