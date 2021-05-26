package domain

interface Repository<T> {

    suspend fun retrieveAll()

}
