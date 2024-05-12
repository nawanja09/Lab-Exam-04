package com.example.sqlitedatabase

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class UpdateActivity : AppCompatActivity() {

    private lateinit var titleInput: EditText
    private lateinit var authorInput: EditText
    private lateinit var pagesInput: EditText
    private lateinit var updateButton: Button
    private lateinit var deleteButton: Button

    private var id: String? = null
    private var title: String? = null
    private var author: String? = null
    private var pages: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        titleInput = findViewById(R.id.title_input2)
        authorInput = findViewById(R.id.author_input2)
        pagesInput = findViewById(R.id.pages_input2)
        updateButton = findViewById(R.id.update_button)
        deleteButton = findViewById(R.id.delete_button)

        getAndSetIntentData()

        updateButton.setOnClickListener {
            val myDB = MyDatabaseHelper(this)
            val result = myDB.updateData(
                id ?: "",
                titleInput.text.toString(),
                authorInput.text.toString(),
                pagesInput.text.toString()
            )
            if (result > 0) {
                Toast.makeText(this, "Data updated successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Failed to update data", Toast.LENGTH_SHORT).show()
            }
        }

        deleteButton.setOnClickListener {
            confirmDialog()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun getAndSetIntentData() {
        val intentData = intent.extras
        if (intentData != null && intentData.containsKey("id") && intentData.containsKey("title") &&
            intentData.containsKey("author") && intentData.containsKey("pages")
        ) {
            id = intentData.getString("id")
            title = intentData.getString("title")
            author = intentData.getString("author")
            pages = intentData.getString("pages")

            titleInput.setText(title)
            authorInput.setText(author)
            pagesInput.setText(pages)
        } else {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun confirmDialog() {
        AlertDialog.Builder(this)
            .setTitle("Delete Book")
            .setMessage("Are you sure you want to delete this book?")
            .setPositiveButton("Yes") { _, _ ->
                val myDB = MyDatabaseHelper(this)
                id?.let { safeId ->
                    val result = myDB.deleteOneRow(safeId)
                    if (result > 0) {
                        Toast.makeText(this, "Book deleted successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, "Failed to delete book", Toast.LENGTH_SHORT).show()
                    }
                } ?: run {
                    Toast.makeText(this, "No ID available", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }
}
