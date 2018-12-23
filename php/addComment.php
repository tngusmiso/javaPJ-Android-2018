<?php

    $Writer = $_GET['WRITER'];
    $Comment = $_GET['COMMENT'];
    $Origin_b = $_GET['ORG_B'];
    $Origin_c = $_GET['ORG_C'];
    
    require_once('dbcon.php');

    $sql="INSERT INTO SHARING_COMMENT (c_writer,c_comment, c_origin_bno, c_origin_cno) 
            Values ('$Writer','$Comment','$Origin_b','$Origin_c')";
        
    if(mysqli_query($con,$sql)){
        $result= array("result" => "100"); // 100이면 댓글 업로드 성공
        $query = "UPDATE SHARING_BOARD SET b_comment_count = b_comment_count + 1 WHERE b_no='$Origin_b'";
            mysqli_query($con,$query);
    }
    else
        $result= array("result" => "50"); // 50이면 db 삽입 실패
    
    $output=json_encode($result);
    echo urldecode($output);
    mysqli_close($con);

 ?>