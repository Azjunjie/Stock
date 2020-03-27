package com.aitewei.stock.fragment;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.EditText;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aitewei.stock.R;
import com.aitewei.stock.activity.CompanyStockListActivity;
import com.aitewei.stock.adapter.CompanyListAdapter;
import com.aitewei.stock.entity.CompanyListEntity;
import com.aitewei.stock.utils.KeyBoardUtils;
import com.aitewei.stock.utils.WebServiceUtils;
import com.aitewei.stock.view.LoadGroupView;
import com.google.gson.Gson;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
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
 * 最优指标页
 */
public class OptimalIndexFragment extends BaseFragment {

    public static OptimalIndexFragment newInstance() {
        OptimalIndexFragment fragment = new OptimalIndexFragment();
        return fragment;
    }

    @BindView(R.id.parent_view)
    ConstraintLayout parentView;
    @BindView(R.id.et_keyword)
    EditText etKeyword;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.loading_view)
    LoadGroupView loadingView;

    private CompanyListAdapter adapter;

    private Thread thread;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LoadGroupView.TYPE_ERROR:
                    if (loadingView != null) {
                        recyclerView.setVisibility(View.GONE);
                        loadingView.setVisibility(View.VISIBLE);
                        loadingView.setLoadType(LoadGroupView.TYPE_ERROR);
                    }
                    break;
                case LoadGroupView.TYPE_LOADING:
                    String json = (String) msg.obj;
                    CompanyListEntity companyListEntity = new Gson().fromJson(json + "", CompanyListEntity.class);
                    if (companyListEntity != null) {
                        List<CompanyListEntity.DataTableBean> list = companyListEntity.getDataTable();
                        if (adapter != null && list != null && !list.isEmpty()) {
                            adapter.setList(list);
                            if (loadingView != null) {
                                recyclerView.setVisibility(View.VISIBLE);
                                loadingView.setVisibility(View.GONE);
                            }
                            return;
                        }
                    }
                    if (recyclerView != null) {
                        recyclerView.setVisibility(View.GONE);
                        loadingView.setVisibility(View.VISIBLE);
                        loadingView.setLoadType(LoadGroupView.TYPE_EMPTY);
                    }
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_optimal_index;
    }

    @Override
    protected void initView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        etKeyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keyword = s.toString();
                if (!TextUtils.isEmpty(keyword)) {
                    if (thread != null) {
                        thread.interrupt();
                    }
                    thread = loadSearchData(keyword);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        KeyBoardUtils.setupUI(getActivity(), parentView);
        etKeyword.requestFocus();
    }

    @Override
    protected void initData() {
        adapter = new CompanyListAdapter(getActivity(), null);
        adapter.setOnItemClickListener(bean ->
                CompanyStockListActivity.start(getActivity(), bean.get名称代码(), bean.get公司代码() + ""));
        recyclerView.setAdapter(adapter);

        thread = loadSearchData("");
    }

    private Thread loadSearchData(String keyword) {
        Thread thread = new Thread(() -> {
            String xml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <tem:GetGsmc>\n" +
                    "         <!--Optional:-->\n" +
                    "         <tem:gsdm>" + keyword + "</tem:gsdm>\n" +
                    "      </tem:GetGsmc>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(MediaType.parse("text/xml; charset=utf-8"), xml);
            Request request = new Request.Builder()
                    .url(WebServiceUtils.WEB_SERVER_URL)
                    .addHeader("SOAPAction", "http://tempuri.org/IIndexService/GetGsmc")
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
                                        if ("GetGsmcResult".equals(parser.getName())) {
                                            String json = parser.nextText();
                                            Log.e("GetGsmcResult", "json==" + json);

                                            //处理UI需要切换到UI线程处理
                                            if (mHandler != null) {
                                                Message msg = Message.obtain();
                                                msg.what = LoadGroupView.TYPE_LOADING;
                                                msg.obj = json;
                                                mHandler.sendMessage(msg);
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

}
