package eu.vikas.invierto.repos

import eu.vikas.invierto.domain.Banco
import eu.vikas.invierto.service.PrimarySequenceService
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent
import org.springframework.stereotype.Component


@Component
class BancoListener(
    private val primarySequenceService: PrimarySequenceService
) : AbstractMongoEventListener<Banco>() {

    override fun onBeforeConvert(event: BeforeConvertEvent<Banco>) {
        if (event.source.id == null) {
            event.source.id = primarySequenceService.getNextValue()
        }
    }

}
