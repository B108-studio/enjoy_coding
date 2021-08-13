package somefun;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
/**
* 计算“生日数”程序 
* 计算公元1年到100万年出生的人的生日数，分别为1～9，统计分类
* 假设某人生日为1999.12.15，把出生年月日拆成个位数，一共得到8个数字
* 把这些数相加，1+9+9+9+1+2+1+5=46
* 再把46拆成4和6，4+6=10
* 再把10拆分成1和0，1+0=1
* 1就是这个人的生日数，将1记录下来
* 打印出这一百万年中生日数为1～9的人分别有多少
*/
public class BrithAnalysis {
	private static long runDays = 0L;
	private static int[] results = new int[9];
	private static int startYear = 1;
	private static int endYear = 999999;
	public static void main(String[] args) {
		//开始计时
		System.out.println("程序已启动...");
		long start = System.currentTimeMillis();

		calc();
		System.out.printf("在公元%d年1月1日到公元%d年12月31日的%d天里，出生数的分布为：\n",startYear,endYear,runDays);
		// 结束计时
		long end = System.currentTimeMillis();
		System.out.printf("\n程序运行时间为：%dms",(end-start));
		// 校验是否有遗漏
		int signNum = 0;
		for(int i=0;i<results.length;i++ ){
			System.out.printf("出生数%d: ",i+1);
			System.out.printf("%d\n\n",results[i]);
			signNum += results[i];
		}
		if(signNum!=runDays) {
			System.out.println("\n存在漏算天数，请检测程序算法");
		}
	}
	
	static void calc() {
		if(startYear<1 || startYear>endYear) return ;
		for(int year=startYear;year<=endYear;year++) {
//			System.out.println("--------Now in year "+year);
			LocalDate date = LocalDate.of(year, 1, 1);
			// 计算年的每位数相加
			int yearSum = recurNum(year);
			int totalDays = date.isLeapYear()?366:365;
			// 先减一天，循环开始就再加上
			date = date.plus(-1,ChronoUnit.DAYS);
			do{
				runDays++;
				date = date.plus(1,ChronoUnit.DAYS);
				int realNum = recurNum(yearSum+date.getMonthValue()+date.getDayOfMonth());
				realNum = recurNum(realNum);
				resultsInc(realNum);
			}while(date.getDayOfYear() < totalDays);
		}
	}
	
	static int recurNum(int n) {
		if (n > 9) return recurNum(n/10 + n%10); 
		
		return n;
	}

	static void resultsInc(int realNum) {
		results[realNum-1]++;
	}
}
