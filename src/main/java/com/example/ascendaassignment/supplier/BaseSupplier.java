package com.example.ascendaassignment.supplier;

import com.example.ascendaassignment.model.Hotel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseSupplier {

    public abstract String endpoint();

    public abstract Hotel parse(Object obj);

    public abstract String getName();
    public List<Hotel> fetch(){

        String url = endpoint();

        List<Object> response = makeHttpRequest(url);

        List<Hotel> hotels = new ArrayList<>();

        response.forEach(obj->{
            hotels.add(parse(obj));
        });

        return hotels;
    }

    // Make http request for getting response
    private List<Object> makeHttpRequest(String url) {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        try {

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() != 200)
            {
                throw new RuntimeException("Failed to fetch data from: " + url + " (Status code: " + response.statusCode() + ")");
            }

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response.body(), new TypeReference<>() {
            });

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    ;
}
