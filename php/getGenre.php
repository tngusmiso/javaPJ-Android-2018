<?php
    require_once('dbcon.php');

    $sql="SELECT * FROM DUCK_GENRE";

    $res=mysqli_query($con,$sql);

    $D_row=array();

    while($row2=mysqli_fetch_array($res)){
        $row_wish['num']=$row2['genre_no'];
        $row_wish['genre']=$row2['genre_string'];
      array_push($D_row,$row_wish);
    }
    echo json_encode($D_row,JSON_UNESCAPED_UNICODE);
 ?>