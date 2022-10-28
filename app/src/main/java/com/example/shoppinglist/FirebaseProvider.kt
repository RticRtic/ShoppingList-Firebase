package com.example.shoppinglist

import android.util.DisplayMetrics
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.withContext
import kotlin.Exception

interface FirebaseProvider {
    fun addItemToFb(item: DefaultFirebaseProviderItem)
    fun updateCheckboxToFb(item: DefaultFirebaseProviderItem)
    fun deleteItemFromFb(item: DefaultFirebaseProviderItem)
}

data class DefaultFirebaseProviderItem(
    val item: String? = null,
    @DocumentId var documentID: String? = null,
    var checkbox: Boolean = false,

    ) : FirebaseProvider {
    private val TAG = "!!!"
    private val db = Firebase.firestore
    private val firebaseRef = db.collection("items")

    override fun addItemToFb(item: DefaultFirebaseProviderItem) {
        firebaseRef.add(item).addOnSuccessListener {
            try {
                Log.d(TAG, "addItemToFb: Added to firestore $it")
            } catch (e: Exception) {
                Log.d(TAG, "addItemToFb: couldnt add to firestore $e")
            }
        }
    }

    override fun updateCheckboxToFb(item: DefaultFirebaseProviderItem) {
        try {
            item.documentID?.let { id ->
                firebaseRef.document(id).update("checkbox", item.checkbox)
            }
        } catch (e: Exception) {
            Log.d(TAG, "updateCheckboxToFb: $e")
        }
    }

    override fun deleteItemFromFb(item: DefaultFirebaseProviderItem) {
        try {
            if (item.checkbox) {
                item.documentID?.let { id ->
                    firebaseRef.document(id).delete()
                }
            }
        } catch (e: Exception) {

        }
    }
}










