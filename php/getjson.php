<?php
  $connect = mysqli_connect("localhost", "root", "k1jjang", "DUCK");
  
  $Id=$_GET['ID'];    //url 뒤에 받아온 값
  
  $sql="SELECT * from USER"; 
  $result = mysqli_query($connect,$sql);
  
  $D_row=array(); //배열 생성
  
  while($row = mysqli_fetch_array($result)){
    $row_array['no']=$row['u_no'];
    $row_array['id']=$row['u_id'];
    $row_array['pwd']=$row['u_pwd'];
    $row_array['name']=$row['u_name'];
    $row_array['email']=$row['u_email'];
    $row_array['duck_type']=$row['duck_type'];
    $row_array['duck_no']=$row['duck_no'];
    $row_array['interest1']=$row['u_interest1'];
    $row_array['interest2']=$row['u_interest2'];
    $row_array['interest3']=$row['u_interest3'];
    array_push($D_row,$row_array);  //배열($D_row)에 원소($row_array) 추가 = 2차원 배열
  }
 
echo json_encode($D_row,JSON_UNESCAPED_UNICODE);

mysqli_close($connect);