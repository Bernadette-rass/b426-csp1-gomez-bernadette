package com.joysistvi.recordingapp.view;

import com.joysistvi.recordingapp.model.Playlist;
import com.joysistvi.recordingapp.model.Song;
import com.joysistvi.recordingapp.service.PlaylistService;
import com.joysistvi.recordingapp.service.SongService;
import java.util.List;
import java.util.Scanner;

public class PlaylistView {
    private final PlaylistService playlistService;
    private final SongService songService;
    private final Scanner scanner;
    private int currentUserId;

    public PlaylistView(PlaylistService playlistService, SongService songService, Scanner scanner) {
        this.playlistService = playlistService;
        this.songService = songService;
        this.scanner = scanner;
    }

    // ======================================================================
    // FULL PLAYLIST MANAGEMENT MENU (Task 4)
    // CRUD + Archive/Restore + Search on the playlists table.
    // ======================================================================
    public void handleMenu() {
        while (true) {
            System.out.println("\n=== PLAYLIST MANAGEMENT ===");
            System.out.println("1. View All Active Playlists");
            System.out.println("2. Create Playlist");
            System.out.println("3. Update Playlist");
            System.out.println("4. Search Playlists");
            System.out.println("5. Archive Playlist");
            System.out.println("6. Restore Archived Playlist");
            System.out.println("7. Delete Playlist (Permanent)");
            System.out.println("0. Back");

            int choice = promptForInt("Choose an option: ");
            switch (choice) {
                case 1 -> displayAllPlaylists();
                case 2 -> promptCreatePlaylist();
                case 3 -> promptUpdatePlaylist();
                case 4 -> promptSearchPlaylists();
                case 5 -> promptArchivePlaylist();
                case 6 -> promptRestorePlaylist();
                case 7 -> promptDeletePlaylist();
                case 0 -> { return; }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public void displayAllPlaylists() {
        printPlaylistTable("----- Active Playlists -----",
                playlistService.getAllActivePlaylists(), "No active playlists found.");
    }

    public void promptCreatePlaylist() {
        System.out.println("\n=== CREATE Playlist ===");
        System.out.print("Enter playlist name: ");
        String name = scanner.nextLine();
        int userId = promptForInt("Enter owner (user) ID: ");

        if (playlistService.createPlaylist(name, userId)) {
            System.out.println("Playlist '" + name.trim() + "' created successfully!");
        } else {
            System.out.println("Failed to create playlist (check input, user ID, or the user may already have a playlist).");
        }
    }

    public void promptUpdatePlaylist() {
        System.out.println("\n=== UPDATE Playlist ===");
        List<Playlist> active = playlistService.getAllActivePlaylists();
        printPlaylistTable("----- Active Playlists -----", active, "No active playlists found.");
        if (active == null || active.isEmpty()) {
            return;
        }

        int id = promptForInt("Enter playlist ID to update: ");
        System.out.print("Enter new playlist name: ");
        String name = scanner.nextLine();
        int userId = promptForInt("Enter owner (user) ID: ");

        if (playlistService.updatePlaylist(id, name, userId)) {
            System.out.println("Playlist ID " + id + " updated successfully!");
        } else {
            System.out.println("No playlist updated (check ID or input).");
        }
    }

    public void promptSearchPlaylists() {
        System.out.println("\n=== SEARCH Playlists ===");
        System.out.print("Enter search keyword: ");
        String keyword = scanner.nextLine();

        List<Playlist> matches = playlistService.searchPlaylists(keyword);
        printPlaylistTable("----- Search Results (Keyword: '" + keyword.trim() + "') -----",
                matches, "No matching active playlists found.");
    }

    public void promptArchivePlaylist() {
        System.out.println("\n=== ARCHIVE Playlist ===");
        List<Playlist> active = playlistService.getAllActivePlaylists();
        printPlaylistTable("----- Active Playlists -----", active, "No active playlists found.");
        if (active == null || active.isEmpty()) {
            return;
        }

        int id = promptForInt("Enter playlist ID to archive: ");
        if (playlistService.archivePlaylist(id)) {
            System.out.println("Playlist ID " + id + " archived successfully!");
        } else {
            System.out.println("No playlist archived (check ID or input).");
        }
    }

    public void promptRestorePlaylist() {
        System.out.println("\n=== RESTORE Playlist ===");
        List<Playlist> archived = playlistService.getAllArchivedPlaylists();
        printPlaylistTable("----- Archived Playlists -----", archived, "No archived playlists found.");

        // Return early if there is nothing to restore
        if (archived == null || archived.isEmpty()) {
            return;
        }

        int id = promptForInt("Enter playlist ID to restore: ");
        if (playlistService.restorePlaylist(id)) {
            System.out.println("Playlist ID " + id + " restored successfully!");
        } else {
            System.out.println("No archived playlist restored (check ID or input).");
        }
    }

    public void promptDeletePlaylist() {
        System.out.println("\n=== DELETE Playlist ===");
        List<Playlist> active = playlistService.getAllActivePlaylists();
        printPlaylistTable("----- Active Playlists -----", active, "No active playlists found.");
        if (active == null || active.isEmpty()) {
            return;
        }

        int id = promptForInt("Enter playlist ID to permanently delete: ");
        if (playlistService.deletePlaylist(id)) {
            System.out.println("Playlist ID " + id + " deleted successfully!");
        } else {
            System.out.println("No playlist deleted (check ID, or it may still be in use - try Archive instead).");
        }
    }

    private void printPlaylistTable(String header, List<Playlist> playlists, String emptyMessage) {
        String border = "+------+---------------------------+--------------------+";

        System.out.println("\n" + header);
        System.out.println(border);
        System.out.printf("| %-4s | %-25s | %-18s |%n", "ID", "Playlist Name", "Owner");
        System.out.println(border);

        if (playlists == null || playlists.isEmpty()) {
            String message = (emptyMessage != null && !emptyMessage.isEmpty())
                    ? emptyMessage
                    : "No playlists found.";
            System.out.printf("| %-52s |%n", truncate(message, 52));
            System.out.println(border);
            return;
        }

        for (Playlist p : playlists) {
            String name = p.getName() != null ? p.getName() : "";
            String owner = p.getUsername() != null ? p.getUsername() : "";
            System.out.printf("| %-4d | %-25s | %-18s |%n",
                    p.getId(),
                    truncate(name, 25),
                    truncate(owner, 18));
        }

        System.out.println(border);
    }

    // ======================================================================
    // PER-USER PLAYLIST MENU (used by the USER dashboard) - unchanged
    // ======================================================================
    public void handleMenu(int userId) {
        this.currentUserId = userId;
        while (true) {
            System.out.println("\n=== MANAGE PLAYLIST ===");
            System.out.println("1. View Playlist");
            System.out.println("2. Create Playlist");
            System.out.println("3. Delete Playlist");
            System.out.println("0. Back");

            int choice = promptForInt("Choose an option: ");
            switch (choice) {
                case 1:
                    handleViewUserPlaylists(currentUserId);
                    break;
                case 2:
                    promptCreatePlaylist(currentUserId);
                    break;
                case 3:
                    promptDeleteUserPlaylist(currentUserId);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    public void promptCreatePlaylist(int userId) {
        System.out.println("\n--- Create Playlist ---");
        if (!playlistService.getPlaylistsByUser(userId).isEmpty()) {
            System.out.println("You already have a playlist. Only one playlist is allowed per user.");
            return;
        }

        System.out.print("Enter playlist name: ");
        String name = scanner.nextLine();

        if (playlistService.createPlaylist(name, userId)) {
            System.out.println("Playlist created successfully.");
        } else {
            System.out.println("Failed to create playlist.");
        }
    }

    public void handleViewUserPlaylists(int userId) {
        List<Playlist> userPlaylists = playlistService.getPlaylistsByUser(userId);
        if (userPlaylists.isEmpty()) {
            System.out.println("\nNo playlist found.");
            return;
        }

        for (Playlist playlist : userPlaylists) {
            System.out.println("\nPlaylist: " + playlist.getName());
            List<Song> songs = playlistService.getSongsInPlaylist(playlist.getId());
            printSongListTable("----- Current Songs in Playlist -----", songs);
        }
    }

    public void promptDeleteUserPlaylist(int userId) {
        System.out.println("\n--- Delete Playlist ---");
        List<Playlist> userPlaylists = playlistService.getPlaylistsByUser(userId);
        if (userPlaylists.isEmpty()) {
            System.out.println("No playlist found to delete.");
            return;
        }

        Playlist playlist = userPlaylists.get(0);
        if (playlistService.deletePlaylist(playlist.getId())) {
            System.out.println("Playlist \"" + playlist.getName() + "\" deleted successfully.");
        } else {
            System.out.println("Failed to delete playlist.");
        }
    }

    // Additional Methods
    public void handleManageSongsInPlaylist(int userId) {
        List<Playlist> userPlaylists = playlistService.getPlaylistsByUser(userId);
        if (userPlaylists.isEmpty()) {
            System.out.println("\nNo playlist found for this user. Please create a playlist first.");
            return;
        }

        Playlist playlist = userPlaylists.get(0);

        while (true) {
            System.out.println("\n=== MANAGE SONGS (" + playlist.getName() + ") ===");
            System.out.println("1. Add Song to Playlist");
            System.out.println("2. Remove Song from Playlist");
            System.out.println("0. Back");

            int choice = promptForInt("Choose an option: ");
            switch (choice) {
                case 1:
                    printSongListTable("----- Available Songs -----", songService.getAllActiveSongs());
                    int addSongId = promptForInt("Enter song ID to add: ");
                    if (playlistService.addSongToPlaylist(playlist.getId(), addSongId)) {
                        System.out.println("Song added to playlist.");
                    } else {
                        System.out.println("Failed to add song to playlist.");
                    }
                    break;
                case 2:
                    printSongListTable("----- Current Songs in Playlist -----", playlistService.getSongsInPlaylist(playlist.getId()));
                    int removeSongId = promptForInt("Enter song ID to remove: ");
                    if (playlistService.removeSongFromPlaylist(playlist.getId(), removeSongId)) {
                        System.out.println("Song removed from playlist.");
                    } else {
                        System.out.println("Failed to remove song from playlist.");
                    }
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private void printSongListTable(String header, List<Song> songs) {
        String border = "+-------+------------------------+-----------------+----------+----------------------+";

        System.out.println("\n" + header);
        System.out.println(border);
        System.out.printf("| %-5s | %-22s | %-15s | %-8s | %-20s |%n",
                "ID", "Title", "Genre", "Length", "Album");
        System.out.println(border);

        if (songs == null || songs.isEmpty()) {
            System.out.printf("| %-84s |%n", truncate("No songs found.", 84));
            System.out.println(border);
            return;
        }

        for (Song s : songs) {
            String title = s.getTitle() != null ? s.getTitle() : "";
            String genre = s.getGenre() != null ? s.getGenre() : "";
            String album = s.getAlbumName() != null ? s.getAlbumName() : "";

            System.out.printf("| %-5d | %-22s | %-15s | %-8s | %-20s |%n",
                    s.getId(),
                    truncate(title, 22),
                    truncate(genre, 15),
                    s.getLength(),
                    truncate(album, 20));
        }

        System.out.println(border);
    }

    private String truncate(String text, int maxLength) {
        if (text == null) {
            return "";
        }
        if (text.length() <= maxLength) {
            return text;
        }
        if (maxLength <= 3) {
            return text.substring(0, maxLength);
        }
        return text.substring(0, maxLength - 3) + "...";
    }

    private int promptForInt(String promptMessage) {
        while (true) {
            System.out.print(promptMessage);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
}
