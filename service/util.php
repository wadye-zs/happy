<?php  
class util{  
      
      
    public static function JSON($str){  
       // $json = json_encode($str);  
       //	return preg_replace("#\\\u([0-9a-f]+)#ie","iconv('UCS-2','UTF-8', pack('H4', '\\1'))",$json);  
 	//return preg_replace("/\u([0-9a-f]{4})/ie", "iconv('UCS-2', 'UTF-8', pack('H*', '$1'));", $json);

	$code = json_encode($str);  
    	return preg_replace("#\\\u([0-9a-f]{4})#ie", "iconv('UCS-2BE', 'UTF-8', pack('H4', '\\1'))", $code);  
    }  
      
}  