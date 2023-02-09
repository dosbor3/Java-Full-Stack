package com.sg.dvdlibrary.dao;

import com.sg.dvdlibrary.dto.Dvd;
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
     * @param title title with which Dvd is to associated that will be edited
     * @return the dvd object associated with the given title,
     * null if no such dvd exists
     */
    Dvd getDvd(String title) throws DvdLibraryDaoException;

}
