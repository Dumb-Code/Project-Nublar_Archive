package net.dumbcode.projectnublar.core.utils;

import org.jetbrains.annotations.Nullable;

import java.lang.constant.Constable;

public enum QuantumBoolean implements Comparable<QuantumBoolean>, Constable {
    TRUE(Boolean.TRUE),
    FALSE(Boolean.FALSE),
    BOTH(null);

    private final Boolean definition;

    QuantumBoolean(@Nullable Boolean def) {
        this.definition = def;
    }

    public boolean getDefinition() {
        return Boolean.TRUE.equals(definition);
    }

    public boolean isTrue() {
        return this == TRUE || this == BOTH;
    }

    public boolean isFalse() {
        return this == FALSE || this == BOTH;
    }

    public boolean isBoth() {
        return this == BOTH;
    }

    public boolean isDefined() {
        return this != BOTH;
    }

    public boolean isUndefined() {
        return this == BOTH;
    }

    public static QuantumBoolean fromBoolean(boolean bool) {
        return bool ? TRUE : FALSE;
    }

    /**
     * A null value will be interpreted as both true and false.
     * @param bool nullable boolean
     * @return QuantumBoolean
     */
    public static QuantumBoolean fromNullableBoolean(@Nullable Boolean bool) {
        return bool == null ? BOTH : fromBoolean(bool);
    }

    public static QuantumBoolean fromString(@Nullable String str) {
        if (str == null) return BOTH;
        if (str.equalsIgnoreCase("true")) {
            return TRUE;
        } else if (str.equalsIgnoreCase("false")) {
            return FALSE;
        } else {
            return BOTH;
        }
    }

    public static int compare(Boolean a, Boolean b) {
        return Boolean.compare(a, b);
    }

    public static QuantumBoolean logicalAnd(QuantumBoolean a, QuantumBoolean b) {
        if (a == TRUE && b == TRUE) return TRUE;
        if (a == FALSE || b == FALSE) return FALSE;
        return BOTH;
    }

    public static QuantumBoolean logicalOr(QuantumBoolean a, QuantumBoolean b) {
        if (a == FALSE && b == FALSE) return FALSE;
        if (a == TRUE || b == TRUE) return TRUE;
        return BOTH;
    }

    public static QuantumBoolean logicalNot(QuantumBoolean a) {
        if (a == TRUE) return FALSE;
        if (a == FALSE) return TRUE;
        return BOTH;
    }

    public static QuantumBoolean logicalXor(QuantumBoolean a, QuantumBoolean b) {
        if (a == FALSE && b == FALSE) return FALSE;
        if (a == TRUE && b == FALSE) return TRUE;
        if (a == FALSE && b == TRUE) return TRUE;
        if (a == TRUE && b == TRUE) return TRUE;
        return BOTH;
    }
}
