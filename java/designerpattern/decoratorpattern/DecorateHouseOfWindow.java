package decoratorpattern;

public class DecorateHouseOfWindow extends AbstractDecorateHouse {
    //public DecorateHouseOfWindow(DecorateHouse decorateHouse) {
    //    super(decorateHouse);
    //}
    //
    //@Override
    //public void decorate() {
    //    super.decorate();
    //    System.out.println("安装窗户");
    //}

    private DecorateHouse decorateHouse;
    public DecorateHouseOfWindow(DecorateHouse decorateHouse) {
        this.decorateHouse = decorateHouse;
    }

    @Override
    public void decorate() {
        decorateHouse.decorate();
        System.out.println("装窗户");
    }
}
