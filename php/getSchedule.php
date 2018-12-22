<?php

    $Duck = $_GET['DUCK']; 

   require_once('dbcon.php');

    $sql="SELECT * FROM SCHEDULE WHERE s_dno='$Duck'";
    
    $res=mysqli_query($con,$sql);

    $D_row=array();

    while($row2=mysqli_fetch_array($res)){
        $row_info['num']=$row2['s_no'];
        $row_info['writer']=$row2['s_writer'];
        $row_info['cretime']=$row2['s_created_timestamp'];
        $row_info['duck']=$row2['s_dno'];
        $row_info['string']=$row2['s_string'];
        $row_info['location']=$row2['s_location'];
        $row_info['start']=$row2['s_start_datetime'];
        $row_info['end']=$row2['s_end_datetime'];
        $row_info['like']=$row2['s_like_count'];
        array_push($D_row,$row_info);
    }
   
    echo json_encode($D_row,JSON_UNESCAPED_UNICODE);
 ?>