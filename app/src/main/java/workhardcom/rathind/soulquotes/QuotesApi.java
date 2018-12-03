package workhardcom.rathind.soulquotes;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class QuotesApi {
    private static final String url= "http://quotes.rest/";
    public static QuotesService quotesService = null;


    public static  QuotesService getQuotesService()
    {
        if(quotesService == null)
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            quotesService = retrofit.create(QuotesService.class);
        }
        return quotesService;


    }



 public interface QuotesService{
     @GET("qod.json")
     Call<QuoteOfTheDay> getContents();

 }

}
