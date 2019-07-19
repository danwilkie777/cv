package dan.wilkie.cv.network

/** These objects match the API's representation of the data and are used for Gson mapping **/

data class GsonCv(
    val name: String,
    val profile: String,
    val skills: List<String>,
    val education: List<GsonEducationItem>,
    val work: List<GsonJob>
)

data class GsonEducationItem(
    val institution: String,
    val period: String,
    val summary: String
)

data class GsonJob(
    val company: String,
    val role: String,
    val logoUrl: String,
    val period: String,
    val summary: String
)