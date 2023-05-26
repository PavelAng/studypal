package com.edu.ruse.studypal.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;

@Configuration
public class GoogleCalendarConfig {

    private static final String APPLICATION_NAME = "StudyPal";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String CREDENTIALS_FILE_PATH = "classpath:studypal-384914-eb8995897598.json";
    private static final Iterable<String> SCOPES = Collections.singleton(CalendarScopes.CALENDAR);

    @Autowired
    private ResourceLoader resourceLoader;

    @Bean
    public Calendar calendarService() throws Exception {
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        GoogleCredential credential = GoogleCredential.fromStream(getCredentialsFileInputStream())
                .createScoped((Collection<String>) SCOPES);
        return new Calendar.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private InputStream getCredentialsFileInputStream() throws IOException {
        Resource resource = resourceLoader.getResource(CREDENTIALS_FILE_PATH);
        return resource.getInputStream();
    }
}
