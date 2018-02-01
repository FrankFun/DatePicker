package cn.aigestudio.datepicker.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.aigestudio.datepicker.bizs.calendars.DPCManager;
import cn.aigestudio.datepicker.bizs.decors.DPDecor;
import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;

/**
 * Demo应用的主Activity
 * The main activity of demo
 *
 * @author AigeStudio 2015-03-26
 */
public class MainActivity extends Activity {
    Bitmap absent;
    Bitmap leave;
    Bitmap normal;
    Bitmap launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        absent = BitmapFactory.decodeResource(getResources(), R.drawable.ic_attendance_absent);
        leave = BitmapFactory.decodeResource(getResources(), R.drawable.ic_attendance_leave);
        normal = BitmapFactory.decodeResource(getResources(), R.drawable.ic_attendance_normal);
        launcher = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        Log.e("onCreate: ", absent.getWidth() + ":" + absent.getHeight());
//        // 默认多选模式
//        DatePicker picker = (DatePicker) findViewById(R.id.main_dp);
//        picker.setDate(2015, 7);
//        picker.setOnDateSelectedListener(new DatePicker.OnDateSelectedListener() {
//            @Override
//            public void onDateSelected(List<String> date) {
//                String result = "";
//                Iterator iterator = date.iterator();
//                while (iterator.hasNext()) {
//                    result += iterator.next();
//                    if (iterator.hasNext()) {
//                        result += "\n";
//                    }
//                }
//                Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
//            }
//        });

        // 自定义背景绘制示例 Example of custom date's background
//        List<String> tmp = new ArrayList<>();
//        tmp.add("2015-7-1");
//        tmp.add("2015-7-8");
//        tmp.add("2015-7-16");
//        DPCManager.getInstance().setDecorBG(tmp);
//
//        DatePicker picker = (DatePicker) findViewById(R.id.main_dp);
//        picker.setDate(2015, 7);
//        picker.setDPDecor(new DPDecor() {
//            @Override
//            public void drawDecorBG(Canvas canvas, Rect rect, Paint paint) {
//                paint.setColor(Color.RED);
//                canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 2F, paint);
//            }
//        });
//        picker.setOnDateSelectedListener(new DatePicker.OnDateSelectedListener() {
//            @Override
//            public void onDateSelected(List<String> date) {
//                String result = "";
//                Iterator iterator = date.iterator();
//                while (iterator.hasNext()) {
//                    result += iterator.next();
//                    if (iterator.hasNext()) {
//                        result += "\n";
//                    }
//                }
//                Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
//            }
//        });

        // 自定义前景装饰物绘制示例 Example of custom date's foreground decor
        List<String> tmpTR = new ArrayList<>();
        tmpTR.add("2018-1-5");
        tmpTR.add("2018-1-6");
        tmpTR.add("2018-1-7");
        tmpTR.add("2018-1-8");
        tmpTR.add("2018-1-9");
        tmpTR.add("2018-1-10");
        tmpTR.add("2018-1-11");
        tmpTR.add("2018-1-12");
        tmpTR.add("2018-1-13");
        tmpTR.add("2018-1-14");
        tmpTR.add("2018-1-15");
        tmpTR.add("2018-1-16");
        DPCManager.getInstance().setDecorTR(tmpTR);

        DatePicker picker = findViewById(R.id.main_dp);
        picker.setDate(2018, 1);
        picker.setFestivalDisplay(true);
        picker.setTodayDisplay(true);
        picker.setHolidayDisplay(false);
        picker.setDeferredDisplay(false);
        picker.setMode(DPMode.NONE);
        picker.setDPDecor(new DPDecor() {

            @Override
            public void drawDecorTR(Canvas canvas, Rect rect, Paint paint, String data) {
                super.drawDecorTR(canvas, rect, paint, data);
                switch (data) {
                    case "2018-1-10":
                    case "2018-1-11":
                    case "2018-1-12":
                        canvas.drawBitmap(absent, null, rect, paint);
                        break;
                    case "2018-1-15":
                    case "2018-1-17":
                    case "2018-1-19":
                        canvas.drawBitmap(leave, null, rect, paint);
                        break;
                    default:
                        canvas.drawBitmap(normal, null, rect, null);
                        break;
                }
            }
        });
//        picker.setOnDateSelectedListener(new DatePicker.OnDateSelectedListener() {
//            @Override
//            public void onDateSelected(List<String> date) {
//                String result = "";
//                Iterator iterator = date.iterator();
//                while (iterator.hasNext()) {
//                    result += iterator.next();
//                    if (iterator.hasNext()) {
//                        result += "\n";
//                    }
//                }
//                Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
//            }
//        });

        // 对话框下的DatePicker示例 Example in dialog
        Button btnPick = findViewById(R.id.main_btn);
        btnPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).create();
                dialog.show();
                DatePicker picker = new DatePicker(MainActivity.this);
                picker.setDate(2015, 10);
                picker.setMode(DPMode.SINGLE);
                picker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
                    @Override
                    public void onDatePicked(String date) {
                        Toast.makeText(MainActivity.this, date, Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setContentView(picker, params);
                dialog.getWindow().setGravity(Gravity.CENTER);
            }
        });
    }
}
