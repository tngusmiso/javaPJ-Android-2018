<?php

  $Genre1=$_GET['GENRE1'];
  $Genre2=$_GET['GENRE2'];
  $Genre3=$_GET['GENRE3'];
    
    require_once('dbcon.php');

    if($Genre1=="")
      $sql="SELECT * FROM DUCK_GENRE";
    else
      $sql="SELECT * FROM DUCK_GENRE WHERE genre_no='$Genre1' OR genre_no='$Genre2' OR genre_no='$Genre3'";

    $res=mysqli_query($con,$sql);

    $D_row=array();

    while($row2=mysqli_fetch_array($res)){
        $row_wish['num']=$row2['genre_no'];
        $row_wish['genre']=$row2['genre_string'];
      array_push($D_row,$row_wish);
    }
    echo json_encode($D_row,JSON_UNESCAPED_UNICODE);
 ?>