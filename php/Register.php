<?php

    $Id = $_GET['ID']; 
    $Pw = $_GET['PWD']; 
    $Name = $_GET['NAME']; 
    $Email = $_GET['EMAIL'];
    $Type = $_GET['TYPE']; 
    $Duck = $_GET['DUCK']; 
    $Interest1 = $_GET['I1']; 
    $Interest2 = $_GET['I2']; 
    $Interest3 = $_GET['I3']; 
    
    
    require_once('dbcon.php');

    $sql = "SELECT * FROM USER WHERE u_id = '$Id'";
    $res =mysqli_query($con,$sql);

    if($res->num_rows ===0){ 
        $sql="INSERT INTO USER (u_id, u_pwd, u_name, u_email, duck_type, duck_no, u_interest1, u_interest2, u_interest3) 
            Values ('$Id','$Pw','$Name','$Email','$Type','$Duck','$Interest1','$Interest2','$Interest3')";
        
        if(mysqli_query($con,$sql))
            $result= array("result" => "100");//100이면 로그인 성공
        else
            $result= array("result" => "50"); // 50이면 db 삽입 실패
    }
    else
        $result= array("result" => "10");//10이면 ID 중복
    
    $output=json_encode($result);
    echo urldecode($output);
    mysqli_close($con);

 ?>