package dan.wilkie.cv.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dan.wilkie.common.di.ActivityScope
import dan.wilkie.cv.core.CvService
import dan.wilkie.cv.network.CvApi
import dan.wilkie.cv.network.RetrofitCvService
import retrofit2.Retrofit

@Module(includes = [CvModule.Internal::class])
class CvModule {

    @Provides
    fun provideCvApi(retrofit: Retrofit) = retrofit.create(CvApi::class.java)

    @Module
    interface Internal {
        @Binds
        @ActivityScope
        fun bindCvService(retrofitCvService: RetrofitCvService): CvService
    }
}

