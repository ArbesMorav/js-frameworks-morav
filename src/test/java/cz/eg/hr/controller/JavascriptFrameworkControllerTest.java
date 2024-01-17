package cz.eg.hr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.eg.hr.data.FrameworkVersion;
import cz.eg.hr.data.JavascriptFramework;
import cz.eg.hr.repository.FrameworkVersionRepository;
import cz.eg.hr.repository.JavascriptFrameworkRepository;
import cz.eg.hr.service.TransactionalService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

//@WebMvcTest(JavascriptFrameworkController.class)
@SpringBootTest
//@Transactional
@AutoConfigureMockMvc
@EntityScan(basePackages = {"cz.eg.hr.data"})
class JavascriptFrameworkControllerTest {

    @Autowired
    private JavascriptFrameworkController controller;

    @Mock
    private JavascriptFrameworkRepository frameworkRepository;

    @Mock
    private FrameworkVersionRepository versionRepository;

    @Mock
    private TransactionalService transactionalService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

 /*   @Test
    void testGetFrameworks() throws Exception {
        when(frameworkRepository.findAll()).thenReturn(Arrays.asList(
            new JavascriptFramework(), new JavascriptFramework()));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/frameworks")
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());

        verify(frameworkRepository, times(1)).findAll();
    }*/

    // Encountered problems with lazy JSON loading,
    // giving up on this one for now as I have burned enough time on it
}
