package com.aitewei.stock.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.aitewei.stock.R;
import com.aitewei.stock.adapter.CompanyStockListAdapter;
import com.aitewei.stock.entity.CompanyStockListEntity;
import com.aitewei.stock.entity.LastTimeEntity;
import com.aitewei.stock.utils.WebServiceUtils;
import com.aitewei.stock.view.LoadGroupView;
import com.aitewei.stock.view.NoscrollListView;
import com.aitewei.stock.view.SyncHorizontalScrollView;
import com.google.gson.Gson;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 公司详情股票列表
 */
public class CompanyStockListActivity extends BaseActivity {

    public static void start(Context context, String companyNameCode, String companyCode) {
        Intent starter = new Intent(context, CompanyStockListActivity.class);
        starter.putExtra("companyNameCode", companyNameCode);
        starter.putExtra("companyCode", companyCode);
        context.startActivity(starter);
    }

    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_search)
    ImageView btnSearch;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.btn_time)
    LinearLayout btnTime;
    @BindView(R.id.header_horizontal)
    SyncHorizontalScrollView headerHorizontal;
    @BindView(R.id.content_view)
    ScrollView contentView;
    @BindView(R.id.data_horizontal)
    SyncHorizontalScrollView dataHorizontal;
    @BindView(R.id.list_view)
    NoscrollListView listView;
    @BindView(R.id.loading_view)
    LoadGroupView loadingView;

    private String companyNameCode;
    private String companyCode;
    private CompanyStockListAdapter adapter;
    private List<CompanyStockListEntity.DataTableBean> list;

    private Thread thread;
    private String lastTime;
    private String requestTime;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LoadGroupView.TYPE_ERROR:
                    if (loadingView != null) {
                        loadingView.setLoadType(LoadGroupView.TYPE_ERROR);
                    }
                    break;
                case LoadGroupView.TYPE_LOADING:
                    String json = (String) msg.obj;
                    CompanyStockListEntity stockListEntity = new Gson().fromJson(json + "", CompanyStockListEntity.class);
                    if (stockListEntity != null) {
                        list = stockListEntity.getDataTable();
                        if (adapter != null && list != null && !list.isEmpty()) {
                            adapter.setList(list);
                            if (contentView != null) {
                                contentView.setVisibility(View.VISIBLE);
                                loadingView.setVisibility(View.GONE);
                            }
                            return;
                        }
                    }
                    if (loadingView != null) {
                        loadingView.setLoadType(LoadGroupView.TYPE_EMPTY);
                    }
                    break;
                case 1000://getLastTime
                    json = (String) msg.obj;
                    LastTimeEntity lastTimeEntity = new Gson().fromJson(json + "", LastTimeEntity.class);
                    if (lastTimeEntity != null) {
                        lastTime = lastTimeEntity.getString();
                        if (!TextUtils.isEmpty(lastTime)) {
                            lastTime = lastTime.substring(0, lastTime.length() - 8);
                            String[] split = lastTime.split("\\/");
                            if (split != null && split.length == 3) {
                                String month = split[1];
                                if (month.length() == 1) {
                                    month = "0" + month;
                                }
                                requestTime = split[0] + "-" + month + "-" + split[2];
                                thread = loadData(companyCode, requestTime);
                                tvTime.setText(requestTime + "");
                            }
                        }
                    }
                    break;
                case 1001://getLastTime failure
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    requestTime = dateFormat.format(new Date());
                    tvTime.setText(requestTime);
                    loadData(companyCode, requestTime);
                    break;
            }
        }
    };

    private DatePickerDialog datePickerDialog;
    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            int mYear = year;
            int mMonth = monthOfYear;
            int mDay = dayOfMonth;
            if (mMonth + 1 < 10) {
                if (mDay < 10) {
                    requestTime = new StringBuffer().append(mYear).append("-").append("0").
                            append(mMonth + 1).append("-").append("0").append(mDay).toString();
                } else {
                    requestTime = new StringBuffer().append(mYear).append("-").append("0").
                            append(mMonth + 1).append("-").append(mDay).toString();
                }

            } else {
                if (mDay < 10) {
                    requestTime = new StringBuffer().append(mYear).append("-").
                            append(mMonth + 1).append("-").append("0").append(mDay).toString();
                } else {
                    requestTime = new StringBuffer().append(mYear).append("-").
                            append(mMonth + 1).append("-").append(mDay).toString();
                }
            }
            tvTime.setText(requestTime);
            thread = loadData(companyCode, requestTime);
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_company_stock_list;
    }

    @Override
    protected void initView() {
        btnBack.setOnClickListener(v -> finish());
        btnSearch.setOnClickListener(v -> SearchActivity.start(CompanyStockListActivity.this));
        btnTime.setOnClickListener(v -> {//日期选择
            if (datePickerDialog == null) {
                Calendar ca = Calendar.getInstance();
                int mYear = ca.get(Calendar.YEAR);
                int mMonth = ca.get(Calendar.MONTH);
                int mDay = ca.get(Calendar.DAY_OF_MONTH);
                if (!TextUtils.isEmpty(lastTime)) {
                    String[] split = lastTime.split("\\/");
                    if (split != null && split.length == 3) {
                        mYear = Integer.valueOf(split[0]);
                        mMonth = Integer.valueOf(split[1]) - 1;
                        mDay = Integer.valueOf(split[2]);
                    }
                }
                datePickerDialog = new DatePickerDialog(CompanyStockListActivity.this, onDateSetListener, mYear, mMonth, mDay);
            }
            datePickerDialog.show();
        });
        loadingView.setOnClickListener(v -> {
            int loadType = loadingView.getLoadType();
            if (loadType == LoadGroupView.TYPE_ERROR) {
                loadData(companyCode, requestTime);
            }
        });
        dataHorizontal.setScrollView(headerHorizontal);
        headerHorizontal.setScrollView(dataHorizontal);
    }

    @Override
    protected void initData() {
        adapter = new CompanyStockListAdapter(this, null, R.layout.item_company_stock_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) ->
                CompanyStockDetailActivity.start(CompanyStockListActivity.this, companyNameCode, list.get(position)));

        Intent intent = getIntent();
        if (intent != null) {
            companyNameCode = intent.getStringExtra("companyNameCode");
            companyCode = intent.getStringExtra("companyCode");

            tvTitle.setText(companyNameCode + "");
            loadLastTimeData(companyCode, "D");
        }
    }

    private Thread loadLastTimeData(String code, String type) {
        contentView.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
        loadingView.setLoadType(LoadGroupView.TYPE_LOADING);
        Thread thread = new Thread(() -> {
            String xml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <tem:GetLastTime>\n" +
                    "         <!--Optional:-->\n" +
                    "         <tem:gsdm>" + code + "</tem:gsdm>\n" +
                    "         <!--Optional:-->\n" +
                    "         <tem:ktype>" + type + "</tem:ktype>\n" +
                    "      </tem:GetLastTime>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(MediaType.parse("text/xml; charset=utf-8"), xml);
            Request request = new Request.Builder()
                    .url(WebServiceUtils.WEB_SERVER_URL)
                    .addHeader("SOAPAction", "http://tempuri.org/IIndexService/GetLastTime")
                    .post(body)
                    .build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    if (mHandler != null) {
                        Message msg = Message.obtain();
                        msg.what = 1001;
                        if (mHandler != null) {
                            mHandler.sendMessage(msg);
                        }
                    }
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String result = response.body().string();
                        Log.e("response", "result==" + result);
                        //xml解析
                        StringReader reader = new StringReader(result);
                        try {
                            XmlPullParser parser = Xml.newPullParser();
                            parser.setInput(reader);
                            int event = parser.getEventType();
                            while (event != XmlPullParser.END_DOCUMENT) {
                                switch (event) {
                                    case XmlPullParser.START_TAG:
                                        if ("GetLastTimeResult".equals(parser.getName())) {
                                            String json = parser.nextText();
                                            Log.e("GetLastTimeResult", "json==" + json);

                                            //处理UI需要切换到UI线程处理
                                            if (mHandler != null) {
                                                Message msg = Message.obtain();
                                                msg.what = 1000;
                                                msg.obj = json;
                                                if (mHandler != null) {
                                                    mHandler.sendMessage(msg);
                                                }
                                            }
                                            return;
                                        }
                                        break;
                                }
                                event = parser.next();
                            }
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        } finally {
                            if (reader != null) {
                                reader.close();
                            }
                        }
                    }
                }
            });
        });
        thread.start();
        return thread;
    }

    private Thread loadData(String code, String date) {
        contentView.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
        loadingView.setLoadType(LoadGroupView.TYPE_LOADING);
        Thread thread = new Thread(() -> {
            String xml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <tem:GetOptimizeFormulaPamar>\n" +
                    "         <!--Optional:-->\n" +
                    "         <tem:gsdm>" + code + "</tem:gsdm>\n" +
                    "         <!--Optional:-->\n" +
                    "         <tem:date>" + date + "</tem:date>\n" +
                    "      </tem:GetOptimizeFormulaPamar>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(MediaType.parse("text/xml; charset=utf-8"), xml);
            Request request = new Request.Builder()
                    .url(WebServiceUtils.WEB_SERVER_URL)
                    .addHeader("SOAPAction", "http://tempuri.org/IIndexService/GetOptimizeFormulaPamar")
                    .post(body)
                    .build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    if (mHandler != null) {
                        mHandler.sendEmptyMessage(LoadGroupView.TYPE_ERROR);
                    }
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String result = response.body().string();
                        //xml解析
                        StringReader reader = new StringReader(result);
                        try {
                            XmlPullParser parser = Xml.newPullParser();
                            parser.setInput(reader);
                            int event = parser.getEventType();
                            while (event != XmlPullParser.END_DOCUMENT) {
                                switch (event) {
                                    case XmlPullParser.START_TAG:
                                        if ("GetOptimizeFormulaPamarResult".equals(parser.getName())) {
                                            String json = parser.nextText();
                                            Log.e("GetOptimizeResult", "json==" + json);

                                            //处理UI需要切换到UI线程处理
                                            if (mHandler != null) {
                                                Message msg = Message.obtain();
                                                msg.what = LoadGroupView.TYPE_LOADING;
                                                msg.obj = json;
                                                if (mHandler != null) {
                                                    mHandler.sendMessage(msg);
                                                }
                                            }
                                            return;
                                        }
                                        break;
                                }
                                event = parser.next();
                            }
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                            if (mHandler != null) {
                                mHandler.sendEmptyMessage(LoadGroupView.TYPE_ERROR);
                            }
                        } finally {
                            if (reader != null) {
                                reader.close();
                            }
                        }
                    }
                }
            });
        });
        thread.start();
        return thread;
    }

    @Override
    protected void onDestroy() {
        if (thread != null) {
            thread.interrupt();
        }
        super.onDestroy();
    }

}
