<?php
$userid=$_POST["userID"];
$password=$_POST["password"];
//echo "Username : $userid \n Password : $password ";
$conn=mysqli_connect("localhost","root","","SmartTokenSystem") or die("Oops !! Something went wrong, cannot establish connection");
$data=mysqli_select_db($conn,"SmartTokenSystem") or die("Database doesn't exists");
$log="select * from Customer where UserID='$userid' and Password='$password'";
$result=mysqli_query($conn,$log);
$count=mysqli_num_rows($result);
if($count)
{
	echo "Login Successful";
	//echo"<html><head><title>Flat HTML5/CSS3 Login Form</title></head>";
	//echo"<body><a href=\"../../index.html\"> Hello $user, Click here to continue</a></body></html>";
} 
else
{
	echo"Login Failed";
	
}	