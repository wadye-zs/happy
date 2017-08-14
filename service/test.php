<?php
require_once('./file.php');	
$data=array(
	'id'=>1,
	'name'=>'wadye',
	'type'=>array(4,5,6),
	'test'=>array(1,2,3=>array(333,'aaaa'),),
);
$file=new File();
if($file->cacheData('index_mk_cache',null)){
	echo "success";
}else{
	echo "error";
}