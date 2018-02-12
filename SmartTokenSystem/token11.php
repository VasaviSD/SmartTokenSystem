<?php
session_start();
//$var_value=$_SESSION["User_ID"];
//$var_value=$_POST["User_ID"];
//$status=$_POST["withdraw"];
//echo "$var_value ----- $status";
$var_value="USR001";
$status="withdraw";
$x='w';
//if($status)
//{
	
//}
$conn=mysqli_connect("localhost","root","") or die("cant connect");
$data=mysqli_select_db($conn,"smarttokensystem") or die("cant connect to database");
$token=rand(000,999) ;
$a=date("y/m/d");
date_default_timezone_set('Asia/Kolkata');
$t=date("h:i:sa");
$q="insert into token values('$token','$a','$t','y','$x','$var_value')";
$res=mysqli_query($conn,$q) or die("cannot insert the values");
if($res)
{
	echo "Your token number is ";
	echo"$token \n";
	//echo"<html><body><a href=\"http://localhost/miniproject/time.php\"></a></body></html>";
}
?>
