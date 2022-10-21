package decoratorpattern;

public class BasicDecorate implements DecorateHouse{
    @Override
    public void decorate() {
        prepare();
    }


    public void prepare(){
        System.out.println("准备好毛坯房");
    }
}
