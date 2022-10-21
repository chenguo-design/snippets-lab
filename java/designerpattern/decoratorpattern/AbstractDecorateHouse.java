package decoratorpattern;

/**
 * 装地板
 */
public abstract class AbstractDecorateHouse implements DecorateHouse{
    DecorateHouse decorateHouse;

    public AbstractDecorateHouse(DecorateHouse decorateHouse) {
        this.decorateHouse = decorateHouse;
    }

    protected AbstractDecorateHouse() {
    }


    @Override
    public void decorate() {
        this.decorateHouse.decorate();
    }
}
