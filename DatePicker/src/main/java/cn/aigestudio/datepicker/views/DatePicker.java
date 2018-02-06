package cn.aigestudio.datepicker.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.aigestudio.datepicker.bizs.languages.DPLManager;
import cn.aigestudio.datepicker.bizs.themes.DPTManager;
import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.utils.MeasureUtil;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * DatePicker
 *
 * @author AigeStudio 2015-06-29
 */
public class DatePicker extends LinearLayout {
    private DPTManager mTManager;// 主题管理器
    private DPLManager mLManager;// 语言管理器

    private MonthView monthView;// 月视图
    private int currentYear = 0;
    private int currentMonth = 0;

    private OnDateChangedListener onDateChangedListener = null;// 日期多选后监听

    /**
     * 日期单选监听器
     */
    public interface OnDatePickedListener {
        void onDatePicked(int year, int month, int day);
    }

    public interface OnDateChangedListener {
        void onDateChanged(int year, int month);
    }


    public DatePicker(Context context) {
        this(context, null);
    }

    public DatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTManager = DPTManager.getInstance();
        mLManager = DPLManager.getInstance();

        // 设置排列方向为竖向
        setOrientation(VERTICAL);

        LayoutParams llParams =
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        // 周视图根布局
        LinearLayout llWeek = new LinearLayout(context);
        llWeek.setBackgroundColor(mTManager.colorTitleBG());
        llWeek.setOrientation(HORIZONTAL);
        int llWeekPadding = MeasureUtil.dp2px(context, 5);
        llWeek.setPadding(0, llWeekPadding, 0, llWeekPadding);
        LayoutParams lpWeek = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpWeek.weight = 1;

        // --------------------------------------------------------------------------------周视图
        for (int i = 0; i < mLManager.titleWeek().length; i++) {
            TextView tvWeek = new TextView(context);
            tvWeek.setText(mLManager.titleWeek()[i]);
            tvWeek.setGravity(Gravity.CENTER);
            tvWeek.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
            tvWeek.setTextColor(mTManager.colorTitle());
            llWeek.addView(tvWeek, lpWeek);
        }
        addView(llWeek, llParams);

        // ------------------------------------------------------------------------------------月视图
        monthView = new MonthView(context);
        monthView.setOnDateChangeListener(new MonthView.OnDateChangeListener() {
            @Override
            public void onMonthChange(int month) {
                currentMonth = month;
                if (onDateChangedListener == null) return;
                onDateChangedListener.onDateChanged(currentYear, currentMonth);
            }

            @Override
            public void onYearChange(int year) {
                currentYear = year;
                if (onDateChangedListener == null) return;
                onDateChangedListener.onDateChanged(currentYear, currentMonth);
//                String tmp = String.valueOf(year);
//                if (tmp.startsWith("-")) {
//                    tmp = tmp.replace("-", mLManager.titleBC());
//                }
//                tvYear.setText(tmp);
            }
        });
        addView(monthView, llParams);
    }


    /**
     * 设置初始化年月日期
     *
     * @param year  ...
     * @param month ...
     */
    public void setDate(int year, int month) {
        if (month < 1) {
            month = 1;
        }
        if (month > 12) {
            month = 12;
        }
        monthView.setDate(year, month);
    }

//    public void setDPDecor(DPDecor decor) {
//        monthView.setDPDecor(decor);
//    }

    /**
     * 设置日期选择模式
     *
     * @param mode ...
     */
    public void setMode(DPMode mode) {
//        if (mode != DPMode.MULTIPLE) {
//            tvEnsure.setVisibility(GONE);
//        }
        monthView.setDPMode(mode);
    }

    public void setFestivalDisplay(boolean isFestivalDisplay) {
        monthView.setFestivalDisplay(isFestivalDisplay);
    }

    public void setTodayDisplay(boolean isTodayDisplay) {
        monthView.setTodayDisplay(isTodayDisplay);
    }

    public void setHolidayDisplay(boolean isHolidayDisplay) {
        monthView.setHolidayDisplay(isHolidayDisplay);
    }

    public void setDeferredDisplay(boolean isDeferredDisplay) {
        monthView.setDeferredDisplay(isDeferredDisplay);
    }

    /**
     * 设置单选监听器
     *
     * @param onDatePickedListener ...
     */
    public void setOnDatePickedListener(OnDatePickedListener onDatePickedListener) {
        if (monthView.getDPMode() != DPMode.SINGLE) {
            throw new RuntimeException(
                    "Current DPMode does not SINGLE! Please call setMode set DPMode to SINGLE!");
        }
        monthView.setOnDatePickedListener(onDatePickedListener);
    }

    public List<String> getDateSelected() {
        return monthView.getDateSelected();
    }

    public void setOnDateChangedListener(OnDateChangedListener onDateChangedListener) {
        this.onDateChangedListener = onDateChangedListener;
    }
}
