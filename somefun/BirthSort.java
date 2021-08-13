package somefun;

public class BirthSort {

	private static final int startYear = 1;
	private static final int endYear = 999999;
	private static int[] results = new int[9];
	private static long runDays = 0L;
	public static void main(String[] args) {
		/**
		* 计算“生日数”程序  不借助日期类的实现
		* 计算公元1年到100万年出生的人的生日数，分别为1～9，统计分类
		* 假设某人生日为1999.12.15，把出生年月日拆成个位数，一共得到8个数字
		* 把这些数相加，1+9+9+9+1+2+1+5=46
		* 再把46拆成4和6，4+6=10
		* 再把10拆分成1和0，1+0=1
		* 1就是这个人的生日数，将1记录下来
		* 打印出这一百万年中生日数为1～9的人分别有多少
		*/
		//开始计时
		System.out.println("程序已启动...");
		long start = System.currentTimeMillis();
		for(int curYear=startYear;curYear<=endYear;curYear++) {
			int yearSum = recurNum(curYear);
			for(int m =1;m<=12;m++) {
				int mon = (m>9)? m/10+m%10:m;
				for(int d=1;d<=daysOfMonth(m,curYear);d++) {
					int day = (d>9) ? recurNum(9):d;
					int realNum = recurNum(yearSum+mon+day);
					resultsInc(realNum);
					runDays++;
				}
			}
		}
		System.out.printf("在公元%d年1月1日到公元%d年12月31日的%d天里，出生数的分布为：\n",startYear,endYear,runDays);
		int signNum = 0;
		for(int i=0;i<results.length;i++ ){
			System.out.printf("出生数%d: ",i+1);
			System.out.printf("%d\n\n",results[i]);
			signNum += results[i];
		}
		if(signNum!=runDays) {
			System.out.println("\n存在漏算天数，请检测程序算法");
		}
		// 结束计时
		long end = System.currentTimeMillis();
		System.out.printf("\n程序运行时间为：%dms",(end-start));
	}
	public static int daysOfMonth(int month,int year) {
		switch(month) {
		case 2:
			return isLeapYear(year)? 29:28;
		case 4:
		case 6:
		case 9:
		case 11:
			return 30;
		default :
			return 31;
		}
	}
	public static boolean isLeapYear(int year) {
		return ((year & 3) == 0) && ((year % 100) != 0 || (year % 400) == 0);
	}
	static void resultsInc(int realNum) {
		results[realNum-1]++;
	}
	public static int recurNum(int n) {
		if(n>9) return recurNum(n/10 + n%10);
		
		return n;
	}
}
