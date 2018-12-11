package workhardcom.rathind.soulquotes;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getRandomApi();
    }

    private void getRandomApi()
    {
        Call<RandomQuotes> randomQuotesCall = QuotesApi.getQuotesService().getContents();
        randomQuotesCall.enqueue(new Callback<RandomQuotes>() {
            @SuppressLint("SetTextI18n")
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
                authorTxt.setText("-"+author);
//Background
                ImageView bgImg = findViewById(R.id.bgimage);

            }

            @Override
            public void onFailure(Call<RandomQuotes> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void getBacground(){

    }



//    private void getData()
//    {
//        Call<QuoteOfTheDay> quoteOfTheDayCall = QuotesApi.getQuotesService().getContents();
//        quoteOfTheDayCall.enqueue(new Callback<QuoteOfTheDay>() {
//            @Override
//            public void onResponse(Call<QuoteOfTheDay> call, Response<QuoteOfTheDay> response) {
//                QuoteOfTheDay contents = response.body();
//                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
////    SharedPreferences
//               mPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
//               mEditor= mPreferences.edit();
//
////    quote
//                String quote = contents.getContents().getQuotes().get(0).getQuote();
//                TextView quoteTxt = findViewById(R.id.quotes);
//
////    putting fetched quote inside SharedPreferences
//                mEditor.putString("quote", quote);
//                mEditor.apply();
//                String shQuote = mPreferences.getString("quote","Empty");
//                quoteTxt.setText(shQuote);
//
//
////    background image
//                String bacground_url = contents.getContents().getQuotes().get(0).getBackground();
//                ImageView bgImg = findViewById(R.id.bgimage);
//
////    putting fetched quotes inside sharedPreference
//                mEditor.putString("BgImage",bacground_url);
//                mEditor.apply();
//                String shBacground_url = mPreferences.getString("BgImage", "Empty");
//
//
//                Glide.with(MainActivity.this)
//                        .load(shBacground_url)
//                        .into(bgImg);
//
//            }
//
//            @Override
//            public void onFailure(Call<QuoteOfTheDay> call, Throwable t) {
//                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
//
//
//            }
//        });
//    }
}
