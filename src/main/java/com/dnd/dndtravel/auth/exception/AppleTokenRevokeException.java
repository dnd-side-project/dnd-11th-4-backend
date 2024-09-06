package com.dnd.dndtravel.auth.exception;

public class AppleTokenRevokeException extends RuntimeException {
    private static final String MESSAGE = "Error revoking Apple token";

    public AppleTokenRevokeException(Exception e) {
        super(MESSAGE, e);
    }
}
