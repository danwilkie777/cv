package dan.wilkie.cv.view

import android.view.View
import android.widget.TextView
import dan.wilkie.common.view.DataAdapter
import dan.wilkie.cv.R
import kotlinx.android.synthetic.main.item_skill.view.*

class SkillViewHolder(itemView: View) : DataAdapter.DataViewHolder<String>(itemView) {

    override fun bind(data: String) {
        itemView.skill.text = data
    }
}
