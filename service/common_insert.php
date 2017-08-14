<?php
	require_once('./response.php');
	require_once('./DB.php');
	require_once('./upload.php');
	
	$c_tel=isset($_POST['c_tel'])?$_POST['c_tel']:1;
	$s_tel=isset($_POST['s_tel'])?$_POST['s_tel']:123456789;
	$comment=isset($_POST['comment'])?$_POST['comment']:'unknow';
	$order_id=isset($_POST['id'])?$_POST['id']:80;
	$grade=isset($_GET['grade'])?$_GET['grade']:5;
	
	$sql="INSERT INTO  `happy`.`comment` (
	`co_id` ,
	`co_sellman_id` ,
	`co_comment` ,
	`co_custom_id`
	)
	VALUES (
	NULL ,  '".$s_tel."',  '".$comment."',  '".$c_tel."'
	);";
	
	try{
		$connect=Db::getInstance()->connect();
	}catch(Exception $e){
		return Response::show(403,'mysql connect failed');
	}
	$result=mysql_query($sql,$connect);
	
	//获取数据库上一条存储信息的id
	$comment_id=mysql_insert_id();
	
	$sql="UPDATE  `happy`.`s_c_order` SET  `comment_id` =  '".$comment_id."' WHERE  `s_c_order`.`id` =".$order_id.";";
	//echo $sql;
	mysql_query($sql,$connect);
	
	$finalGrade=3;
	$sql="select grade from sellman where sm_tel='".$s_tel."';";
	$result2=mysql_query($sql,$connect);
	while($test=mysql_fetch_assoc($result2)){
			$tests=$test;
	}
	$finalGrade=($tests['grade']+$grade)/2;
		
	$sql="UPDATE  `happy`.`sellman` SET  `grade` =  '".$finalGrade."' WHERE  `sellman`.`sm_tel` =".$s_tel.";";
	mysql_query($sql,$connect);
	if($result){
		$save=new FileSave();
		$save->commentPhotoSave($comment_id);
		return Response::show(200,'insert successful');
	}else{
		return Response::show(400,'insert failed');
	}
?>