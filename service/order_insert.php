<?php
	require_once('./response.php');
	require_once('./DB.php');
	
	$time=isset($_GET['time'])?$_GET['time']:'2017-01-01';
	$peo_num=isset($_GET['peo_num'])?$_GET['peo_num']:1;
	$sm_tel=isset($_GET['sm_tel'])?$_GET['sm_tel']:123456789;
	$c_tel=isset($_GET['c_tel'])?$_GET['c_tel']:1;
	
	$sql="insert into s_c_order (id,sm_tel,c_tel,time,peo_num) values (NULL,'".$sm_tel."','".$c_tel."','".$time."','".$peo_num."')";
	try{
		$connect=Db::getInstance()->connect();
	}catch(Exception $e){
		return Response::show(403,'mysql connect failed');
	}
	$result=mysql_query($sql,$connect);
		
	if($result){
		return Response::show(200,'insert successful');
	}else{
		return Response::show(400,'insert failed');
	}
?>