package com.andryr.musicplayer.images;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.andryr.musicplayer.animation.TransitionDrawable;

import java.lang.ref.WeakReference;

/**
 * Created by Andry on 23/01/16.
 */
abstract public class AbstractBitmapCache<K> {

    private Drawable mDefaultDrawable;

    public Bitmap getBitmap(K key, int w, int h) {
        Bitmap b = getCachedBitmap(key, w, h);
        if (b != null) {
            return b;
        }

        b = retrieveBitmap(key, w, h);
        if (b != null) {
            cacheBitmap(key, b);
        }

        return b;
    }

    abstract public Bitmap getCachedBitmap(K key, int w, int h);

    abstract protected Bitmap retrieveBitmap(K key, int w, int h);

    abstract protected void cacheBitmap(K key, Bitmap bitmap);

    abstract protected Bitmap getDefaultBitmap();

    protected Drawable getDefaultDrawable(Context context) {
        if (mDefaultDrawable == null) {
            mDefaultDrawable = BitmapHelper.createBitmapDrawable(context, getDefaultBitmap());
        }
        return mDefaultDrawable;
    }

    public void loadBitmap(final K key, ImageView view, final int w, final int h) {
        Context context = view.getContext();

        Bitmap b = getCachedBitmap(key, w, h);
        if (b != null) {
            setBitmap(b, view);
            return;
        }
        view.setScaleType(ImageView.ScaleType.FIT_CENTER);
        view.setImageDrawable(getDefaultDrawable(context));


        final Object viewTag = view.getTag();

        final WeakReference<ImageView> viewRef = new WeakReference<>(view);
        new AsyncTask<Void, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(Void... params) {
                return retrieveBitmap(key, w, h);
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                ImageView view11 = viewRef.get();
                if (result != null) {
                    cacheBitmap(key, result);
                    if (view11 != null && viewTag == view11.getTag()) {
                        setBitmap(result, view11);
                    }
                }

            }
        }.execute();

    }

    public void loadBitmap(final K key, final int w, final int h, final Callback callback) {


        new AsyncTask<Void, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(Void... params) {
                return retrieveBitmap(key, w, h);
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                if (result != null) {
                    cacheBitmap(key, result);
                    callback.onBitmapLoaded(result);
                }
            }
        }.execute();

    }

    protected void setBitmap(Bitmap bitmap, ImageView imageView) {
        Context context = imageView.getContext();

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        TransitionDrawable transitionDrawable = new TransitionDrawable(getDefaultDrawable(context), BitmapHelper.createBitmapDrawable(context, bitmap));
        imageView.setImageDrawable(transitionDrawable);
        transitionDrawable.startTransition();
    }

    abstract public void clear();

    public interface Callback {
        void onBitmapLoaded(Bitmap bitmap);
    }
}
