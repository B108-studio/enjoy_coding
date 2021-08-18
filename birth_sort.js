const [startYear,endYear] = [1,99999]
let runDays = 0
let results = new Array(9).fill(0)

let startTime = Date.now()
console.log('程序员已启动...');
run(startYear,endYear)
let endTime = Date.now()
console.log(`runDays: ${runDays}\nresults: ${results}`);
console.log(`程序结束，耗费时间为：${endTime-startTime} ms`);

function run(startYear, endYear){
    for(let cur=startYear;cur<=endYear;cur++){
        let yearSum = recurNum(cur)
        for(let mon=1;mon<=12;mon++){
            for(let day=1;day<=daysOfMonth(cur,mon);day++){
                let realNum = recurNum(yearSum+mon+day)
                results[realNum-1]++
                runDays++
            }
        }
    }
    // 校验是否有遗漏天数
    let sum = 0
    results.map(n=>{sum+=n})
    if(runDays!==sum)console.log('存在漏算天数');
}
function recurNum(n){
    if(n>9) return recurNum(Math.floor(n/10)+n%10)
    return n
}
//计算是否闰年
function isLeapYear(year){
    return ((year%4===0)&&year%100!==0) || year%400==0
}
//计算某年某月的天数
function daysOfMonth(year,mon){
    if( mon=== undefined || year<1 || mon<1 || mon>12) return false
    switch(mon){
        case 2:
            return isLeapYear(year)?29:28
        case 4:
        case 6:
        case 9:
        case 11:
            return 30
        default:
            return 31
    }
}
