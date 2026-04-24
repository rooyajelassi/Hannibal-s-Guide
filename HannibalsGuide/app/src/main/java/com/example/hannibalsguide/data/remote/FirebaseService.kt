package com.example.hannibalsguide.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseService @Inject constructor(
    val firestore: FirebaseFirestore
)