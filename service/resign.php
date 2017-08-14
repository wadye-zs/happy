<?php
require_once('./doauth.php');

$resign=new Account();
$state=$resign->resign();
