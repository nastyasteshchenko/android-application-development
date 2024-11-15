package ru.nsu.contact.application.data.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.nsu.contact.application.data.entity.ContactEntity

@Dao
interface ContactDao {
    @Query("Select * FROM contact")
    fun getContacts(): Flow<List<ContactEntity>>

    @Query("Select * FROM contact WHERE id = :id")
    suspend fun getContactById(id: Int): ContactEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: ContactEntity)

    @Delete
    suspend fun deleteContact(contact: ContactEntity)

}