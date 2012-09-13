package edu.ntnu.texasai.persistence;

import com.google.inject.Inject;
import edu.ntnu.texasai.model.cards.EquivalenceClass;
import edu.ntnu.texasai.utils.Logger;

import java.sql.*;

public class PersistenceManager {
    public static final String TABLE_EQUIVALENCE_NAME = "Equivalences";

    private final Logger logger;
    private final Connection connection;

    @Inject
    public PersistenceManager(final Logger logger) {
        this.logger = logger;
        connection = getConnection();
    }

    public void createEquivalencesTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "DROP TABLE IF EXISTS " + TABLE_EQUIVALENCE_NAME + ";";
            query += "CREATE TABLE " + TABLE_EQUIVALENCE_NAME + "(players integer," +
                    "number1 VARCHAR_IGNORECASE,number2 VARCHAR_IGNORECASE, type VARCHAR_IGNORECASE, wins double)";
            statement.executeUpdate(query);
            System.out.println("Table creation process successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    public Double retrievePercentageOfWinsByPlayerAndEquivalenceClass(Integer numberOfPlayers,
                                                                      EquivalenceClass equivalenceClass) {
        String number1 = equivalenceClass.getNumber1().toString();
        String number2 = equivalenceClass.getNumber2().toString();
        String type = equivalenceClass.getType();
        String query = "SELECT wins FROM " + TABLE_EQUIVALENCE_NAME + " WHERE players = ? AND type = ? AND " +
                "((number1 = ? AND number2 = ?) OR (number1 = ? AND number2 = ?))";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, numberOfPlayers);
            statement.setString(2, type);
            statement.setString(3, number1);
            statement.setString(4, number2);
            statement.setString(5, number2);
            statement.setString(6, number1);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getDouble("wins");
            } else {
                throw new RuntimeException("Probability not calculated for these parameters");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    public void persistResult(Integer numberOfPlayers, EquivalenceClass equivalenceClass, Double percentage) {
        try {
            String insert = "INSERT INTO " + TABLE_EQUIVALENCE_NAME + " VALUES(?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(insert);
            statement.setInt(1, numberOfPlayers);
            statement.setString(2, equivalenceClass.getNumber1().toString());
            statement.setString(3, equivalenceClass.getNumber2().toString());
            statement.setString(4, equivalenceClass.getType());
            statement.setDouble(5, percentage);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    public void printAll() {
        String query = "SELECT * FROM " + TABLE_EQUIVALENCE_NAME;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                logger.log(result.getInt("players") + " " + result.getString("number1")
                        + " " + result.getString("number2") + " " + result.getString("type") + " "
                        + result.getDouble("wins"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    private Connection getConnection() {
        try {
            Class.forName("org.h2.Driver");
            return DriverManager.getConnection("jdbc:h2:equivalenceTable/equivalence_table", "sa", "");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }
}
