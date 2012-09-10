package edu.ntnu.texasai.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.inject.Inject;

import edu.ntnu.texasai.model.cards.EquivalenceClass;
import edu.ntnu.texasai.utils.Logger;

public class PersistenceController {

    private final Logger logger;

    @Inject
    public PersistenceController(final Logger logger) {
        this.logger = logger;
    }

    public void createTable() {
        Connection conn = null;
        try {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:h2:equivalenceTable/equivalence_table", "sa", "");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            Statement st = conn.createStatement();
            String table = "CREATE TABLE IF NOT EXISTS Equivalences(id integer, players integer,number1 varchar(10),number2 varchar(10), type varchar(10),wins integer)";
            st.executeUpdate(table);
            System.out.println("Table creation process successfully!");
        } catch (SQLException s) {
            System.out.println("Table already exists!");
        }

        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Double retrievePercentageOfWinsByPlayerAndEquivalenceClass(
            Integer numberOfPlayers, EquivalenceClass equivalenceClass) {
        Connection connection = null;
        String number1 = equivalenceClass.getNumber1().toString();
        String number2 = equivalenceClass.getNumber2().toString();
        String type = equivalenceClass.getType();
        String query = "SELECT wins FROM Equivalences WHERE players = "
                + numberOfPlayers + " ,number1 = " + number1 + " ,number2 = "
                + number2 + " , type = " + type;
        Double percentageOfWins = null;
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:h2:equivalenceTable/equivalence_table", "sa", "");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                percentageOfWins = new Double(result.getDouble("wins"));
            }
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return percentageOfWins;
    }

    public void persistResult(Integer id, Integer numberOfPlayers,
            EquivalenceClass equivalenceClass, Double percentage) {
        Connection conn = null;
        try {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:h2:equivalenceTable/equivalence_table", "sa", "");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            String insert = "INSERT INTO Equivalences VALUES(?,?,?,?,?,?)";
            PreparedStatement statement = conn.prepareStatement(insert);
            statement.setInt(1, id);
            statement.setInt(2, numberOfPlayers);
            statement.setString(3, equivalenceClass.getNumber1().toString());
            statement.setString(4, equivalenceClass.getNumber2().toString());
            statement.setString(5, equivalenceClass.getType());
            statement.setDouble(6, percentage);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printAll() {
        Connection connection = null;
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:h2:equivalenceTable/equivalence_table", "sa", "");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String query = "SELECT * FROM Equivalences";
        try {
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                logger.log(result.getInt("id") + " " + result.getInt("players"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {       
            e.printStackTrace();
        }

    }

}
