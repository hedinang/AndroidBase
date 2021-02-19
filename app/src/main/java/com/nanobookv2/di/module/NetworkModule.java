package com.nanobookv2.di.module;

import android.content.Context;

import com.nanobookv2.core.SessionManager;
import com.nanobookv2.core.config.NetworkConfig;
import com.nanobookv2.data.remote.ApiAuthService;
import com.nanobookv2.data.remote.ApiBookService;
import com.nanobookv2.di.qualifier.ApiInfo;
import com.nanobookv2.di.scope.ActivityScope;
import com.nanobookv2.ui.broadcast.NetworkConnectReceiver;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.http.RealResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public abstract class NetworkModule {

    @Provides
    @ApiInfo
    static String providerUrlBase() {
        return NetworkConfig.URL_BASE;
    }

    @Provides
    static Cache providerCache(Context context) {
        return new Cache(context.getCacheDir(), 5 * 1024 * 1024L);
    }

    @Provides
    static OkHttpClient providerClient(SessionManager sessionManager, Cache cache) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient
                .cache(cache)
                .addInterceptor(chain -> {
                            if (!NetworkConnectReceiver.isConnected())
                                throw new SocketTimeoutException("timeout");
                            Request original = chain.request();
                            chain.withConnectTimeout(NetworkConfig.CONNECTION_TIME_OUT, TimeUnit.MICROSECONDS);
                            chain.withReadTimeout(NetworkConfig.CONNECTION_TIME_OUT, TimeUnit.MICROSECONDS);
                            chain.withWriteTimeout(NetworkConfig.CONNECTION_TIME_OUT, TimeUnit.MICROSECONDS);

                            String path = original.url().encodedPath();
                            System.out.println(original.url().toString());
                            boolean isAllow = false;
                            for (String endpoint : NetworkConfig.ALLOW_PATHS) {
                                if (path.matches(endpoint)) isAllow = true;
                            }
                            Request.Builder builder;
                            builder = original
                                    .newBuilder()
                                    .header(NetworkConfig.HEADER_DEVICE_ID, sessionManager.deviceId)
                                    .header(NetworkConfig.HEADER_LANGUAGE, sessionManager.language)
                                    .method(original.method(), original.body());
                            if (!isAllow) {
                                builder = builder
                                        .header(NetworkConfig.HEADER_AUTHENTICATION, "Bearer " + sessionManager.accessToken);
                            }
                            return chain.proceed(builder.build());
                        }
                );
        return httpClient.build();
    }

    @Provides
    @ActivityScope
    static Retrofit provideRetrofit(OkHttpClient client, @ApiInfo String urlBase) {
        return new Retrofit.Builder()
                .baseUrl(urlBase)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

    @Provides
    static ApiAuthService providerAuthService(Retrofit retrofit) {
        return retrofit.create(ApiAuthService.class);
    }

    @Provides
    static ApiBookService providerBookService(Retrofit retrofit) {
        return retrofit.create(ApiBookService.class);
    }

    @Provides
    static CompositeDisposable providerCompositeDisposable() {
        return new CompositeDisposable();
    }
}
