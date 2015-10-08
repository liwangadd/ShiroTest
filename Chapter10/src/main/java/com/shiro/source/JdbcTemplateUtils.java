package com.shiro.source;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by liwang on 15-10-6.
 */
public class JdbcTemplateUtils {

    private static JdbcTemplate jdbcTemplate;

    public static JdbcTemplate getInstance() {
        if (jdbcTemplate == null)
            jdbcTemplate = createJdbcTemplate();
        return jdbcTemplate;
    }

    private static JdbcTemplate createJdbcTemplate() {
        DataSource dataSource = new DataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/Shiro");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate;
    }

}