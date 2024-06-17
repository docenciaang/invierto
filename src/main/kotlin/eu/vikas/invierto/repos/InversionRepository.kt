package eu.vikas.invierto.repos

import eu.vikas.invierto.domain.Inversion
import org.springframework.data.mongodb.repository.MongoRepository


interface InversionRepository : MongoRepository<Inversion, Long>
