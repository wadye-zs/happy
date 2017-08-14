<?php
//http://127.0.0.1/prictice/list.php?page-=1&pagesize=12

require_once('./response.php');
require_once('./DB.php');
require_once('./file.php');

$page=isset($_GET['page'])?$_GET['page']:1;
$pageSize=isset($_GET['pagesize'])?$_GET['pagesize']:6;
if(!is_numeric($page)||!is_numeric($pageSize)){
	echo $pageSize;
	return Response::show(401,'error');
}

$offset=($page-1)*$pageSize;
$sql="select * from sellman";

$cache=new File();
$tests=array();
if(!$tests=$cache->cacheData('index_mk_cache'.$page.'-'.$pageSize)){
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
		$cache->cacheData('index_mk_cache'.$page.'-'.$pageSize,$tests,1200);
	}
}
if($tests){
	return Response::show(200,'successful',$tests);
}else{
	return Response::show(400,'failed',$tests);
}







