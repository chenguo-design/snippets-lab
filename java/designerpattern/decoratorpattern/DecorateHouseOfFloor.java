package decoratorpattern;

/**
 * 装修地板
 */
public class DecorateHouseOfFloor extends AbstractDecorateHouse{
    //public DecorateHouseOfFloor(DecorateHouse house){
    //    super(house);
    //}
    //
    //@Override
    //public void decorate() {
    //    super.decorate();
    //    System.out.println("装地板");
    //}

    private DecorateHouse decorateHouse;
    public DecorateHouseOfFloor(DecorateHouse decorateHouse) {
        this.decorateHouse = decorateHouse;
    }

    @Override
    public void decorate() {
        decorateHouse.decorate();
        System.out.println("装地板");
    }
}
