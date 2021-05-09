package fr.asterox.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import fr.asterox.ReportGenerator.bean.PatientDTO;
import fr.asterox.ReportGenerator.proxy.NotesCentralProxy;
import fr.asterox.ReportGenerator.proxy.PatientManagementProxy;
import fr.asterox.ReportGenerator.service.ReportService;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

	@Mock
	private PatientManagementProxy patientManagementProxy;

	@Mock
	private NotesCentralProxy notesCentralProxy;

	@InjectMocks
	ReportService reportService;
	SimpleDateFormat ft = new SimpleDateFormat("yyyy, MM, dd");

	@BeforeEach
	public void setUp() {
		reportService = new ReportService();
		reportService.setNotesCentralProxy(notesCentralProxy);
		reportService.setPatientManagementProxy(patientManagementProxy);
	}

	@Test
	public void givenATestNoneM30Patient_whenGetRestReport_thenReturnPatientInfoAndNoneDangerLevel()
			throws ParseException {
		// GIVEN
		PatientDTO patient = new PatientDTO(1L, "Test", "TestNone", ft.parse("1990, 12, 31"), "1 Brookside St",
				"100-222-3333", PatientDTO.Sex.M);
		when(patientManagementProxy.getPatientById(1L)).thenReturn(patient);
		when(notesCentralProxy.getPatientStringNotes(1L)).thenReturn("Hémoglobine A1C, Microalbumine");
		// WHEN
		String result = reportService.getRestReport(1L);

		// THEN
		verify(patientManagementProxy, Mockito.times(1)).getPatientById(1L);
		verify(notesCentralProxy, Mockito.times(1)).getPatientStringNotes(1L);
		assertEquals("Patient : Test TestNone (age 30) diabetes assessment is: None", result);
	}

	@Test
	public void givenATestInDangerM30Patient_whenGetRestReport_thenReturnPatientInfoAndNoneDangerLevel()
			throws ParseException {
		// GIVEN
		PatientDTO patient = new PatientDTO(1L, "Test", "TestInDanger", ft.parse("1990, 12, 31"), "1 Brookside St",
				"100-222-3333", PatientDTO.Sex.M);
		when(patientManagementProxy.getPatientById(1L)).thenReturn(patient);
		when(notesCentralProxy.getPatientStringNotes(1L)).thenReturn("Hémoglobine A1C, Microalbumine, Taille, Poids");
		// WHEN
		String result = reportService.getRestReport(1L);

		// THEN
		verify(patientManagementProxy, Mockito.times(1)).getPatientById(1L);
		verify(notesCentralProxy, Mockito.times(1)).getPatientStringNotes(1L);
		assertEquals("Patient : Test TestInDanger (age 30) diabetes assessment is: InDanger", result);
	}

	@Test
	public void givenATestEarlyOnsetM30Patient_whenGetRestReport_thenReturnPatientInfoAndNoneDangerLevel()
			throws ParseException {
		// GIVEN
		PatientDTO patient = new PatientDTO(1L, "Test", "TestEarlyOnset", ft.parse("1990, 12, 31"), "1 Brookside St",
				"100-222-3333", PatientDTO.Sex.M);
		when(patientManagementProxy.getPatientById(1L)).thenReturn(patient);
		when(notesCentralProxy.getPatientStringNotes(1L))
				.thenReturn("Hémoglobine A1C, Microalbumine, Taille, Poids, Fumeur");
		// WHEN
		String result = reportService.getRestReport(1L);

		// THEN
		verify(patientManagementProxy, Mockito.times(1)).getPatientById(1L);
		verify(notesCentralProxy, Mockito.times(1)).getPatientStringNotes(1L);
		assertEquals("Patient : Test TestEarlyOnset (age 30) diabetes assessment is: EarlyOnset", result);
	}

	@Test
	public void givenATestNoneF30Patient_whenGetRestReport_thenReturnPatientInfoAndNoneDangerLevel()
			throws ParseException {
		// GIVEN
		PatientDTO patient = new PatientDTO(1L, "Test", "TestNone", ft.parse("1990, 12, 31"), "1 Brookside St",
				"100-222-3333", PatientDTO.Sex.F);
		when(patientManagementProxy.getPatientById(1L)).thenReturn(patient);
		when(notesCentralProxy.getPatientStringNotes(1L)).thenReturn("Hémoglobine A1C, Microalbumine, Taille");
		// WHEN
		String result = reportService.getRestReport(1L);

		// THEN
		verify(patientManagementProxy, Mockito.times(1)).getPatientById(1L);
		verify(notesCentralProxy, Mockito.times(1)).getPatientStringNotes(1L);
		assertEquals("Patient : Test TestNone (age 30) diabetes assessment is: None", result);
	}

	@Test
	public void givenATestInDangerF30Patient_whenGetRestReport_thenReturnPatientInfoAndNoneDangerLevel()
			throws ParseException {
		// GIVEN
		PatientDTO patient = new PatientDTO(1L, "Test", "TestInDanger", ft.parse("1990, 12, 31"), "1 Brookside St",
				"100-222-3333", PatientDTO.Sex.F);
		when(patientManagementProxy.getPatientById(1L)).thenReturn(patient);
		when(notesCentralProxy.getPatientStringNotes(1L))
				.thenReturn("Hémoglobine A1C, Microalbumine, Taille, Poids, Fumeur, Anormal");
		// WHEN
		String result = reportService.getRestReport(1L);

		// THEN
		verify(patientManagementProxy, Mockito.times(1)).getPatientById(1L);
		verify(notesCentralProxy, Mockito.times(1)).getPatientStringNotes(1L);
		assertEquals("Patient : Test TestInDanger (age 30) diabetes assessment is: InDanger", result);
	}

	@Test
	public void givenATestEarlyOnsetF30Patient_whenGetRestReport_thenReturnPatientInfoAndNoneDangerLevel()
			throws ParseException {
		// GIVEN
		PatientDTO patient = new PatientDTO(1L, "Test", "TestEarlyOnset", ft.parse("1990, 12, 31"), "1 Brookside St",
				"100-222-3333", PatientDTO.Sex.F);
		when(patientManagementProxy.getPatientById(1L)).thenReturn(patient);
		when(notesCentralProxy.getPatientStringNotes(1L))
				.thenReturn("Hémoglobine A1C, Microalbumine, Taille, Poids, Fumeur, Anormal, Cholestérol");
		// WHEN
		String result = reportService.getRestReport(1L);

		// THEN
		verify(patientManagementProxy, Mockito.times(1)).getPatientById(1L);
		verify(notesCentralProxy, Mockito.times(1)).getPatientStringNotes(1L);
		assertEquals("Patient : Test TestEarlyOnset (age 30) diabetes assessment is: EarlyOnset", result);
	}

	@Test
	public void givenATestNone31Patient_whenGetRestReport_thenReturnPatientInfoAndNoneDangerLevel()
			throws ParseException {
		// GIVEN
		PatientDTO patient = new PatientDTO(1L, "Test", "TestNone", ft.parse("1989, 12, 31"), "1 Brookside St",
				"100-222-3333", PatientDTO.Sex.F);
		when(patientManagementProxy.getPatientById(1L)).thenReturn(patient);
		when(notesCentralProxy.getPatientStringNotes(1L)).thenReturn("Microalbumine");
		// WHEN
		String result = reportService.getRestReport(1L);

		// THEN
		verify(patientManagementProxy, Mockito.times(1)).getPatientById(1L);
		verify(notesCentralProxy, Mockito.times(1)).getPatientStringNotes(1L);
		assertEquals("Patient : Test TestNone (age 31) diabetes assessment is: None", result);
	}

	@Test
	public void givenATestBorderline31Patient_whenGetRestReport_thenReturnPatientInfoAndBorderlineDangerLevel()
			throws ParseException {
		// GIVEN
		PatientDTO patient = new PatientDTO(1L, "Test", "Borderline", ft.parse("1989, 12, 31"), "1 Brookside St",
				"100-222-3333", PatientDTO.Sex.F);
		when(patientManagementProxy.getPatientById(1L)).thenReturn(patient);
		when(notesCentralProxy.getPatientStringNotes(1L)).thenReturn("Hémoglobine A1C, Microalbumine");
		// WHEN
		String result = reportService.getRestReport(1L);

		// THEN
		verify(patientManagementProxy, Mockito.times(1)).getPatientById(1L);
		verify(notesCentralProxy, Mockito.times(1)).getPatientStringNotes(1L);
		assertEquals("Patient : Test Borderline (age 31) diabetes assessment is: Borderline", result);
	}

	@Test
	public void givenATestInDangerM31Patient_whenGetRestReport_thenReturnPatientInfoAndNoneDangerLevel()
			throws ParseException {
		// GIVEN
		PatientDTO patient = new PatientDTO(1L, "Test", "TestInDanger", ft.parse("1989, 12, 31"), "1 Brookside St",
				"100-222-3333", PatientDTO.Sex.M);
		when(patientManagementProxy.getPatientById(1L)).thenReturn(patient);
		when(notesCentralProxy.getPatientStringNotes(1L))
				.thenReturn("Hémoglobine A1C, Microalbumine, Taille, Poids, Anticorps, Poids");
		// WHEN
		String result = reportService.getRestReport(1L);

		// THEN
		verify(patientManagementProxy, Mockito.times(1)).getPatientById(1L);
		verify(notesCentralProxy, Mockito.times(1)).getPatientStringNotes(1L);
		assertEquals("Patient : Test TestInDanger (age 31) diabetes assessment is: InDanger", result);
	}

	@Test
	public void givenATestEarlyOnsetM31Patient_whenGetRestReport_thenReturnPatientInfoAndNoneDangerLevel()
			throws ParseException {
		// GIVEN

		PatientDTO patient = new PatientDTO(1L, "Test", "TestEarlyOnset", ft.parse("1989, 12, 31"), "1 Brookside St",
				"100-222-3333", PatientDTO.Sex.M);
		when(patientManagementProxy.getPatientById(1L)).thenReturn(patient);
		when(notesCentralProxy.getPatientStringNotes(1L))
				.thenReturn("Taille, Poids, FUMEUR, anormal, Cholésterol, Vertige, Rechute, Réaction");
		// WHEN
		String result = reportService.getRestReport(1L);

		// THEN
		verify(patientManagementProxy, Mockito.times(1)).getPatientById(1L);
		verify(notesCentralProxy, Mockito.times(1)).getPatientStringNotes(1L);
		assertEquals("Patient : Test TestEarlyOnset (age 31) diabetes assessment is: EarlyOnset", result);
	}

}
