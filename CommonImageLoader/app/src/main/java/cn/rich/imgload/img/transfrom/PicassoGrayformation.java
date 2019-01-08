package cn.rich.imgload.img.transfrom;

import android.content.Context;
import android.graphics.*;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import cn.rich.imgload.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.IOException;

import static android.graphics.Bitmap.createBitmap;
import static android.graphics.Paint.ANTI_ALIAS_FLAG;
import static android.graphics.Shader.TileMode.REPEAT;

public class PicassoGrayformation implements Transformation {

    private Context context;
    private final Picasso picasso;

    public PicassoGrayformation(Context context, Picasso picasso) {
        this.context = context;
        this.picasso = picasso;
    }
    @Override
    public Bitmap transform(Bitmap source) {
        //return BlurBitmapUtil.instance().blurBitmap(context, bitmap, 20);


//        Bitmap.Config config =
//                toTransform.getConfig() != null ? toTransform.getConfig() : Bitmap.Config.ARGB_8888;
//        Bitmap bitmap = pool.get(width, height, config);
        Bitmap bitmap = source.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bitmap);
        ColorMatrix saturation = new ColorMatrix();
        saturation.setSaturation(0f);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(saturation));
        canvas.drawBitmap(source, 0, 0, paint);
        source.recycle();
        return bitmap;

    }

    @Override
    public String key() {
        return "blur";
    }


}
