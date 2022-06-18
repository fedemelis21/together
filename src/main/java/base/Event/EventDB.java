package base.Event;

import base.Calendar.Calendar;
import base.User.User;
import res.DBManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventDB implements EventDAO{

    Statement statement;

    public void MySQLConnection() throws SQLException {
        DBManager.setConnection(
                DBManager.JDBC_Driver_MySQL,
                DBManager.JDBC_URL_MySQL);
        statement = DBManager.getConnection().createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
    }

    public EventDB() throws SQLException {
        MySQLConnection();
    }

    @Override
    public List<Event> selectAllEventOfCalendar(Calendar calendar) throws SQLException {
        String query = String.format("SELECT * FROM event WHERE idCalendar = %d", calendar.getIdCalendar());
        ResultSet rs = statement.executeQuery(query);
        return rsToEventList(rs);
    }

    @Override
    public List<Event> selectAllEventOfSpecifiedMonth(Calendar calendar, int month) throws SQLException {
        String query = String.format("SELECT * FROM event WHERE idCalendar = %d and month(data) = %d", calendar.getIdCalendar(), month);
        ResultSet rs = statement.executeQuery(query);
        return rsToEventList(rs);
    }

    @Override
    public void insertEvent(int idEvent, Calendar cal, String nome, String date, User u, String type, String desc)  throws SQLException{
        String query = String.format("INSERT INTO event (idevent, idCalendar, nome, data, iduser, type, desc) VALUES(%d, %d, '%s', '%s', '%s', '%s', '%s')",
                idEvent, cal.getIdCalendar(), nome, date, u.getUsername(), type, desc);
        statement.executeUpdate(query);
    }

    @Override
    public void insertEventWithType(int idEvent, Calendar cal, String nome, String date, User u, String type) throws SQLException{
        String query = String.format("INSERT INTO event (idevent, idCalendar, nome, data, iduser, type, desc) VALUES(%d, %d, '%s', '%s', '%s', '%s', NULL)",
                idEvent, cal.getIdCalendar(), nome, date, u.getUsername(), type);
        statement.executeUpdate(query);
    }

    @Override
    public void insertEventWithDesc(int idEvent, Calendar cal, String nome, String date, User u, String desc) throws SQLException{
        String query = String.format("INSERT INTO event (idevent, idCalendar, nome, data, iduser, type, desc) VALUES(%d, %d, '%s', '%s', '%s', NULL, '%s')",
                idEvent, cal.getIdCalendar(), nome, date, u.getUsername(), desc);
        statement.executeUpdate(query);
    }

    @Override
    public void insertEvent(int idEvent, Calendar cal, String nome, String date, User u) throws SQLException {
        String query = String.format("INSERT INTO event (idevent, idCalendar, nome, data, iduser, type, desc) VALUES(%d, %d, '%s', '%s', '%s', NULL, NULL)",
                idEvent, cal.getIdCalendar(), nome, date, u.getUsername());
        statement.executeUpdate(query);
    }

    @Override
    public void deleteEventById(int idEvent) {

    }

    public List<Event> rsToEventList(ResultSet rs) throws SQLException {
        List<Event> eventList = new ArrayList<Event>();
        while (rs.next()) {
            Event e = new Event(
                    rs.getInt(1),
                    rs.getInt(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7));
            eventList.add(e);
        }
        return eventList;
    }
}
