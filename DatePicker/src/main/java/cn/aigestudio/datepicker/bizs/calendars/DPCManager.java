package cn.aigestudio.datepicker.bizs.calendars;

import android.text.TextUtils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import cn.aigestudio.datepicker.entities.DPInfo;

/**
 * 日期管理器
 * The manager of date picker.
 *
 * @author AigeStudio 2015-06-12
 */
public final class DPCManager {
    private static HashMap<Integer, HashMap<Integer, DPInfo[][]>> DATE_CACHE = new HashMap<>();
    private static HashMap<Integer, Set<Integer>> DECOR_CHANGED = new HashMap<>();

    private static HashMap<String, Set<String>> DECOR_CACHE_BG = new HashMap<>();
    private static HashMap<String, Set<String>> DECOR_CACHE_TL = new HashMap<>();
    private static HashMap<String, Set<String>> DECOR_CACHE_T = new HashMap<>();
    private static HashMap<String, Set<String>> DECOR_CACHE_TR = new HashMap<>();
    private static HashMap<String, Set<String>> DECOR_CACHE_L = new HashMap<>();
    private static HashMap<String, Set<String>> DECOR_CACHE_R = new HashMap<>();

    private static DPCManager sManager;
    private Calendar calendar = Calendar.getInstance();
    private int todayYear = calendar.get(Calendar.YEAR);
    private int todayMonth = calendar.get(Calendar.MONTH) + 1;
    private String todayDay = calendar.get(Calendar.DAY_OF_MONTH) + "";
    private DPCalendar c;

    private DPCManager() {
        // 默认显示为中文日历
        String locale = Locale.getDefault().getCountry().toLowerCase();
        if (locale.equals("cn")) {
            initCalendar(new DPCNCalendar());
        } else {
            initCalendar(new DPUSCalendar());
        }
    }

    /**
     * 获取月历管理器
     * Get calendar manager
     *
     * @return 月历管理器
     */
    public static DPCManager getInstance() {
        if (null == sManager) {
            sManager = new DPCManager();
        }
        return sManager;
    }

    /**
     * 初始化日历对象
     * <p/>
     * Initialization Calendar
     *
     * @param c ...
     */
    private void initCalendar(DPCalendar c) {
        this.c = c;
    }

    public void addDecorBG(int year, int month, int day) {
        addDecor(year, month, day, DECOR_CACHE_BG);
    }

    public void addDecorTL(int year, int month, int day) {
        addDecor(year, month, day, DECOR_CACHE_TL);
    }

    public void addDecorT(int year, int month, int day) {
        addDecor(year, month, day, DECOR_CACHE_T);
    }

    public void addDecorTR(int year, int month, int day) {
        addDecor(year, month, day, DECOR_CACHE_TR);
    }

    public void addDecorL(int year, int month, int day) {
        addDecor(year, month, day, DECOR_CACHE_L);
    }

    public void addDecorR(int year, int month, int day) {
        addDecor(year, month, day, DECOR_CACHE_R);
    }

    /**
     * 获取指定年月的日历对象数组
     *
     * @param year  公历年
     * @param month 公历月
     * @return 日历对象数组 该数组长度恒为6x7 如果某个下标对应无数据则填充为null
     */
    public DPInfo[][] obtainDPInfo(int year, int month) {
        HashMap<Integer, DPInfo[][]> dataOfYear = DATE_CACHE.get(year);
        if (null != dataOfYear && dataOfYear.size() != 0) {
            DPInfo[][] dataOfMonth = dataOfYear.get(month);
            if (dataOfMonth != null) {
                return dataOfMonth;
            }
            dataOfMonth = buildDPInfo(year, month);
            dataOfYear.put(month, dataOfMonth);
            return dataOfMonth;
        }
        if (null == dataOfYear) dataOfYear = new HashMap<>();
        DPInfo[][] dataOfMonth = buildDPInfo(year, month);
        dataOfYear.put((month), dataOfMonth);
        DATE_CACHE.put(year, dataOfYear);
        return dataOfMonth;
    }

    private void addDecor(int year, int month, int day, HashMap<String, Set<String>> cache) {
        String key = year + "-" + month;
        Set<String> days = cache.get(key);
        if (null == days) {
            days = new HashSet<>();
            cache.put(key, days);
        }
        days.add(day + "");
        Set<Integer> monthsChanged = DECOR_CHANGED.get(year);
        if (monthsChanged == null) {
            monthsChanged = new HashSet<>();
            DECOR_CHANGED.put(year, monthsChanged);
        }
        monthsChanged.add(month);
    }

    public void notifyDataSetChanged() {
        for (Integer year : DECOR_CHANGED.keySet()) {
            for (Integer month : DECOR_CHANGED.get(year)) {
                DPInfo[][] dataOfMonth = buildDPInfo(year, month);
                HashMap<Integer, DPInfo[][]> dataOfYear = DATE_CACHE.get(year);
                if (null == dataOfYear) dataOfYear = new HashMap<>();
                dataOfYear.put((month), dataOfMonth);
                DATE_CACHE.put(year, dataOfYear);
            }
        }
        DECOR_CHANGED.clear();
    }

    private DPInfo[][] buildDPInfo(int year, int month) {
        DPInfo[][] info = new DPInfo[6][7];

        String[][] strG = c.buildMonthG(year, month);
        String[][] strF = c.buildMonthFestival(year, month);
        String key = year + "-" + month;
        Set<String> decorBG = DECOR_CACHE_BG.get(key);
        Set<String> decorTL = DECOR_CACHE_TL.get(key);
        Set<String> decorT = DECOR_CACHE_T.get(key);
        Set<String> decorTR = DECOR_CACHE_TR.get(key);
        Set<String> decorL = DECOR_CACHE_L.get(key);
        Set<String> decorR = DECOR_CACHE_R.get(key);

        for (int i = 0; i < info.length; i++) {
            for (int j = 0; j < info[i].length; j++) {
                DPInfo tmp = new DPInfo();
                tmp.strG = strG[i][j];
                if (c instanceof DPCNCalendar) {
                    tmp.strF = strF[i][j].replace("F", "");
                } else {
                    tmp.strF = strF[i][j];
                }
                tmp.isToday = todayYear == year && todayMonth == month && Objects.equals(tmp.strG, todayDay);
                if (j == 0 || j == info[i].length - 1) tmp.isWeekend = true;
                if (c instanceof DPCNCalendar) {
                    if (!TextUtils.isEmpty(tmp.strG)) tmp.isSolarTerms =
                            ((DPCNCalendar) c).isSolarTerm(year, month, Integer.valueOf(tmp.strG));
                    if (!TextUtils.isEmpty(strF[i][j]) && strF[i][j].endsWith("F"))
                        tmp.isFestival = true;
                } else {
                    tmp.isFestival = !TextUtils.isEmpty(strF[i][j]);
                }
                if (null != decorBG && decorBG.contains(tmp.strG)) tmp.isDecorBG = true;
                if (null != decorTL && decorTL.contains(tmp.strG)) tmp.isDecorTL = true;
                if (null != decorT && decorT.contains(tmp.strG)) tmp.isDecorT = true;
                if (null != decorTR && decorTR.contains(tmp.strG)) tmp.isDecorTR = true;
                if (null != decorL && decorL.contains(tmp.strG)) tmp.isDecorL = true;
                if (null != decorR && decorR.contains(tmp.strG)) tmp.isDecorR = true;
                info[i][j] = tmp;
            }
        }
        return info;
    }
}
