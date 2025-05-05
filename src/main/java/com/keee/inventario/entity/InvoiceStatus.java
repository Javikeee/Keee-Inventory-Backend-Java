package com.keee.inventario.entity;

import lombok.Getter;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

@Getter
public enum InvoiceStatus {
    PENDING(1, "invoice.status.pending"),
    PAID(2, "invoice.status.in_progress"),
    COMPLETED(2, "invoice.status.complete"),
    CANCELED(4, "invoice.status.canceled");

    private final int code;

    private final String messageKey;

    InvoiceStatus(int code, String messageKey) {
        this.code = code;
        this.messageKey = messageKey;
    }

    public static OrderStatus fromCode(int code) {
        return Arrays.stream(OrderStatus.values())
                .filter(status -> status.getCode() == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid status code: " + code));
    }

    public String getLocalizedDescription() {
        Locale locale = LocaleContextHolder.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
        return bundle.getString(this.messageKey);
    }
}
