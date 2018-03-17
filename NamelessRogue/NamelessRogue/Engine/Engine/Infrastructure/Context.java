package Engine.Infrastructure;

import abstraction.ISystem;

import java.awt.*;
import java.util.ArrayList;

public class Context {
    public ArrayList<ISystem> Systems;
    public Context(){
        Systems = new ArrayList<>();
    }
}
