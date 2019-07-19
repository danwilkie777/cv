package dan.wilkie.cv.view

import android.view.View
import android.widget.TextView
import dan.wilkie.common.view.DataAdapter
import dan.wilkie.cv.R

class SkillViewHolder(itemView: View) : DataAdapter.DataViewHolder<String>(itemView) {
    val skillView = itemView.findViewById<TextView>(R.id.skill)

    override fun bind(data: String) {
        skillView.text = data
    }
}
