package dan.wilkie.cv.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import dan.wilkie.common.view.DataAdapter
import dan.wilkie.common.view.ImageLoader
import dan.wilkie.cv.R
import dan.wilkie.cv.core.Job

class JobViewHolder(itemView: View) : DataAdapter.DataViewHolder<Job>(itemView) {
    val company = itemView.findViewById<TextView>(R.id.company)
    val period = itemView.findViewById<TextView>(R.id.period)
    val role = itemView.findViewById<TextView>(R.id.role)
    val summary = itemView.findViewById<TextView>(R.id.summary)
    val logo = itemView.findViewById<ImageView>(R.id.logo)

    override fun bind(data: Job) {
        company.text = data.company
        period.text = data.period
        role.text = data.role
        summary.text = data.summary
        ImageLoader.loadImage(data.logoUrl, logo)
    }
}
