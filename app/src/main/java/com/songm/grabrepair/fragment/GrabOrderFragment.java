package com.songm.grabrepair.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.songm.grabrepair.GrabOrderInfoActivity;
import com.songm.grabrepair.R;
import com.songm.grabrepair.adapter.myorder.MyOrderAdapter;
import com.songm.grabrepair.model.myorder.Order;
import com.songm.grabrepair.utils.UrlUtils;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.util.PtrLocalDisplay;

/**
 * Created by SongM on 2017/9/19.
 * 抢单列表
 */

public class GrabOrderFragment extends Fragment {

    private PtrFrameLayout mPtrFrame;
    private ListView lv_grab_order_orderList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_grab_order, null);

        // 绑定控件
        lv_grab_order_orderList = (ListView) view.findViewById(R.id.lv_grab_order_orderList);
        mPtrFrame = (PtrFrameLayout) view.findViewById(R.id.ptr);

        // 设置列表点击监听
        lv_grab_order_orderList.setOnItemClickListener(new ItemClick());

        // 自动加载订单列表
        //getMyOrderList();

        /**
         * 经典 风格的头部实现
         */
        final PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(getContext());
        header.setPadding(0, PtrLocalDisplay.dp2px(15), 0, 0);

        mPtrFrame.setHeaderView(header);

        mPtrFrame.addPtrUIHandler(header);

        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {

            //需要加载数据时触发
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPtrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getMyOrderList();
                    }
                }, 0);
            }

            /**
             * 检查是否可以执行下来刷新，比如列表为空或者列表第一项在最上面时。
             */
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                // 默认实现，根据实际情况做改动
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });

        return view;
    }

    // 加载抢修单列表
    private void getMyOrderList() {
        // 取出我的信息
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("info", getContext().MODE_PRIVATE);
        // OkGo
        OkGo.<String>get(UrlUtils.orderByPass)
                .tag(getContext())
                .execute(new StringCallback() {
                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mPtrFrame.refreshComplete();
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        Order order = new Gson().fromJson(response.body(), Order.class);
                        if(order.getFind().equals("success")) {
                            lv_grab_order_orderList.setAdapter(new MyOrderAdapter(getContext(), order.getOrderList()));
                        }
                    }
                });
    }

    // 内部类监听器
    class ItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // 取出orderId
            Order.OrderListBean orderListBean = (Order.OrderListBean) parent.getItemAtPosition(position);
            int orderId = orderListBean.getOrderId();
            // 跳转到报修单详细页面
            Intent intent = new Intent(getContext(), GrabOrderInfoActivity.class);
            intent.putExtra("orderId", orderId);
            startActivity(intent);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getMyOrderList(); // 重新加载数据
    }
}
