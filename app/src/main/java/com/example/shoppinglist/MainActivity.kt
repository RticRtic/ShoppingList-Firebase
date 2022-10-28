package com.example.shoppinglist

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.adapters.ShoppingListAdapter
import com.example.shoppinglist.viewmodels.MainActivityViewModel

interface MainView {
    fun setData(items: MutableList<DefaultFirebaseProviderItem>)
}

class MainActivity : AppCompatActivity(), MainView {

    private lateinit var viewModel: MainActivityViewModel
    private val shoppingListAdapter = ShoppingListAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.attach(this)
        val itemList = findViewById<RecyclerView>(R.id.rvItemList)
        val addButton = findViewById<AppCompatButton>(R.id.btnAdd)
        val addItemText = findViewById<EditText>(R.id.etAddItem)
        itemList.adapter = shoppingListAdapter
        itemList.layoutManager = LinearLayoutManager(this)

        addButton.setOnClickListener {
            val itemTextName = addItemText.text.toString()
            if (itemTextName.isNotEmpty()) {
                val item = DefaultFirebaseProviderItem(itemTextName)
                addItemText.text.clear()
                viewModel.showNewAddedItem(item)
            } else {
                Toast.makeText(this, "Please Enter Text", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun setData(items: MutableList<DefaultFirebaseProviderItem>) {
        shoppingListAdapter.setData(items)
    }
}