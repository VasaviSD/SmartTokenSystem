<?php 
$conn=mysqli_connect("localhost","root","") or die("cant connect");
$data=mysqli_select_db($conn,"smarttokensystem") or die("cant connect to database");
$que="select * from timetrack2";
$query=mysqli_query($conn,$que) or die("cannot select");
$result=mysqli_affected_rows($conn);
//echo "$result";
$t1='5';
$s='y';
if($result==NULL)
{	
$q1="insert into timetrack2 values('$t1','$s')";
$query1=mysqli_query($conn,$q1) or die("null");
$t1=date("i:s",$t1*60);
echo "your waiting time is $t1 ";
}
else
{
	$result=$result+1;
	//$second=strtotime("$t1");
	//$s1=$second*$result;
	//$t=explode($t1,":");
	$wait=$result*$t1*60;
	$t1=date("i:s",$wait);
	//echo"$t1";
	$q1="insert into timetrack2 values('$t1','$s')";
$query1=mysqli_query($conn,$q1) or die("null");
echo "your waiting time is $t1";
}
?>