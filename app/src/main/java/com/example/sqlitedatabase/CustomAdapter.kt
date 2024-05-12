package com.example.sqlitedatabase

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(
    private val activity: AppCompatActivity,
    private val context: Context, // Change RecyclerView to Context
    private val book_id: ArrayList<String>,
    private val book_title: ArrayList<String>,
    private val book_author: ArrayList<String>,
    private val book_pages: ArrayList<String>
) : RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.my_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.book_id_txt.text = book_id[position]
        holder.book_title_txt.text = book_title[position]
        holder.book_author_txt.text = book_author[position]
        holder.book_pages_txt.text = book_pages[position]
        // RecyclerView onClickListener
        holder.mainLayout.setOnClickListener {
            val intent = Intent(context, UpdateActivity::class.java)
            intent.putExtra("id", book_id[position])
            intent.putExtra("title", book_title[position])
            intent.putExtra("author", book_author[position])
            intent.putExtra("pages", book_pages[position])
            activity.startActivityForResult(intent, 1)
        }
    }

    override fun getItemCount(): Int {
        return book_id.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val book_id_txt: TextView = itemView.findViewById(R.id.book_id_text)
        val book_title_txt: TextView = itemView.findViewById(R.id.book_title_text)
        val book_author_txt: TextView = itemView.findViewById(R.id.book_author_text)
        val book_pages_txt: TextView = itemView.findViewById(R.id.book_pages_text)
        val mainLayout: LinearLayout = itemView.findViewById(R.id.mainLayout)

        init {
            // Animate RecyclerView
            val translateAnim: Animation = AnimationUtils.loadAnimation(itemView.context, R.anim.translate_anim)
            mainLayout.animation = translateAnim
        }
    }
}
