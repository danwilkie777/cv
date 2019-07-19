package dan.wilkie.common.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dan.wilkie.common.network.RxThreadCallAdapterFactory
import dan.wilkie.cv.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideContext() = context

    @Provides
    @Singleton
    fun provideRetrofit(
        context: Context,
        rxThreadCallAdapterFactory: RxThreadCallAdapterFactory
    ): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(BODY))
            .build()
        return Retrofit.Builder()
            .baseUrl(context.getString(R.string.base_url))
            .addCallAdapterFactory(rxThreadCallAdapterFactory)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideRxThreadCallAdapterFactory() = RxThreadCallAdapterFactory(
        Schedulers.io(),
        AndroidSchedulers.mainThread()
    )
}
