package fr.asterox.ReportGenerator.proxy;

import javax.validation.constraints.NotNull;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import fr.asterox.ReportGenerator.bean.PatientDTO;

@FeignClient(name = "PatientManagement", url = "localhost:8081")
public interface PatientManagementProxy {

	@GetMapping(value = "/rest/patient/{id}")
	public PatientDTO getPatientById(@PathVariable("id") @NotNull(message = "patientId is compulsory") Long patientId);

	@GetMapping(value = "rest/patient/exist/{id}")
	public boolean askExistenceOfPatient(
			@PathVariable("id") @NotNull(message = "patientId is compulsory") Long patientId);
}
