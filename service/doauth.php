<?php

require_once('./response.php');
require_once('./DB.php');
require_once('./upload.php');

class Account{
	
	/**
	* 账号检测方法
	* @param string $user 用户id
	* @param string $pass 用户密码
	* return bool 
	*/
	public function doAuth(){
		$user=isset($_GET['user'])?$_GET['user']:1;
		$pass=isset($_GET['pass'])?$_GET['pass']:123456;
		$sql="select c_password from customer where c_tel=".$user;
		
		try{
			$connect=Db::getInstance()->connect();
		}catch(Exception $e){
			return Response::show(403,'mysql connect failed');
		}
		$result=mysql_query($sql,$connect);

		while($test=mysql_fetch_assoc($result)){
			$tests=$test;
		}
		
		if($tests['c_password'] == $pass){
			return true;
		}else{
			return false;
		}
		//var_dump($tests);
		//return Response::show(200,'Loginin success',$tests);
		//if($tests){
		//	return Response::show(200,'successful',$tests);
		//}else{
		//	return Response::show(400,'failed',$tests);
		//}
	}
	
	
	/**
	* 卖家账号检测方法
	* @param string $user 用户id
	* @param string $pass 用户密码
	* return bool 
	*/
	public function SellmandoAuth(){
		$user=isset($_POST['user'])?$_POST['user']:123456789;
		$pass=isset($_POST['pass'])?$_POST['pass']:123456;
		$sql="select sm_password from sellman where sm_tel=".$user;
		
		try{
			$connect=Db::getInstance()->connect();
		}catch(Exception $e){
			return Response::show(403,'mysql connect failed');
		}
		$result=mysql_query($sql,$connect);

		while($test=mysql_fetch_assoc($result)){
			$tests=$test;
		}
		
		if($tests['sm_password'] == $pass){
			return true;
		}else{
			return false;
		}
		//var_dump($tests);
		//return Response::show(200,'Loginin success',$tests);
		//if($tests){
		//	return Response::show(200,'successful',$tests);
		//}else{
		//	return Response::show(400,'failed',$tests);
		//}
	}
	
	
	/**
	* 账号注册方法
	* @param string $user 用户id
	* @param string $pass 用户密码
	* return JSON 
	*/
	public function resign(){
		$user=isset($_GET['user'])?$_GET['user']:1;
		$pass=isset($_GET['pass'])?$_GET['pass']:12346;
		$name=isset($_GET['name'])?$_GET['name']:'unknow';
		
		$stat=self::checkId($user);
		if($stat){
			return Response::show(401,'Resign failed');
		}else{		
			$sql="insert into customer (c_tel,c_password,c_name) values('".$user."','".$pass."','".$name."')";
			
			try{
				$connect=Db::getInstance()->connect();
			}catch(Exception $e){
				return Response::show(403,'mysql connect failed');
			}
			$result=mysql_query($sql,$connect);
			
			if($result){
				return Response::show(200,'Resign successful');
			}else{
				return Response::show(400,'Resign failed');
			}
		}
	}
	
	/**
	* 卖家账号注册方法
	* @param string $user 用户id
	* @param string $pass 用户密码
	* return JSON 
	*/
	public function resignSell(){
		$id=isset($_POST['s_id'])?$_POST['s_id']:1;
		$pass=isset($_POST['pass'])?$_POST['pass']:12346;
		$name=isset($_POST['s_name'])?$_POST['s_name']:'unknow';
		$tel=isset($_POST['sm_tel'])?$_POST['sm_tel']:123456;
		$address=isset($_POST['sm_address'])?$_POST['sm_address']:123456;
		$keyword=isset($_POST['keyword'])?$_POST['keyword']:123456;
		$introduce=isset($_POST['introduce'])?$_POST['introduce']:123456;

		
		$stat=self::checkId2($tel);
		if($stat){
			return Response::show(401,'Resign failed');
		}else{		
			$sql="insert into sellman (sm_password,s_name,sm_tel,sm_address,keyword,introduce) values('".$pass."','".$name."','".$tel."','".$address."','".$keyword."','".$introduce."')";
			try{
				$connect=Db::getInstance()->connect();
			}catch(Exception $e){
				return Response::show(403,'mysql connect failed');
			}
			$result=mysql_query($sql,$connect);
			
			if($result){
				$save=new FileSave();
				$save->imgSave($tel);
				return Response::show(200,'Resign successful');
			}else{
				return Response::show(400,'Resign failed');
			}
		}
	}





	/**
	* 查重方法
	* @param string $user 用户id
	* return bool 
	*/
	public function checkId($user){

		$sql="select * from customer where c_tel=".$user;
		
		try{
			$connect=Db::getInstance()->connect();
		}catch(Exception $e){
			return Response::show(403,'mysql connect failed');
		}
		$result=mysql_query($sql,$connect);

		while($test=mysql_fetch_assoc($result)){
			$tests=$test;
		}
		if($tests['c_tel'] == $user){
			return true;
		}else{
			return false;
		}
	}

	/**
	* 查重方法2
	* @param string $user 用户id
	* return bool 
	*/
	public function checkId2($tel){

		$sql="select * from sellman where sm_tel=".$tel;
		
		try{
			$connect=Db::getInstance()->connect();
		}catch(Exception $e){
			return Response::show(403,'mysql connect failed');
		}
		$result=mysql_query($sql,$connect);

		while($test=mysql_fetch_assoc($result)){
			$tests=$test;
		}
		if($tests['sm_tel'] == $tel){
			return true;
		}else{
			return false;
		}
	}
	
	/*
	*	更新商家信息
	*/
	public function update($tel){
		$address=isset($_POST['sm_address'])?$_POST['sm_address']:1234567;
		$keyword=isset($_POST['keyword'])?$_POST['keyword']:1234567;
		$introduce=isset($_POST['introduce'])?$_POST['introduce']:1234567;
		
		$sql="UPDATE  `happy`.`sellman` SET  sm_address =  '".$address."',keyword = '".$keyword."',introduce = '".$introduce."' WHERE  `sellman`.`sm_tel` =".$tel;
		echo $sql;
		try{
			$connect=Db::getInstance()->connect();
		}catch(Exception $e){
			return Response::show(403,'mysql connect failed');
		}
		$result=mysql_query($sql,$connect);
		
		if($result){
			return Response::show(200,'update successful');
		}else{
			return Response::show(400,'update failed');
		}
	}
}




