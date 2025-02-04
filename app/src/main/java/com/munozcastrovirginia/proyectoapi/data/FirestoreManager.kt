package com.munozcastrovirginia.proyectoapi.data

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObject
import com.munozcastrovirginia.proyectoapi.model.Asignatura
import com.munozcastrovirginia.proyectoapi.model.AsignaturaDB
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class FirestoreManager(auth: AuthManager, context: Context) {
    private val firestore = FirebaseFirestore.getInstance()
    private val userId = auth.getCurrentUser()?.uid

    companion object {
        const val ASIGNATURA_COLLECTION = "Asignaturas"
    }

    fun getAsignaturas(): Flow<List<Asignatura>> {
        return firestore.collection(ASIGNATURA_COLLECTION)
            .whereEqualTo("userId", userId)
            .snapshots()
            .map { qs ->
                qs.documents.mapNotNull { ds ->
                    ds.toObject(AsignaturaDB::class.java)?.let { asignaturaDB ->
                        Asignatura(
                            id = ds.id,
                            userId = asignaturaDB.userId,
                            codigo = asignaturaDB.codigo,
                            nombre = asignaturaDB.nombre,
                            descripcion = asignaturaDB.descripcion,
                            horas = asignaturaDB.horas
                        )
                    }
                }
            }
    }

    suspend fun addAsignatura(asignatura: Asignatura){
        firestore.collection(ASIGNATURA_COLLECTION).add(asignatura).await()
    }

    suspend fun updateAsignatura(asignatura: Asignatura) {
        val asignaturaRef = asignatura.id?.let {
            firestore.collection("Asignaturas").document(it)
        }
        asignaturaRef?.set(asignatura)?.await()
    }

    suspend fun deleteAsignaturaById(asignaturaId: String) {
        firestore.collection("Asignaturas").document(asignaturaId).delete().await()
    }

    fun getAsignaturaId(id: String): Asignatura {
        return firestore.collection(ASIGNATURA_COLLECTION).document(id)
            .get().result?.toObject(AsignaturaDB::class.java)?.let {
                Asignatura(
                    id = id,
                    userId = it.userId,
                    codigo = it.codigo,
                    nombre = it.nombre,
                    descripcion = it.descripcion,
                    horas = it.horas
                )
            }!!
    }
}