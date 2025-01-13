package com.sivaprakash.user_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a requested resource is not found in the system.
 * This exception is automatically mapped to HTTP 404 (NOT_FOUND) status code.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new ResourceNotFoundException with null as its detail message.
     */
    public ResourceNotFoundException() {
        super();
    }

    /**
     * Constructs a new ResourceNotFoundException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new ResourceNotFoundException with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     * @param cause the cause (which is saved for later retrieval by the getCause() method)
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new ResourceNotFoundException with the specified detail message, cause,
     * suppression enabled or disabled, and writable stack trace enabled or disabled.
     *
     * @param message the detail message
     * @param cause the cause
     * @param enableSuppression whether suppression is enabled or disabled
     * @param writableStackTrace whether the stack trace should be writable
     */
    public ResourceNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Creates a ResourceNotFoundException for a specific resource type and identifier.
     *
     * @param resourceType the type of resource that was not found (e.g., "User", "Customer")
     * @param identifier the identifier used to look up the resource
     * @return a new ResourceNotFoundException with a formatted message
     */
    public static ResourceNotFoundException forResource(String resourceType, String identifier) {
        return new ResourceNotFoundException(String.format("%s not found with identifier: %s", resourceType, identifier));
    }

    /**
     * Creates a ResourceNotFoundException for a specific resource type and multiple identifiers.
     *
     * @param resourceType the type of resource that was not found
     * @param criteria a map of criteria used to look up the resource
     * @return a new ResourceNotFoundException with a formatted message
     */
    public static ResourceNotFoundException forResourceWithCriteria(String resourceType, java.util.Map<String, Object> criteria) {
        return new ResourceNotFoundException(String.format("%s not found with criteria: %s", resourceType, criteria));
    }
}