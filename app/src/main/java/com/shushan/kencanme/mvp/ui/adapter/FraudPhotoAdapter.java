package com.shushan.kencanme.mvp.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.kencanme.R;
import com.shushan.kencanme.help.ImageLoaderHelper;

import org.devio.takephoto.model.TImage;

import java.util.ArrayList;

/**
 * PhotoAdapter
 */
public class FraudPhotoAdapter extends BaseQuickAdapter<TImage, BaseViewHolder> {

    private ImageLoaderHelper mImageLoaderHelper;
    Context mContext;

    public FraudPhotoAdapter(Context context, @Nullable ArrayList<TImage> data, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.photo_item, data);
        mImageLoaderHelper = imageLoaderHelper;
        mContext = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, TImage item) {
        helper.addOnClickListener(R.id.photo_delete).addOnClickListener( R.id.photo_item_rl);
        ImageView imageView = helper.getView(R.id.photo_iv);
        if (helper.getAdapterPosition() == 0) {
            helper.setVisible(R.id.photo_delete, false);
        } else {
            helper.setVisible(R.id.photo_delete, true);
        }
        if (item != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(item.getOriginalPath());
            imageView.setImageBitmap(bitmap);
        }
    }
}
