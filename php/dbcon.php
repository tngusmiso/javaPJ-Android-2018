<?php
define('HOST','localhost'); //바꿀 필요 없음
define('USER','root');      //유저네임 (나도 root임)
define('PASS','k1jjang');    //디비 비밀번호
define('DB','DUCK'); //디비 이름

$con=mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');