<?php

require_once('./DB.php');
require_once('./response.php');


$sm_id=isset($_GET['s_id'])?$_GET['s_id']:1;

$sql='select * from sellman where sm_id='.$sm_id;

try{
	$connect=Db::getInstance()->connect();
}catch(Exception $e){
	return Response::show(403,'mysql connect failed');
}

$result=mysql_query($sql,$connect);

while($test=mysql_fetch_assoc($result)){
	$tests[]=$test;
	//var_dump($test);
}
$smInfo=$tests[0];
?>

<!DOCTYPE html>  
<html>  
<head>  
    <meta charset="UTF-8">  
    <meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
    <title>商家简介</title>  
</head> 
<style type="text/css">
p{
	text-align:center;
	font-size:18px;
	padding-left:30px;
	padding-right:30px;
}
</style>
<body>
<br/><br/>

<h1 style="color:green;"><?php echo $smInfo[s_name];?></h1>

<hr style="height:10px;border:none;border-top:10px groove skyblue;" />
<hr style=" height:2px;border:none;border-top:2px dotted #185598;" />

<p ><?php echo $smInfo[introduce];?></p>

<hr style=" height:2px;border:none;border-top:2px dotted #185598;" /><br/><br/><br/>
<h2 style="text-align:center;color:#339966">活动介绍</h2>
<hr style="height:3px;border:none;border-top:3px double #339966;" />

<?php
	$sql='select * from active where sm_tel='.$smInfo[sm_tel];
	//echo $sql;
	$res=mysql_query($sql,$connect);
	while($test=mysql_fetch_assoc($res)){
		
?>

<h3 style="text-align:center;"><?php echo $test[active_name];?></h3>
<p><?php echo $test[act_introduce];?></p>

<?php
	$sqll='select * from act_photo where act_id='.$test[active_id];
	$pic=mysql_query($sqll,$connect);
	while($photo=mysql_fetch_assoc($pic)){
		//echo $photo[aphoto];
?>
<div style="text-align:center;">
<img src="<?php echo $photo[aphoto];?>" style="max-width:90%;" alt="图片-<?php echo $test[active_name];?>"/>
<?php }} ?>
</div>
<br/><br/><br/><br/>




















