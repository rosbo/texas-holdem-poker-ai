package edu.ntnu.texasai.controller;

import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.ntnu.texasai.controller.preflopsim.PlayerControllerPreFlopRoll;
import edu.ntnu.texasai.dependencyinjection.TexasModule;
import edu.ntnu.texasai.model.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class StatisticsControllerTest {
    private StatisticsController statisticsController;
    private PlayerControllerPreFlopRoll playerControllerPreFlopRoll;

    @Before
    public void setUp() throws Exception {
        Injector injector = Guice.createInjector(new TexasModule());
        this.statisticsController = injector
                .getInstance(StatisticsController.class);
        this.playerControllerPreFlopRoll = injector
                .getInstance(PlayerControllerPreFlopRoll.class);
    }

    @Test
    public void testStoreWinners() {
        Player p1 = new Player(1, 1000, playerControllerPreFlopRoll);
        List<Player> winners = new ArrayList<Player>();
        statisticsController.initializeStatistics();
        winners.add(p1);
        statisticsController.storeWinners(winners);
        assertEquals(1, statisticsController.getPlayer1Wins());
    }
}
