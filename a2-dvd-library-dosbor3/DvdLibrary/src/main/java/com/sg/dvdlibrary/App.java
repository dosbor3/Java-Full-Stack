package com.sg.dvdlibrary;
import com.sg.dvdlibrary.controller.*;
import com.sg.dvdlibrary.ui.*;
import com.sg.dvdlibrary.dao.*;

public class App {

    public static void main(String[] args) throws DvdLibraryDaoException {
        UserIO myIo = new UserIOConsoleImpl();
        DvdLibraryView myView = new DvdLibraryView(myIo);
        DvdLibraryDao myDao = new DvdLibraryDaoFileImpl();
        DvdLibraryController controller =
                new DvdLibraryController(myDao, myView);
        controller.run();
    }
}
