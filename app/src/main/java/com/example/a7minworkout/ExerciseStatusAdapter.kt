package com.example.a7minworkout

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

import com.example.a7minworkout.databinding.ItemExerciseStatusBinding

class ExerciseStatusAdapter(val items : ArrayList<ExerciseModel>) :
    RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {


        class ViewHolder(binding: ItemExerciseStatusBinding) :
                RecyclerView.ViewHolder(binding.root){
                    val tvitem = binding.tvitem
                }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemExerciseStatusBinding.inflate(
            LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model : ExerciseModel = items[position]
        holder.tvitem.text = model.getId().toString()

        when{
            model.getIsSelected() -> {
                holder.tvitem.background =
                    ContextCompat.getDrawable(holder.itemView.context,
                    R.drawable.item_circular_color_curr_selected_back)
                holder.tvitem.setTextColor(Color.BLACK)


            }
            model.getIsCompleted() -> {
                holder.tvitem.background =
                    ContextCompat.getDrawable(holder.itemView.context,
                        R.drawable.item_circular_color_selected_back)
                holder.tvitem.setTextColor(Color.BLACK)

            }
            else ->{
                holder.tvitem.background =
                    ContextCompat.getDrawable(holder.itemView.context,
                        R.drawable.item_circular_color_grey_back)

            }
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }
}