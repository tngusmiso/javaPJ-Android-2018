<?php

    $Origin = $_GET['ORIGIN'];

   require_once('dbcon.php');

    if($Num=="")
        $sql="SELECT * FROM SHARING_COMMENT WHERE c_origin_bno='$Origin'";
    
    $res=mysqli_query($con,$sql);

    $D_row=array();

    while($row2=mysqli_fetch_array($res)){
        $row_info['num']=$row2['c_no'];
        $row_info['writer']=$row2['c_writer'];
        $row_info['comment']=$row2['c_comment'];
        $row_info['cretime']=$row2['c_created_timestamp'];
        $row_info['bno']=$row2['c_origin_bno'];
        $row_info['cno']=$row2['c_origin_cno'];
        array_push($D_row,$row_info);
    }
   
    echo json_encode($D_row,JSON_UNESCAPED_UNICODE);
 ?>