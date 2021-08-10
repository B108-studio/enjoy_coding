package somefun;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BrithAnalysis {
	private static int runDays = 0;
	
	public static void main(String[] args) {
		//开始计时
		long start = System.currentTimeMillis();
		int startYear = 1;
		int endYear = 1000000;
		int[] results = calc(startYear,endYear);
		System.out.printf("在公元%d年1月1日到公元%d年12月31日的%d天里，出生数的分布为：\n",startYear,endYear,runDays);
		for(int i=0;i<results.length;i++ ){
			System.out.printf("出生数%d\t¦\t",i+1);
		}
		System.out.println();
		int signNum = 0;
		for(int i=0;i<results.length;i++ ){
			System.out.printf("%d\t¦\t",results[i]);
			signNum += results[i];
		}
		if(signNum!=runDays) {
			System.out.println("存在漏算天数，请检测程序算法");
		}
		// 结束计时
		long end = System.currentTimeMillis();
		System.out.printf("\n程序运行时间为：%dms",(end-start));
	}
	
	static int[] calc(int startYear,int endYear) {
		if(startYear<1 || startYear>endYear) return null;
		int[] results = new int[9];
		for(int year=startYear;year<=endYear;year++) {
//			System.out.println("--------Now in year "+year);
			LocalDate date = LocalDate.of(year, 1, 1);
			int month = date.getMonthValue();
			
			int totalDays = date.isLeapYear()?366:365;
			date = date.plus(-1,ChronoUnit.DAYS);
			do{
				runDays++;
				date = date.plus(1,ChronoUnit.DAYS);
//				System.out.println(date.getDayOfYear());
				int thisMonth = date.getMonthValue();
				int today = date.getDayOfMonth();
				int th = year/1000;
				int han = year%1000/100;
				int sh = year%1000 % 100 / 10;
				int ge = year%1000 % 100 % 10;
				int mSh = thisMonth / 10;
				int mGe = thisMonth % 10;
				int dSh = today / 10;
				int dGe = today % 10;
				int realNum = th + han + sh + ge + mSh + mGe + dSh + dGe;
				
				realNum = recurNum(realNum);
				switch (realNum){
					case 1:
						results[0]++;
						break;
					case 2:
						results[1]++;
						break;
					case 3:
						results[2]++;
						break;
					case 4:
						results[3]++;
						break;
					case 5:
						results[4]++;
						break;
					case 6:
						results[5]++;
						break;
					case 7:
						results[6]++;
						break;
					case 8:
						results[7]++;
						break;
					case 9:
						results[8]++;
						break;
				}
			}while(date.getDayOfYear() < totalDays);
		}
		return results;
	}
	
	static int recurNum(int n) {
		if (n > 9) {
			int shi = n / 10;
			int g = n % 10;
			n = shi + g;
		}
		if(n>9) return recurNum(n);
		
		return n;
	}

}
