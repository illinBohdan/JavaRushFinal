package com.javarush.javarushfinal.controller;


import com.javarush.javarushfinal.config.SecurityConfigTest;
import com.javarush.javarushfinal.entity.User;
import com.javarush.javarushfinal.service.purchase_history.PurchaseHistoryService;
import com.javarush.javarushfinal.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
@Import(SecurityConfigTest.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;
    private PurchaseHistoryService purchaseHistoryService;

    @Test
    void getUserProfileWhenStatus_200() throws Exception {
        User user = new User();
        user.setUserName("testuser");
        user.setEmail("testuser@mail.com");

        when(userService.findByUserName("testuser")).thenReturn(Optional.of(user));

        mockMvc.perform(get("/user/testuser"))
                .andExpect(status().isOk());


        //       З ввімкненим SecuritySpring
//        mockMvc.perform(get("/user/testuser")
//                        .with(user("testuser").password("password").roles("USER")))
//                .andExpect(status().isOk());

    }



    @Test
    public void getUserProfileWhenStatus_NotFound() throws Exception {

        when(userService.findByUserName("unknownuser"))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Користувача не знайдено"));

        mockMvc.perform(get("/user/unknownuser"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testIfComeEmptyUserField() throws Exception {
        mockMvc.perform(get("/user/"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testBusinessLogic() throws Exception {
        User user = new User();
        user.setUserName("testuser");
        user.setEmail("testuser@mail.com");

        when(userService.findByUserName("testuser")).thenReturn(Optional.of(user));

        mockMvc.perform(get("/user/testuser"))
                .andExpect(view().name("user-profile"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", user));
    }

    @Test
    void testUserDataConversion() throws Exception {
        User user = new User();
        user.setUserName("testuser");
        user.setEmail("testuser@mail.com");

        when(userService.findByUserName("testuser")).thenReturn(Optional.of(user));

        mockMvc.perform(get("/user/testuser"))
                .andExpect(model().attributeExists("user"))
                .andExpect(result -> {
                    User userFromModel = (User) result.getModelAndView().getModel().get("user");

                    assertEquals("testuser", userFromModel.getUserName());
                    assertEquals("testuser@mail.com", userFromModel.getEmail());
                });
    }

    @Test
    void testUserReturnFromController() throws Exception {
        User user = new User();
        user.setUserName("testuser");
        user.setEmail("testuser@mail.com");

        when(userService.findByUserName("testuser")).thenReturn(Optional.of(user));


        mockMvc.perform(get("/user/testuser"))
                .andExpect(view().name("user-profile"))
                .andExpect(model().attributeExists("user"))
                .andExpect(result -> {
                    User userFromModel = (User) result.getModelAndView().getModel().get("user");


                    assertNotNull(userFromModel);
                });
    }

    @Test
    void testUserNotFoundError() throws Exception {

        when(userService.findByUserName("unknownuser")).thenThrow(
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Користувача не знайдено")
        );

        mockMvc.perform(get("/user/unknownuser"))
                .andExpect(status().isNotFound())
                .andExpect(result -> {
                     String errorMessage = result.getResolvedException().getMessage();
                    assertTrue(errorMessage.contains("Користувача не знайдено"));
                });
    }


}



