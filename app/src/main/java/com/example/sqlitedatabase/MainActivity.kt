// MainActivity.kt
package com.example.sqlitedatabase

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var add_button: FloatingActionButton
    private lateinit var empty_imageview: ImageView
    private lateinit var no_data: TextView

    private lateinit var myDB: MyDatabaseHelper
    private lateinit var book_id: ArrayList<String>
    private lateinit var book_title: ArrayList<String>
    private lateinit var book_author: ArrayList<String>
    private lateinit var book_pages: ArrayList<String>
    private lateinit var customAdapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        add_button = findViewById(R.id.add_button)
        empty_imageview = findViewById(R.id.empty_imageview)
        no_data = findViewById(R.id.no_data)
        add_button.setOnClickListener {
            val intent = Intent(this@MainActivity, AddActivity::class.java)
            startActivity(intent)
        }

        myDB = MyDatabaseHelper(this@MainActivity)
        book_id = ArrayList()
        book_title = ArrayList()
        book_author = ArrayList()
        book_pages = ArrayList()

        storeDataInArrays()

        customAdapter = CustomAdapter(this@MainActivity, this@MainActivity, book_id, book_title, book_author, book_pages)
        recyclerView.adapter = customAdapter
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.my_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_all) {
            confirmDialog()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete All?")
        builder.setMessage("Are you sure you want to delete all Data?")
        builder.setPositiveButton("Yes") { _, _ ->
            myDB.deleteAllData()
            recreate()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.create().show()
    }

    private fun storeDataInArrays() {
        val cursor = myDB.readAllData()
        cursor?.apply {
            if (count == 0) {
                empty_imageview.visibility = View.VISIBLE
                no_data.visibility = View.VISIBLE
            } else {
                while (moveToNext()) {
                    book_id.add(getString(0))
                    book_title.add(getString(1))
                    book_author.add(getString(2))
                    book_pages.add(getString(3))
                }
                empty_imageview.visibility = View.GONE
                no_data.visibility = View.GONE
            }
            close()
        }
    }
}
