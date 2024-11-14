package ru.nsu.contact.application.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.nsu.contact.application.domain.model.Contact

@Database(entities = [Contact::class], version = 1)
abstract class ContactDataBase : RoomDatabase() {
    abstract fun contactDao(): ContactDao

    companion object {
        const val DATABASE_NAME = "contacts_db"
    }
}