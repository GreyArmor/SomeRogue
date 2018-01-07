package Engine.Components.Stats;

import Engine.Components.Component;

public abstract class SimpleStat extends Component {
    private int value;
    private int minValue;
    private int maxValue;

    public SimpleStat(int value, int minValue, int maxValue)
    {

        this.value = value;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public int getValue() {
        return value;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getMaxValue() {
        return maxValue;
    }
}
