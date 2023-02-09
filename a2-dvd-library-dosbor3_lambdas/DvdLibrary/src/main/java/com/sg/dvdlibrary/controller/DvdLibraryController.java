package com.sg.dvdlibrary.controller;
import com.sg.dvdlibrary.dao.*;
import com.sg.dvdlibrary.dto.Dvd;
import java.util.*;

import com.sg.dvdlibrary.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DvdLibraryController {
    private DvdLibraryView view;
    private DvdLibraryDao dao;


    @Autowired
    public DvdLibraryController(DvdLibraryDao dao, DvdLibraryView view) {
        this.dao = dao;
        this.view = view;
    }

    public void run() throws DvdLibraryDaoException {
        boolean keepGoing = true;
        int menuSelection = 0;
        while (keepGoing) {
            menuSelection = getMenuSelection();

            switch (menuSelection) {
                case 1:
                    addDvd();
                    break;
                case 2:
                    removeDvd();
                    break;
                case 3:
                    editDvdInfo();
                    break;
                case 4:
                    listDvds();
                    break;
                case 5:
                    viewDvd();
                    break;
                case 6:
                    searchDvdByTitle();
                    break;
                case 7:
                    searchDvdByRange();
                    break;
                case 8:
                    searchDvdByRating();
                    break;
                case 9:
                    searchDvdByDirector();
                    break;
                case 10:
                    searchDvdByStudio();
                    break;
                case 11:
                    displayAverage();
                    break;
                case 12:
                    displayNewestMovie();
                    break;
                case 13:
                    displayOldestMovie();
                    break;
                case 14:
                    displayAverage();
                    break;
                case 15:
                    keepGoing = false;
                    break;
                default:
                    unknownCommand();
            }

        }
        exitMessage();
    }

    private int getMenuSelection() {return view.printMenuAndGetSelection();}

    private void addDvd() throws DvdLibraryDaoException {
        view.displayCreateDvdBanner();
        Dvd newDvd = view.getNewDvdInfo();
        dao.addDvd(newDvd.getTitle(), newDvd);
        view.displayAddSuccessBanner();
    }

    private void listDvds() throws DvdLibraryDaoException {
        view.displayDisplayAllBanner();
        List<Dvd> dvdList = dao.getAllDvds();
        view.displayDvdList(dvdList);
    }

    private void viewDvd() throws DvdLibraryDaoException {
        view.displayDisplayDvdBanner();
        String title = view.getDvdTitle();
        Dvd dvd = dao.getDvd(title);
        view.displayDvd(dvd);
    }
    private void removeDvd() throws DvdLibraryDaoException {
        view.displayRemoveDvdBanner();
        String title = view.getDvdTitle();
        Dvd removedDvd = dao.removeDvd(title);
        view.displayRemoveResult(removedDvd);
    }

    private void editDvdInfo() throws DvdLibraryDaoException {
        String title = view.getDvdTitle();
        Dvd dvd = dao.getDvd(title);
        Dvd editedDvd = view.getUpdatedDvdInfo(title, dvd);
        dao.editDvdInfo(title, editedDvd);
    }
    private void searchDvdByTitle() {
        String title = view.getDvdTitle();
        view.getDvdTitle();
        Dvd searchedDvd = new Dvd(title);
        view.displayDvd(searchedDvd);
    }

    private void searchDvdByRange() throws DvdLibraryDaoException {
        view.displaySearchByRangeBanner();
        String years = view.getSearchRange();
        view.displayDvdList(dao.getAllDvdsReleasedByYears(years));
    }

    private void searchDvdByRating() throws DvdLibraryDaoException {
        view.displaySearchByMpaaRatingBanner();
        String rating = view.getMpaaRatingForSearch();
        view.displayDvdList(dao.getAllDvdsByMpaaRating(rating));
    }

    private void searchDvdByDirector() throws DvdLibraryDaoException {
        view.displaySearchByDirectorBanner();
        String director = view.getDirectorForSearch();
        HashMap<String, List<Dvd>> directorTitles = new HashMap<>(dao.getAllDvdsByDirector(director));
        view.displayDvdMap(directorTitles);
    }

    private void searchDvdByStudio() throws DvdLibraryDaoException {
        view.displaySearchByStudioBanner();
        String studio = view.getStudioName();
        view.displayDvdList(dao.getAllDvdsByStudio(studio));
    }

    private void displayAverage() throws DvdLibraryDaoException {
        view.displayMoviesAverageRatingBanner();
        double average = dao.getAverageAgeofAllDvds();
        view.displayMoviesAverageAge(average);
        view.returnToSelectionMenu();
    }

    private void displayNewestMovie() throws DvdLibraryDaoException {
        view.displayNewestMovieBanner();
        view.displayDvd(dao.getNewestDvd());
        view.returnToSelectionMenu();
    }

    private void displayOldestMovie() throws DvdLibraryDaoException {
        view.displayOldestMovieBanner();
        view.displayDvd(dao.getOldestDvd());
        view.returnToSelectionMenu();
    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }
}
