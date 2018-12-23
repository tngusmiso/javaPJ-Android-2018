<?php

    $Duck = $_GET['DUCK']; 
    $Num = $_GET['NUM']; 

   require_once('dbcon.php');

    if($Num=="")
        $sql="SELECT * FROM SHARING_BOARD WHERE b_dno='$Duck'";
    else
        $sql="SELECT * FROM SHARING_BOARD WHERE b_no='$Num'";
    
    $res=mysqli_query($con,$sql);

    $D_row=array();

    while($row2=mysqli_fetch_array($res)){
        $row_info['num']=$row2['b_no'];
        $row_info['writer']=$row2['b_writer'];
        $row_info['cretime']=$row2['b_created_timestamp'];
        $row_info['duck']=$row2['b_dno'];
        $row_info['title']=$row2['b_title'];
        $row_info['content']=$row2['b_content'];
        $row_info['due']=$row2['b_due_date'];
        $row_info['comment']=$row2['b_comment_count'];
        array_push($D_row,$row_info);
    }
   
    echo json_encode($D_row,JSON_UNESCAPED_UNICODE);
 ?>