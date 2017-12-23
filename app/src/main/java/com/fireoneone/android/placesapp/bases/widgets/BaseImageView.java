package com.fireoneone.android.placesapp.bases.widgets;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.fireoneone.android.placesapp.utils.Validator;

import java.io.File;

import jp.wasabeef.glide.transformations.CropTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class BaseImageView extends android.support.v7.widget.AppCompatImageView {
    public static final String TAG = "BaseImageView";

    private float heightByWidth = 0;
    private float widthByHeight = 0;

    public interface LoadResourceCallback {
        DrawableRequestBuilder<Integer> onBuild(DrawableRequestBuilder<Integer> drb);
    }

    public interface LoadFileCallback {
        DrawableRequestBuilder<File> onBuild(DrawableRequestBuilder<File> drb);
    }

    public interface DownloadImageCallback {
        DrawableRequestBuilder<String> onBuild(DrawableRequestBuilder<String> drb);
    }

    public BaseImageView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public BaseImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public BaseImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public void init(Context context, AttributeSet attrs, int defStyleAttr) {
        if (isInEditMode()) {
            return;
        }
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        if (widthByHeight != 0) {
            width = (int) (height * widthByHeight);
        } else if (heightByWidth != 0) {
            height = (int) (width * heightByWidth);
        }

        setMeasuredDimension(width, height);
    }

    public void loadGif(Context context, int resourceId) {
        Glide.with(context).load(resourceId).into(new GlideDrawableImageViewTarget(this));
    }

    public void loadResource(Context context, int resourceId,
                             LoadResourceCallback loadResourceCallback) {
        loadResource(Glide.with(context), resourceId, loadResourceCallback);
    }

    public void loadResource(Activity activity, int resourceId,
                             LoadResourceCallback loadResourceCallback) {
        loadResource(Glide.with(activity), resourceId, loadResourceCallback);
    }

    public void loadResource(FragmentActivity fragmentActivity, int resourceId,
                             LoadResourceCallback loadResourceCallback) {
        loadResource(Glide.with(fragmentActivity), resourceId, loadResourceCallback);
    }

    public void loadResource(Fragment fragment, int resourceId,
                             LoadResourceCallback loadResourceCallback) {
        loadResource(Glide.with(fragment), resourceId, loadResourceCallback);
    }

    protected void loadResource(RequestManager requestManager, int resourceId,
                                LoadResourceCallback loadResourceCallback) {
        DrawableRequestBuilder<Integer> drb = requestManager.load(resourceId);

        if (loadResourceCallback != null) {
            drb = loadResourceCallback.onBuild(drb);
        }

        drb.into(this);
    }

    public void loadFile(Context context, File file, LoadFileCallback loadFileCallback) {
        loadFile(Glide.with(context), file, loadFileCallback);
    }

    public void loadFile(Activity activity, File file, LoadFileCallback loadFileCallback) {
        loadFile(Glide.with(activity), file, loadFileCallback);
    }

    public void loadFile(FragmentActivity fragmentActivity, File file,
                         LoadFileCallback loadFileCallback) {
        loadFile(Glide.with(fragmentActivity), file, loadFileCallback);
    }

    public void loadFile(Fragment fragment, File file, LoadFileCallback loadFileCallback) {
        loadFile(Glide.with(fragment), file, loadFileCallback);
    }

    protected void loadFile(RequestManager requestManager, File file,
                            LoadFileCallback loadFileCallback) {
        if (file == null || !Validator.isValid(file.getPath())) {
            return;
        }

        DrawableRequestBuilder<File> drb = requestManager.load(file);

        if (loadFileCallback != null) {
            drb = loadFileCallback.onBuild(drb);
        }

        drb.into(this);
    }

    public void downloadImage(Context context, String url,
                              DownloadImageCallback downloadImageCallback) {
        downloadImage(Glide.with(context), url, downloadImageCallback);
    }

    public void downloadImage(Activity activity, String url,
                              DownloadImageCallback downloadImageCallback) {
        downloadImage(Glide.with(activity), url, downloadImageCallback);
    }

    public void downloadImage(FragmentActivity fragmentActivity, String url,
                              DownloadImageCallback downloadImageCallback) {
        downloadImage(Glide.with(fragmentActivity), url, downloadImageCallback);
    }

    public void downloadImage(Fragment fragment, String url,
                              DownloadImageCallback downloadImageCallback) {
        downloadImage(Glide.with(fragment), url, downloadImageCallback);
    }

    protected void downloadImage(RequestManager requestManager, String url,
                                 DownloadImageCallback downloadImageCallback) {
        if (!Validator.isValid(url)) {
            return;
        }

        DrawableRequestBuilder<String> drb = requestManager.load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        if (downloadImageCallback != null) {
            drb = downloadImageCallback.onBuild(drb);
        }

        drb.into(this);
    }

    /**
     * Set image from String Path
     *
     * @param path path of image
     */
    public void setImageStringPath(String path) {
        Uri uri = Uri.parse(path);
        super.setImageURI(uri);
        setImageDrawable(getDrawable());
    }

    public static final DownloadImageCallback getDownloadImageCallback(final Context context,
                                                                       final int radius,
                                                                       final RoundedCornersTransformation.CornerType cornerType,
                                                                       final int width,
                                                                       final int height) {
        return new BaseImageView.DownloadImageCallback() {
            @Override
            public DrawableRequestBuilder<String> onBuild(DrawableRequestBuilder<String> drb) {
                Resources resources = context.getResources();

                drb = drb.centerCrop();

                if (width > 0 && height > 0) {
                    drb = drb.bitmapTransform(new CropTransformation(
                                    context,
                                    resources.getDimensionPixelSize(width),
                                    resources.getDimensionPixelSize(height),
                                    CropTransformation.CropType.CENTER
                            ),
                            new RoundedCornersTransformation(
                                    context,
                                    resources.getDimensionPixelSize(radius),
                                    0,
                                    cornerType
                            ));
                } else {
                    drb = drb.bitmapTransform(
                            new RoundedCornersTransformation(
                                    context,
                                    resources.getDimensionPixelSize(radius),
                                    0,
                                    cornerType
                            ));
                }

                return drb;
            }
        };
    }
}
