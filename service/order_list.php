<?php
	require_once('./response.php');
	require_once('./DB.php');
	require_once('./file.php');
	
	$c_tel=isset($_GET['c_tel'])?$_GET['c_tel']:1;
	$sql="select * from s_c_order,sellman where c_tel=".$c_tel." and sellman.sm_tel=s_c_order.sm_tel";
	$tests=array();
	try{
		$connect=Db::getInstance()->connect();
	}catch(Exception $e){
		return Response::show(403,'mysql connect failed');
	}
	$result=mysql_query($sql,$connect);

	while($test=mysql_fetch_assoc($result)){
		$tests[]=$test;
	}
	
if($tests){
	return Response::show(200,'successful',$tests);
}else{
	return Response::show(400,'failed',$tests);
}
	
?>