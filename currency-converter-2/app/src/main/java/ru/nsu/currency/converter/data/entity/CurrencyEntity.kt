package ru.nsu.currency.converter.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency")
data class CurrencyEntity(
    @PrimaryKey val id: String,
    val name: String,
    val value: Double,
    val prevValue: Double,
    val charCode: String
)