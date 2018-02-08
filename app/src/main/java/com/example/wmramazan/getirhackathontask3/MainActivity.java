package com.example.wmramazan.getirhackathontask3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.leavjenn.smoothdaterangepicker.date.SmoothDateRangePickerFragment;

public class MainActivity extends AppCompatActivity {

    Intent intent;
    String start_date, end_date;
    SmoothDateRangePickerFragment smoothDateRangePickerFragment;
    TextView dateRange;
    EditText minCount, maxCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //startActivity(new Intent(this, RecordsActivity.class).putExtra("startDate", "2016-1-1").putExtra("endDate", "2017-1-1").putExtra("minCount", 3000).putExtra("maxCount", 3005));

        dateRange = findViewById(R.id.date_range);
        minCount = findViewById(R.id.min_count);
        maxCount = findViewById(R.id.max_count);

        showDateRangePicker();
    }

    public void pickDateRange(View view) {
        showDateRangePicker();
    }

    public void showDateRangePicker() {
        smoothDateRangePickerFragment = SmoothDateRangePickerFragment.newInstance(new SmoothDateRangePickerFragment.OnDateRangeSetListener() {
            @Override
            public void onDateRangeSet(SmoothDateRangePickerFragment view,
                                       int yearStart, int monthStart,
                                       int dayStart, int yearEnd,
                                       int monthEnd, int dayEnd) {
                start_date = yearStart + "-" + monthStart + "-" + dayStart;
                end_date = yearEnd + "-" + monthEnd + "-" + dayEnd;
                dateRange.setText(start_date + " / " + end_date);
            }
        });
        smoothDateRangePickerFragment.show(getFragmentManager(), "DatePickerDialog");
    }

    public void searchRecords(View view) {
        if(null != start_date &&
                !minCount.getText().toString().isEmpty() &&
                !maxCount.getText().toString().isEmpty()
                ) {
            intent = new Intent(this, RecordsActivity.class);
            intent.putExtra("startDate", start_date);
            intent.putExtra("endDate", end_date);
            intent.putExtra("minCount", Integer.parseInt(minCount.getText().toString()));
            intent.putExtra("maxCount", Integer.parseInt(maxCount.getText().toString()));
            startActivity(intent);
        }
    }
}
