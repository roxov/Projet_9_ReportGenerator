package fr.asterox.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import fr.asterox.ReportGenerator.ReportGeneratorApplication;
import fr.asterox.ReportGenerator.bean.PatientDTO;
import fr.asterox.ReportGenerator.bean.PatientDTO.Sex;
import fr.asterox.ReportGenerator.proxy.NotesCentralProxy;
import fr.asterox.ReportGenerator.proxy.PatientManagementProxy;

@SpringBootTest(classes = ReportGeneratorApplication.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ReportRestControllerIT {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	PatientManagementProxy patientManagementProxy;

	@MockBean
	NotesCentralProxy notesCentralProxy;

	@WithMockUser
	@Test
	public void givenAPatientId_whenGetRestReport_thenReturnOkAndReport() throws Exception {
		// GIVEN
		SimpleDateFormat ft = new SimpleDateFormat("yyyy, MM, dd");

		PatientDTO patient = new PatientDTO(2L, "gigi", "toto", ft.parse("1990, 12, 31"), "address", "023456", Sex.M);
		when(patientManagementProxy.askExistenceOfPatient(2L)).thenReturn(true);
		when(patientManagementProxy.getPatientById(2L)).thenReturn(patient);
		when(notesCentralProxy.getPatientStringNotes(2L)).thenReturn("Hémoglobine A1C, microalbumine, Reaction");

		// WHEN THEN
		String jsonResponse = mockMvc
				.perform(MockMvcRequestBuilders.get("/rest/assess/{id}", 2).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		verify(patientManagementProxy, Mockito.times(1)).askExistenceOfPatient(2L);
		verify(patientManagementProxy, Mockito.times(1)).getPatientById(2L);
		verify(notesCentralProxy, Mockito.times(1)).getPatientStringNotes(2L);
		assertEquals("Patient : gigi toto (age 30) diabetes assessment is: InDanger", jsonResponse);
	}

}
