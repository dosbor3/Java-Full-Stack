package com.sg.dvdlibrary.ui;
import com.sg.dvdlibrary.ui.UserIO;
import com.sg.dvdlibrary.dto.Dvd;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DvdLibraryView {
    private UserIO io;

    public DvdLibraryView(UserIO io) {
        this.io = io;
    }

    public int printMenuAndGetSelection() {
        io.print("Main Menu");
        io.print("1. Add DVD");
        io.print("2. Remove DVD");
        io.print("3. Edit DVD Info");
        io.print("4. List All DVDs");
        io.print("5. List A DVD");
        io.print("6. Search By Title");
        io.print("7. Exit");

        return io.readInt("Please select from the above choices.", 1, 7);
    }

    public Dvd getNewDvdInfo() {
        String title = io.readString("Please enter movie title: ");
        String genre = io.readString("Please enter movie genre: ");
        String rating = io.readString(("Please enter the movie rating: "));
        String release_date = io.readString("Please enter the movie release date ( MM/dd/yyyy ): ");
        LocalDate ld_release_date = LocalDate.parse(release_date, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        String dir_name = io.readString("Please enter the movie director's name:");
        String studio = io.readString("Please enter the movie's studio: ");
        String rating_or_note = io.readString("Please enter Your rating or comments :");

        Dvd currentDvd = new Dvd(title);
        currentDvd.setGenre(genre);
        currentDvd.setRating(rating);
        currentDvd.setReleaseDate(ld_release_date);
        currentDvd.setDirectorName(dir_name);
        currentDvd.setStudio(studio);
        currentDvd.setNote_or_rating(rating_or_note);
        return currentDvd;
    }
    public void displayCreateDvdBanner() {
        io.print("=== ADD DVD ===");
    }

    public void displayAddSuccessBanner() {
        io.readString(
                "Dvd successfully added.  Please hit enter to continue");
    }

    public void displayDvdList(List<Dvd> dvdList) {
        if (dvdList.size() > 0) {
            for(Dvd currentDvd: dvdList) {
                io.print("===================================");
                io.print(currentDvd.getTitle());
                io.print(currentDvd.getGenre());
                io.print(currentDvd.getRating());
                io.print(currentDvd.getReleaseDate().toString());
                io.print(currentDvd.getDirectorName());
                io.print(currentDvd.getStudio());
                io.print(currentDvd.getNote_or_rating());
                io.print("===================================");
            }
        }
        else {
            io.print("There are NO Dvds in the collection");
        }

        io.readString("Please hit enter to continue.");
    }
    public void displayDisplayAllBanner() {
        io.print("=== Display All Dvds ===");
    }

    public void displayDisplayDvdBanner () {
        io.print("=== Display Dvd ===");
    }

    public String getDvdTitle() {
        return io.readString("Please enter the Dvd title.");
    }

    public void displayDvd(Dvd dvd) {
        if (dvd != null) {
            io.print("===================================");
            io.print(dvd.getTitle());
            io.print(dvd.getGenre());
            io.print(dvd.getRating());
            io.print(dvd.getReleaseDate().toString());
            io.print(dvd.getDirectorName());
            io.print(dvd.getStudio());
            io.print(dvd.getNote_or_rating());
            io.print("===================================");
            io.print("");
        } else {
            io.print("No such dvd.");
        }
        io.readString("Please hit enter to continue.");
    }
    public void displayRemoveDvdBanner () {
        io.print("=== Remove DVD ===");
    }

    public void displayRemoveResult(Dvd dvdRecord) {
        if(dvdRecord != null){
            io.print("Dvd successfully removed.");
        }else{
            io.print("No such dvd.");
        }
        io.readString("Please hit enter to continue.");
    }

    public Dvd getUpdatedDvdInfo(String title, Dvd dvd) {
        int selection;

        do {
            io.print("1. Edit DVD Release Date");
            io.print("2. Edit DVD Rating");
            io.print("3. Edit DVD Director's Name");
            io.print("4. Edit DVD Studio");
            io.print("5. Edit your User Rating or Comment");
            io.print("6. Exit");
            io.print("");
            selection = io.readInt("Select the Information you wish to edit: ", 1, 6);

            switch (selection) {
                case 1: {
                    String user_input_release_date = io.readString("Enter Updated Release Date( MM/dd/yyyy ): ");
                    LocalDate ld_release_date = LocalDate.parse(user_input_release_date, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                    dvd.setReleaseDate(ld_release_date);
                    break;
                }
                case 2: dvd.setRating(io.readString("Enter Updated Rating: ")); break;
                case 3: dvd.setDirectorName(io.readString("Enter Updated Director's Name: ")); break;
                case 4: dvd.setStudio(io.readString("Enter Updated Studio: ")); break;
                case 5: dvd.setNote_or_rating(io.readString("Enter Updated Rating or Comment: ")); break;
                case 6: break;
                default: io.print("UNKNOWN COMMAND"); break;
            }
        } while (selection != 6);
        return dvd;
    }

    public void displayExitBanner() {
        io.print("Good Bye!!!");
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }
}
