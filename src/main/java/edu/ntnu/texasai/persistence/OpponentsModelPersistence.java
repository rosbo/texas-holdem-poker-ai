package edu.ntnu.texasai.persistence;

import edu.ntnu.texasai.model.opponentmodeling.ContextAction;
import edu.ntnu.texasai.model.opponentmodeling.ContextAggregate;
import edu.ntnu.texasai.utils.Logger;

import javax.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OpponentsModelPersistence {
    private static final String TABLE_OPPONENTS_MODEL = "Opponents";

    private final PersistenceManager persistenceManager;
    private final Logger logger;

    @Inject
    public OpponentsModelPersistence(final PersistenceManager persistenceManager, final Logger logger) {
        this.persistenceManager = persistenceManager;
        this.logger = logger;

        init();
    }

    public void persist(ContextAggregate contextAggregate) {
        try {
            String insert = "INSERT INTO " + TABLE_OPPONENTS_MODEL + " VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement statement = persistenceManager.getConnection().prepareStatement(insert);
            ContextAction contextAction = contextAggregate.getContextAction();
            statement.setInt(1, contextAction.getPlayer().getNumber());
            statement.setString(2, contextAction.getBettingDecision().toString());
            statement.setString(3, contextAction.getBettingRoundName().toString());
            statement.setString(4, contextAction.getContextRaises().toString());
            statement.setString(5, contextAction.getContextPlayers().toString());
            statement.setString(6, contextAction.getContextPotOdds().toString());
            statement.setInt(7, contextAggregate.getNumberOfOccurrences());
            statement.setDouble(8, contextAggregate.getHandStrengthAverage());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    public double retrieve(ContextAction contextAction) {
        String query = "SELECT handstrength FROM " + TABLE_OPPONENTS_MODEL + " WHERE player = ? AND decision = ? AND " +
                "roundname = ? AND raises = ? AND playercount = ? AND potodds = ?";

        try {
            PreparedStatement statement = persistenceManager.getConnection().prepareStatement(query);
            statement.setInt(1, contextAction.getPlayer().getMoney());
            statement.setString(2, contextAction.getBettingDecision().toString());
            statement.setString(3, contextAction.getBettingRoundName().toString());
            statement.setString(4, contextAction.getContextRaises().toString());
            statement.setString(5, contextAction.getContextPlayers().toString());
            statement.setString(6, contextAction.getContextPotOdds().toString());
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getDouble("handstrength");
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    public void print() {
        String query = "SELECT * FROM " + TABLE_OPPONENTS_MODEL + " ORDER BY player, decision, roundname, " +
                "raises, playercount, potodds, occurences, handstrength ASC";
        try {
            PreparedStatement statement = persistenceManager.getConnection().prepareStatement(query);
            ResultSet result = statement.executeQuery();
            logger.log("P\tDecision\tRound\tRaises\t#Players\tPO\tOccurences\tHS");
            while (result.next()) {
                logger.log(result.getInt("player") + "\t" + result.getString("decision") + "\t" + result.getString
                        ("roundname") + "\t" + result.getString("raises") + "\t" + result.getString("playercount") +
                        "\t" + result.getString("potodds") + "\t" + result.getString("occurences") + "\t" + result
                        .getString("handstrength"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    public void clear() {
        String query = "TRUNCATE TABLE " + TABLE_OPPONENTS_MODEL;
        try {
            Statement statement = persistenceManager.getConnection().createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getLocalizedMessage());
        }

    }

    private void init() {
        try {
            Statement statement = persistenceManager.getConnection().createStatement();
            String query = "CREATE TABLE IF NOT EXISTS " + TABLE_OPPONENTS_MODEL + "(player integer," +
                    "decision VARCHAR_IGNORECASE,roundname VARCHAR_IGNORECASE, raises VARCHAR_IGNORECASE, " +
                    "playercount VARCHAR_IGNORECASE, potodds VARCHAR_IGNORECASE, occurences integer, " +
                    "handstrength double)";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }
}
