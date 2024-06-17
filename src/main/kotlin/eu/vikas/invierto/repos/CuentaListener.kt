package eu.vikas.invierto.repos

import eu.vikas.invierto.domain.Cuenta
import eu.vikas.invierto.service.PrimarySequenceService
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent
import org.springframework.stereotype.Component


@Component
class CuentaListener(
    private val primarySequenceService: PrimarySequenceService
) : AbstractMongoEventListener<Cuenta>() {

    override fun onBeforeConvert(event: BeforeConvertEvent<Cuenta>) {
        if (event.source.id == null) {
            event.source.id = primarySequenceService.getNextValue()
        }
    }

}
