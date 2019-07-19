package dan.wilkie.cv.network

import dan.wilkie.cv.core.Cv
import dan.wilkie.cv.core.CvService
import dan.wilkie.cv.core.EducationItem
import dan.wilkie.cv.core.Job
import javax.inject.Inject

class RetrofitCvService @Inject constructor(private val cvApi: CvApi) : CvService {

    override fun getCv() = cvApi.getCv()
        .map { gsonCv ->
            Cv(
                gsonCv.name,
                gsonCv.profile,
                gsonCv.skills,
                gsonCv.education.map { EducationItem(it.institution, it.period, it.summary) },
                gsonCv.work.map { Job(it.company, it.role, it.logoUrl, it.period, it.summary) }
            )
        }

}