package com.sg.dvdlibrary.controller;
import com.sg.dvdlibrary.dao.*;
import com.sg.dvdlibrary.dto.Dvd;
import java.util.*;

import com.sg.dvdlibrary.ui.*;

public class DvdLibraryController {
    private DvdLibraryView view;
    private DvdLibraryDao dao;

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

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }
}
