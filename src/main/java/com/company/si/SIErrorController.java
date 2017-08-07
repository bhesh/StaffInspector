package com.company.si;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Maps all errors to here.
 * 
 * @author Brian Hession
 *
 */
@RestController
@RequestMapping("/error")
public class SIErrorController implements ErrorController {

	private static final String PATH = "/error";

	@Autowired
	private ErrorAttributes errorAttrs;

	public String getErrorPath() {
		return PATH;
	}

	/**
	 * All errors will be mapped to this response. It just returns the status,
	 * error, and message in a JSON format versus HTML.
	 * 
	 * @param request
	 *            the HTTP request
	 * @param response
	 *            the HTTP response
	 * @return the error status and a JSON formatted error message
	 */
	@RequestMapping
	public ResponseEntity<?> error(HttpServletRequest request, HttpServletResponse response) {
		int status = response.getStatus();
		Map<String, Object> errorDetails = errorAttrs.getErrorAttributes(new ServletRequestAttributes(request), false);
		String error = (String) errorDetails.get("error");
		String message = (String) errorDetails.get("message");
		return ResponseEntity.status(status).body(new ErrorResponse(status, error, message));
	}

	/**
	 * ErrorResponse
	 * 
	 * A quick error class that Spring will map to JSON.
	 * 
	 * @author Brian Hession
	 *
	 */
	public static class ErrorResponse {

		public int status;
		public String error;
		public String message;

		public ErrorResponse(int status, String error, String message) {
			this.status = status;
			this.error = error;
			this.message = message;
		}
	}
}
