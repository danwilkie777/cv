package dan.wilkie.cv.network

import dan.wilkie.cv.core.Cv
import dan.wilkie.cv.core.EducationItem
import dan.wilkie.cv.core.Job
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitCvServiceTest {
    private val mockWebServer = MockWebServer()
    private val cvService = RetrofitCvService(createApi(mockWebServer))

    @Test
    fun parsesJsonIntoCvModel() {
        initialiseServer()

        cvService.getCv().test().assertValue(expectedResponse())
    }

    private fun expectedResponse() = Cv(
        "name",
        "profile",
        listOf("skill1", "skill2"),
        listOf(
            EducationItem("institution1", "period1", "summary1"),
            EducationItem("institution2", "period2", "summary2")
        ),
        listOf(Job("company", "role", "logoUrl", "period", "summary"))
    )

    private fun initialiseServer() {
        val response = MockResponse()
            .setBody(loadJson("test-cv.json"))
            .setResponseCode(200)
            .setHeader("Content-Type", "application/json")
        mockWebServer.enqueue(response)
    }

    private fun createApi(mockServer: MockWebServer): CvApi {
        val adapter = RxJava2CallAdapterFactory.create()
        val retrofit = Retrofit.Builder()
            .baseUrl(mockServer.url("/").toString())
            .addCallAdapterFactory(adapter)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(CvApi::class.java)
    }

    private fun loadJson(filename: String): String =
        javaClass.classLoader.getResourceAsStream(filename)
            .bufferedReader()
            .use { it.readText() }

}
