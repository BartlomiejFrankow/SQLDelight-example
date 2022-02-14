package com.example.sqldelightexample.data

import db.person.PersonEntity
import kotlinx.coroutines.flow.Flow

interface PersonDataSource {

    suspend fun getPersonById(id: Long): PersonEntity?

    fun getAllPersons(): Flow<List<PersonEntity>>

    suspend fun deletePersonById(id: Long)

    suspend fun insertPerson(firstName: String, lastName: String, id: Long? = null)
}
