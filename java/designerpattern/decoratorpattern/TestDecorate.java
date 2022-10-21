package decoratorpattern;

import org.junit.jupiter.api.Test;

public class TestDecorate {

    @Test
    public void test_decorate(){
        BasicDecorate basicDecorate = new BasicDecorate();
        DecorateHouseOfFloor decorateHouseOfFloor = new DecorateHouseOfFloor(basicDecorate);
        DecorateHouseOfWindow decorateHouseOfWindow = new DecorateHouseOfWindow(decorateHouseOfFloor);
        decorateHouseOfWindow.decorate();
    }
}
