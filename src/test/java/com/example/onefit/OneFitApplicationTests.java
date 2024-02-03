package com.example.onefit;

import com.example.onefit.user.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class OneFitApplicationTests {
    @Autowired
    private UserRepository userRepository;

    private static  PostgreSQLContainer<?> postgreSQLContainer=new PostgreSQLContainer<>("postgres:16")
            .withUsername("postgres")
            .withPassword("123")
            .withExposedPorts(9000)
            .withDatabaseName("test_db");
    static {
        postgreSQLContainer.start();
    }


    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url",
                () -> String.format("jdbc:postgresql://localhost:%d/test_db",postgreSQLContainer.getFirstMappedPort())
        );
        registry.add("spring.datasource.username",() -> postgreSQLContainer.getUsername());
        registry.add("spring.datasource.password",() -> postgreSQLContainer.getPassword());
    }

    @AfterAll
    public static void end(){
        postgreSQLContainer.stop();
    }


    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
    }

    }


