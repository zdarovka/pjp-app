package cz.tul.configuration;

import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.test.ImportAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.integration.transaction.PseudoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Profile({"prod_mongo", "test"})
@ImportAutoConfiguration({
        MongoAutoConfiguration.class,
        MongoDataAutoConfiguration.class,
        MongoRepositoriesAutoConfiguration.class})
@EnableMongoRepositories(basePackages = "cz.tul.repositories")
public class MongoConfig {

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new PseudoTransactionManager();
    }
}
