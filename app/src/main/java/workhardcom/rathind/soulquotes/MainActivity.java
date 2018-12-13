package workhardcom.rathind.soulquotes;

import android.annotation.SuppressLint;

import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.MotionEvent;

import android.widget.TextView;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout mConstraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mConstraintLayout = findViewById(R.id.bgimage);
        setContentView(R.layout.activity_main);
        getBacground();
        getRandomApi();

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
        mConstraintLayout = findViewById(R.id.bgimage);
        mConstraintLayout.setBackground(ContextCompat.getDrawable(this,R.drawable.picsart4));
    }

}
