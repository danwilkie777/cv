package dan.wilkie.cv.network

import io.reactivex.Single
import retrofit2.http.GET

interface CvApi {
    @GET("cv.json")
    fun getCv(): Single<GsonCv>
}