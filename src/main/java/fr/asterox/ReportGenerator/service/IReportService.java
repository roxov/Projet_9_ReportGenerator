package fr.asterox.ReportGenerator.service;

/**
 * 
 * Microservice calculating the data to generate a report.
 *
 */
public interface IReportService {
	public String getRestReport(Long patientId);

	public void getDiabetesReport(Long patientId);
}
