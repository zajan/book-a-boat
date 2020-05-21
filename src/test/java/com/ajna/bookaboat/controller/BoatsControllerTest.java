package com.ajna.bookaboat.controller;

import com.ajna.bookaboat.entity.Boat;
import com.ajna.bookaboat.entity.BoatType;
import com.ajna.bookaboat.service.BoatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@EnableSpringDataWebSupport
public class BoatsControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private BoatsController boatsController;

    @MockBean
    private BoatService boatService;

    private List<Boat> boats;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(boatsController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers(new ViewResolver() {
                    @Override
                    public View resolveViewName(String viewName, Locale locale) throws Exception {
                        return new MappingJackson2JsonView();
                    }
                }).setControllerAdvice(new ExceptionControllerAdvice())
                .build();

        BoatType boattype1 = new BoatType(1, "boattype1");
        BoatType boatType2 = new BoatType(2, "boattype2");
        Boat boat1 = new Boat(1, "boat1", "description1", boattype1, 4, 40, 10, false, 2008, 3, null, null);
        Boat boat2 = new Boat(2, "boat2", "description2", boattype1, 6, 60, 20, true, 2000, 4, null, null);
        Boat boat3 = new Boat(3, "boat3", "description3", boatType2, 12, 1000, 460, true, 2004, 8, null, null);

        boats = new ArrayList<>();
        boats.add(boat1);
        boats.add(boat2);
        boats.add(boat3);
    }


    @Test
    public void testGetBoats_Success() throws Exception {
        Mockito.when(boatService.findAll(any(), any(Pageable.class))).thenReturn(boats);

        mockMvc.perform(MockMvcRequestBuilders.get("/boats"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(boats.size())))
                .andExpect(jsonPath("$[0].name", is(boats.get(0).getName())));
    }


    @Test
    public void testGetBoatById_Success() throws Exception {
        Mockito.when(boatService.findById(1)).thenReturn(boats.get(1));

        mockMvc.perform(MockMvcRequestBuilders.get("/boats/1"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(boats.get(1).getId())))
                .andExpect(jsonPath("$.name", is(boats.get(1).getName())));
    }

    @Test
    public void testGetBoatById_NotFound() throws Exception {
        Mockito.when(boatService.findById(1)).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/boats/1"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isNotFound());
    }


    @Test
    public void testSaveBoat_BadRequest_NoBoat() throws Exception {
        Mockito.when(boatService.save(any(Boat.class))).thenReturn(boats.get(0));

        mockMvc.perform(MockMvcRequestBuilders.multipart("/boats"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSaveBoat_BadRequest_IncompleteBoat() throws Exception {
        Mockito.when(boatService.save(any(Boat.class))).thenReturn(boats.get(0));

        Boat newBoat = new Boat();
        ObjectMapper mapper = new ObjectMapper();
        String newBoatJSON = mapper.writeValueAsString(newBoat);

        MockMultipartFile boatFile = new MockMultipartFile("boat", "", "application/json", newBoatJSON.getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/boats")
                .file(boatFile))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSaveBoat_Success() throws Exception {
        Mockito.when(boatService.save(any(), any())).thenReturn(boats.get(0));

        ObjectMapper mapper = new ObjectMapper();
        String newBoatJSON = mapper.writeValueAsString(boats.get(0));
        MockMultipartFile boatFile = new MockMultipartFile("boat", "", "application/json", newBoatJSON.getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/boats")
                .file(boatFile))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(boats.get(0).getId())))
                .andExpect(jsonPath("$.name", is(boats.get(0).getName())));
    }


    // TODO



}
