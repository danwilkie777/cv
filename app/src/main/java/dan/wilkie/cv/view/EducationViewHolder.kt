package dan.wilkie.cv.view

import android.view.View
import android.widget.TextView
import dan.wilkie.common.view.DataAdapter
import dan.wilkie.cv.R
import dan.wilkie.cv.core.EducationItem
import kotlinx.android.synthetic.main.item_education.view.*

class EducationViewHolder(itemView: View) : DataAdapter.DataViewHolder<EducationItem>(itemView) {

    override fun bind(data: EducationItem) {
        itemView.institution.text = data.institution
        itemView.period.text = data.period
        itemView.summary.text = data.summary
    }
}
