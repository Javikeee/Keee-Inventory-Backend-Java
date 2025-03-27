package com.keee.inventario.model;

import lombok.Getter;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

@Getter
public enum OrderStatus {
    PENDING(1, "order.status.pending"),
    IN_PROGRESS(2, "order.status.in_progress"),
    SHIPPED(3, "order.status.shipped"),
    CANCELED(4, "order.status.canceled");

    private final int code;

    private final String messageKey;

    OrderStatus(int code, String messageKey) {
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
