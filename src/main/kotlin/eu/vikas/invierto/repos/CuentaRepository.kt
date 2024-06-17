package eu.vikas.invierto.repos

import eu.vikas.invierto.domain.Cuenta
import org.springframework.data.mongodb.repository.MongoRepository


interface CuentaRepository : MongoRepository<Cuenta, Long>
