package eu.crushedpixel.replaymod.gui.elements;

import net.minecraft.client.gui.FontRenderer;

public class GuiNumberInput extends GuiAdvancedTextField {

    protected Double minimum, maximum;

    protected boolean acceptFloats = false;

    public GuiNumberInput(FontRenderer fontRenderer,
                          int xPos, int yPos, int width, Double minimum, Double maximum, Double defaultValue, boolean acceptFloats) {
        super(fontRenderer, xPos, yPos, width, 20);
        this.minimum = minimum;
        this.maximum = maximum;
        this.acceptFloats = acceptFloats;
        setCursorPositionZero();
        if(defaultValue != null) {
            setValue(defaultValue);
        }
    }

    public GuiNumberInput(FontRenderer fontRenderer,
                          int xPos, int yPos, int width, int minimum, int maximum, int defaultValue, boolean acceptFloats) {
        this(fontRenderer, xPos, yPos, width, (double) minimum, (double) maximum, (double) defaultValue, acceptFloats);
    }

    public void setValue(double value) {
        if(minimum != null && value < minimum) {
            setText(acceptFloats ? minimum.toString() : Integer.toString((int) Math.round(minimum)));
        } else if(maximum != null && value > maximum) {
            setText(acceptFloats ? maximum.toString() : Integer.toString((int) Math.round(maximum)));
        } else {
            if(acceptFloats) {
                setText("" + value);
            } else {
                setText("" + (int) Math.round(value));
            }
        }
        setCursorPositionZero();
    }

    @Override
    public void writeText(String text) {
        String textBefore = getText();
        int cursorPositionBefore = getCursorPosition();
        try {
            super.writeText(text);

            double val;
            if(acceptFloats) {
                val = Double.valueOf(getText());
            } else {
                val = Integer.valueOf(getText());
            }

            if(minimum != null && val < minimum) {
                setText(acceptFloats ? minimum.toString() : Integer.toString((int) Math.round(minimum)));
            } else if(maximum != null && val > maximum) {
                setText(acceptFloats ? maximum.toString() : Integer.toString((int) Math.round(maximum)));
            }
        } catch(NumberFormatException e) {
            setText(textBefore);
            setCursorPosition(cursorPositionBefore);
        }
    }

    public int getIntValue() {
        try {
            return Integer.valueOf(getText());
        } catch(Exception e) {
            return 0;
        }
    }

    public Integer getIntValueNullable() {
        try {
            return Integer.valueOf(getText());
        } catch(Exception e) {
            return null;
        }
    }

    public double getPreciseValue() {
        try {
            return Double.valueOf(getText());
        } catch(Exception e) {
            return 0d;
        }
    }

}
