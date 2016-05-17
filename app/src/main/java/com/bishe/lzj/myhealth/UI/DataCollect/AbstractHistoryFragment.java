package com.bishe.lzj.myhealth.UI.DataCollect;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.bishe.lzj.myhealth.Logic.DataSender.DataSender;
import com.bishe.lzj.myhealth.Logic.DataSender.Impl.VolleyBPSender;
import com.bishe.lzj.myhealth.Logic.DataSender.Impl.VolleyBSSender;
import com.bishe.lzj.myhealth.Logic.DataSender.Impl.VolleyOSSender;
import com.bishe.lzj.myhealth.Logic.DataSender.Impl.VolleyPulseSender;
import com.bishe.lzj.myhealth.Logic.DataSender.VolleyHealthDataSender;
import com.bishe.lzj.myhealth.MyApplication;
import com.bishe.lzj.myhealth.R;
import com.bishe.lzj.myhealth.Util.LogUtil;
import com.bishe.lzj.myhealth.Util.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemSelected;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by lzj on 2016/3/2.
 */
public abstract class AbstractHistoryFragment extends Fragment {

    protected RecyclerView recyclerView;

    protected LineChartView lineChartView;
    @InjectView(R.id.show_type)
    protected Spinner show_type_spinner;
    @InjectView(R.id.table)
    protected FrameLayout tableFrameLayout;
    @InjectView(R.id.tu)
    protected FrameLayout tuFrameLayout;
    protected LineChartData lineChartData = new LineChartData();

    private RecyclerView.Adapter adapter;
    private VolleyHealthDataSender sender;
    protected List data = new ArrayList();

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.i(getTAG(), "onCreateView");
        View view = inflater.inflate(R.layout.layout_history_base, container, false);
        ButterKnife.inject(this, view);
        //init recycleview
        View view_contatin_recycleview = LayoutInflater.from(getActivity()).inflate(getLayoutHistoryLayoutId(), tableFrameLayout, true);
        recyclerView = (RecyclerView) view_contatin_recycleview.findViewById(R.id.recyclerview);
        initSpinner();
        adapter = getAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        sender = getSender();
        return view;
    }

    private void initSpinner() {
        show_type_spinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, new String[]{"表格", "图"}));
        showContentView(show_type_spinner.getSelectedItemPosition());
    }

    private void showContentView(int position) {
        switch (position) {
            case 0://表格
                tableFrameLayout.setVisibility(View.VISIBLE);
                tuFrameLayout.setVisibility(View.GONE);
                break;
            case 1:
//                if(lineChartView == null) {
                LogUtil.i(getTAG(), "create linechartview");
                lineChartView = new LineChartView(getActivity());
                initLineChart();
                tuFrameLayout.addView(lineChartView);
//                }
                tableFrameLayout.setVisibility(View.GONE);
                tuFrameLayout.setVisibility(View.VISIBLE);
                updateLineChart();
                break;
        }
    }

    @OnItemSelected(R.id.show_type)
    protected void selectShowType(int position) {
        LogUtil.i(getTAG(), "you clicked " + position);
        showContentView(position);
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.i(getTAG(), "onResume");
    }

    protected void queryData() {
        sender.query(MyApplication.getUser().getId() + "", "2000-01-01%2010:00:00", new DataSender.FinishedCallbackListener() {
            @Override
            public void onFinished(Bundle bundle) {
                data = getResult(bundle);
                adapter.notifyDataSetChanged();
                ToastUtil.showShort("已更新至最新数据");
            }
        }, new DataSender.ErrorCallbackListener() {
            @Override
            public void onError(String error) {
                ToastUtil.showShort("获取数据失败");
            }
        });
    }

    private void initLineChart() {
        //横坐标
        Axis axis_X = new Axis();
        axis_X.setTextColor(Color.BLUE);
        axis_X.setMaxLabelChars(2);
        List<AxisValue> axisValues = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            axisValues.add(new AxisValue(i).setLabel("" + i));
        }
        axis_X.setValues(axisValues);
        lineChartData.setAxisXBottom(axis_X);
        //纵坐标
        Axis axis_Y = new Axis();
        axis_Y.setTextColor(Color.BLUE);
        axis_Y.setMaxLabelChars(4);
//        List<AxisValue> axisValues_Y = new ArrayList<>();
//        for (int i = 0; i < 15; ++i) {
//            axisValues_Y.add(new AxisValue(i * 10).setLabel("" + (i * 10)));
//        }
//        axis_Y.setValues(axisValues_Y);
        lineChartData.setAxisYLeft(axis_Y);
    }

    private void updateLineChart() {
        LogUtil.i(getTAG(), "updateLineChart");
        List<Line> lines = getLines();
        for (Line l :
                lines) {
            setLineStyle(l);
        }
        lineChartData.setLines(lines);
        lineChartData.setValueLabelsTextColor(Color.BLUE);
        lineChartView.setLineChartData(lineChartData);
    }

    private void setLineStyle(Line l) {
        LogUtil.i(getTAG(), "setLineStyle" + l.toString());
        l.setColor(Color.GREEN);
    }

    protected abstract List<Line> getLines();

    @Override
    public void onPause() {
        Log.i(getTAG(), "onPause");
        super.onPause();
    }

    protected abstract String getTAG();

    @Override
    public void onStop() {
        Log.i(getTAG(), "onStop");
        super.onStop();
    }

    private List getResult(Bundle bundle) {
        String rs = bundle.getString("result");
        if (sender instanceof VolleyBPSender)
            return ((VolleyBPSender) sender).parseReturnList(rs);
        else if (sender instanceof VolleyBSSender) {
            return ((VolleyBSSender) sender).parseReturnList(rs);
        } else if (sender instanceof VolleyOSSender)
            return ((VolleyOSSender) sender).parseReturnList(rs);
        else if (sender instanceof VolleyPulseSender)
            return ((VolleyPulseSender) sender).parseReturnList(rs);
        else
            return new ArrayList();
    }

    protected abstract VolleyHealthDataSender getSender();

    protected abstract RecyclerView.Adapter getAdapter();

    protected abstract int getLayoutHistoryLayoutId();

    @Override
    public void onDestroyView() {

        super.onDestroyView();
    }

    abstract class MyAdapter extends RecyclerView.Adapter<MyHolder> {

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(getItemLayoutId(), parent, false);
            return getMyHolder(view);
        }

        @NonNull
        protected abstract MyHolder getMyHolder(View view);

        protected abstract int getItemLayoutId();

        @Override
        public int getItemCount() {
            return data.size();
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            bindDataOnViewHolder(holder, position);
        }

        protected abstract void bindDataOnViewHolder(MyHolder holder, int position);
    }


    abstract class MyHolder extends RecyclerView.ViewHolder {
        View self;

        public MyHolder(View itemView) {
            super(itemView);
            self = itemView;
        }
    }

    protected String parseDate(Date date) {
        return sdf.format(date);
    }

}
