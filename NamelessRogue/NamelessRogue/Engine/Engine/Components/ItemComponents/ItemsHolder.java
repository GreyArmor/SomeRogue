package Engine.Components.ItemComponents;

import Engine.Components.Component;

import java.util.ArrayList;
import java.util.Vector;

public class ItemsHolder extends Component {

    private ArrayList<Item> items;
    public ItemsHolder()
    {
        items = new ArrayList<>();
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public ArrayList<Item> getItems() {
        return items;
    }
}
