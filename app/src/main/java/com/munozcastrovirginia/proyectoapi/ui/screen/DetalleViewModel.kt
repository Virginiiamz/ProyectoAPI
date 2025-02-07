package com.munozcastrovirginia.proyectoapi.ui.screen

import com.munozcastrovirginia.proyectoapi.model.Profesor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.munozcastrovirginia.proyectoapi.data.FirestoreManager
import com.munozcastrovirginia.proyectoapi.model.Asignatura
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetalleViewModel(val firestoreManager: FirestoreManager, idAsignatura: String) : ViewModel() {

    val _uiState = MutableStateFlow(UiStateDetalle())
    val uiState: StateFlow<UiStateDetalle> = _uiState

//    private val _profesor = MutableStateFlow<Profesor?>(null)
//    val profesor: StateFlow<Profesor?> = _profesor

    init {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            firestoreManager.getProfesoresByAsignaturaId(idAsignatura).collect { profesores ->
                _uiState.update { uiState ->
                    uiState.copy(
                        profesores = profesores,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun addProfesor(profesor: Profesor) {
        viewModelScope.launch {
            firestoreManager.addProfesor(profesor)
        }
    }

    fun updateProfesor(profesor: Profesor) {
        viewModelScope.launch {
            firestoreManager.updateProfesor(profesor)
        }
    }

    fun deleteProfesoraById(profesorId: String) {
        if (profesorId.isEmpty()) return
        viewModelScope.launch {
            firestoreManager.deleteProfesorById(profesorId)
        }
    }

    fun onAddProfesorSelected() {
        _uiState.update { it.copy(showAddProfesorDialog = true) }
    }

    fun dismisShowAddProfesorDialog() {
        _uiState.update { it.copy(showAddProfesorDialog = false) }
    }

    fun onLogoutSelected() {
        _uiState.update { it.copy(showLogoutDialog = true) }
    }

    fun dismisShowLogoutDialog() {
        _uiState.update { it.copy(showLogoutDialog = false) }
    }
}

data class UiStateDetalle(
    val profesores: List<Profesor> = emptyList(),
    val isLoading: Boolean = false,
    val showAddProfesorDialog: Boolean = false,
    val showLogoutDialog: Boolean = false
)

class DetalleViewModelFactory(private val firestoreManager: FirestoreManager,val idAsignatura: String) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetalleViewModel(firestoreManager, idAsignatura) as T
    }
}