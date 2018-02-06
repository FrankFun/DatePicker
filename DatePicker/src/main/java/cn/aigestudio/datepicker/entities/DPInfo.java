package cn.aigestudio.datepicker.entities;

import android.graphics.Bitmap;

/**
 * 日历数据实体
 * 封装日历绘制时需要的数据
 * <p>
 * Entity of calendar
 *
 * @author AigeStudio 2015-03-26
 */
public class DPInfo {
    public int strG;
    public String strF;
    public boolean isHoliday;
    public boolean isToday, isWeekend;
    public boolean isSolarTerms, isFestival, isDeferred;
    public Bitmap isDecorBG = null;
    public Bitmap isDecorTL = null;
    public Bitmap isDecorT = null;
    public Bitmap isDecorTR = null;
    public Bitmap isDecorL = null;
    public Bitmap isDecorR = null;
}