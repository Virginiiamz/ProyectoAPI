package com.munozcastrovirginia.proyectoapi.data

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreManager(auth: AuthManager, context: Context) {
    private val firestore = FirebaseFirestore.getInstance()
    private val userId = auth.getCurrentUser()?.uid
}