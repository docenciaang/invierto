package eu.vikas.invierto.repos

import eu.vikas.invierto.domain.Transaccion
import eu.vikas.invierto.service.PrimarySequenceService
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent
import org.springframework.stereotype.Component


@Component
class TransicionListener(
    private val primarySequenceService: PrimarySequenceService
) : AbstractMongoEventListener<Transaccion>() {

    override fun onBeforeConvert(event: BeforeConvertEvent<Transaccion>) {
        if (event.source.id == null) {
            event.source.id = primarySequenceService.getNextValue()
        }
    }

}
