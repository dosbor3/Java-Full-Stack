package com.sg.dvdlibrary.dao;

import com.sg.dvdlibrary.dto.Dvd;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.*;
import java.io.*;
import java.util.stream.Collectors;

@Component
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

    /**
     * Returns a List of all dvds released in the last N years
     *
     * @param years string duration period in years to seach for dvd movie titles
     * @return List containing all dvs in the collection that was release during the specified time period (years)
     */
    @Override
    public List<Dvd> getAllDvdsReleasedByYears(String years) throws DvdLibraryDaoException {
        loadDvdLibrary();
        List<Dvd> list_of_dvds = new ArrayList(dvds.values());
        int current_year = LocalDate.now().getYear();
        List<Dvd> releasedDvds = list_of_dvds.stream()
                .filter((d) -> Integer.parseInt(d.getReleaseYear()) >= (current_year - Integer.parseInt(years)))
                .filter((d) -> Integer.parseInt(d.getReleaseYear()) <= current_year)
                .collect(Collectors.toList());
        return releasedDvds;
    }

    /**
     * Returns a List of all dvds in the collection.
     *
     * @param rating String rating to search for dvd titles to return in a list
     * @return List containing all dvs in the collection with the specified rating
     */
    @Override
    public List<Dvd> getAllDvdsByMpaaRating(String rating) throws DvdLibraryDaoException {
        loadDvdLibrary();
        List<Dvd> list_of_dvds = new ArrayList(dvds.values());
        List<Dvd> ratedTitles = list_of_dvds.stream()
                .filter((d) -> d.getRating().equals(rating))
                .collect(Collectors.toList());
        return ratedTitles;
    }

    /**
     * Returns a List of all dvds in the collection.
     *
     * @param director String dir_name to search for dvd titles to return in a list
     * @return List containing all dvs in the collection with the specified dir_name
     */
    @Override
    public Map<String, List<Dvd>> getAllDvdsByDirector(String director) throws DvdLibraryDaoException {
        loadDvdLibrary();
        List<Dvd> list_of_dvds = new ArrayList(dvds.values());
        Map<String, Dvd> map_of_dvds = new HashMap<>(dvds);
        Map<String, List<Dvd>> directorTitles =  list_of_dvds.stream()
                .filter((d) -> d.getDirectorName().equals(director))
                .collect(Collectors.groupingBy((d) -> d.getRating()));
        return directorTitles;
    }

    /**
     * Returns a List of all dvds in the collection.
     *
     * @param studio String studio to search for dvd titles to return in a list
     * @return List containing all dvs in the collection with the specified studio
     */
    @Override
    public List<Dvd> getAllDvdsByStudio(String studio) throws DvdLibraryDaoException {
        loadDvdLibrary();
        List<Dvd> list_of_dvds = new ArrayList(dvds.values());
        List<Dvd> studioTitles = list_of_dvds.stream()
                .filter((d) -> d.getStudio().equals(studio))
                .collect(Collectors.toList());
        return studioTitles;
    }

    /**
     * Returns a List of all dvds in the collection.
     *
     * @return List containing all dvs in the collection within the specified average age
     */
    @Override
    public double getAverageAgeofAllDvds() throws DvdLibraryDaoException {
        loadDvdLibrary();
        List<Dvd> list_of_dvds = new ArrayList(dvds.values());
        int current_year = LocalDate.now().getYear();
        int sum = 0;
        List<Dvd> filtered = list_of_dvds.stream()
                .filter((d) -> !d.getReleaseYear().isEmpty())
                .collect(Collectors.toList());
        for(Dvd fd : filtered) {
            sum += (current_year - Integer.parseInt(fd.getReleaseYear()));
        }

        double average = sum / filtered.size();
        return average;
    }

    /**
     * Returns the dvd object with the youngest age
     * Returns null if no such dvd exists
     *
     * @return the dvd object with the youngest age,
     * null if no such dvd exists
     */
    @Override
    public Dvd getNewestDvd() throws DvdLibraryDaoException {
        loadDvdLibrary();
        List<Dvd> list_of_dvds = new ArrayList(dvds.values());
        Dvd newestDvd = Collections.max(list_of_dvds, Comparator.comparing(d -> Integer.parseInt(d.getReleaseYear())));
        return newestDvd;
    }



    /**
     * Returns the dvd object with the oldest age
     * Returns null if no such dvd exists
     *
     * @return the dvd object with the oldest age,
     * null if no such dvd exists
     */
    @Override
    public Dvd getOldestDvd() throws DvdLibraryDaoException {
        loadDvdLibrary();
        List<Dvd> list_of_dvds = new ArrayList(dvds.values());
        Dvd oldestDvd = Collections.min(list_of_dvds, Comparator.comparing(d -> Integer.parseInt(d.getReleaseYear())));
        return oldestDvd;
    }

    /**
     * Returns the average number of notes in the DVD collection
     *
     * @return the String average number of notes associated with the DVD collection,
     * null if no such dvd exists
     */
    @Override
    public String getAverageNumberOfNotes() throws DvdLibraryDaoException {
        return null;
    }

    private Dvd unmarshallingDvd(String dvdAsText) {
        //Black Panther: Wakanda Forever::Family::4.5::2005-06-26::Ryan Coogler::Marvel:: It was amazing!
        String[] dvdTokens = dvdAsText.split(DELIMITER);
        String title = dvdTokens[0];
        Dvd dvdFromFile = new Dvd(title);
        dvdFromFile.setGenre(dvdTokens[1]);
        dvdFromFile.setRating(dvdTokens[2]);
        LocalDate ld_release_date = LocalDate.parse(dvdTokens[3], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int ld_release_year = ld_release_date.getYear();
        String ld_year = String.valueOf(ld_release_year);
        dvdFromFile.setReleaseYear(ld_year);
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
