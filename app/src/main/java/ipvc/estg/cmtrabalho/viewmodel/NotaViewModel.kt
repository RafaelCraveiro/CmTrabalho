package ipvc.estg.cmtrabalho.viewmodel

import android.app.Application
import androidx.lifecycle.*
import ipvc.estg.cmtrabalho.db.NotaRepository
import ipvc.estg.cmtrabalho.db.NotasRoomDB
import ipvc.estg.cmtrabalho.entities.Nota
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NotaRepository
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allNotas: LiveData<List<Nota>>

    init {
        val notinDao = NotasRoomDB.getDatabase(application, viewModelScope).notaDao()
        repository = NotaRepository(notinDao)
        allNotas = repository.allNotas
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(titulo: Nota) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(titulo)
    }

    // delete all
     //fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
       //  repository.deleteAll()
     //}

     // delete by id
     fun deleteByID(id: Int) = viewModelScope.launch(Dispatchers.IO) {
         repository.deleteByID(id)
     }
    fun updateNotaByID(id: Int, titulo: String, descricao: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateNotaByID(id,titulo,descricao)
    }

/*

     fun updateCity(city: City) = viewModelScope.launch {
         repository.updateCity(city)
     }

     fun updateCountryFromCity(city: String, country: String) = viewModelScope.launch {
         repository.updateCountryFromCity(city, country)
     }*/
}