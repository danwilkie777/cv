package dan.wilkie.cv.core

/** The core model objects to be used across the app. In this case they match the Gson representations
 * exactly, though in real-life applications this would be less likely */

data class Cv(
    val name: String,
    val profile: String,
    val skills: List<String>,
    val education: List<EducationItem>,
    val work: List<Job>
)

data class EducationItem(
    val institution: String,
    val period: String,
    val summary: String
)

data class Job(
    val company: String,
    val role: String,
    val logoUrl: String,
    val period: String,
    val summary: String
)