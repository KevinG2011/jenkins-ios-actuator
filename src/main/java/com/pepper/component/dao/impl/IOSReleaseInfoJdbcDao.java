package com.pepper.component.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;

public class IOSReleaseInfoJdbcDao {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
}