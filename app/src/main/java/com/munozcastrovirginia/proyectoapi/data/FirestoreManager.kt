package com.munozcastrovirginia.proyectoapi.data

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObject
import com.munozcastrovirginia.proyectoapi.model.Asignatura
import com.munozcastrovirginia.proyectoapi.model.AsignaturaDB
import com.munozcastrovirginia.proyectoapi.model.Profesor
import com.munozcastrovirginia.proyectoapi.model.ProfesorDB
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class FirestoreManager(auth: AuthManager, context: Context) {
    private val firestore = FirebaseFirestore.getInstance()
    private val userId = auth.getCurrentUser()?.uid

    companion object {
        const val ASIGNATURA_COLLECTION = "Asignaturas"
        const val PROFESOR_COLLECTION = "Profesores"
    }

    //    Funciones de las asignaturas
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

    suspend fun addAsignatura(asignatura: Asignatura) {
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

    suspend fun getAsignaturaById(id: String): Asignatura? {
        return firestore.collection(ASIGNATURA_COLLECTION).document(id)
            .get().await().toObject(AsignaturaDB::class.java)?.let {
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

//    Funciones de los profesores

    fun getProfesoresByAsignaturaId(asignaturaId: String): Flow<List<Profesor>> {
        return firestore.collection(PROFESOR_COLLECTION)
            .whereEqualTo("asignaturaId", asignaturaId)
            .snapshots()
            .map { qs ->
                qs.documents.mapNotNull { ds ->
                    ds.toObject(ProfesorDB::class.java)?.let { profesorDB ->
                        Profesor(
                            id = ds.id,
                            asignaturaId = profesorDB.asignaturaId,
                            userId = profesorDB.userId,
                            nombre = profesorDB.nombre,
                            apellidos = profesorDB.apellidos,
                            email = profesorDB.email,
                        )
                    }
                }
            }
    }

    suspend fun addProfesor(profesor: Profesor) {
        firestore.collection(PROFESOR_COLLECTION).add(profesor).await()
    }

    suspend fun updateProfesor(profesor: Profesor) {
        val profesorRef = profesor.id?.let {
            firestore.collection("Profesores").document(it)
        }
        profesorRef?.set(profesor)?.await()
    }

    suspend fun deleteProfesorById(profesorId: String) {
        firestore.collection("Profesores").document(profesorId).delete().await()
    }
}