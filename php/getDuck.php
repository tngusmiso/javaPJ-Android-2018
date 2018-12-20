<?php

    $Genre=$_GET['GENRE'];
    $Parent=$_GET['PARENT'];

    require_once('dbcon.php');

    if($Parent=="")
        $sql="SELECT * FROM DUCK_LIST WHERE d_genre='$Genre' AND d_type=0";
    else
        $sql="SELECT * FROM DUCK_LIST WHERE d_type=1 AND d_parent_no='$Parent'";
    
    $res=mysqli_query($con,$sql);

    $D_row=array();

   
    while($row2=mysqli_fetch_array($res)){
        $row_duck['num']=$row2['d_no'];
        $row_duck['name']=$row2['d_name'];
        array_push($D_row,$row_duck);
    }
   
    echo json_encode($D_row,JSON_UNESCAPED_UNICODE);
 ?>