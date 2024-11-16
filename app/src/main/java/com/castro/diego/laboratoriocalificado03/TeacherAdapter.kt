package com.castro.diego.laboratoriocalificado03

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.castro.diego.laboratoriocalificado03.databinding.ItemTeacherBinding


class TeacherAdapter(
    private var list: List<TeacherResponse>
) : RecyclerView.Adapter<TeacherAdapter.ViewHolder>() {

    var onItemClick: ((TeacherResponse) -> Unit)? = null
    var onItemLongClick: ((TeacherResponse) -> Unit)? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemTeacherBinding.bind(view)

        fun bind(teacher: TeacherResponse) {
            binding.tvName.text = "${teacher.name} ${teacher.last_name}"
            binding.tvPhone.text = teacher.phone
            binding.tvEmail.text = teacher.email

            Glide
                .with(itemView)
                .load(teacher.getTeacherImage())
                .into(binding.ivTeacher)

            // Configura los clics
            itemView.setOnClickListener {
                onItemClick?.invoke(teacher)
            }

            itemView.setOnLongClickListener {
                onItemLongClick?.invoke(teacher)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemTeacherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemTeacher = list[position]
        holder.bind(itemTeacher)
    }

    override fun getItemCount(): Int = list.size

    fun updateList(newList: List<TeacherResponse>) {
        list = newList
        notifyDataSetChanged()
    }
}
