package jp.co.yumemi.android.code_check.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.co.yumemi.android.code_check.BuildConfig
import jp.co.yumemi.android.code_check.data.source.RemoteDataSource
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): RemoteDataSource = retrofit.create(
        RemoteDataSource::class.java)

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        //ログレベルを設定
        if(BuildConfig.BUILD_TYPE == "debug") {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            interceptor.level = HttpLoggingInterceptor.Level.BASIC
        }
        else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(90, TimeUnit.SECONDS)
            .readTimeout(90, TimeUnit.SECONDS)
            .writeTimeout(90, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
}