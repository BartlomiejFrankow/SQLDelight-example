package com.example.sqldelightexample.data

import com.example.sqldelightexample.PersonDatabase
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import db.person.PersonEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class PersonDataSourceImpl(db: PersonDatabase) : PersonDataSource {

    private val queries = db.personEntityQueries

    override suspend fun getPersonById(id: Long): PersonEntity? {
        return withContext(Dispatchers.IO) {
            queries.getPersonById(id).executeAsOneOrNull()
        }
    }

    override fun getAllPersons(): Flow<List<PersonEntity>> {
        return queries.getAllPersons().asFlow().mapToList()
    }

    override suspend fun deletePersonById(id: Long) {
        withContext(Dispatchers.IO) {
            queries.deletePersonById(id)
        }
    }

    override suspend fun insertPerson(firstName: String, lastName: String, id: Long?) {
        withContext(Dispatchers.IO) {
            queries.insertPerson(
                id = id,
                firstName = firstName,
                lastName = lastName
            )
        }
    }
}
