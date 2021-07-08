package br.com.qeep.moving.loja.pecas.spring.api.exception;

public class ErroDeNegocioException extends Exception {

	private static final long serialVersionUID = 4929529810224174835L;

	public ErroDeNegocioException(String message) {
        super(message);
    }
}