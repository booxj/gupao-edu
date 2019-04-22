package com.booxj.gp.pattern.template;

import java.sql.ResultSet;

public interface RowMapper<T> {

    T mapRow(ResultSet rs) throws Exception;
}
