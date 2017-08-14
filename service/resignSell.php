<?php
require_once('./doauth.php');
require_once('./upload.php');

$resign=new Account();
$state=$resign->resignSell();
