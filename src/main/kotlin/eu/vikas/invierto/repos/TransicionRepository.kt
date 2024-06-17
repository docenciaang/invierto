package eu.vikas.invierto.repos

import eu.vikas.invierto.domain.Transaccion
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query



interface TransicionRepository : MongoRepository<Transaccion, Long> {
    @Query("{ '\$or': [ { 'origenId': ?0 }, { 'destinoId': ?0 } ] }")
    fun findPorInversion(idOrigen: Long, sort: Sort): List<Transaccion>
}
