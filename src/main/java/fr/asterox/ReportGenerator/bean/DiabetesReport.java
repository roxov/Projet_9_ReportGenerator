package fr.asterox.ReportGenerator.bean;

import org.springframework.lang.NonNull;

public class DiabetesReport {
	private PatientDTO patient;
	@NonNull
	private DangerLevel dangerLevel = DangerLevel.None;

	public DiabetesReport() {
		super();
	}

	public DiabetesReport(PatientDTO patient, DangerLevel dangerLevel) {
		super();
		this.patient = patient;
		this.dangerLevel = dangerLevel;
	}

	public PatientDTO getPatient() {
		return patient;
	}

	public void setPatient(PatientDTO patient) {
		this.patient = patient;
	}

	public DangerLevel getDangerLevel() {
		return dangerLevel;
	}

	public void setDangerLevel(DangerLevel dangerLevel) {
		this.dangerLevel = dangerLevel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dangerLevel == null) ? 0 : dangerLevel.hashCode());
		result = prime * result + ((patient == null) ? 0 : patient.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DiabetesReport other = (DiabetesReport) obj;
		if (dangerLevel != other.dangerLevel)
			return false;
		if (patient == null) {
			if (other.patient != null)
				return false;
		} else if (!patient.equals(other.patient))
			return false;
		return true;
	}

}
