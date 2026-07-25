package dev.cucei.siiapi.common.pagination;

import java.util.List;

/**
 * Generic paginated response wrapper.
 *
 * @param <T> the type of items in the results
 */
public record PaginatedResponse<T>(long total, List<T> results) {
}
