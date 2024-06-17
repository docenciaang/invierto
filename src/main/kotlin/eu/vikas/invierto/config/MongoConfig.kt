package eu.vikas.invierto.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.MongoTransactionManager
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean


@Configuration
@EnableMongoRepositories("eu.vikas.invierto.repos")
class MongoConfig {

    @Bean
    fun transactionManager(databaseFactory: MongoDatabaseFactory): MongoTransactionManager =
            MongoTransactionManager(databaseFactory)

    @Bean
    fun validatingMongoEventListener(factory: LocalValidatorFactoryBean):
            ValidatingMongoEventListener = ValidatingMongoEventListener(factory)

}
