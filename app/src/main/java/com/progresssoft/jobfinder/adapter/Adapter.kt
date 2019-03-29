package com.progresssoft.jobfinder.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.progresssoft.jobfinder.R
import com.progresssoft.jobfinder.bean.SearchObject
import com.progresssoft.jobfinder.interfaces.OnItemClickListener
import kotlinx.android.synthetic.main.row_job.view.*

/**
 * Created by Mohammad.Arqoub on 28-Mar-19.
 */

class Adapter(val items: List<SearchObject>, val context: Context, val listener: OnItemClickListener, val ID: Int) :
    RecyclerView.Adapter<ViewHolder2>() {

    override fun onBindViewHolder(p0: ViewHolder2, p1: Int) {
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): ViewHolder2 {
        return ViewHolder2(
            LayoutInflater.from(context).inflate(
                R.layout.row_job,
                p0,
                false
            )
        )

    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder2, position: Int, payloads: MutableList<Any>) {

        holder.tvTitle?.text = items.get(position).companyName
        holder.jobTitleTv?.text = items.get(position).jobPosition
        holder.postDateTv?.text = items.get(position).postDate
        holder.locationTv?.text = items.get(position).location

        if (items[position].logo.isNullOrEmpty())
            holder.companyLogo.setImageDrawable(context.getDrawable(R.drawable.progresssoft_logo))
        else
            Glide.with(context).load(items.get(position).logo).into(holder.companyLogo)

          holder.cellCard.setOnClickListener {
              listener.Clicked(position)
          }

    }


}

class ViewHolder2(view: View) : RecyclerView.ViewHolder(view) {
    val tvTitle = view.companyNameTv
    val jobTitleTv = view.jobTitleTv
    val postDateTv = view.postDateTv
    val companyLogo = view.companyLogo
    val locationTv = view.locationTv
    val cellCard = view.cellCard
}