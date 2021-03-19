package ipvc.estg.cmtrabalho.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Notas_table")

data class Nota( @PrimaryKey(autoGenerate = true) val id: Int? = null,
                 @ColumnInfo(name = "titulo") val titulo: String,
                 @ColumnInfo(name = "descricao") val descricao: String)