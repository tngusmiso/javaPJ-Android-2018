<?php
    $con = mysqli_connect("localhost","root","k1jjang","DUCK");

    $id = $_POST["u_id"];
    $pwd = $_POST["u_pwd"];
    $name = $_POST["u_name"];
    $email = $_POST["u_email"];
    $duck_type = $_POST["duck_type"];
    $duck_no = $_POST["duck_no"];
    
    
    $sql = "INSERT INTO USER (
                u_id, 
                u_pwd, 
                u_name, 
                u_email, 
                duck_type, 
                duck_no
            ) VALUES (
                '$u_id',
                '$u_pwd',
                '$u_name',
                '$u_email',
                '$duck_type',
                '$duck_no')";

    $result = mysqli_query($con, $sql);
    if($result === false){
        echo mysqli_error($con);
    }
?>