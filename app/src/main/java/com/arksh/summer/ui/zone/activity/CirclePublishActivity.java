package com.arksh.summer.ui.zone.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.arksh.common.app.AppCache;
import com.arksh.common.base.BaseActivity;
import com.arksh.common.utils.ImageLoaderUtils;
import com.arksh.common.utils.ToastUitl;
import com.arksh.common.widget.NoScrollGridView;
import com.arksh.common.widget.NormalTitleBar;
import com.arksh.summer.R;
import com.arksh.summer.app.AppConstant;
import com.arksh.summer.ui.zone.adapter.NinePicturesAdapter;
import com.arksh.summer.ui.zone.bean.CircleItem;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CirclePublishActivity extends BaseActivity {

    @BindView(R.id.ntb)
    NormalTitleBar ntb;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.view_gad)
    View viewGad;
    @BindView(R.id.gridview)
    NoScrollGridView gridview;

    private NinePicturesAdapter ninePicturesAdapter;
    private int REQUEST_CODE=120;

    public static void startAction(Context context) {
        Intent intent = new Intent(context, CirclePublishActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_circle_publish;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        ntb.setTitleText(getString(R.string.zone_publish_title));
        ninePicturesAdapter = new NinePicturesAdapter(this,9, positin -> choosePhoto());
        gridview.setAdapter(ninePicturesAdapter);
    }

    @OnClick({R.id.tv_back,R.id.tv_save})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_save:
                if(!TextUtils.isEmpty(etContent.getText().toString())) {
                    CircleItem circleItem = new CircleItem();
                    circleItem.setContent(etContent.getText().toString());
                    circleItem.setPictures(getPictureString());
                    circleItem.setIcon(AppCache.getInstance().getIcon());
                    circleItem.setUserId(AppCache.getInstance().getUserId());
                    circleItem.setNickName(AppCache.getInstance().getUserName());
                    circleItem.setCreateTime(Long.parseLong("1471942968000"));
                    mRxManager.post(AppConstant.ZONE_PUBLISH_ADD,circleItem);
                    finish();
                }else{
                    ToastUitl.showToastWithImg(getString(R.string.circle_publish_empty),R.drawable.ic_warm);
                }
                break;
        }
    }

    private ImageLoader loader = (ImageLoader) (context, path, imageView) -> ImageLoaderUtils.display(context,imageView,path);

    /**
     * 开启图片选择器
     */
    private void choosePhoto() {
        ImgSelConfig config = new ImgSelConfig.Builder(loader)
                // 是否多选
                .multiSelect(true)
                // 确定按钮背景色
                .btnBgColor(Color.TRANSPARENT)
                .titleBgColor(ContextCompat.getColor(this,R.color.main_color))
                // 使用沉浸式状态栏
                .statusBarColor(ContextCompat.getColor(this,R.color.main_color))
                // 返回图标ResId
                .backResId(R.drawable.ic_arrow_back)
                .title("图片")
                // 第一个是否显示相机
                .needCamera(true)
                // 最大选择图片数量
                .maxNum(9-ninePicturesAdapter.getPhotoCount())
                .build();
        ImgSelActivity.startActivity(this, config, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
            if(ninePicturesAdapter!=null){
                ninePicturesAdapter.addAll(pathList);
            }
        }
    }

    /**
     * 获取到拼接好的图片
     * @return
     */
    private String getPictureString(){
        //拼接图片链接
        List<String> strings = ninePicturesAdapter.getData();
        if (strings != null && strings.size() > 0) {
            StringBuilder allUrl = new StringBuilder();
            for (int i = 0; i < strings.size(); i++) {
                if (!TextUtils.isEmpty(strings.get(i))) {
                    allUrl.append(strings.get(i) + ";");
                }
            }
            if (!TextUtils.isEmpty(allUrl)) {
                String url = allUrl.toString();
                url = url.substring(0, url.lastIndexOf(";"));
                return url;
            }else{
                return "";
            }
        }else{
            return "";
        }
    }

}
