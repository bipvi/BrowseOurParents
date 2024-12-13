package bip.vi.Browse.our.Parents.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice

public class ExceptionsHandler {

	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
	public ErrorsResponseDTO handleBadrequest(BadRequestException ex) {
		return new ErrorsResponseDTO(ex.getMessage(), LocalDateTime.now());
	}

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND) // 404
	public ErrorsResponseDTO handleNotFound(NotFoundException ex) {
		return new ErrorsResponseDTO(ex.getMessage(), LocalDateTime.now());
	}

	@ExceptionHandler(UnauthorizedException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ErrorsResponseDTO handleException(UnauthorizedException ex) {
		return new ErrorsResponseDTO("Non autorizzato", LocalDateTime.now());
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
	public ErrorsResponseDTO handleGeneric(Exception ex) {
		ex.printStackTrace();
		return new ErrorsResponseDTO("Problema lato server! Giuro che risolveremo presto!", LocalDateTime.now());
	}
}
