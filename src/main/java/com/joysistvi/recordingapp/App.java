package com.joysistvi.recordingapp;

import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.repository.ArtistRepository;
import com.joysistvi.recordingapp.repository.ArtistRepositoryImpl;


// Entry Point
public class App {
    public static void main(String[] args) {
        DbConnection dbConnection = new DbConnection();

        ArtistRepository artistRepository = new ArtistRepositoryImpl(dbConnection);

    }
}
