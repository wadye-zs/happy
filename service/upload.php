<?php  
  
require_once('./response.php');
require_once('./DB.php');

class FileSave{

	//头像储存
	public function imgSave($id){
		//获取到临时文件  
		$file=$_FILES['file'];  

		//获取文件名  
		$fileName=$file['name'];  


		//echo "上传文件的路径及名称： ".$file['tmp_name']."<br>";
		//echo "上传文件的名称".$file['name']."<br>";
		//echo "上传文件的类型".$file['type']."<br>";
		//echo "上传文件的错误信息".$file['error']."<br>";
		//echo "上传文件的大小".$file['size']."<br>";
	
		if(!is_dir("images")){
			mkdir("images");
		}
		
		$str=time().substr($file['name'],-9,9);
		$path="images/".$str;
		//echo "文件名为： ".$str."<br>";
		//echo $path."<br>";
		if(move_uploaded_file($file['tmp_name'],$path)){
			echo "上传文件成功，文件名为： ".$str;
		}else{
			$str="head.png";
			$path="images/".$str;
			echo "文件上传失败";
		}

		$sql="UPDATE `sellman` SET  `sm_photo` =  'http://www.wadye.cn/prictice/".$path."' WHERE `sm_tel` =".$id;
		
		try{
			$connect=Db::getInstance()->connect();
		}catch(Exception $e){
			return Response::show(403,'mysql connect failed');
		}
		$result=mysql_query($sql,$connect);
			
		if($result){
			return Response::show(200,'Resign successful22222');
		}else{
			return Response::show(400,'Resign failed');
		}
	}

	//活动照片保存
	public function activityPhotpSave($id){

		if(!is_dir("ActivityPhotos")){
			mkdir("ActivityPhotos");
		}

		foreach($_FILES["files"]["error"] as $key => $error)	{
			if($error == UPLOAD_ERR_OK){
				$name=$_FILES["files"]["name"][$key];
				$tmp_name=$_FILES["files"]["tmp_name"][$key];
				$str=time().substr($name,-7,7);
				$path="ActivityPhotos/".$str;
				if(move_uploaded_file($tmp_name,$path)){
					//echo "上传文件成功，文件名为： ".$str;
				}else{
					echo "文件上传失败";
				}
				
				$sql="INSERT INTO  `act_photo` (`photo_id` ,`aphoto` ,`act_id`)VALUES (NULL , 'http://www.wadye.cn/prictice/".$path."',  '".$id."');";
				//echo $sql;
				try{
					$connect=Db::getInstance()->connect();
				}catch(Exception $e){
					return Response::show(403,'mysql connect failed');
				}
				$result=mysql_query($sql,$connect);
			
				if($result){
					//echo successful;
				}else{
					echo faild;
				}
			}
		}


	}
	
	//评论照片保存
	public function commentPhotoSave($id){

		if(!is_dir("CommentPhotos")){
			mkdir("CommentPhotos");
		}

		foreach($_FILES["files"]["error"] as $key => $error)	{
			if($error == UPLOAD_ERR_OK){
				$name=$_FILES["files"]["name"][$key];
				$tmp_name=$_FILES["files"]["tmp_name"][$key];
				$str=time().substr($name,-7,7);
				$path="CommentPhotos/".$str;
				if(move_uploaded_file($tmp_name,$path)){
					//echo "上传文件成功，文件名为： ".$str;
				}else{
					echo "文件上传失败";
				}
				
				$sql="INSERT INTO  `comment_photo` (`cp_id` ,`cphoto` ,`cp_comment_id`)VALUES (NULL , 'http://www.wadye.cn/prictice/".$path."',  '".$id."');";
				echo $sql;
				//echo $sql;
				try{
					$connect=Db::getInstance()->connect();
				}catch(Exception $e){
					return Response::show(403,'mysql connect failed');
				}
				$result=mysql_query($sql,$connect);
			
				if($result){
					//echo successful;
				}else{
					echo faild;
				}
			}
		}


	}

}










