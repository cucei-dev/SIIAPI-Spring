package dev.cucei.siiapi.info;

/**
 * API information response.
 */
public record InfoResponse(
    String status,
    String version,
    String site,
    String name,
    String description
) {
}
