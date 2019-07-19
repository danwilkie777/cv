package dan.wilkie.common.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class DataAdapter<T, VH : DataAdapter.DataViewHolder<T>>(
    private val layoutId: Int,
    private val holderCreator: (View) -> VH
) : RecyclerView.Adapter<VH>() {
    private val items: MutableList<T> = ArrayList()

    fun setItems(items: List<T>) {
        this.items.clear()
        this.items.addAll(items)
        this.notifyDataSetChanged()
    }

    fun getItem(position: Int) = this.items[position]

    override fun getItemCount() = this.items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater.from(parent.context).inflate(this.layoutId, parent, false)
        return this.holderCreator(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(this.getItem(position))
    }

    abstract class DataViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(data: T)
    }
}