package br.ufrn.imd.marketplace.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;

@Component
public class DatabaseInitializer {

    @Autowired
    private DB_Connection dbConnection;

    @PostConstruct
    public void init() {
        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            var resource = new ClassPathResource("schema.sql");
            String sql = Files.readString(resource.getFile().toPath(), StandardCharsets.UTF_8);
            stmt.execute(sql);
            System.out.println("✅ Banco de dados inicializado com sucesso!");
        } catch (Exception e) {
            System.out.println("⚠️ Falha ao inicializar o banco: " + e.getMessage());
        }
    }
}
