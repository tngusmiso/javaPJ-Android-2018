<?php

    $Num=$_GET['NUM'];

    require_once('dbcon.php');

    $sql="SELECT * FROM DUCK_LIST WHERE d_no='$Num'";
    
    $res=mysqli_query($con,$sql);

    $D_row=array();

    while($row2=mysqli_fetch_array($res)){
        $row_info['num']=$row2['d_no'];
        $row_info['name']=$row2['d_name'];
        $row_info['follower']=$row2['d_follower_count'];
        $row_info['link']=$row2['d_link'];
        $row_info['type']=$row2['d_type'];
        $row_info['parent']=$row2['d_parent_no'];
        $row_info['pic']=$row2['d_photo_path'];
        
        array_push($D_row,$row_info);
    }
   
    echo json_encode($D_row,JSON_UNESCAPED_UNICODE);
 ?>