package javastart.jspexample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class Database extends SQLException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Pobiera z bazy danych pe³n¹ nazwê u¿ytkownika.
	 * 
	 * @param userid
	 *            ci¹g identyfikatora u¿ytkownika
	 * @return ci¹g pe³nej nazwy
	 * @throws SQLException
	 *             w przypadku problemu z baz¹ danych
	 */
	public String lookupFullname(String user) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String fullname = "";
	    String DRIVER = "org.h2.Driver";
		String URL = "jdbc:h2:tcp://localhost/~/test";
		String QUERY = "SELECT USER FROM PERSONS WHERE USER = ?";
		String USER = "sa";
		String PASSWORD = "";
		try {
			 Class.forName(DRIVER);
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			statement = connection.prepareStatement(QUERY);
			statement.setString(1, user);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				fullname = resultSet.getString("USER");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (resultSet != null)
				resultSet.close();
			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();
		}
		return fullname;
	}
	public String lookupPassword(String user) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String user_pass = "";
	    String DRIVER = "org.h2.Driver";
		String URL = "jdbc:h2:tcp://localhost/~/test";
		String QUERY = "SELECT PASSWORD FROM PERSONS WHERE USER = ?";
		String USER = "sa";
		String PASSWORD = "";
		try {
			 Class.forName(DRIVER);
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			statement = connection.prepareStatement(QUERY);
			statement.setString(1, user);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				user_pass = resultSet.getString("PASSWORD");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (resultSet != null)
				resultSet.close();
			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();
		}
		return user_pass;
	}
	
	public void updatePassword(String user, String password) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Boolean result = false;

		String DRIVER = "org.h2.Driver";
		String URL = "jdbc:h2:tcp://localhost/~/test";
		String QUERY = "UPDATE PERSONS SET Password = ?, DATA_USTAWIENIA   = CURRENT_TIMESTAMP , DATA_WYGASNIECIA = CURRENT_TIMESTAMP+0.004,DATA_NOWE = CURRENT_TIMESTAMP+0.002   WHERE USER = ?";
		String USER = "sa";
		String PASSWORD = "";

		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			statement = connection.prepareStatement(QUERY);
			statement.setString(1, password);
			statement.setString(2, user);
			result = statement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();
		}
	}
	
	public void insertPasswordHistory(String user, String password) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;

		String DRIVER = "org.h2.Driver";
		String URL = "jdbc:h2:tcp://localhost/~/test";
		String QUERY = "INSERT INTO PasswordHistory (User, Password, DATA_USTAWIENIA  ) VALUES (?, ?, CURRENT_TIMESTAMP)";
		String USER = "sa";
		String PASSWORD = "";

		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			statement = connection.prepareStatement(QUERY);

			statement.setString(1, user);
			statement.setString(2, password);
			statement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();
		}
	}
	
	public Boolean SprawdzHistorieHasel(String user, String password) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		Boolean returnValue = true;

		String DRIVER = "org.h2.Driver";
		String URL = "jdbc:h2:tcp://localhost/~/test";
		String QUERY = "SELECT Password FROM PasswordHistory WHERE User = ? ORDER BY DATA_USTAWIENIA  DESC LIMIT 20";
		String USER = "sa";
		String PASSWORD = "";
		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			statement = connection.prepareStatement(QUERY);
			statement.setString(1, user);
			result = statement.executeQuery();

			while (result.next()) {
				if (result.getString("Password").equals(password)) {
					returnValue = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();
		}

		return returnValue;
	}
	
	public Boolean NoweHaslo(String user) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		Boolean returnValue = true;

		String DRIVER = "org.h2.Driver";
		String URL = "jdbc:h2:tcp://localhost/~/test";
		String QUERY = "SELECT USER FROM PERSONS WHERE DATA_NOWE > CURRENT_TIMESTAMP AND USER=?";
		String USER = "sa";
		String PASSWORD = "";
		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			statement = connection.prepareStatement(QUERY);
			statement.setString(1, user);
			result = statement.executeQuery();

			while (result.next()) {
				if (result.getString("user").equals(user)) {
					returnValue = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();
		}

		return returnValue;
	}
	
	public Boolean HasloWygasniete(String user) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		Boolean returnValue = true;

		String DRIVER = "org.h2.Driver";
		String URL = "jdbc:h2:tcp://localhost/~/test";
		String QUERY = "SELECT USER FROM PERSONS WHERE DATA_WYGASNIECIA < CURRENT_TIMESTAMP AND USER=?";
		String USER = "sa";
		String PASSWORD = "";
		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			statement = connection.prepareStatement(QUERY);
			statement.setString(1, user);
			result = statement.executeQuery();

			while (result.next()) {
				if (result.getString("user").equals(user)) {
					returnValue = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();
		}

		return returnValue;
	}
	
	public void insertLogowania(String user, Boolean IO, String poprawne_logowanie) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		Boolean result = false;

		String DRIVER = "org.h2.Driver";
		String URL = "jdbc:h2:tcp://localhost/~/test";
		String QUERY_IN = "INSERT INTO LOGOWANIA (User, Data_logowania, Stan) VALUES (?, CURRENT_TIMESTAMP, ?)";
		String QUERY_OUT = "INSERT INTO LOGOWANIA (User, Data_wylogowania, Stan) VALUES (?, CURRENT_TIMESTAMP, ?)";
		String USER = "sa";
		String PASSWORD = "";
		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			if (IO) {
				statement = connection.prepareStatement(QUERY_IN);
			} else {
				statement = connection.prepareStatement(QUERY_OUT);
			}

			statement.setString(1, user);
			statement.setString(2, poprawne_logowanie);
			result = statement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();
		}
	}
	
	public Boolean BlokowanieKonta(String user) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		Boolean returnValue = true;
		int iterator = 0;

		String DRIVER = "org.h2.Driver";
		String URL = "jdbc:h2:tcp://localhost/~/test";
		String QUERY = "SELECT * FROM Logowania WHERE User = ? AND Data_Logowania IS NOT NULL ORDER BY Data_Logowania DESC LIMIT 5";
		String USER = "sa";
		String PASSWORD = "";
		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			statement = connection.prepareStatement(QUERY);
			statement.setString(1, user);
			result = statement.executeQuery();

			while (result.next()) {
				System.out.println("DEBUGOWANIE_HISTORII " + result.getString("Stan") + "  //  "
						+ result.getString("Data_logowania"));

				SimpleDateFormat formatter = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
				Date logInTime = (Date) formatter.parse(result.getString("Data_Logowania"));

				LocalDateTime now = LocalDateTime.now();
				Date dateNow = new Date(System.currentTimeMillis());
				Calendar calendarCurrent = Calendar.getInstance();
				calendarCurrent.setTime(dateNow);

				calendarCurrent.add(Calendar.MINUTE, -2);
				dateNow = calendarCurrent.getTime();

				if (logInTime.after(dateNow) && (result.getString("Stan").contentEquals("Nie"))) {
					iterator++;
					System.out.println(iterator);
				}

				if (iterator >= 3) {
					returnValue = false;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();
		}

		return returnValue;
	}
	
	
}
