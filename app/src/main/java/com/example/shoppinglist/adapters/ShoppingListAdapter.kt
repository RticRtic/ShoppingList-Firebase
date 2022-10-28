package com.example.shoppinglist.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.DefaultFirebaseProviderItem
import com.example.shoppinglist.R
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.shoppinglist_item.view.*
import kotlinx.android.synthetic.main.shoppinglist_item.view.btnDelete

class ShoppingListAdapter(
    private var items: MutableList<DefaultFirebaseProviderItem>
) : RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder>() {

    inner class ShoppingListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shoppinglist_item, parent, false)
        return ShoppingListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShoppingListViewHolder, position: Int) {
        val currentItem = items[position]
        holder.itemView.apply {
            tvItemName.text = currentItem.item
            cbCheckbox.isChecked = currentItem.checkbox
            cbCheckbox.setOnCheckedChangeListener { _, isChecked ->
                currentItem.checkbox = isChecked
                updateCheckbox(currentItem)
            }
            holder.itemView.btnDelete.setOnClickListener {
                deleteItemFromFb(currentItem)
                if(!currentItem.checkbox) {
                    Toast.makeText(holder.itemView.context, "Checkbox must be checked", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setData(newItems: MutableList<DefaultFirebaseProviderItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    private fun deleteItemFromFb(item: DefaultFirebaseProviderItem) {
        item.deleteItemFromFb(item)
        notifyDataSetChanged()
    }

    private fun updateCheckbox(item: DefaultFirebaseProviderItem) {
        item.updateCheckboxToFb(item)
        notifyDataSetChanged()
    }
}









