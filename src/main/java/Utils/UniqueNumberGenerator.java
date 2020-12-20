package Utils;

public class UniqueNumberGenerator {
    private static UniqueNumberGenerator instance;
    private int counter;

    public static synchronized UniqueNumberGenerator getInstance() {
        if (instance == null) {
            instance = new UniqueNumberGenerator();
        }
        return instance;
    }

    public int getNumber(){
        return counter++;
    }
}
