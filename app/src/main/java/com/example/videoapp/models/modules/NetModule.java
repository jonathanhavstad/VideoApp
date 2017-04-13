package com.example.videoapp.models.modules;

import com.example.videoapp.models.data.RestCall;
import com.example.videoapp.models.rest.VideoAPI;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jonathanhavstad on 4/13/17.
 */

@Module
public class NetModule {
    private final String baseUrl;
    private final String hostName;
    private final String pin1;
    private final String pin2;
    private final String pin3;

    public NetModule(String baseUrl, String hostName, String pin1, String pin2, String pin3) {
        this.baseUrl = baseUrl;
        this.hostName = hostName;
        this.pin1 = pin1;
        this.pin2 = pin2;
        this.pin3 = pin3;
    }

    @Provides
    @Singleton
    public CertificatePinner providesCertificatePinner() {
        return new CertificatePinner.Builder()
                .add(hostName, pin1)
                .add(hostName, pin2)
                .add(hostName, pin3)
                .build();
    }

    @Provides
    @Singleton
    public OkHttpClient providesClient(CertificatePinner certificatePinner) {
        return new OkHttpClient.Builder()
                .certificatePinner(certificatePinner)
                .build();
    }

    @Provides
    @Singleton
    public Gson providesGson() {
        return new Gson();
    }

    @Provides
    @Singleton
    public Retrofit providesRetrofit(OkHttpClient client, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    public VideoAPI providesVideoAPI(Retrofit retrofit) {
        return retrofit.create(VideoAPI.class);
    }

    @Provides
    @Singleton
    public RestCall providesRestTask(Gson gson, VideoAPI videoAPI) {
        return new RestCall(gson, videoAPI);
    }
}
