<?php

    $Num = $_GET['NUM'];
    
    require_once('dbcon.php');

    $sql = "SELECT * FROM SHARING_BOARD WHERE b_no = '$Num'";
    $res =mysqli_query($con,$sql);

    if($res->num_rows ===0)
        $result= array("result" => "0");//0이면 해당 번호 없음
        
    else{
        if(mysqli_query($con,$sql)){
            $result= array("result" => "100");//100이면 삭제 성공
            $del = "DELETE FROM SHARING_BOARD WHERE b_no='$Num'";
            $res =mysqli_query($con,$del);
        }
        else
            $result= array("result" => "50"); // 50이면 db 삭제 실패
    }
    
    $output=json_encode($result);
    echo urldecode($output);
    mysqli_close($con);
 ?>