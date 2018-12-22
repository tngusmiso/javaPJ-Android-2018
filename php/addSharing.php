<?php

    $Writer = $_GET['WRITER']; 
    $Duck = $_GET['DUCK']; 
    $Title = $_GET['TITLE']; 
    $Content = $_GET['CONTENT'];
    $Due = $_GET['DUE'];
    
    require_once('dbcon.php');

    $sql="INSERT INTO SHARING_BOARD (b_writer, b_dno, b_title, b_content, b_due_date) 
            Values ('$Writer','$Duck','$Title','$Content','$Due')";
        
    if(mysqli_query($con,$sql))
        $result= array("result" => "100"); // 100이면 게시글 업로드 성공
    else
        $result= array("result" => "50"); // 50이면 db 삽입 실패
    
    $output=json_encode($result);
    echo urldecode($output);
    mysqli_close($con);

 ?>