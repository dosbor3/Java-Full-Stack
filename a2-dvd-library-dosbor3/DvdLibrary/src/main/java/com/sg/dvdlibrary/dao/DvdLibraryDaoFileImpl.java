package com.sg.dvdlibrary.dao;

import com.sg.dvdlibrary.dto.Dvd;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.*;
import java.io.*;

public class DvdLibraryDaoFileImpl implements DvdLibraryDao{
    private Map<String, Dvd> dvds = new HashMap<>();
    public static final String DVD_FILE = "dvd_collection.txt";
    public static final String DELIMITER = "::";

    /**
     * adds the given Dvd to the collection and associates it with the given Dvd title
     * if the Dvd exists, the current Dvd will be returned, null otherwise
     *
     * @param input_title title with which Dvd is to associated
     * @param dvd   dvd to be added to the collection
     * @return the Dvd object previously associated with the given dvd title if it exists, null otherwise
     */
    @Override
    public Dvd addDvd(String input_title, Dvd dvd) throws DvdLibraryDaoException {
        String title = input_title.toUpperCase();
        loadDvdLibrary();
        Dvd prevDvd = dvds.put(title, dvd);
        writeDvdLibrary();
        return prevDvd;
    }

    /**
     * removes the given Dvd from the collection and associates it with the given Dvd title
     * Returns the Dvd object tht is being removed or null if thee is no Dvd associated with the given id
     *
     * @param input_title title with which Dvd is to associated that will be removed
     * @return the Dvd object that is being removed
     */
    @Override
    public Dvd removeDvd(String input_title) throws DvdLibraryDaoException{
        loadDvdLibrary();
        String title = input_title.toUpperCase();
        Dvd removedDvd = dvds.remove(title);
        writeDvdLibrary();
        return removedDvd;
    }

    /**
     * allows the user to edit the Dvd information for the Dvd object associated with the dvd title
     *
     * @param input_title title with which Dvd is to associated that will be edited
     * @return the Dvd object that is being edited
     */
    @Override
    public void editDvdInfo(String input_title, Dvd editedDvd) throws DvdLibraryDaoException{
        loadDvdLibrary();
        String title = input_title.toUpperCase();
        dvds.put(title, editedDvd);
        writeDvdLibrary();
        //return updatedDvd;
    }

    /**
     * Returns a List of all dvds in the collection.
     *
     * @return List containing all dvs in the collection
     */
    @Override
    public List<Dvd> getAllDvds() throws DvdLibraryDaoException{
        loadDvdLibrary();
        return new ArrayList<Dvd>(dvds.values());
    }

    /**
     * Returns the dvd object associated with the given title
     * Returns null if no such dvd exists
     *
     * @param input_title title with which Dvd is to associated that will be edited
     * @return the dvd object associated with the given title,
     * null if no such dvd exists
     */
    @Override
    public Dvd getDvd(String input_title) throws DvdLibraryDaoException{
        loadDvdLibrary();
        String title = input_title.toUpperCase();
        return dvds.get(title);
    }
    private Dvd unmarshallingDvd(String dvdAsText) {
        //Black Panther: Wakanda Forever::Family::4.5::2005-06-26::Ryan Coogler::Marvel:: It was amazing!
        String[] dvdTokens = dvdAsText.split(DELIMITER);
        String title = dvdTokens[0];
        Dvd dvdFromFile = new Dvd(title);
        dvdFromFile.setGenre(dvdTokens[1]);
        dvdFromFile.setRating(dvdTokens[2]);
        LocalDate ld_release_date = LocalDate.parse(dvdTokens[3], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        dvdFromFile.setReleaseDate(ld_release_date);
        dvdFromFile.setDirectorName(dvdTokens[4]);
        dvdFromFile.setStudio(dvdTokens[5]);
        dvdFromFile.setNote_or_rating(dvdTokens[6]);
        return dvdFromFile;

    }
    private void loadDvdLibrary() throws DvdLibraryDaoException {
        Scanner scanner;

        try {
            scanner = new Scanner(new BufferedReader(new FileReader(DVD_FILE)));
        }
        catch(FileNotFoundException e) {
            throw new DvdLibraryDaoException("-_- Could not load dvd data into memory." , e);
        }

        String currentLine;

        Dvd currentDvd;

        while(scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentDvd = unmarshallingDvd(currentLine);

            dvds.put(currentDvd.getTitle(), currentDvd);
        }
        scanner.close();
    }
    private String marshallDvd(Dvd aDvd) {
        String dvdAsText = aDvd.getTitle() + DELIMITER;
        dvdAsText += aDvd.getGenre() + DELIMITER;
        dvdAsText += aDvd.getRating() + DELIMITER;
        //LocalDate foratted_release_date = aDvd.getReleaseDate().
        dvdAsText += aDvd.getReleaseDate() + DELIMITER;
        dvdAsText += aDvd.getDirectorName()  + DELIMITER;
        dvdAsText += aDvd.getStudio() + DELIMITER;
        dvdAsText += aDvd.getNote_or_rating() + DELIMITER;
        return dvdAsText;
    }

    private void writeDvdLibrary() throws DvdLibraryDaoException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(DVD_FILE));
        } catch (IOException e) {
            throw new DvdLibraryDaoException(
                    "Could not save Dvd data.", e);
        }

        String dvdAsText;
        List<Dvd> dvdList = this.getAllDvds();
        for (Dvd currentDvd : dvdList) {
            dvdAsText = marshallDvd(currentDvd);
            out.println(dvdAsText);
            out.flush();
        }
        // Clean up
        out.close();
    }
}
