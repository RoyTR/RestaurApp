package upc.edu.pe.restaurapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Roy on 06/07/2015.
 */
public class ImageFotoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_layout);

        Intent intent = getIntent();

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(intent.getStringExtra("url"), (ImageView)findViewById(R.id.restaurante_recomendacion_ver_foto_normal));
        findViewById(R.id.textView123456).setVisibility(View.GONE);
    }
}
