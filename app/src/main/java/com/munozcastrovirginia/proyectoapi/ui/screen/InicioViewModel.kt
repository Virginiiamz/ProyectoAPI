package com.munozcastrovirginia.proyectoapi.ui.screen

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

class InicioViewModel(val firestoreManager: FirestoreManager) : ViewModel() {

    val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    private val _asignatura = MutableStateFlow<Asignatura?>(null)
    val asignatura: StateFlow<Asignatura?> = _asignatura

    init {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            firestoreManager.getAsignaturas().collect { asignaturas ->
                _uiState.update { uiState ->
                    uiState.copy(
                        asignaturas = asignaturas,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun addAsignatura(asignatura: Asignatura) {
        viewModelScope.launch {
            firestoreManager.addAsignatura(asignatura)
        }
    }

    fun deleteAsignaturaById(asignaturaId: String) {
        if (asignaturaId.isEmpty()) return
        viewModelScope.launch {
            firestoreManager.deleteAsignaturaById(asignaturaId)
        }
    }

    fun updateAsignatura(asignatura: Asignatura) {
        viewModelScope.launch {
            firestoreManager.updateAsignatura(asignatura)
        }
    }

    fun getAsignaturaById(asignaturaId: String) {
        viewModelScope.launch {
            _asignatura.value = firestoreManager.getAsignaturaById(asignaturaId)
        }
    }

    fun onAddAsignaturaSelected() {
        _uiState.update { it.copy(showAddAsignaturaDialog = true) }
    }

    fun dismisShowAddAsignaturaDialog() {
        _uiState.update { it.copy(showAddAsignaturaDialog = false) }
    }

    fun onLogoutSelected() {
        _uiState.update { it.copy(showLogoutDialog = true) }
    }

    fun dismisShowLogoutDialog() {
        _uiState.update { it.copy(showLogoutDialog = false) }
    }
}

data class UiState(
    val asignaturas: List<Asignatura> = emptyList(),
    val isLoading: Boolean = false,
    val showAddAsignaturaDialog: Boolean = false,
    val showLogoutDialog: Boolean = false
)

class InicioViewModelFactory(private val firestoreManager: FirestoreManager) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return InicioViewModel(firestoreManager) as T
    }
}