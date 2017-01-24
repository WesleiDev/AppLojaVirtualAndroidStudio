package weslei.com.br.applojavirtual.async;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by weslei on 18/01/2017.
 */

public class AsyncImageHelper extends  AsyncTask<String, Void, Bitmap> {

    private ImageView imageView;
    private Bitmap bitmapImg = null;

    public AsyncImageHelper(ImageView imageView){
        this.imageView = imageView;

    }

    //Quando for executar corre este método
    @Override
    protected Bitmap doInBackground(String... urls) {
        String imgUrl = urls[0];

        try {
            InputStream inputStream = new URL(imgUrl).openStream();
            bitmapImg = BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmapImg;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}
