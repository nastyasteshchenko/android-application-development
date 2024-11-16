package ru.nsu.contact.application.data.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.nsu.contact.application.data.entity.ContactEntity

@Dao
interface ContactDao {
    @Query("Select * FROM contact")
    suspend fun getContacts(): List<ContactEntity>

    @Query("Select * FROM contact WHERE id = :id")
    suspend fun getContactById(id: Long): ContactEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: ContactEntity)

    @Delete
    suspend fun deleteContact(contact: ContactEntity)

}