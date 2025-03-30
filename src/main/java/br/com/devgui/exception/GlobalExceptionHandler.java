package br.com.devgui.exception;

import java.io.IOException;
import com.google.gson.Gson;
import br.com.devgui.controller.response.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GlobalExceptionHandler {

  private static final Gson gson = new Gson();

  public static void handleException(Exception e, HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

    int statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    String message = "Internal server error.";
    String detail = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();

    if (e instanceof DatabaseException) {
      statusCode = HttpServletResponse.SC_BAD_GATEWAY;
      message = "Database error.";

    } else if (e instanceof DatadragonException) {
      statusCode = HttpServletResponse.SC_FORBIDDEN;
      message = "Error getting data from DataDragon.";

    } else if (e instanceof RiotApiException) {
      statusCode = HttpServletResponse.SC_SERVICE_UNAVAILABLE;
      message = "Error communicating with Riot API.";

    } else if (e instanceof IllegalArgumentException) {
      statusCode = HttpServletResponse.SC_BAD_REQUEST;
      message = "Invalid parameters.";
    }

    ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(statusCode, message, detail);
    String responseJson = gson.toJson(errorResponseDTO);

    resp.setStatus(statusCode);
    resp.setContentType("application/json");
    resp.getWriter().write(responseJson);
  }
}
