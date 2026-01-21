package org.likelion.recruit.resource.common.domain;

public class UpdateNullableField<T> {
    private boolean present;
    private T value;

    private UpdateNullableField(boolean present, T value) {
        this.present = present;
        this.value = value;
    }

    // 필드 부존재
    public static <T> UpdateNullableField<T> absent() {
        return new UpdateNullableField<>(false, null);
    }

    // 필드 존재
    public static <T> UpdateNullableField<T> of(T value) {
        return new UpdateNullableField<>(true, value);
    }

    public boolean isPresent() {
        return present;
    }

    public T getValue() {
        return value;
    }
}
