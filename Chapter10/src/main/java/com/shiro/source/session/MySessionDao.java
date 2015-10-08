package com.shiro.source.session;

import com.shiro.source.JdbcTemplateUtils;
import com.shiro.source.SerializableUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liwang on 15-10-6.
 */
public class MySessionDao extends CachingSessionDAO {

    private JdbcTemplate jdbcTemplate= JdbcTemplateUtils.getInstance();

    @Override
    protected void doUpdate(Session session) {
        if(session instanceof ValidatingSession && !((ValidatingSession)session).isValid()){
            return;
        }
        String sql = "UPDATE sessions SET session = ? WHERE id = ?";
        jdbcTemplate.update(sql, SerializableUtils.serialize(session), session.getId());
    }

    @Override
    protected void doDelete(Session session) {
        String sql = "DELETE FROM sessions WHERE id = ? ";
        jdbcTemplate.update(sql, session.getId());
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId=generateSessionId(session);
        assignSessionId(session,sessionId);
        String sql = "INSERT INTO sessions(id, session) VALUES (?, ?)";
        jdbcTemplate.update(sql, sessionId, SerializableUtils.serialize(session));
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable serializable) {
        String sql = "SELECT session FROM sessions WHERE id = ?";
        List<String> sessionStrList=jdbcTemplate.queryForList(sql, String.class, serializable);
        if(sessionStrList==null) return null;
        return SerializableUtils.deserialize(sessionStrList.get(0));
    }
}
