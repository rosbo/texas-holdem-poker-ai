package edu.ntnu.texasai.controller;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import edu.ntnu.texasai.model.Player;
import edu.ntnu.texasai.preflopsim.PlayerControllerPreFlopRoll;
import edu.ntnu.texasai.preflopsim.PreFlopSimulatorModule;

public class StatisticsControllerTest {

    private StatisticsController statisticsController;
    private PlayerControllerPreFlopRoll playerControllerPreFlopRoll;

    @Before
    public void setUp() throws Exception {
        Injector injector = Guice.createInjector(new PreFlopSimulatorModule());
        this.statisticsController = injector
                .getInstance(StatisticsController.class);
        this.playerControllerPreFlopRoll = injector
                .getInstance(PlayerControllerPreFlopRoll.class);
    }

    @Test
    public void testStoreWinners() {
        Player p1 = new Player(0, 1000, playerControllerPreFlopRoll);
        List<Player> winners = new ArrayList<Player>();
        statisticsController.initializeStatistics();
        winners.add(p1);
        statisticsController.storeWinners(winners);
        assertEquals(new Integer(1), statisticsController.getPlayer0Wins());
    }



}
