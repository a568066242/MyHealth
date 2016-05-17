package com.bishe.lzj.myhealth.UI.DieaseIntro;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bishe.lzj.myhealth.Bean.DieaseCategory;
import com.bishe.lzj.myhealth.Logic.DataSender.VolleyDataSender;
import com.bishe.lzj.myhealth.R;
import com.bishe.lzj.myhealth.UI.MainActivity;
import com.bishe.lzj.myhealth.Util.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;


public abstract class BaseDieaseIntroFragment extends MainActivity.PlaceholderFragment {

    private RecyclerView rv;
    private List<DieaseCategory> categories;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dieaseintro_main,container,false);
        rv = (RecyclerView) view.findViewById(R.id.recyclerview);
        rv.setLayoutManager(new LinearLayoutManager(container.getContext()));
        get();
        return view;
    }


    protected  void get(){
        StringRequest sr = new StringRequest(VolleyDataSender.getBaseURL() + "/diease" + getParamUrl(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Gson gson = new Gson();
                        categories =  gson.fromJson(s,new TypeToken<List<DieaseCategory>>(){}.getType());
                        rv.setAdapter(new MyAdapter());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtil.showShort("请求数据失败");
            }
        });
        Volley.newRequestQueue(getActivity()).add(sr);
    }

    /**
     * 获取子类的额外url进行拼接
     * @return
     */
    protected abstract String getParamUrl();

    private class MyAdapter extends RecyclerView.Adapter<MyHolder>{

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_dieasecategory_item,parent,false);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, final int position) {
            holder.tv_name.setText(categories.get(position).getName());
            holder.self.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BaseDieaseIntroFragment subFragment = getSubFragment(categories.get(position).getId());
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.hide(BaseDieaseIntroFragment.this);
                    fragmentTransaction.add(R.id.container_main,subFragment);
//                    fragmentTransaction.replace(R.id.container_main,subFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
        }

        @Override
        public int getItemCount() {
            return categories.size();
        }
    }

    /**
     * 获取点击进去的fragment
     * @param id 点击的id，作为查询参数
     * @return
     */
    protected abstract BaseDieaseIntroFragment getSubFragment(int id);

    private class MyHolder extends RecyclerView.ViewHolder{
        private TextView tv_name;
        private View self;
        public MyHolder(View itemView) {
            super(itemView);
            self = itemView;
            tv_name = (TextView) self.findViewById(R.id.diease_name);

        }
    }


}
