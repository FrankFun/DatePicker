package cn.aigestudio.datepicker.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import cn.aigestudio.datepicker.bizs.calendars.DPCManager;
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
    Button btnPick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        btnPick = findViewById(R.id.main_btn);
        absent = BitmapFactory.decodeResource(getResources(), R.drawable.ic_attendance_absent);
        leave = BitmapFactory.decodeResource(getResources(), R.drawable.ic_attendance_leave);
        normal = BitmapFactory.decodeResource(getResources(), R.drawable.ic_attendance_normal);
        // 自定义前景装饰物绘制示例 Example of custom date's foreground decor
        DPCManager.getInstance().addDecorTR(2018, 2, 5, absent);
        DPCManager.getInstance().addDecorTR(2018, 2, 6, leave);
        DPCManager.getInstance().addDecorTR(2018, 2, 7, absent);
        DPCManager.getInstance().addDecorTR(2018, 2, 8, normal);
        DPCManager.getInstance().addDecorTR(2018, 2, 9, absent);
        DatePicker picker = findViewById(R.id.main_dp);
        picker.setDate(2018, 2);
        picker.setFestivalDisplay(true);
        picker.setTodayDisplay(true);
        picker.setHolidayDisplay(false);
        picker.setDeferredDisplay(true);
        picker.setMode(DPMode.SINGLE);
        picker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(int year, int month) {
                btnPick.setText(year + "年" + month + "月");
            }
        });
        picker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
            @Override
            public void onDatePicked(int year, int month, int day) {

            }
        });
        // 对话框下的DatePicker示例 Example in dialog
        btnPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DPCManager.getInstance().addDecorTR(2018, 2, 10, absent);
                DPCManager.getInstance().addDecorTR(2018, 2, 11, leave);
                DPCManager.getInstance().addDecorTR(2018, 2, 12, normal);
                DPCManager.getInstance().addDecorTR(2018, 2, 13, leave);
                DPCManager.getInstance().addDecorTR(2018, 2, 14, absent);
                DPCManager.getInstance().addDecorTR(2018, 2, 15, normal);
                DPCManager.getInstance().addDecorTR(2018, 2, 16, absent);
                DPCManager.getInstance().addDecorTR(2018, 2, 19, absent);
                DPCManager.getInstance().notifyDataSetChanged();
                final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).create();
                dialog.show();
                DatePicker picker = new DatePicker(MainActivity.this);
                picker.setDate(2015, 10);
                picker.setMode(DPMode.SINGLE);
                picker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
                    @Override
                    public void onDatePicked(int year, int month, int day) {
                        Toast.makeText(MainActivity.this, year+"-"+month+"-"+day, Toast.LENGTH_LONG).show();
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
