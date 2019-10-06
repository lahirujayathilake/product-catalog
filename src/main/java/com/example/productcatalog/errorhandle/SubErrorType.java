package com.example.productcatalog.errorhandle;

/**
 * Error types
 *
 * @author Lahiru Jayathilake
 * @since 0.0.1-SNAPSHOT
 */
public enum SubErrorType {
    VALIDATION_ERROR("Validation Error"),
    CONSTRAINT_VIOLATION("Constraint Violation");

    private final String value;

    SubErrorType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
