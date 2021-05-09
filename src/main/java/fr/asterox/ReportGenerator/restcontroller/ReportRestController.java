package fr.asterox.ReportGenerator.restcontroller;

import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.asterox.ReportGenerator.service.ReportService;

@RestController
@RequestMapping("rest/patient")
public class ReportRestController {

	private static final Logger LOGGER = LogManager.getLogger(ReportRestController.class);

	@Autowired
	private ReportService reportService;

	@GetMapping(value = "/{id}")
	public String getPatientReport(@PathVariable("id") @NotNull(message = "patientId is compulsory") Long patientId) {
		LOGGER.info("Getting diabetes report for patient identified by id : " + patientId);
		return reportService.getRestReport(patientId);
	}

}
