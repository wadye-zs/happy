<?php
	
class Db{
	
	private static $_instance;//单例模式，静态参数
	private static $_connectSource;
	private $_dbConfig=array(
		'host'=>'127.0.0.1',
		'user'=>'root',
		'password'=>'happyplay',
		'database'=>'happy',
	);
	
	//单例模式 构造函数不能被外部访问
	private function __construct(){
		
	}
	
	//外部入口
	public static function getInstance(){
		if(!(self::$_instance instanceof self)){
			self::$_instance=new self();
		}
		return self::$_instance;
	}
	
	//数据库链接
	public function connect(){
		if(!self::$_connectSource){
			self::$_connectSource=mysql_connect($this->_dbConfig['host'],$this->_dbConfig['user'],
						$this->_dbConfig['password']);
			if(!self::$_connectSource){
				throw new Exception('mysql connect error'.mysql_error());
				//die('mysql connect error'.mysql_error());
			}
		
			mysql_select_db($this->_dbConfig['database'],self::$_connectSource);
			mysql_query("set names UTF8",self::$_connectSource);
		}
		return self::$_connectSource;
	}
}

/*$connect=Db::getInstance()->connect();

$sql="select * from test";
$result=mysql_query($sql,$connect);
echo mysql_num_rows($result);*/








