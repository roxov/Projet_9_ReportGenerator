package fr.asterox.ReportGenerator.controller;

import java.net.URI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import fr.asterox.ReportGenerator.bean.DiabetesReport;
import fr.asterox.ReportGenerator.service.ReportService;

@Controller
public class ReportController {

	private static final Logger LOGGER = LogManager.getLogger(ReportController.class);

	@Autowired
	private ReportService reportService;

	@GetMapping("/assess/{id}")
	public String getPatientReport(@PathVariable("id") Long patientId, Model model) {
		DiabetesReport diabetesReport = reportService.getDiabetesReport(patientId);
		LOGGER.info("Getting the diabetes report for the patiet with id : " + patientId);
		model.addAttribute("report", diabetesReport);
		return "report/get";
	}

	@GetMapping("/patient/list")
	public ResponseEntity<Void> getPatientsList() {
		LOGGER.info("Redirecting to port 8080");
		return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:8080/patient/list"))
				.build();
	}

}
