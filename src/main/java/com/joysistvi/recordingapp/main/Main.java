package com.joysistvi.recordingapp.main;

import java.sql.*;

public class Main {

    static String url = "jdbc:mysql://localhost:3306/recording_studio_db";
    static String username = "root";
    static String password = "";

    public static void main(String[] args) {
        //viewAllAlbums();
        viewAllAlbums();
        createAlbum("Coco", 1990, 1);
        updateAlbum(14, "Coco (Remastered)", 1995, 1);
        deleteAlbum(13);
        // --- Testing Artists CRUD ---
        viewAllArtists();
        createArtist("Paolo Sandejas");
        updateArtist(12, "Paolo Sandejas");
        deleteArtist(11);


        getAlbumById(1);
        getArtistById(1);
        getAlbumById(999);   // should print "No album found with ID 999."
    }

    // ducking exception
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }


    //CRUD Operation

    // Read Albums
    public static void viewAllAlbums() {
        String query = "SELECT albums.id, albums.name, albums.year, artists.name AS artist_name " +
                "FROM albums " +
                "JOIN artists " +
                "ON albums.artist_id = artists.id"; // Create SQL Statement / Query

        try (Connection connection = connect();
             Statement statement = connection.createStatement(); // create a statement object for sending sql statement
             ResultSet resultSet = statement.executeQuery(query); // execute query then store the result in the result set
        ) {
            System.out.println("\n------Albums------");

            System.out.printf("%-4s %-20s %-8s %-20s%n", "ID", "Name", "Year", "Artist"); // print header

            // Extract the data
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int year = resultSet.getInt("year");
                String artistName = resultSet.getString("artist_name");

                System.out.printf("%-4d %-20s %-8d %-20s%n", id, name, year, artistName);
            }
        } catch (SQLException e) {
            System.out.println("Read Albums: " + e.getMessage());
        }
    }

    //DML - Data Manipulation Language (preparedStatement) (executeUpdate)-> Create, Update, Delete
    //DQL - Data Query Language (createStatement) (executeQuery)-> Read / Select

    // Create Album
    public static void createAlbum (String name,int year, int artistId){
        String query = "INSERT INTO albums (name, year, artist_id) " +
                "VALUES (?, ?, ?)"; // parameterized query (anti-sql injection)

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            // setting parameter wild cards
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, year);
            preparedStatement.setInt(3, artistId);

            preparedStatement.executeUpdate();

            System.out.println("Album: " + name + " created successfully!");
            viewAllAlbums(); // RAW
        } catch (SQLException e) {
            System.out.println("Update Album: " + e.getMessage());
        }
    }

    // Update Album
    public static void updateAlbum(int id, String name, int year, int artistId) {
        String query = "UPDATE albums SET name = ?, year = ?, artist_id = ? WHERE id = ?";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, year);
            preparedStatement.setInt(3, artistId);
            preparedStatement.setInt(4, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("\nAlbum ID " + id + " updated successfully!");
                viewAllAlbums();
            } else {
                System.out.println("\nNo album found with ID " + id + ".");
            }
        } catch (SQLException e) {
            System.out.println("\nUpdate Album Error: " + e.getMessage());
        }
    }

    // Delete Album
    public static void deleteAlbum(int id) {
        String query = "DELETE FROM albums WHERE id = ?";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("\nAlbum ID " + id + " deleted successfully!");
                viewAllAlbums();
            } else {
                System.out.println("\nNo album found with ID " + id + ".");
            }
        } catch (SQLException e) {
            System.out.println("\nDelete Album Error: " + e.getMessage());
        }
    }

    //CRUD Operation for Artist

    // Read Artists
    public static void viewAllArtists() {
        String query = "SELECT * FROM artists ";

        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            System.out.println("\n----- Artists -----");
            System.out.printf("%-4s %-20s%n", "ID", "Artist Name");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                System.out.printf("%-4d %-20s%n", id, name);
            }
        } catch (SQLException e) {
            System.out.println("Read Artists Error: " + e.getMessage());
        }
    }

    // Create Artist
    public static void createArtist(String name) {
        String query = "INSERT INTO artists (name) VALUES (?)";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, name);

            preparedStatement.executeUpdate();

            System.out.println("\nArtist: " + name + " created successfully!");
            viewAllArtists();
        } catch (SQLException e) {
            System.out.println("Create Artist Error: " + e.getMessage());
        }
    }

    // Update Artist
    public static void updateArtist(int id, String name) {
        String query = "UPDATE artists SET name = ? WHERE id = ?";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("\nArtist ID " + id + " updated successfully!");
                viewAllArtists();
            } else {
                System.out.println("\nNo artist found with ID " + id + ".");
            }
        } catch (SQLException e) {
            System.out.println("\nUpdate Artist Error: " + e.getMessage());
        }
    }

    // Delete Artist
    public static void deleteArtist(int id) {
        String query = "DELETE FROM artists WHERE id = ?";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("\nArtist ID " + id + " deleted successfully!");
                viewAllArtists();
            } else {
                System.out.println("\nNo artist found with ID " + id + ".");
            }
        } catch (SQLException e) {
            System.out.println("\nDelete Artist Error: " + e.getMessage());
        }
    }

    // Find Album by ID
    public static void getAlbumById(int id) {
        String query = "SELECT albums.id, albums.name, albums.year, artists.name AS artist_name " +
                "FROM albums " +
                "JOIN artists ON albums.artist_id = artists.id " +
                "WHERE albums.id = ?";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("\n------Album Found------");
                    System.out.printf("%-4s %-20s %-8s %-20s%n", "ID", "Name", "Year", "Artist");
                    System.out.printf("%-4d %-20s %-8d %-20s%n",
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getInt("year"),
                            resultSet.getString("artist_name"));
                } else {
                    System.out.println("\nNo album found with ID " + id + ".");
                }
            }
        } catch (SQLException e) {
            System.out.println("Find Album Error: " + e.getMessage());
        }
    }

    // Find Artist by ID
    public static void getArtistById(int id) {
        String query = "SELECT * FROM artists WHERE id = ?";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("\n----- Artist Found -----");
                    System.out.printf("%-4s %-20s%n", "ID", "Artist Name");
                    System.out.printf("%-4d %-20s%n",
                            resultSet.getInt("id"),
                            resultSet.getString("name"));
                } else {
                    System.out.println("\nNo artist found with ID " + id + ".");
                }
            }
        } catch (SQLException e) {
            System.out.println("Find Artist Error: " + e.getMessage());
        }
    }
}