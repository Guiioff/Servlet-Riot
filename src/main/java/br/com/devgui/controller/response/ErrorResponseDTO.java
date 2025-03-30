package br.com.devgui.controller.response;

public record ErrorResponseDTO(int statusCode, String message, String detail) {

}
