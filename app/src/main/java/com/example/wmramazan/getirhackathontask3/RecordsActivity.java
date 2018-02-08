package com.example.wmramazan.getirhackathontask3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class RecordsActivity extends AppCompatActivity {

    final String URL = "https://getir-bitaksi-hackathon.herokuapp.com/searchRecords";
    final int NUMBER_OF_RECORDS = 10;

    Intent intent;
    AsyncHttpClient client;
    RequestParams params;
    TextView errorText, pageText;
    ProgressBar progressBar;
    RecyclerView recordList;
    LinearLayoutManager linearLayoutManager;
    RecordListAdapter recordListAdapter;
    ArrayList<RecordItem> records;
    ArrayList<RecordItem> allRecords;
    int number_of_pages, page, first_index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        intent = getIntent();
        setTitle(getString(R.string.records));

        errorText = findViewById(R.id.error_text);
        pageText = findViewById(R.id.page_text);
        progressBar = findViewById(R.id.progress_bar);
        recordList = findViewById(R.id.record_list);

        records = new ArrayList<>();
        allRecords = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recordList.setLayoutManager(linearLayoutManager);
        recordList.setHasFixedSize(true);
        recordListAdapter = new RecordListAdapter(this, records);
        recordList.setAdapter(recordListAdapter);

        params = new RequestParams();
        params.put("startDate", intent.getStringExtra("startDate"));
        params.put("endDate", intent.getStringExtra("endDate"));
        params.put("minCount", intent.getIntExtra("minCount", 0));
        params.put("maxCount", intent.getIntExtra("maxCount", 0));

        client = new AsyncHttpClient();
        client.post(URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                Log.d("ADN", new String(responseBody));

                try {
                    JSONObject jsonObject = new JSONObject(new String(responseBody));
                    if(jsonObject.getInt("code") == 0) {
                        JSONArray items = jsonObject.getJSONArray("records");
                        int end = items.length();
                        if(end == 0) {
                            errorText.setText(getString(R.string.no_records));
                            showError();
                        } else {
                            JSONObject item;
                            for(int i = 0; i < end; i++) {
                                item = items.getJSONObject(i);
                                allRecords.add(new RecordItem(item.getJSONObject("_id").getString("createdAt"), item.getInt("totalCount")));
                            }
                            showRecords();
                            recordListAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                        }

                    } else
                        showError();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                showError();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }

    public void showRecords() {
        page = 1;
        number_of_pages = (allRecords.size() / NUMBER_OF_RECORDS) + 1;

        getPage();
    }

    public void getPage() {
        pageText.setText(page + " / " + number_of_pages);

        first_index = (page - 1) *  NUMBER_OF_RECORDS;
        records.clear();
        if(page == number_of_pages)
            records.addAll(allRecords.subList(first_index, allRecords.size()));
        else
            records.addAll(allRecords.subList(first_index, first_index + NUMBER_OF_RECORDS));
        recordListAdapter.notifyDataSetChanged();
    }

    public void showError() {
        progressBar.setVisibility(View.GONE);
        errorText.setVisibility(View.VISIBLE);
    }

    public void goToPreviousPage(View view) {
        if(!allRecords.isEmpty() && page != 1) {
            page--;
            getPage();
        }
    }

    public void goToNextPage(View view) {
        if(!allRecords.isEmpty() && page != number_of_pages) {
            page++;
            getPage();
        }
    }
}
