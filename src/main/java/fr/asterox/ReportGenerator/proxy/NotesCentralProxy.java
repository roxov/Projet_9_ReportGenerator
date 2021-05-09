package fr.asterox.ReportGenerator.proxy;

import javax.validation.constraints.NotNull;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "NotesCentral", url = "localhost:8082")
public interface NotesCentralProxy {

	@GetMapping(value = "rest/note/patient/{id}")
	public String getPatientStringNotes(
			@PathVariable("id") @NotNull(message = "patientId is compulsory") Long patientId);

}
