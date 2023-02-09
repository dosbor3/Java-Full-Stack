package com.sg.testing.dao.implementations;

import com.sg.testing.dao.MonsterDao;
import com.sg.testing.model.Monster;
import com.sg.testing.model.MonsterType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AGoodMonsterDaoTest {
    MonsterDao testDao = new AGoodMonsterDao();

    public AGoodMonsterDaoTest() {

    }

    @Test
    void addMonster() {
        //Create our method test inputs
        Monster m = new Monster(01234);
        m.setName("Jason");
        m.setFavoriteFood("Brains");
        m.setPeopleEaten(5);
        m.setType(MonsterType.YETI);

        //add monsters to the DAO
        testDao.addMonster(m.getId(), m);

        //get monsters from the DAO
        Monster retrievedMonster_m = testDao.getMonster(m.getId());

        // Check the data is equal for monster m
        assertEquals(m.getId(), retrievedMonster_m.getId());
        assertEquals(m.getName(), retrievedMonster_m.getName());
        assertEquals(m.getType(), retrievedMonster_m.getType());
        assertEquals(m.getPeopleEaten(), retrievedMonster_m.getPeopleEaten());
        assertEquals(m.getFavoriteFood(), retrievedMonster_m.getFavoriteFood());

    }

    @Test
    void getAllMonsters() {
        Monster d = new Monster(5678,"Pru",MonsterType.VAMPIRE, 15,"blood");
        Monster x = new Monster(8901,"Freddie",MonsterType.SWAMPTHING, 87,"Flesh");

        testDao.addMonster(d.getId(), d);
        testDao.addMonster(x.getId(), x);

        //Retrieve the list of all monsters within the DAO
        List<Monster> allMonsters = testDao.getAllMonsters();


        // Check the general contents of the list
        assertNotNull(allMonsters, "The list of monsters must not null");
        assertEquals(2, allMonsters.size(), "List of monsters should have 2 monsters.");

        //The Specifics
        assertTrue(testDao.getAllMonsters().contains(d), "The list of monsters should include Pru");
        assertTrue(testDao.getAllMonsters().contains(x), "The list of monsters should include Freddie");

    }

    @Test
    void removeMonster() {
        Monster d = new Monster(5678,"Pru",MonsterType.VAMPIRE, 15,"blood");
        Monster x = new Monster(8901,"Freddie",MonsterType.SWAMPTHING, 87,"Flesh");

        // Add both to the DAO
        testDao.addMonster(d.getId(), d);
        testDao.addMonster(x.getId(), x);

        // Get all the monsters
        List<Monster> allMonsters = testDao.getAllMonsters();

        // remove the second monster added - Freddie
        Monster removedMonster = testDao.removeMonster(x.getId());

        // Check that the correct object was removed.
        assertEquals(removedMonster, x, "The removed monster should be Freddie");

        // Get all the monsters
        allMonsters = testDao.getAllMonsters();

        // First check the general contents of the list
        assertNotNull( allMonsters, "All monsters list should be not null.");
        assertEquals( 1, allMonsters.size(), "All monsters should only have 1 monster.");

        // Then the specifics
        assertFalse( allMonsters.contains(x), "All monsters should NOT include Freddie.");
        assertTrue( allMonsters.contains(d), "All monsters should include Pru.");

        // Remove the second monster, which is the first monster added - Pru
        removedMonster = testDao.removeMonster(d.getId());

        // Check that the correct object was removed.
        assertEquals( removedMonster, d, "The removed student should be Pru.");

        // retrieve all of the monsters again, and check the list.
        allMonsters = testDao.getAllMonsters();

        // Check the contents of the list - it should be empty
        assertTrue( allMonsters.isEmpty(), "The retrieved list of monsters should be empty.");

        // Try to 'get' both students by their old id - they should be null!
        Monster retrievedMonster = testDao.getMonster(x.getId());
        assertNull(retrievedMonster, "Freddie was removed, should be null.");

        Monster retrievedMonster2 = testDao.getMonster(d.getId());
        assertNull(retrievedMonster2, "Pru was removed, should be null.");


    }
}