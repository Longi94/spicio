package com.tlongdev.spicio.storage.dao;

import com.tlongdev.spicio.domain.model.SeriesActivities;

import java.util.Map;

/**
 * @author Long
 * @since 2016. 04. 05.
 */
public interface UserDao {
    int insertUserActivities(Map<Integer, SeriesActivities> user);
}
