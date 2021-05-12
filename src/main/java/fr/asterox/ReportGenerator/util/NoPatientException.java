package fr.asterox.ReportGenerator.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoPatientException extends RuntimeException {

	public NoPatientException(String msg) {
		super(msg);
	}

}
