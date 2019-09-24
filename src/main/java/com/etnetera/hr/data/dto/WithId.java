package com.etnetera.hr.data.dto;

/**
 * Generic interface for getting and setting object's ID
 * @param <T>
 */
public interface WithId<T> {
    /**
     * Get ID
     * @return ID
     */
    T getId();

    /**
     * Set ID
     * @param id ID
     */
    void setId(T id);
}
