package dan.wilkie.cv

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding3.swiperefreshlayout.refreshes
import dan.wilkie.cv.core.Cv
import dan.wilkie.cv.core.CvPresenter
import kotlinx.android.synthetic.main.activity_cv.*

class CvActivity : AppCompatActivity(), CvPresenter.View {
    //private val presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cv)
    }

    override fun onRefresh() = swipeRefreshLayout.refreshes()

    override fun showLoading(show: Boolean) {
        progressBar.visibility = if (show) VISIBLE else GONE
    }

    override fun showCv(cv: Cv) {
        profile.text = cv.profile
    }

    override fun showError() {
        Toast.makeText(this, "Something went wrong!", LENGTH_LONG).show()
    }

    // TODO DI, recyclers, logos, styles, UI tests, DI
}
