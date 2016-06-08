package cz.tul.configuration;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Profile({"prod_mysql", "test"})
@EnableJpaRepositories(basePackages = "cz.tul.repositories")
public class JpaConfig {


}
