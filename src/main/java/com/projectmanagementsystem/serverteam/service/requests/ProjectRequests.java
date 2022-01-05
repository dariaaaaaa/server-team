package com.projectmanagementsystem.serverteam.service.requests;

import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

public class ProjectRequests {
    private static final String projectBaseUrl = "http://server-project:8080/projects";
    private static final RestTemplate restTemplate = new RestTemplate();

    public static void checkForExisting(long projectId) throws IllegalArgumentException{
        final String projectUrl = String.format( projectBaseUrl + "/exist?id=%d", projectId);

        try {
            final int userResponse = restTemplate.exchange(URI.create(projectUrl), HttpMethod.HEAD, null, Void.class ).getStatusCodeValue();
        } catch (HttpClientErrorException e) {throw new IllegalArgumentException("Project not found");}
    }
}
