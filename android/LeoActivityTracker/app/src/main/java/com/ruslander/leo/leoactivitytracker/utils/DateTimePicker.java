package com.ruslander.leo.leoactivitytracker.utils;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class DateTimePicker {
    private DateTimePicker() {}

    public static void pickDateTime(final Context context, final EditText textbox) {
        final Calendar cal = Calendar.getInstance();

        int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                String monthStr = String.valueOf(selectedMonth + 1);
                if (selectedMonth < 9)
                    monthStr = "0" + monthStr;
                String dayStr = String.valueOf(selectedDay);
                if (selectedDay < 10)
                    dayStr = "0" + dayStr;

                final String dateStr = selectedYear + "-" + monthStr + "-" + dayStr + " ";

                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int min = cal.get(Calendar.MINUTE);
                TimePickerDialog timePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String minuteStr = String.valueOf(selectedMinute);
                        if (selectedMinute < 10)
                            minuteStr = "0" + selectedMinute;

                        String timeStr = selectedHour + ":" + minuteStr;
                        String dateTimeStr = dateStr + timeStr;

                        textbox.setText(dateTimeStr);
                    }
                }, hour, min, true);
                timePicker.setTitle("Select time");
                timePicker.show();
            }
        }, year, month, day);
        datePicker.setTitle("Select date");
        datePicker.show();
    }
}
