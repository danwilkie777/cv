package dan.wilkie.cv.core

import dan.wilkie.common.core.Repository

class CvRepository(private val cvService: CvService) : Repository<Cv>(
    requestFunction = { cvService.getCv() }
)