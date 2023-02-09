package com.sg.dvdlibrary.dao;

import com.sg.dvdlibrary.dto.Dvd;

import javax.print.DocFlavor;
import java.util.*;

public interface DvdLibraryDao {
    /**
     * adds the given Dvd to the collection and associates it with the given Dvd title
     * if the Dvd exists, the cureent Dvd will be returned, null otherwise
     * @param title title with which Dvd is to associated
     * @param dvd dvd to be added to the collection
     * @return the Dvd object previously associated wi the given dvd title if it exists, null otherwise
     */
    Dvd addDvd(String title, Dvd dvd) throws DvdLibraryDaoException;

    /**
     * removes the given Dvd from the collection and associates it with the given Dvd title
     * Returns the Dvd object tht is being removed or null if thee is no Dvd associated with the given id
     *
     * @param title title with which Dvd is to associated that will be removed
     * @return the Dvd object that is being removed
     */
    Dvd removeDvd(String title) throws DvdLibraryDaoException;

    /**
     * allows the user to edit the Dvd information for the Dvd object associated with the dvd title
     * @param title title with which Dvd is to associated that will be edited
     * @return the Dvd object that is being edited
     */
    void editDvdInfo(String title, Dvd dvd) throws DvdLibraryDaoException;

    /**
     * Returns a List of all dvds in the collection.
     * @return List containing all dvs in the collection
     */
    List<Dvd> getAllDvds() throws DvdLibraryDaoException;

    /**
     * Returns the dvd object associated with the given title
     * Returns null if no such dvd exists
     *
     * @param title String title with which Dvd is to associated that will be edited
     * @return the dvd object associated with the given title,
     * null if no such dvd exists
     */
    Dvd getDvd(String title) throws DvdLibraryDaoException;

    /**
     * Returns a List of all dvds released in the last N years
     * @param years string duration period in years to seach for dvd movie titles
     * @return List containing all dvs in the collection that was release during the specified time period (years)
     */
    List<Dvd> getAllDvdsReleasedByYears(String years) throws DvdLibraryDaoException;

    /**
     * Returns a List of all dvds in the collection.
     * @param rating String rating to search for dvd titles to return in a list
     * @return List containing all dvs in the collection with the specified rating
     */
    List<Dvd> getAllDvdsByMpaaRating(String rating) throws DvdLibraryDaoException;

    /**
     * Returns a List of all dvds in the collection.
     * @param director String dir_name to search for dvd titles to return in a list
     * @return List containing all dvs in the collection with the specified dir_name
     */
    Map<String, List<Dvd>> getAllDvdsByDirector(String director) throws DvdLibraryDaoException;

    /**
     * Returns a List of all dvds in the collection.
     * @param studio String studio to search for dvd titles to return in a list
     * @return List containing all dvs in the collection with the specified studio
     */
    List<Dvd> getAllDvdsByStudio(String studio) throws DvdLibraryDaoException;

    /**
     * Returns a List of all dvds in the collection.
     * @return List containing all dvs in the collection within the specified average age
     */
     double getAverageAgeofAllDvds() throws DvdLibraryDaoException;

    /**
     * Returns the dvd object with the youngest age
     * Returns null if no such dvd exists
     * @return the dvd object with the youngest age,
     * null if no such dvd exists
     */
    Dvd getNewestDvd() throws DvdLibraryDaoException;

    /**
     * Returns the dvd object with the oldest age
     * Returns null if no such dvd exists
     * @return the dvd object with the oldest age,
     * null if no such dvd exists
     */
    Dvd getOldestDvd() throws DvdLibraryDaoException;

    /**
     * Returns the average number of notes in the DVD collection
     * @return the String average number of notes associated with the DVD collection,
     * null if no such dvd exists
     */
    String getAverageNumberOfNotes() throws DvdLibraryDaoException;



}
