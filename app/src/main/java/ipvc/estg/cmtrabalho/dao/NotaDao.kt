package ipvc.estg.cmtrabalho.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ipvc.estg.cmtrabalho.entities.Nota


@Dao
interface NotaDao {

    // The flow always holds/caches latest version of data. Notifies its observers when the
    // data has changed.
    @Query("SELECT * FROM Notas_table ORDER BY id ASC")
    fun getAllNotas(): LiveData<List<Nota>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(titulo: Nota)

    @Query("DELETE FROM Notas_table  ")
    suspend fun deleteAll()

    @Query("UPDATE Notas_table SET titulo=:titulo , descricao=:descricao WHERE id == :id")
    suspend fun updateNotaByID(id: Int, titulo: String,descricao: String)

    @Query("DELETE FROM Notas_table where id == :id")
    suspend fun deleteByID(id: Int)
}