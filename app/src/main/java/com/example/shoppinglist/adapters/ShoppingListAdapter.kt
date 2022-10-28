package com.example.shoppinglist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.DefaultFirebaseProviderItem
import com.example.shoppinglist.R
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.shoppinglist_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShoppingListAdapter(
    private var items: MutableList<DefaultFirebaseProviderItem>
) : RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder>() {

    inner class ShoppingListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.shoppinglist_item, parent, false)
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
        }
//        holder.itemView.btnDelete.setOnClickListener {
//            deleteItemFromFb(currentItem)
//            if (!currentItem.checkbox) {
//                Toast.makeText(
//                    holder.itemView.context,
//                    "Checkbox must be checked",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setData(newItems: MutableList<DefaultFirebaseProviderItem>) = CoroutineScope(Dispatchers.IO).launch {
        items.clear()
        items.addAll(newItems)
        withContext(Dispatchers.Main) {
            notifyItemInserted(items.size - 1)
        }
    }

    private fun deleteItemFromFb(item: DefaultFirebaseProviderItem) {
        item.deleteItemFromFb(item)

    }

    private fun updateCheckbox(item: DefaultFirebaseProviderItem) = CoroutineScope(Dispatchers.IO).launch() {
        item.updateCheckboxToFb(item)
        withContext(Dispatchers.Main) {
            notifyDataSetChanged()
        }
    }
}









