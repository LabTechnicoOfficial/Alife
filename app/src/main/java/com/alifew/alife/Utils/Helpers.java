package com.alifew.alife.Utils;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alife.BuildConfig;
import com.alifew.alife.R;
import com.gkemon.XMLtoPDF.PdfGenerator;
import com.gkemon.XMLtoPDF.PdfGeneratorListener;
import com.gkemon.XMLtoPDF.model.FailureResponse;
import com.gkemon.XMLtoPDF.model.SuccessResponse;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Helpers {

    public static String uniqueProductCodeGenerator(String text) {


        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyMMddHHmmss").format(Calendar.getInstance().getTime());

        if (text.length() > 0) {
            text = text.substring(0, 2).toLowerCase(Locale.ROOT) + "al";
            return text + timeStamp;
        } else {
            return timeStamp;
        }


    }

    public static Bitmap barCodeGenerator(Context context, String barCode) {
        //write code here
        Bitmap bitmap = null;
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.encodeBitmap(barCode, BarcodeFormat.CODE_128, 800, 400);


        } catch (Exception e) {

        }
        return bitmap;
    }

    public static void createPDF(View view, Context context, String fileName) {

        GeneratePDF.getBuilder()
                .setContext(context)
                .fromViewSource()
                .fromView(view)
                .setFileName(generateFileName(fileName))
                .setFolderName("Alife")
                .openPDFafterGeneration(true)
                .build(new PdfGeneratorListener() {
                    @Override
                    public void onFailure(FailureResponse failureResponse) {
                        super.onFailure(failureResponse);
                        Log.d("dataxx", "onFailure: " + failureResponse.getErrorMessage());
                    }

                    @Override
                    public void showLog(String log) {
                        super.showLog(log);

                        Log.d("dataxx", "showLog: " + log);
                    }

                    @Override
                    public void onStartPDFGeneration() {
                        /*When PDF generation begins to start*/
                    }

                    @Override
                    public void onFinishPDFGeneration() {
                        /*When PDF generation is finished*/
                    }

                    @Override
                    public void onSuccess(SuccessResponse response) {
                        super.onSuccess(response);

                        Log.d("dataxx", "onSuccesPATH: " + response.getPath() + " ab: " + response.getFile().getAbsolutePath());
                        Toast.makeText(context, context.getResources().getString(R.string.file_save) + Html.fromHtml(" \n<b>" + response.getPath() + "<b>"), Toast.LENGTH_SHORT).show();

                        //openPdf(response.getPath(), context);

                    }
                });

    }


    @SuppressLint("SimpleDateFormat")
    public static String generateFileName(String fileName) {


        return fileName + new SimpleDateFormat("yyMMddHHmmss").format(Calendar.getInstance().getTime());
    }

    public static Bitmap loadBitmap(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    public static Bitmap createBitmapFromScrollView(ScrollView scrollView) {
        if (scrollView == null) {
            return null;
        }/*from  www. j  a  v a 2 s .  co  m*/
        int totalHeight = scrollView.getChildAt(0).getHeight();
        int totalWidth = scrollView.getChildAt(0).getWidth();
        Bitmap b = getBitmapFromView(scrollView, totalHeight, totalWidth);
        return b;
    }

    public static Bitmap getBitmapFromView(View view, int totalHeight,
                                           int totalWidth) {
        Bitmap returnedBitmap = Bitmap.createBitmap(totalWidth,
                totalHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return returnedBitmap;
    }

    public static Bitmap getScreenshotFromRecyclerView(RecyclerView view,Context context, int column) {
        view.setHasFixedSize(true);
        view.setLayoutManager(new GridLayoutManager(context, column));
        RecyclerView.Adapter adapter = view.getAdapter();
        Bitmap bigBitmap = null;
        if (adapter != null) {
            int size = adapter.getItemCount();
            int height = 0;
            Paint paint = new Paint();
            int iHeight = 0;
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

            // Use 1/8th of the available memory for this memory cache.
            final int cacheSize = maxMemory / 8;
            LruCache<String, Bitmap> bitmaCache = new LruCache<>(cacheSize);
            for (int i = 0; i < size; i++) {
                RecyclerView.ViewHolder holder = adapter.createViewHolder(view, adapter.getItemViewType(i));
                adapter.onBindViewHolder(holder, i);
                holder.itemView.measure(View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(), holder.itemView.getMeasuredHeight());
                holder.itemView.setDrawingCacheEnabled(true);
                holder.itemView.buildDrawingCache();
                Bitmap drawingCache = holder.itemView.getDrawingCache();
                if (drawingCache != null) {

                    bitmaCache.put(String.valueOf(i), drawingCache);
                }
//                holder.itemView.setDrawingCacheEnabled(false);
//                holder.itemView.destroyDrawingCache();
                height += holder.itemView.getMeasuredHeight();
            }

            bigBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), height, Bitmap.Config.ARGB_8888);
            Canvas bigCanvas = new Canvas(bigBitmap);
            bigCanvas.drawColor(Color.WHITE);

            for (int i = 0; i < size; i++) {
                Bitmap bitmap = bitmaCache.get(String.valueOf(i));
                bigCanvas.drawBitmap(bitmap, 0f, iHeight, paint);
                iHeight += bitmap.getHeight();
                bitmap.recycle();
            }

        }
        return bigBitmap;
    }

    public static void openPdf(String filePath, Context context) {
        File file = new File(filePath);
        if (file.exists()) {

            Uri uriPdfPath = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);

            Intent pdfOpenIntent = new Intent(Intent.ACTION_VIEW);
            pdfOpenIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            pdfOpenIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            pdfOpenIntent.setClipData(ClipData.newRawUri("", uriPdfPath));
            pdfOpenIntent.setDataAndType(uriPdfPath, "application/pdf");
            pdfOpenIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION |  Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            try {
                context.startActivity(pdfOpenIntent);
            } catch (ActivityNotFoundException activityNotFoundException) {
                Toast.makeText(context,"There is no app to load corresponding PDF",Toast.LENGTH_LONG).show();

            }
        }
    }
}
