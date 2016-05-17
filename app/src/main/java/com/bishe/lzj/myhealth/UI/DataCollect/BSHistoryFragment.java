package com.bishe.lzj.myhealth.UI.DataCollect;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bishe.lzj.myhealth.Bean.BloodSugar;
import com.bishe.lzj.myhealth.Logic.DataSender.Impl.VolleyBSSender;
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
public class BSHistoryFragment extends AbstractHistoryFragment {


    @Override
    protected List<Line> getLines() {
        List<Line> lines = new ArrayList<>();
        List<PointValue> datas = new ArrayList<>();
        for (int i = 0; i < 10 && i < data.size(); i++) {
            BloodSugar bp = (BloodSugar) data.get(i);
            datas.add(new PointValue(i, (float) bp.getBS()));
        }
        lines.add(new Line(datas));
        return lines;
    }

    @Override
    protected String getTAG() {
        return "BSHistoryFragment";
    }

    @Override
    protected VolleyHealthDataSender getSender() {
        return VolleyBSSender.instance();
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new MyBSAdapter();
    }

    @Override
    protected int getLayoutHistoryLayoutId() {
        return R.layout.layout_bs_history;
    }

    class MyBSAdapter extends  MyAdapter{

        @NonNull
        @Override
        protected MyHolder getMyHolder(View view) {
            return new MyBSholder(view);
        }

        @Override
        protected int getItemLayoutId() {
            return R.layout.layout_bs_item;
        }

        @Override
        protected void bindDataOnViewHolder(MyHolder holder, int position) {
            BloodSugar bs = (BloodSugar) data.get(position);
            MyBSholder bSholder = (MyBSholder) holder;
            bSholder.tv_date.setText(parseDate(bs.getDate()));
            bSholder.tv_data.setText(bs.getBS()+"");
        }


    }

    class MyBSholder extends MyHolder{
        @InjectView(R.id.tv_date)
        TextView tv_date;

        @InjectView(R.id.tv_data)
        TextView tv_data;

        public MyBSholder(View itemView) {
            super(itemView);
            ButterKnife.inject(this,itemView);
        }
    }
}
