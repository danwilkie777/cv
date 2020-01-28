package dan.wilkie.cv.view

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.jakewharton.rxbinding3.swiperefreshlayout.refreshes
import dan.wilkie.common.view.ScopeActivity
import dan.wilkie.common.di.AppComponent
import dan.wilkie.common.view.DataAdapter
import dan.wilkie.cv.R
import dan.wilkie.cv.core.Cv
import dan.wilkie.cv.core.CvPresenter
import dan.wilkie.cv.di.CvComponent
import dan.wilkie.cv.di.DaggerCvComponent
import kotlinx.android.synthetic.main.activity_cv.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject


class CvActivity : ScopeActivity<CvComponent>(), CvPresenter.View {
    @Inject
    lateinit var presenter: CvPresenter

    private val skillAdapter = DataAdapter(R.layout.item_skill) { view -> SkillViewHolder(view) }
    private val educationAdapter = DataAdapter(R.layout.item_education) { view -> EducationViewHolder(view) }
    private val jobAdapter = DataAdapter(R.layout.item_job) { view -> JobViewHolder(view) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cv)
        setSupportActionBar(toolbar)
        component.inject(this)

        setAdapters()

        presenter.attach(this)
    }

    private fun setAdapters() {
        skills.adapter = skillAdapter
        education.adapter = educationAdapter
        work.adapter = jobAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach(this)
    }

    override fun buildComponent(appComponent: AppComponent): CvComponent {
        return DaggerCvComponent.builder()
            .appComponent(appComponent)
            .build()
    }

    override fun onRefresh() = swipeRefreshLayout.refreshes()

    override fun showLoading(show: Boolean) {
        progressBar.visibility = if (show) VISIBLE else GONE
        if (!show) swipeRefreshLayout.isRefreshing = false
    }

    override fun showCv(cv: Cv) {
        title = cv.name
        profile.text = cv.profile
        skillAdapter.setItems(cv.skills)
        educationAdapter.setItems(cv.education)
        jobAdapter.setItems(cv.work)
    }

    override fun showError() {
        Toast.makeText(this, "Something went wrong!", LENGTH_LONG).show()
    }
}
