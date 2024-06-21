package eu.vikas.invierto.repos

import eu.vikas.invierto.domain.Transaccion
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query



interface TransicionRepository : MongoRepository<Transaccion, Long> {
    @Query("{ '\$or': [ { 'origenId': ?0 }, { 'destinoId': ?0 } ] }")
    fun findPorInversion(idInversion: Long, sort: Sort): List<Transaccion>

    @Query("{ '\$or': [ { 'origenId': ?0 }, { 'destinoId': ?0 } ] }")
    fun findPorCuenta(idCuenta: Long, sort: Sort): List<Transaccion>
}
