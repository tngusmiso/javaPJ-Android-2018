<?php

    $Id=$_GET['ID'];
    $Pwd=$_GET['PWD'];
    
    require_once('dbcon.php');

    $sql="SELECT * FROM USER WHERE u_id='$Id' AND u_pwd='$Pwd'";
    $res=mysqli_query($con,$sql);
    
    if($res->num_rows===0)
        $result =array("result"=>"false");
    else
        $result =array("result"=>"true");

    $output=json_encode($result);
    echo urldecode($output);
    $mysqli->close();

 ?>