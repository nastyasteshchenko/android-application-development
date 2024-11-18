package ru.nsu.contact.application.data.datasource

import android.content.ContentResolver
import android.database.Cursor
import android.provider.ContactsContract
import ru.nsu.contact.application.domain.model.Contact
import javax.inject.Inject

class ContactsFromBookDataSourceImpl @Inject constructor(
    private val contentResolver: ContentResolver
) : ContactsFromBookDataSource {

    override fun getContacts(): List<Contact> {
        val contacts = mutableListOf<Contact>()
        val cursor: Cursor? = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            arrayOf(
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.PHOTO_URI
            ),
            null, null, null
        )

        cursor?.use {
            while (it.moveToNext()) {
                val id =
                    it.getString(
                        it.getColumnIndexOrThrow(
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                        )
                    )
                val name =
                    it.getString(
                        it.getColumnIndexOrThrow(
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                        )
                    )
                val phoneNumber =
                    it.getString(
                        it.getColumnIndexOrThrow(
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                        )
                    )
                var photo =
                    it.getString(
                        it.getColumnIndexOrThrow(
                            ContactsContract.CommonDataKinds.Phone.PHOTO_URI
                        )
                    )
                if (photo.isNullOrEmpty()) {
                    photo = "https://goo.su/03kflYr"
                }
                val contact = Contact(
                    id.toLong(), name = name, phoneNumber = phoneNumber,
                    photoUri = photo
                )

                if (!contacts.contains(contact)) {
                    contacts.add(contact)
                }
            }
        }
        return contacts
    }
}