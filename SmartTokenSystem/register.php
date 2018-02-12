<?php
$name=$_POST["user"];
$mob_no=$_POST["contact"];
$passwd=$_POST["password"];
//echo $name, $mob_no, $passwd;
$userID="USR";
if(file_exists('count.txt'))
{
	$fptr=fopen('count.txt','r');
	$data=fread($fptr,filesize('count.txt')+1);
	fclose($fptr);
	$data=$data+1;
	$count=$data;
	//echo $data;
	$fptr=fopen('count.txt','w');
	fwrite($fptr,$data);
	fclose($fptr);
}
else{
	$fptr=fopen('count.txt','w');
	fwrite($fptr,1);
	fclose($fptr);
}
$conn=mysqli_connect("localhost","root","");
$data=mysqli_select_db($conn,"SmartTokenSystem") or die("database doesn't exist");

$ID=$userID.$count;
$q="insert into customer values('$name','$passwd','$mob_no','$ID')";

$query=mysqli_query($conn,$q);
if($query)
{
	echo"Successfully Registered!! Your UserID is $ID";
	//echo"<html><body><a href=\"http://localhost/miniproject/login.html>Click here to continue></a></body></html>";
}
else
{
	echo "Try again please";
}
mysqli_close($conn);
?> 