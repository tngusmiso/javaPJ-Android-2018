<?php

    $Id=$_GET['ID'];
    $Num=$_GET['NUM'];

    require_once('dbcon.php');

    if($Id != "")
        $sql="SELECT * FROM USER WHERE u_id='$Id'";
    else if($Num != "")
        $sql="SELECT * FROM USER WHERE u_no='$Num'";
    
    $res=mysqli_query($con,$sql);

    $D_row=array();

    while($row2=mysqli_fetch_array($res)){
        $row_info['num']=$row2['u_no'];
        $row_info['id']=$row2['u_id'];
        $row_info['name']=$row2['u_name'];
        $row_info['email']=$row2['u_email'];
        $row_info['duck']=$row2['duck_no'];
        $row_info['itrst1']=$row2['u_interest1'];
        $row_info['itrst2']=$row2['u_interest2'];
        $row_info['itrst3']=$row2['u_interest3'];
        array_push($D_row,$row_info);
    }
   
    echo json_encode($D_row,JSON_UNESCAPED_UNICODE);
 ?>