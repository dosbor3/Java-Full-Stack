package com.sg.dvdlibrary;
import com.sg.dvdlibrary.controller.*;
import com.sg.dvdlibrary.ui.*;
import com.sg.dvdlibrary.dao.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContextExtensionsKt;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) throws DvdLibraryDaoException {
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.scan("com.sg.dvdlibrary");
        appContext.refresh();

        DvdLibraryController controller = appContext.getBean("dvdLibraryController", DvdLibraryController.class);
        controller.run();
    }
}
