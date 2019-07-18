package dan.wilkie.cv.network

import dan.wilkie.cv.core.Cv
import dan.wilkie.cv.core.CvService
import dan.wilkie.cv.core.EducationItem
import dan.wilkie.cv.core.Job

class RetrofitCvService(private val cvApi: CvApi) : CvService {

    override fun getCv() = cvApi.getCv()
        .map {
            Cv(
                it.profile,
                it.skills,
                it.education.map { EducationItem(it.institution, it.period, it.summary) },
                it.work.map { Job(it.company, it.role, it.logoUrl, it.period, it.summary) }
            )
        }

}