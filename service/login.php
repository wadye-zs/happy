<?php
require_once('./doauth.php');


$login=new Account();
$state=$login->doAuth();

if($state){
	return Response::show(200,'Login success');
}else{
	return Response::show(400,'Login failed');
}