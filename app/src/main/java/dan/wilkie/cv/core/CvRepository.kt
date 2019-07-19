package dan.wilkie.cv.core

import dan.wilkie.common.core.Repository
import javax.inject.Inject

class CvRepository @Inject constructor(private val cvService: CvService) : Repository<Cv>(
    requestFunction = { cvService.getCv() }
)