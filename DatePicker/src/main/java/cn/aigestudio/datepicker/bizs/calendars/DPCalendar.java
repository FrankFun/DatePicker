package cn.aigestudio.datepicker.bizs.calendars;

import java.util.Calendar;

/**
 * 月历抽象父类
 * 继承该类可以实现自己的日历对象
 * <p/>
 * Abstract class of Calendar
 *
 * @author AigeStudio 2015-06-15
 */
public abstract class DPCalendar {
    private final Calendar calendar = Calendar.getInstance();

    /**
     * 获取某年某月的节日数组
     * <p/>
     * Build the festival date array of given year and month
     *
     * @param year  某年
     * @param month 某月
     * @return 该月节日数组
     */
    public abstract String[][] buildMonthFestival(int year, int month);

    /**
     * 判断某年是否为闰年
     *
     * @param year ...
     * @return true表示闰年
     */
    private boolean isLeapYear(int year) {
        return (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0));
    }

    /**
     * 生成某年某月的公历天数数组
     * 数组为6x7的二维数组因为一个月的周数永远不会超过六周
     * 天数填充对应相应的二维数组下标
     * 如果某个数组下标中没有对应天数那么则填充一个空字符串
     *
     * @param year  某年
     * @param month 某月
     * @return 某年某月的公历天数数组
     */
    int[][] buildMonthG(int year, int month) {
        calendar.clear();
        int tmp[][] = new int[6][7];
        calendar.set(year, month - 1, 1);
        int daysInMonth;
        if (month == 2) {
            if (isLeapYear(year)) {
                daysInMonth = 29;
            } else {
                daysInMonth = 28;
            }
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            daysInMonth = 30;
        } else {
            daysInMonth = 31;
        }
        //周天为第一天
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int day = 1;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                tmp[i][j] = 0;
                if ((i == 0 && j >= dayOfWeek) || (i > 0 && day <= daysInMonth)) {
                    tmp[i][j] += day;
                    day++;
                }
            }
        }
        return tmp;
    }

    long GToNum(int year, int month, int day) {
        month = (month + 9) % 12;
        year = year - month / 10;
        return 365 * year + year / 4 - year / 100 + year / 400 + (month * 306 + 5) / 10 + (day - 1);
    }

    int getBitInt(int data, int length, int shift) {
        return (data & (((1 << length) - 1) << shift)) >> shift;
    }
}
