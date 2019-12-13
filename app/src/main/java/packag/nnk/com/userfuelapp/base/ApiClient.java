package packag.nnk.com.userfuelapp.base;

import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient
{
    private  Retrofit retrofit;

    public  Retrofit getApiClient(String BaseUrl)
    {
        OkHttpClient.Builder okhttpBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttpBuilder.addInterceptor(httpLoggingInterceptor);
        okhttpBuilder.connectTimeout(13, TimeUnit.SECONDS);
        okhttpBuilder.writeTimeout(13, TimeUnit.SECONDS);
        okhttpBuilder.readTimeout(16, TimeUnit.SECONDS);


        if (retrofit == null)
        {

            retrofit = new Retrofit.Builder()
                    .baseUrl(BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                    .client(okhttpBuilder.build())
                    .build();
        }

        return retrofit;
    }
}
