package dan.wilkie.cv.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import dan.wilkie.common.view.DataAdapter
import dan.wilkie.common.view.ImageLoader
import dan.wilkie.cv.R
import dan.wilkie.cv.core.Job
import kotlinx.android.synthetic.main.item_job.view.*

class JobViewHolder(itemView: View) : DataAdapter.DataViewHolder<Job>(itemView) {

    override fun bind(data: Job) {
        itemView.company.text = data.company
        itemView.period.text = data.period
        itemView.role.text = data.role
        itemView.summary.text = data.summary
        ImageLoader.loadImage(data.logoUrl, itemView.logo)
    }
}
