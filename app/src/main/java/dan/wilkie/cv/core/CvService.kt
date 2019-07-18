package dan.wilkie.cv.core

import io.reactivex.Single

interface CvService {
    fun getCv(): Single<Cv>
}