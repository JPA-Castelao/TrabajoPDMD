package gz.dam.roomapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RecordDao {

    @Query("SELECT * FROM record")
    fun getAll(): List<Record>

    @Insert
    fun insertAll(vararg records: Record)

    @Delete
    fun delete(record: Record)
}