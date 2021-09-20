package org.tensorflow.lite.examples.poseestimation.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.tensorflow.lite.examples.poseestimation.R

class MyAdapter(
    private val poseList : ArrayList<Pose>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<MyAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.pose_item,
            parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = poseList[position]
        holder.poseName.text = currentitem.Name
    }

    override fun getItemCount(): Int {
        return poseList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener{

        val poseName : TextView = itemView.findViewById(R.id.tvposeName)
        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }

        init{
            itemView.setOnClickListener(this)
        }

    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

}