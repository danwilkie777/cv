package dan.wilkie.cv.view

import android.view.View
import android.widget.TextView
import dan.wilkie.common.view.DataAdapter
import dan.wilkie.cv.R
import dan.wilkie.cv.core.EducationItem

class EducationViewHolder(itemView: View) : DataAdapter.DataViewHolder<EducationItem>(itemView) {
    val institution = itemView.findViewById<TextView>(R.id.institution)
    val period = itemView.findViewById<TextView>(R.id.period)
    val summary = itemView.findViewById<TextView>(R.id.summary)

    override fun bind(data: EducationItem) {
        institution.text = data.institution
        period.text = data.period
        summary.text = data.summary
    }
}
