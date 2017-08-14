<?php
	require_once('./response.php');
	require_once('./DB.php');
	require_once('./doauth.php');
	
	
	$tel = isset($_GET['sm_tel'])?$_GET['sm_tel']:123;
	$u = new Account();
	$u->update($tel);
?>