package eu.vikas.invierto.service

import eu.vikas.invierto.domain.PrimarySequence
import org.springframework.data.mongodb.core.FindAndModifyOptions
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service


@Service
class PrimarySequenceService(
    private val mongoOperations: MongoOperations
) {

    fun getNextValue(): Long {
        var primarySequence: PrimarySequence? = mongoOperations.findAndModify(
                Query.query(Criteria.where("_id").`is`(PRIMARY_SEQUENCE)),
                Update().inc("seq", 1),
                FindAndModifyOptions.options().returnNew(true),
                PrimarySequence::class.java)
        if (primarySequence == null) {
            primarySequence = PrimarySequence()
            primarySequence.id = PRIMARY_SEQUENCE
            primarySequence.seq = 10000
            mongoOperations.insert(primarySequence)
        }
        return primarySequence.seq!!
    }


    companion object {

        const val PRIMARY_SEQUENCE = "primarySequence"

    }

}
