package workhardcom.rathind.soulquotes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getData();
    }


    private void getData()
    {
        Call<QuoteOfTheDay> quoteOfTheDayCall = QuotesApi.getQuotesService().getContents();
        quoteOfTheDayCall.enqueue(new Callback<QuoteOfTheDay>() {
            @Override
            public void onResponse(Call<QuoteOfTheDay> call, Response<QuoteOfTheDay> response) {
                QuoteOfTheDay quote1 = response.body();
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<QuoteOfTheDay> call, Throwable t) {

            }
        });
    }
}
