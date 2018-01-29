package com.example.rruizp.pruebavalid.theMovieDatabaseAPI;

import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rruizp on 29/01/2018.
 */

public class RestApiAdapter {

    private static Retrofit retrofit;

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(String.valueOf(HttpUrl.parse(Constants.BASE_URL)))
            .addConverterFactory(GsonConverterFactory.create());

    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS);

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null, null);
    }


    public static <S> S createService(Class<S> serviceClass, String username, String password) {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(loggingInterceptor);


        builder.client(httpClient.build());
        retrofit = builder.build();

        return retrofit.create(serviceClass);
    }

}
