package fr.asterox.ReportGenerator.service;

import fr.asterox.ReportGenerator.bean.DiabetesReport;

/**
 * 
 * Microservice calculating the data to generate a report.
 *
 */
public interface IReportService {
	public String getRestReport(Long patientId);

	public DiabetesReport getDiabetesReport(Long patientId);
}
