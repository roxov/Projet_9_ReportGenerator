package fr.asterox.ReportGenerator.service;

import java.text.Normalizer;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.asterox.ReportGenerator.bean.DangerLevel;
import fr.asterox.ReportGenerator.bean.DiabetesReport;
import fr.asterox.ReportGenerator.bean.PatientDTO;
import fr.asterox.ReportGenerator.bean.PatientDTO.Sex;
import fr.asterox.ReportGenerator.constant.MedicalTerminology;
import fr.asterox.ReportGenerator.proxy.NotesCentralProxy;
import fr.asterox.ReportGenerator.proxy.PatientManagementProxy;
import fr.asterox.ReportGenerator.util.NoPatientException;

@Service
public class ReportService implements IReportService {
	@Autowired
	private PatientManagementProxy patientManagementProxy;

	@Autowired
	private NotesCentralProxy notesCentralProxy;

	private static final Logger LOGGER = LogManager.getLogger(ReportService.class);

	@Override
	public String getRestReport(Long patientId) {
		if (!patientManagementProxy.askExistenceOfPatient(patientId)) {
			LOGGER.info("The requested patient does not exist.");
			throw new NoPatientException("This patient does not exist.");
		}
		PatientDTO patient = patientManagementProxy.getPatientById(patientId);

		return "Patient : " + patient.getGivenName() + " " + patient.getFamilyName() + " (age "
				+ this.getAge(patient.getBirthdate()) + ") diabetes assessment is: "
				+ this.calculateDangerLevel(patient);
	}

	@Override
	public DiabetesReport getDiabetesReport(Long patientId) {
		if (!patientManagementProxy.askExistenceOfPatient(patientId)) {
			LOGGER.info("The requested patient does not exist.");
			throw new NoPatientException("This patient does not exist.");
		}
		PatientDTO patient = patientManagementProxy.getPatientById(patientId);
		return new DiabetesReport(patient, this.calculateDangerLevel(patient));
	}

	private int getAge(Date birthdate) {

		int age = Period.between(Instant.ofEpochMilli(birthdate.getTime()).atZone(ZoneId.systemDefault()).toLocalDate(),
				LocalDate.now()).getYears();
		LOGGER.debug("Calculating age from birthdate");

		return age;
	}

	private DangerLevel calculateDangerLevel(PatientDTO patient) {

		int occurrences = this.getTerminologyOccurrences(patient);
		int age = this.getAge(patient.getBirthdate());

		LOGGER.debug("Calculating level danger for patient : " + patient.getPatientId());
		if (age <= 30) {
			if (patient.getSex().equals(Sex.M)) {
				if (occurrences < 3) {
					return DangerLevel.None;
				} else if (occurrences < 5) {
					return DangerLevel.InDanger;
				}
				return DangerLevel.EarlyOnset;
			} else if (occurrences < 4) {
				return DangerLevel.None;
			} else if (occurrences < 7) {
				return DangerLevel.InDanger;
			}
			return DangerLevel.EarlyOnset;
		} else if (occurrences < 2) {
			return DangerLevel.None;
		} else if (occurrences < 6) {
			return DangerLevel.Borderline;
		} else if (occurrences < 8) {
			return DangerLevel.InDanger;
		}
		return DangerLevel.EarlyOnset;
	}

	private int getTerminologyOccurrences(PatientDTO patient) {
		String notes = normalizeString(notesCentralProxy.getPatientStringNotes(patient.getPatientId()));
		LOGGER.debug("Getting the occurences of terminology");
		return MedicalTerminology.MEDICAL_TRIGGER.stream().map(this::normalizeString)
				.mapToInt(t -> this.countOccurrences(notes, t)).sum();
	}

	private int countOccurrences(String notes, String medicalTrigger) {
		int cursor = notes.indexOf(medicalTrigger);
		int counter = 0;
		while (cursor != -1) {
			counter++;
			cursor = notes.indexOf(medicalTrigger, cursor + 1);
		}
		return counter;
	}

	private String normalizeString(String s) {
		String strTemp = Normalizer.normalize(s, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(strTemp).replaceAll("").toLowerCase();
	}

	public PatientManagementProxy setPatientManagementProxy(PatientManagementProxy patientManagementProxy) {
		return this.patientManagementProxy = patientManagementProxy;
	}

	public NotesCentralProxy setNotesCentralProxy(NotesCentralProxy notesCentralProxy) {
		return this.notesCentralProxy = notesCentralProxy;
	}

}
