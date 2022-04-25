package com.census.support.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    public ResponseEntity<?> getTotalTickets() {
        String sql = "SELECT COUNT(*) FROM ticket";
        return ResponseEntity.ok(jdbcTemplate.queryForObject(sql, Integer.class));
    }

    public ResponseEntity<?> getReceivedTickets() {
        String sql = "SELECT COUNT(*) FROM ticket WHERE status = 'RECEIVED'";
        return ResponseEntity.ok(jdbcTemplate.queryForObject(sql, Integer.class));
    }

    public ResponseEntity<?> getHoldTickets() {
        String sql = "SELECT COUNT(*) FROM ticket WHERE status = 'HOLD'";
        return ResponseEntity.ok(jdbcTemplate.queryForObject(sql, Integer.class));
    }

    public ResponseEntity<?> getResolvedTickets() {
        String sql = "SELECT COUNT(*) FROM ticket WHERE status = 'SOLVED'";
        return ResponseEntity.ok(jdbcTemplate.queryForObject(sql, Integer.class));
    }
}
