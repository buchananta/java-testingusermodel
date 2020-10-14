package com.lambdaschool.usermodel.services;

import static org.junit.Assert.*;

import com.lambdaschool.usermodel.models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceImplTest
{
    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        List<User> myList = userService.findAll();
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void a_findUserById() throws Exception
    {
        assertEquals("admin", userService.findUserById(4).getUsername());
    }

    @Test
    public void b_findByNameContaining()
    {
        assertEquals(1,
                userService.findByNameContaining("miss").size());
    }

    @Test
    public void c_findAll()
    {
        assertEquals(5, userService.findAll().size());
    }

    @Test
    public void d_delete()
    {
        userService.delete(11);
        assertEquals(4, userService.findAll().size());
    }

    @Test
    public void e_findByName()
    {
        assertEquals(7, userService.findByName("cinnamon").getUserid());
    }

    @Test
    public void f_save()
    {
        User nu = new User("testuser",
                "password",
                "test@lambdaschool.local");
        User addUser = userService.save(nu);
        assertNotNull(addUser);
        assertEquals(nu.getUsername(), addUser.getUsername());

    }

    @Test
    public void g_update()
    {
    }

    @Test
    public void h_deleteAll()
    {
        userService.deleteAll();
        assertEquals(0, userService.findAll().size());
    }
}