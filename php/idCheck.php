<?php

    $Id=$_GET['ID'];
    
    require_once('dbcon.php');

    $sql="SELECT * FROM USER WHERE u_id='$Id'";
    $res=mysqli_query($con,$sql);
    
    if($res->num_rows===0)
        $result =array("result"=>"false");  // 중복 아이디가 없으면 false
    else
        $result =array("result"=>"true");   // 아이디가 중복이면 true

    $output=json_encode($result);
    echo urldecode($output);
    $mysqli->close();

 ?>