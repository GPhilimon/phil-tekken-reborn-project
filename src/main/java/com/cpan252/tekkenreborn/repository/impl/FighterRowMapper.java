package com.cpan252.tekkenreborn.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import com.cpan252.tekkenreborn.model.Fighter;

public class FighterRowMapper implements RowMapper<Fighter> {

    @Override
    @Nullable
    public Fighter mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Fighter.builder().id(rs.getLong("id")).name(rs.getString("name"))
                .damagePerHit(rs.getInt("damage_per_hit")).health(rs.getInt("health"))
                .resistance(rs.getBigDecimal("resistance")).animeFrom(Fighter.Anime.valueOf(rs.getString("anime_from")))
                .build();
    }
}
