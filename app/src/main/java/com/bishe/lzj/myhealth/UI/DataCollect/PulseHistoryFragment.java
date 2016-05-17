package com.bishe.lzj.myhealth.UI.DataCollect;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bishe.lzj.myhealth.Bean.Pulse;
import com.bishe.lzj.myhealth.Logic.DataSender.Impl.VolleyPulseSender;
import com.bishe.lzj.myhealth.Logic.DataSender.VolleyHealthDataSender;
import com.bishe.lzj.myhealth.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.PointValue;

/**
 * Created by lzj on 2016/3/1.
 */
public class PulseHistoryFragment extends AbstractHistoryFragment {


    @Override
    protected List<Line> getLines() {
        List<Line> lines = new ArrayList<>();
        List<PointValue> datas = new ArrayList<>();
        for (int i = 0; i < 10 && i < data.size(); i++) {
            Pulse bp = (Pulse) data.get(i);
            datas.add(new PointValue(i, bp.getPulse()));
        }
        lines.add(new Line(datas));
        return lines;
    }

    @Override
    protected String getTAG() {
        return "PulseHistoryFragment";
    }

    @Override
    protected VolleyHealthDataSender getSender() {
        return VolleyPulseSender.instance();
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new MyPulseAdapter();
    }

    @Override
    protected int getLayoutHistoryLayoutId() {
        return R.layout.layout_pulse_history;
    }

    class MyPulseAdapter extends  MyAdapter{

        @NonNull
        @Override
        protected MyHolder getMyHolder(View view) {
            return new MyPulseholder(view);
        }

        @Override
        protected int getItemLayoutId() {
            return R.layout.layout_bs_item;
        }

        @Override
        protected void bindDataOnViewHolder(MyHolder holder, int position) {
            Pulse bs = (Pulse) data.get(position);
            MyPulseholder bSholder = (MyPulseholder) holder;
            bSholder.tv_date.setText(parseDate(bs.getDate()));
            bSholder.tv_data.setText(bs.getPulse()+"");
        }


    }

    class MyPulseholder extends MyHolder{
        @InjectView(R.id.tv_date)
        TextView tv_date;

        @InjectView(R.id.tv_data)
        TextView tv_data;

        public MyPulseholder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }


}
