<?php

//让crontab定时执行的脚本程序  */5 * * * * /usr/bin/php /data/www/app/cron.php

//获取test中的数据

require_once('./DB.php');
require_once('./file.php');

$sql="select * from test order by ls desc";

try{
	$connect=Db::getInstance()->connect();
}catch(Exception $e){
	file_put_contents('./logs/'.date('y-m-d').'.txt',$e->getMessage());
	return;
}
$result=mysql_query($sql,$connect);
$tests=array();
while($test=mysql_fetch_assoc($result)){
	$tests[]=$test;
}
$file=new File();
if($tests){
	$file->cacheData('index_cron_cache',$tests);
}else{
	file_put_contents('./logs/'.data('y-m-d').'txt','There Have Not Data');
}
return;








