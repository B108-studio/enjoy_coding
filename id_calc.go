package main

import (
	"errors"
	"fmt"
	"strconv"
	"strings"
)

func main() {
	nums := get_nums()
	res, err := nums()
	for err != nil {
		fmt.Println(err)
		res, err = nums()
	}

	fmt.Printf("身份证前17位 %s 的校验码为：%s\n", res, calc_num(&res))
}

// 处理用户输入信息
func get_nums() func() (string, error) {
	var i int = 0
	var top_nums string

	return func() (string, error) {
		if i == 0 {
			fmt.Println("请输入身份证前17位：")
		} else {
			fmt.Println("请重新输入:")
		}
		i++
		fmt.Scanln(&top_nums)

		_, err := strconv.Atoi(top_nums)
		if err != nil {
			return top_nums, errors.New("您的输入内容包含非数字")
		}
		if len(top_nums) != 17 {
			err := fmt.Sprintf("输入长度应为17位, 您输入了%d位\n", len(top_nums))
			return top_nums, errors.New(err)
		}
		month, _ := strconv.Atoi(top_nums[10:12])
		day, _ := strconv.Atoi(top_nums[12:14])
		if month > 12 || day > 31 || month == 0 || day == 0 {
			err := "您输入的身份证号日期格式错误"
			return top_nums, errors.New(err)
		}
		return top_nums, nil
	}

}

// 根据规则计算校验码
func calc_num(top_nums *string) string {
	weight := []int{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2}
	mods := []string{"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"}
	sum := 0
	top_nums_arr := strings.Split(*top_nums, "")
	for i, v := range top_nums_arr {
		val, _ := strconv.Atoi(v)
		sum += val * weight[i]
	}
	return mods[sum%11]
}
