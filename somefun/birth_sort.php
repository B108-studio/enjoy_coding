<?php
$startYear = 1;
$endYear = 9999;
$results = [];
$runDays = 0;
for($i=0;$i<9;$i++){
  $results[$i] = 0;
}
/**
		* 计算“生日数”程序  php的实现
		* 计算公元1年到100万年出生的人的生日数，分别为1～9，统计分类
		* 假设某人生日为1999.12.15，把出生年月日拆成个位数，一共得到8个数字
		* 把这些数相加，1+9+9+9+1+2+1+5=46
		* 再把46拆成4和6，4+6=10
		* 再把10拆分成1和0，1+0=1
		* 1就是这个人的生日数，将1记录下来
		* 打印出这一百万年中生日数为1～9的人分别有多少
		*/
$start =msectime();
for($curYear=$startYear;$curYear<=$endYear;$curYear++) {
			$yearSum = recurNum($curYear);
			for($m =1;$m<=12;$m++) {
				$mon = ($m>9)? $m/10+$m%10:$m;
				for($d=1;$d<=daysOfMonth($m,$curYear);$d++) {
					$day = ($d>9) ? recurNum(9):$d;
					$realNum = recurNum($yearSum+$mon+$day);
					resultsInc($realNum);
					$runDays++;
				}
		}
}
$end = msectime();
echo $end;
printf("在公元%d年1月1日到公元%d年12月31日的%d天里，出生数的分布为：\n",$startYear,$endYear,$runDays);
printf("\n程序运行时间为：%d ms \n",($end-$start));
var_dump($results);

//获取当前时间毫秒
function msectime()
{
    list($msec, $sec) = explode(' ', microtime());
    $msectime =  (float)sprintf('%.0f', (floatval($msec) + floatval($sec)) * 1000);
    return $msectime;
}
function recurNum($n){
 if($n>9) return recurNum(floor($n/10)+$n%10);

	return $n;
}
function resultsInc($index){
	//for($i=0;$i<=count($results)-1;$i++)
	if($index<0) return false;
	global $results;
	$results[--$index]++;
		
}
function isLeapYear($n){
	return (($n%4==0)&&($n%100!=0)) || ($n%400==0);
}
function daysOfMonth($month,$year){
	if($month<0 || $month>12 || $year<0) return "err";
	switch($month) {
		case 2:
			return isLeapYear($year)?29:28;
		case 4:
		case 6:
		case 9:
		case 11:
			return 30;
		default :
			return 31;
		}
}
?>
