package ru.stupakov.insidemessages;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import ru.stupakov.insidemessages.initializer.PostgresInitializer;

import javax.transaction.Transactional;

/**
 * @author Stupakov D. L.
 **/
@SpringBootTest
@ActiveProfiles("test")
@Transactional
@ContextConfiguration(initializers = {
        PostgresInitializer.Initializer.class
})
@Sql("/sql/data.sql")
public class IntegrationTestBase {
    @BeforeAll
    static void init(){
        PostgresInitializer.container.start();
    }
}
