package eu.vikas.invierto.repos

import eu.vikas.invierto.domain.Banco
import org.springframework.data.mongodb.repository.MongoRepository


interface BancoRepository : MongoRepository<Banco, Long>
