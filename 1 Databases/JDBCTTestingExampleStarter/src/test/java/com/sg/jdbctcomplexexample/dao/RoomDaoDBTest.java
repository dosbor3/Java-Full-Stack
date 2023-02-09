package com.sg.jdbctcomplexexample.dao;

import com.sg.jdbctcomplexexample.entity.*;
import com.sg.jdbctcomplexexample.TestApplicationConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

//These annotations allow the tests to access all of our DAOs and ensure that Spring Boot will understand
//that these are tests it can run.
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)




class RoomDaoDBTest {
    //Although we are only testing the RoomDAO in this test file, we want access to the other two DAOs so we can create
    //complex objects in the appropriate tests. For the Room DAO, the delete test will specifically need a complex object.
    @Autowired
    RoomDao roomDao;

    @Autowired
    EmployeeDao employeeDao;

    @Autowired
    MeetingDao meetingDao;


    //One last thing to do before we get to the actual tests is the "setUp" method, the one with the @BeforeEach
    // annotation. We use this method to empty the test database before every single test. By emptying the database,
    // we return it to an expected state: if nothing is in the database, only what we add to it will be considered
    // for the test.
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        List<Room> rooms = roomDao.getAllRooms();
        for(Room room : rooms) {
            roomDao.deleteRoomById(room.getId());
        }

        List<Employee> employees = employeeDao.getAllEmployees();
        for(Employee employee : employees) {
            employeeDao.deleteEmployeeById(employee.getId());
        }

        List<Meeting> meetings = meetingDao.getAllMeetings();
        for(Meeting meeting : meetings) {
            meetingDao.deleteMeetingById(meeting.getId());
        }
    }

    @org.junit.jupiter.api.Test
    void getAllRooms() {
        /*
        We start by creating our Room object, filling in all the details so we can be sure our mapper is
        working correctly.
        We call our "addRoom" method to put it into the database.
        We then retrieve the Room back out of the database by calling "getRoomById".
        We then use "assertEquals" to see that the Room we created in this method is equal to the one we pulled
        from the database.
         */
        Room room1 = new Room();
        room1.setName("Test Room 1");
        room1.setDescription("Test Room 1 Description");
        room1 = roomDao.addRoom(room1);

        Room fromDao1 = roomDao.getRoomById(room1.getId());

        Room room2 = new Room();
        room2.setName("Test Room 2");
        room2.setDescription("Test Room 2 Description");
        room2 = roomDao.addRoom(room2);

        Room fromDao = roomDao.getRoomById(room2.getId());

        List<Room> rooms = roomDao.getAllRooms();

        assertEquals(2, rooms.size());
        assertTrue(rooms.contains(room1));
        assertTrue(rooms.contains(room2));
    }

    @org.junit.jupiter.api.Test
    void getAddRoomById() {
        /*
        We start by creating a room with full details and adding it to the database.
        We create a second room with full details and add it to the database as well.
        Now we make our call to "getAllRooms" and save it into a List.
        We start by asserting that we have received two rooms back.
        We then assert that each room is in the list we get back.
         */
        Room room = new Room();
        room.setName("Test Room");
        room.setDescription("Test Room Description");
        room = roomDao.addRoom(room);

        Room fromDao = roomDao.getRoomById(room.getId());

        assertEquals(room, fromDao);
    }


    @org.junit.jupiter.api.Test
    void updateRoom() {
        /*
        We again start by creating a Room with full details and adding it to the database.
        We pull it back out and verify they are the same.
        Next, we make a change to the Room; in this case, a new name.
        We then assert this new object doesn't match the one we had previously retrieved from the database.
        Next, we make our call to "updateRoom" to send the changes to the database.
        We follow that up by re-retrieving the Room from the database.
        Finally, we assert that our local Room object is equal to the one we pulled from the database.
        The final "assertEquals" call in this method is the most important one, but the other two are there to
        help verify everything is working as expected
         */
        Room room = new Room();
        room.setName("Test Room");
        room.setDescription("Test Room Description");
        room = roomDao.addRoom(room);

        Room fromDao = roomDao.getRoomById(room.getId());

        assertEquals(room, fromDao);

        room.setName("Another Test Room");

        roomDao.updateRoom(room);

        assertNotEquals(room, fromDao);

        fromDao = roomDao.getRoomById(room.getId());

        assertEquals(room, fromDao);
    }

    @org.junit.jupiter.api.Test
    void deleteRoomById() {
        /*
        We start out by creating our Room object and adding it to the database.
        We then create an Employee object and add it to the database as well.
        Next, we create a Meeting object, associating it with the Room we created and adding the Employee we created as an attendee.
        By adding in the Meeting to the database, we are creating the links from Room, to Meeting, to the Meeting/Employee bridge table that our delete method works against.
        Next, we make our call to "deleteRoomById".
        We then try to retrieve our Room from the database.
        Finally, we assert that the Room we tried to get from the database is null, which means it doesn't exist anymore.
         */
        Room room = new Room();
        room.setName("Test Room");
        room.setDescription("Test Room Description");
        room = roomDao.addRoom(room);

        Employee employee = new Employee();
        employee.setFirstName("Test First");
        employee.setLastName("Test Last");
        employee = employeeDao.addEmployee(employee);

        Meeting meeting = new Meeting();
        meeting.setName("Test Meeting");
        meeting.setTime(LocalDateTime.now());
        meeting.setRoom(room);
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        meeting.setAttendees(employees);
        meeting = meetingDao.addMeeting(meeting);

        roomDao.deleteRoomById(room.getId());

        Room fromDao = roomDao.getRoomById(room.getId());
        assertNull(fromDao);
    }
}