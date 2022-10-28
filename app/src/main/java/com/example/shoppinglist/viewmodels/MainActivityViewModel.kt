package com.example.shoppinglist.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.DefaultFirebaseProviderItem
import com.example.shoppinglist.FirebaseProvider
import com.example.shoppinglist.MainView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel : ViewModel() {

    private val TAG = "!!!"
    private var view: MainView? = null
    private val db = Firebase.firestore
    private val firebaseRef = db.collection("items")

    fun attach(mainView: MainView) {
        view = mainView
        listenForItemUpdates()
    }

    fun showNewAddedItem(item: DefaultFirebaseProviderItem) {
        item.addItemToFb(item)
    }

    private fun listenForItemUpdates() {
        val items = mutableListOf<DefaultFirebaseProviderItem>()
        firebaseRef.addSnapshotListener { snapshot, error ->
            snapshot?.let { querySnapshot ->
                try {
                    items.clear()
                    for (document in querySnapshot.documents) {
                        val queryItem = document.toObject<DefaultFirebaseProviderItem>()
                        queryItem?.documentID = document.id
                        if (queryItem != null) {
                            items.add(queryItem)
                        }
                    }
                    view?.setData(items)
                } catch (e: Exception) {
                    Log.d(TAG, "listenForItemUpdates: $e")
                }
            }
            error?.let {
                Log.d(TAG, "listenForItemUpdates: $it")
            }
        }
    }
}