package guru.springframework.sdjpaintro.clean;

import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/*
 * para aplicar esta estrategia se debe configurar el perfil "clean" en la configuración del run
 */
@Profile("clean")
@Configuration
public class FlywayDatabaseClean {

    /**
     * Strategy used to initialize Flyway migration. Custom implementations may be registered as a
     * @Bean to override the default migration behavior.
     */
    @Bean
    public FlywayMigrationStrategy clean() {
        /*return new FlywayMigrationStrategy(){
            @Override
            public void migrate(Flyway flyway) {
                flyway.clean();
                flyway.migrate();
            }
        };*/
        return flyway -> {
            System.out.println("FlywayMigrationStrategy.flyway - Init");
            flyway.clean();
            flyway.migrate();
            System.out.println("FlywayMigrationStrategy.flyway - End");
        };
    }
}
