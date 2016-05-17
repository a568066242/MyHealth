package com.bishe.lzj.myhealth.UI.DataCollect;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.bishe.lzj.myhealth.Bean.BloodPressure;
import com.bishe.lzj.myhealth.Logic.DataSender.Impl.VolleyBPSender;
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
public class BPHistoryFragment extends AbstractHistoryFragment{

    @Override
    protected List<Line> getLines() {
        List<Line> lines = new ArrayList<>();

        List<PointValue> sbps = new ArrayList<>();
        List<PointValue> dbps = new ArrayList<>();
        for (int i = 0; i < 10 && i < data.size(); i++) {
            BloodPressure bp = (BloodPressure) data.get(i);
            sbps.add(new PointValue(i,bp.getSBP()));
            dbps.add(new PointValue(i,bp.getDBP()));
        }
        lines.add(new Line(sbps));
        lines.add(new Line(dbps));
        return lines;
    }

    @Override
    protected String getTAG() {
        return "BPHistoryFragment";
    }

    @Override
    protected VolleyHealthDataSender getSender() {
        return VolleyBPSender.instance();
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new MyBPAdapter();
    }

    @Override
    protected int getLayoutHistoryLayoutId() {
        return R.layout.layout_bp_history;
    }

    class MyBPAdapter extends MyAdapter{


        @NonNull
        @Override
        protected MyHolder getMyHolder(View view) {
            return new MyBPViewHolder(view);
        }

        @Override
        protected int getItemLayoutId() {
            return R.layout.layout_bp_item;
        }

        @Override
        protected void bindDataOnViewHolder(MyHolder holder,int position) {
            BloodPressure o = (BloodPressure) data.get(position);
            MyBPViewHolder holder1 = (MyBPViewHolder) holder;
            holder1.tv_date.setText(parseDate(o.getDate()));
            holder1.tv_sbp.setText(String.valueOf(o.getSBP()));
            holder1.tv_dbp.setText(String.valueOf(o.getDBP()));
        }

       class MyBPViewHolder extends  MyHolder{

           @InjectView(R.id.tv_date)
           TextView tv_date;
           @InjectView(R.id.tv_sbp)
           TextView tv_sbp;

           @InjectView(R.id.tv_dbp)
           TextView tv_dbp;

           public MyBPViewHolder(View itemView) {
               super(itemView);
               ButterKnife.inject(this,itemView);
           }
       }


    }
}
