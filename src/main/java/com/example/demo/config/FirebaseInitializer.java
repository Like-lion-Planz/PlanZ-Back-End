package com.example.demo.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseInitializer {

    @Autowired
    private FirebaseProperties firebaseProperties;

    @PostConstruct
    public void initialize() {
        try {
            InputStream serviceAccount = this.getClass().getClassLoader().getResourceAsStream(firebaseProperties.getKeyPath());

            if (serviceAccount == null) {
                System.err.println("Firebase key file not found in classpath: " + firebaseProperties.getKeyPath());
                throw new IOException("Firebase key file not found in classpath: " + firebaseProperties.getKeyPath());
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setProjectId(firebaseProperties.getProjectId())
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
        } catch (IOException e) {
            e.printStackTrace(); // 여기에 로깅 추가 또는 적절한 예외 처리 필요
        }
    }
}
