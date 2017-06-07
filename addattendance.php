<?php
//COLLECT DATAS
$studid=$_GET["studid"];
$date=$_GET["date"];
$period=$_GET["hour"];
//CONNECT TO DB

$connection = mysqli_connect("mysql.hostinger.in","u870631422_tcs","12345678","u870631422_tcs");
//DEFINE QUERY

if ($period='period1')
{

$sql="update attendance set A1='P' where studid='$studid' and date='$date'" ;
mysqli_query($connection,$sql);
$sql="select * from attendance where id='$id' and date='$date'" ;
$result=mysqli_query($connection,$sql);
while($row=mysqli_fetch_assoc($result))
{
	if($row['A1'] != 'P')
	{
		$studid=$row['studid'];
		$sql="update attendance set A1='A' where A1='NM' and date='$date'" ;
	}
}
}
elseif ($period='period2')
{
$sql="update attendance set A2='P' where studid='$studid' and date='$date'" ;
mysqli_query($connection,$sql);
$sql="select * from attendance where id='$id' and date='$date'" ;
$result=mysqli_query($connection,$sql);
while($row=mysqli_fetch_assoc($result))
{
	if($row['A2'] != 'P')
	{
		$studid=$row['studid'];
		$sql="update attendance set A2='A' where A2='NM' and date='$date'" ;
	}
}
}
elseif ($period='period3')
{
$sql="update attendance set A3='P' where studid='$studid' and date='$date'" ;
mysqli_query($connection,$sql);
$sql="select * from attendance where id='$id' and date='$date'" ;
$result=mysqli_query($connection,$sql);
while($row=mysqli_fetch_assoc($result))
{
	if($row['A3'] != 'P')
	{
		$studid=$row['studid'];
		$sql="update attendance set A3='A' where A3='NM' and date='$date'" ;
	}
}
}
elseif ($period='period4')
{
$sql="update attendance set A4='P' where studid='$studid' and date='$date'" ;
mysqli_query($connection,$sql);
$sql="select * from attendance where id='$id' and date='$date'" ;
$result=mysqli_query($connection,$sql);
while($row=mysqli_fetch_assoc($result))
{
	
	if($row['A4'] != 'P')
	{
		$studid=$row['studid'];
		$sql="update attendance set A4='A' where A4='NM' and date='$date'" ;
	}
}
}
elseif ($period='period5')
{
$sql="update attendance set A5='P' where studid='$studid' and date='$date'" ;
mysqli_query($connection,$sql);
$sql="select * from attendance where id='$id' and date='$date'" ;
$result=mysqli_query($connection,$sql);
while($row=mysqli_fetch_assoc($result))
{
	
	if($row['A5'] != 'P')
	{
		$studid=$row['studid'];
		$sql="update attendance set A5='A' where A5='NM' and date='$date'" ;
	}
}
}
elseif ($period='period6')
{
$sql="update attendance set A6='P' where studid='$studid' and date='$date'" ;
mysqli_query($connection,$sql);
$sql="select * from attendance where id='$id' and date='$date'" ;
$result=mysqli_query($connection,$sql);
while($row=mysqli_fetch_assoc($result))
{
	
	if($row['A6'] != 'P')
	{
		$studid=$row['studid'];
		$sql="update attendance set A6='A' where A6='NM' and date='$date'" ;
	}
}
}
elseif ($period='period7')
{
$sql="update attendance set A7='P' where studid='$studid' and date='$date'" ;
mysqli_query($connection,$sql);
$sql="select * from attendance where id='$id' and date='$date'" ;
$result=mysqli_query($connection,$sql);
while($row=mysqli_fetch_assoc($result))
{
	
	if($row['A7'] != 'P')
	{
		$studid=$row['studid'];
		$sql="update attendance set A7='A' where A7='NM' and date='$date'" ;
	}
}
}
//EXECUTE QUERY
$result = mysqli_query($connection, $sql) or die("Error in Selecting " . mysqli_error($connection));

?>