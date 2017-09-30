package com.songm.grabrepair;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.songm.grabrepair.model.orderinfo.Img;
import com.songm.grabrepair.model.orderinfo.Order;
import com.songm.grabrepair.utils.UrlUtils;
import com.wenhuaijun.easyimageloader.imageLoader.EasyImageLoader;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by SongM on 2017/9/16.
 * 订单信息
 */

public class GrabOrderInfoActivity extends AppCompatActivity {

    private ImmersionBar immersionBar;
    private TextView tv_order_info_orderRoom, tv_order_info_orderId, tv_order_info_dateTime, tv_order_info_orderState, tv_order_info_orderInfo;
    private Button btn_grab_order_info_grabOrder;
    private LinearLayout ll_order_info_orderImg;
    private Order.OrderBean orderBean;
    private int orderId;
    private SweetAlertDialog sweetAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grab_order_info);
        immersionBar = ImmersionBar.with(this);
        immersionBar.statusBarColor(R.color.colorPrimary); // 状态栏颜色，不写默认透明色
        immersionBar.init();

        // 标题返回
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // 绑定所有控件
        tv_order_info_orderRoom = (TextView) findViewById(R.id.tv_order_info_orderRoom);
        tv_order_info_orderId = (TextView) findViewById(R.id.tv_order_info_orderId);
        tv_order_info_dateTime = (TextView) findViewById(R.id.tv_order_info_dateTime);
        tv_order_info_orderState = (TextView) findViewById(R.id.tv_order_info_orderState);
        tv_order_info_orderInfo = (TextView) findViewById(R.id.tv_order_info_orderInfo);
        btn_grab_order_info_grabOrder = (Button) findViewById(R.id.btn_grab_order_info_grabOrder);

        // 取到订单ID
        orderId = getIntent().getIntExtra("orderId", 0);

        gohttp();
    }

    // 网络请求
    private void gohttp() {
        // 清空图片缓存
        try {
            ll_order_info_orderImg.removeAllViews();
        } catch (Exception e) {

        }

        // 查询订单详细信息GET请求
        OkGo.<String>get(UrlUtils.orderFindByOrderId(orderId)) // 请求方式和请求url
                .tag(this) // 请求的 tag, 主要用于取消对应的请求
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (response.code() == 200) {
                            Order order = new Gson().fromJson(response.body(), Order.class);
                            if (order.getFind().equals("success")) {
                                orderBean = order.getOrder();
                                tv_order_info_orderRoom.setText(orderBean.getOrderRoom());
                                tv_order_info_orderId.setText("单号：" + orderBean.getOrderId());
                                tv_order_info_dateTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(orderBean.getOrderStartTime())));
                                String orderState = "";
                                switch (orderBean.getOrderState()) {
                                    case 1:
                                        orderState = "正在审核";
                                        break;
                                    case 2:
                                        orderState = "审核通过";
                                        btn_grab_order_info_grabOrder.setText("抢单");
                                        btn_grab_order_info_grabOrder.setClickable(true); // 将抢单价按钮激活
                                        break;
                                    case 3:
                                        orderState = "审核失败";
                                        break;
                                    case 11:
                                        orderState = "正在维修";
                                        break;
                                    case 12:
                                        orderState = "维修完成";
                                        break;
                                    case 13:
                                        orderState = "维修失败";
                                        break;
                                }
                                tv_order_info_orderState.setText(orderState);
                                tv_order_info_orderInfo.setText(orderBean.getOrderInfo());
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        Toast.makeText(getApplicationContext(), "请求出错了！", Toast.LENGTH_SHORT).show();
                        super.onError(response);
                    }
                });

        // 获取报修订单的图片
        OkGo.<String>get(UrlUtils.imgFindByOrderId(orderId)) // 请求方式和请求url
                .tag(this) // 请求的 tag, 主要用于取消对应的请求
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (response.code() == 200) {
                            Img img = new Gson().fromJson(response.body(), Img.class);
                            if (img.getFind().equals("success")) {
                                List<Img.ImgListBean> imgList = img.getImgList();
                                for (int i = 0; i < imgList.size(); i++) {
                                    String imgUrl = UrlUtils.HOST + imgList.get(i).getImgUrl();
                                    setImg(imgUrl);
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        Toast.makeText(getApplicationContext(), "请求出错了！", Toast.LENGTH_SHORT).show();
                        super.onError(response);
                    }
                });
    }

    // 获取并加载图片
    private void setImg(final String imgUrl) {
        // 将图片添加到布局中
        ll_order_info_orderImg = (LinearLayout) findViewById(R.id.ll_order_info_orderImg);
        ImageView imageView = new ImageView(getApplicationContext());
        // 设置图片大小及间距
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(250, 500);
        lp.setMargins(15, 0, 15, 0);
        imageView.setLayoutParams(lp);
        // 根据图片url给imageView加载图片，自动本地缓存、内存缓存，注意Context需使用ApplicationContext，否则会导致内存泄露
        EasyImageLoader.getInstance(getApplicationContext()).bindBitmap(imgUrl, imageView);
        ll_order_info_orderImg.addView(imageView);

        // 图片点击事件
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LookImageActivity.class);
                intent.putExtra("imgUrl", imgUrl);
                startActivity(intent);
            }
        });
    }

    // orderInfoMore
    public void click_orderInfoMore(View view) {
        Intent intent = new Intent(this, OrderInfoMoreActivity.class);
        // 取到创建人ID
        if (orderBean.getStuId() != null) {
            intent.putExtra("authorId", orderBean.getStuId());
            intent.putExtra("isStu", true);
        } else {
            intent.putExtra("authorId", orderBean.getHmrId().toString());
            intent.putExtra("isStu", false);
        }
        // 取到维修员ID
        String repairerId = null;
        if (orderBean.getRepairerId() != null) {
            repairerId = orderBean.getRepairerId().toString();
        }
        intent.putExtra("repairerId", repairerId);
        startActivity(intent);
    }

    // 抢单
    public void click_grabOrder(View view) {
        sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("是否接手该报修单?")
                .setCancelText("否")
                .setConfirmText("是")
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        // 查询订单状态
                        OkGo.<String>get(UrlUtils.orderFindByOrderId(orderId))
                                .tag(this)
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(Response<String> response) {
                                        Order order = new Gson().fromJson(response.body(), Order.class);
                                        if (order.getFind().equals("success") && order.getOrder().getOrderState() == 2) {
                                            // 取到维修员ID
                                            SharedPreferences sp = getSharedPreferences("info", MODE_PRIVATE);
                                            String repairerId = sp.getString("id", null);
                                            // 抢单网络请求
                                            OkGo.<String>post(UrlUtils.grabOrder)
                                                    .tag(this)
                                                    .params("orderId", orderId)
                                                    .params("repairerId", repairerId)
                                                    .execute(new StringCallback() {
                                                        @Override
                                                        public void onSuccess(Response<String> response) {
                                                            try {
                                                                JSONObject jsonObject = new JSONObject(response.body());
                                                                String get_state = jsonObject.getString("get");
                                                                if (get_state.equals("error")) {
                                                                    String reason = (String) jsonObject.get("reason");
                                                                    sweetAlertDialog.setTitleText(reason)
                                                                            .setConfirmText("OK")
                                                                            .showCancelButton(false)
                                                                            .setCancelClickListener(null)
                                                                            .setConfirmClickListener(null)
                                                                            .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                                                } else {
                                                                    sweetAlertDialog.setTitleText("成功!")
                                                                            .setConfirmText("OK")
                                                                            .showCancelButton(false)
                                                                            .setCancelClickListener(null)
                                                                            .setConfirmClickListener(null)
                                                                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                                                    gohttp(); // 重新调用网络请求
                                                                    btn_grab_order_info_grabOrder.setText("已抢");
                                                                    btn_grab_order_info_grabOrder.setClickable(false); // 将抢单价按钮屏蔽
                                                                }
                                                            } catch (Exception e) {

                                                            }
                                                        }
                                                    });
                                        } else {
                                            // 订单状态有变，显示和提示
                                            sweetAlertDialog.setTitleText("该订单已经被抢！")
                                                    .setConfirmText("OK")
                                                    .showCancelButton(false)
                                                    .setCancelClickListener(null)
                                                    .setConfirmClickListener(null)
                                                    .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                            gohttp(); // 重新调用网络请求
                                            btn_grab_order_info_grabOrder.setText("已抢");
                                            btn_grab_order_info_grabOrder.setClickable(false); // 将抢单价按钮屏蔽
                                        }
                                    }
                                });
                    }
                });
        sweetAlertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
            case R.id.action_refresh:
                try {
                    ll_order_info_orderImg.removeAllViews(); // 清空已加载的图片
                } catch (Exception e) {}
                gohttp(); // 重新调用网络请求
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        immersionBar.destroy(); //必须调用该方法，防止内存泄漏
        super.onDestroy();
    }
}
