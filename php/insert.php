<?php
    $conn = mysqli_connect("localhost", "root", "k1jjang", "DUCK");
    $sql  = "
        INSER INTO USER (
            u_id,
            u_pwd,
            u_name,
            u_email,
            duck_type, duck_no,
            u_interest1, u_interest2, u_interest3
        ) VALUES (
            'k1miso',
            '1234',
            '또롱',
            'k1miso@naver.com',
            1, 1,
            3, 4, 5
        )";
    $result = mysqli_query($conn, $sql);
    if($result === false){
        echo mysqli_error($conn);
    }
?>