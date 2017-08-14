<?php

require_once('./response.php');
require_once('./DB.php');
require_once('./upload.php');

$sm_tel=isset($_POST['sm_tel'])?$_POST['sm_tel']:123456;
$act_name=isset($_POST['act_name'])?$_POST['act_name']:123;
$act_introduce=isset($_POST['act_introduce'])?$_POST['act_introduce']:1;

if(!isset($_POST['act_introduce'])){
	return Response::show(400,'act_introduce is empty');
}

$sql="INSERT INTO `active` (
`active_id` ,
`active_name` ,
`act_introduce` ,
`sm_tel`
)
VALUES (
NULL ,  '".$act_name."',  '".$act_introduce."',  '".$sm_tel."'
);";


try{
	$connect=Db::getInstance()->connect();
}catch(Exception $e){
	return Response::show(403,'mysql connect failed');
}
$result=mysql_query($sql,$connect);

$act_id=mysql_insert_id();

if($result){
	$save=new FileSave();
	//echo act.$act_id;
	$save->activityPhotpSave($act_id);
	return Response::show(200,'Resign successful');
}else{
	return Response::show(400,'Resign failed');
}





