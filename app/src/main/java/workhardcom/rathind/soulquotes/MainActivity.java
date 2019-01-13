package workhardcom.rathind.soulquotes;

import android.annotation.SuppressLint;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.MotionEvent;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {



    private ConstraintLayout rootContent;
    private FloatingActionButton share;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootContent = findViewById(R.id.bgimage);
        setContentView(R.layout.activity_main);
//        Share Floating button
        share = findViewById(R.id.share);

        getBacground();
        getRandomApi();
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Share button clicked", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                takeScreenshot();


            }
        });

    }

    private void takeScreenshot() {
        Bitmap b = null;
        b = ScreenshotUtils.getScreenShot(rootContent);

        //If bitmap is not null
        if (b != null) {

            File saveFile = ScreenshotUtils.getMainDirectoryName(this);//get the path to save screenshot
            File file = ScreenshotUtils.store(b, "screenshot" + ".jpg", saveFile);//save the screenshot to selected path
            shareScreenshot(file);//finally share screenshot
        } else
            //If bitmap is null show toast message
            Toast.makeText(this, "screenshot_take_failed", Toast.LENGTH_SHORT).show();

    }

    private void shareScreenshot(File file) {
        Uri photoUri =FileProvider.getUriForFile(MainActivity.this,getString(R.string.file_provider_authority), file);
//        Uri uri = Uri.fromFile(file);//Convert file path into Uri for sharing
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "Sharing");
        intent.putExtra(Intent.EXTRA_STREAM, photoUri);//pass uri here
        startActivity(Intent.createChooser(intent, "Share via"));
    }


    private void getRandomApi() {
        Call<RandomQuotes> randomQuotesCall = QuotesApi.getQuotesService().getContents();
        randomQuotesCall.enqueue(new Callback<RandomQuotes>() {
            @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
            @Override
            public void onResponse(Call<RandomQuotes> call, Response<RandomQuotes> response) {
                RandomQuotes contents = response.body();
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                //    quote
                String quote = contents.getQuoteText();
                TextView quoteTxt = findViewById(R.id.quotes);
                quoteTxt.setText(quote);
//Author name
                String author = contents.getQuoteAuthor();
                TextView authorTxt = findViewById(R.id.authorNameTxtView);
                authorTxt.setText("-" + author);
//Background
                ConstraintLayout bgImg = findViewById(R.id.bgimage);

                  bgImg.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {

                    public void onSwipeRight() {
                        Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
                    }

                    public void onSwipeLeft() {
                        getRandomApi();
                        Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
                    }

                });


            }


            @Override
            public void onFailure(Call<RandomQuotes> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getBacground() {
        rootContent = findViewById(R.id.bgimage);
        rootContent.setBackground(ContextCompat.getDrawable(this,R.drawable.picsart4));
    }

}
