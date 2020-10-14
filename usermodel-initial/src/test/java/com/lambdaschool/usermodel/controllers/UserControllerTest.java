package com.lambdaschool.usermodel.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.usermodel.models.Role;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.models.UserRoles;
import com.lambdaschool.usermodel.services.HelperFunctions;
import com.lambdaschool.usermodel.services.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class)
public class UserControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HelperFunctions helperFunctions;
    @MockBean
    private UserService userService;

    private List<User> userList;

    @Before
    public void setUp() throws Exception
    {
        userList = new ArrayList<>();

//        Role r1 = new Role("admin");

        User u1 = new User("admin",
                "password",
                "admin@lambdaschool.local");
//        u1.getRoles()
//                .add(new UserRoles(u1, r1));
        u1.setUserid(10);
        userList.add(u1);
        User u2 = new User("cinnamon",
                "1234567",
                "cinnamon@lambdaschool.local");
        u2.setUserid(20);
        userList.add(u2);
        User u3 = new User("barnbarn",
                "ILuvM4th!",
                "barnbarn@lambdaschool.local");
        u3.setUserid(30);
        userList.add(u3);
        User u4 = new User("puttat",
                "password",
                "puttat@school.lambda");
        u4.setUserid(40);
        userList.add(u4);
        User u5 = new User("misskitty",
                "password",
                "misskitty@school.lambda");
        u5.setUserid(50);
        userList.add(u5);
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void listAllUsers() throws Exception
    {
        String apiUrl = "/users/users";
        Mockito.when(userService.findAll()).thenReturn(userList);

        RequestBuilder rb =
                MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(userList);

        assertEquals(er, tr);
    }

    @Test
    public void getUserById() throws Exception
    {
        String apiUrl = "/users/user/10";
        Mockito.when(userService.findUserById(10)).thenReturn(userList.get(0));

        RequestBuilder rb =
                MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(userList.get(0));

        assertEquals(er, tr);
    }

    @Test
    public void getUserByName() throws Exception
    {
        String apiUrl = "/users/user/name/cinnamon";
        Mockito.when(userService.findByName("cinnamon")).thenReturn(userList.get(1));

        RequestBuilder rb =
                MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(userList.get(1));

        assertEquals(er, tr);
    }

    @Test
    public void getUserLikeName() throws Exception
    {
        List<User> resList = userList.stream().filter(a ->
                a.getUsername().contains("b")).collect(Collectors.toList());
        String apiUrl = "/users/user/name/like/b";
        Mockito.when(userService.findByNameContaining("b"))
                .thenReturn(resList);

        RequestBuilder rb =
                MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(resList);

        assertEquals(er, tr);
    }

    @Test
    public void addNewUser() throws Exception
    {
        String apiUrl = "/users/user";

        User u9 = new User("testuser",
                "password",
                "test@lambdaschool.local");
        u9.setUserid(90);

        ObjectMapper mapper = new ObjectMapper();
        String userString = mapper.writeValueAsString(u9);

        Mockito.when(userService.save(any(User.class))).thenReturn(u9);

        RequestBuilder rb =
                MockMvcRequestBuilders.post(apiUrl).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(userString);
        mockMvc.perform(rb).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateFullUser() throws Exception
    {
//        String apiUrl = "/users/user/10";
//
//        User u9 = new User("testuser",
//                "password",
//                "test@lambdaschool.local");
////        u9.setUserid(90);
//
//        ObjectMapper mapper = new ObjectMapper();
//        String userString = mapper.writeValueAsString(u9);
//
//        Mockito.when(userService.update(any(User.class),10)).thenReturn(u9);
//
//        RequestBuilder rb =
//                MockMvcRequestBuilders.patch(apiUrl).accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(userString);
//        mockMvc.perform(rb).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateUser()
    {
    }

    @Test
    public void deleteUserById() throws Exception
    {
        // I can't get this to work, because userService.delete is a void
        // method, and I can not figure out how to mock out a void method
//        String apiUrl = "/users/user/10";
//        Mockito.doReturn( userList.remove(0)).when(userService).delete(10);
//
//        RequestBuilder rb =
//                MockMvcRequestBuilders.delete(apiUrl).accept(MediaType.APPLICATION_JSON);
//        MvcResult r = mockMvc.perform(rb).andReturn();
//        String tr = r.getResponse().getContentAsString();
//        ObjectMapper mapper = new ObjectMapper();
//        String er = mapper.writeValueAsString(userList.get(0));
//
//        assertEquals(0, userList.stream().filter(a ->
//                ( a.getUserid() == 10 )).collect(Collectors.toList()).size());

        String apiUrl = "/users/user/{userid}";

        RequestBuilder rb = MockMvcRequestBuilders.delete(apiUrl, "10")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(rb)
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}