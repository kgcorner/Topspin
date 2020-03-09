package com.kgcorner.topspin.persistence;

import com.kgcorner.topspin.dao.MongoUserDao;
import com.kgcorner.topspin.model.User;
import com.kgcorner.topspin.model.UserModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.when;


/**
 * Description : Test for {@link MongoUserPersistenceLayer}
 * Author: kumar
 * Created on : 24/11/19
 */
@Repository
public class MongoUserPersistenceLayerTest {
    private MongoUserPersistenceLayer persistenceLayer;
    private MongoUserDao<UserModel> mongoUserDao;

    @Before
    public void setUp() throws Exception {
        persistenceLayer = new MongoUserPersistenceLayer();
        mongoUserDao = PowerMockito.mock(MongoUserDao.class);
        Whitebox.setInternalState(persistenceLayer, "dao", mongoUserDao);
    }

    @Test
    public void createUser() {
        UserModel user = new UserModel();
        when(mongoUserDao.create(ArgumentMatchers.any(UserModel.class))).thenReturn(user);
        User response = persistenceLayer.createUser(user);
        assertNotNull(response);
    }

    @Test
    public void updateUser() {
        UserModel user = new UserModel();
        user.setId("id");
        when(mongoUserDao.update(ArgumentMatchers.any(UserModel.class))).thenReturn(user);
        User response = persistenceLayer.updateUser(user);
        assertNotNull(response);
        assertEquals("id", response.getId());

    }

    @Test
    public void getUser() {
        UserModel user = new UserModel();
        user.setId("id");
        when(mongoUserDao.getById("id", UserModel.class)).thenReturn(user);
        User response = persistenceLayer.getUser("id");
        assertNotNull(response);
        assertEquals("id", response.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getUserByNullId() {
        persistenceLayer.getUser(null);
    }

    @Test
    public void getUserByUserName() {
        UserModel user = new UserModel();
        user.setUserName("name");
        when(mongoUserDao.getByKey("userName", "name", UserModel.class)).thenReturn(user);
        User response = persistenceLayer.getUserByUserName("name");
        assertNotNull(response);
        assertEquals("name", response.getUserName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getUserByNullUserName() {
        persistenceLayer.getUserByUserName(null);
    }

    @Test
    public void deleteUser() {
        UserModel user = new UserModel();
        user.setUserName("name");
        user.setId("id");
        when(mongoUserDao.getById("id", UserModel.class)).thenReturn(user);
        doNothing().when(mongoUserDao).remove(user);
        persistenceLayer.deleteUser("id");
        assertEquals("name", user.getUserName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteUserByNullId() {
        persistenceLayer.deleteUser(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteUserByNullUser() {
        when(mongoUserDao.getById("id", UserModel.class)).thenReturn(null);
        persistenceLayer.deleteUser("id");
    }

    @Test
    public void getUsersEmptyList() {
        List<User> users = persistenceLayer.getUsers(1, 0);
        assertEquals(0, users.size());
    }

    @Test
    public void getUsersEmpty() {
        List<UserModel> found = new ArrayList<>();
        int countPerPage = 10;
        for (int i = 0; i < countPerPage; i++)
            found.add(new UserModel());
        when(mongoUserDao.getAll(1, countPerPage, UserModel.class)).thenReturn(found);
        List<User> users = persistenceLayer.getUsers(1, 10);
        assertEquals(countPerPage, users.size());

    }
}