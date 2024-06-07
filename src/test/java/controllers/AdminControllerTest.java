package controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.FLsolutions.schoolTrack.controllers.AdminController;
import com.FLsolutions.schoolTrack.exceptions.GenericUserException;
import com.FLsolutions.schoolTrack.models.Admin;
import com.FLsolutions.schoolTrack.services.AdminService;
import com.FLsolutions.schoolTrack.dtos.AdminResponseDto;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    @Test
    public void fetchAdminBySysId_shouldReturnAdminDTO_whenAdminExists() throws Exception {
        long sysId = 1L;
        AdminResponseDto adminResponseDto = new AdminResponseDto(sysId, "John", "Doe");

        when(adminService.fetchAdminBySysId(sysId)).thenReturn(adminResponseDto);

        mockMvc.perform(get("/admins/{id}", sysId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sysId").value(sysId))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    public void fetchAdminBySysId_shouldReturnNotFound_whenAdminDoesNotExist() throws Exception {
        long sysId = 2L;

        doThrow(new GenericUserException(
                "Admin with this sysId doesn't exist in the database: " + sysId,
                HttpStatus.NOT_FOUND)).when(adminService).fetchAdminBySysId(sysId);

        mockMvc.perform(get("/admins/{id}", sysId))
                .andExpect(status().isNotFound());
    }
}

