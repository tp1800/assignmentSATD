package org.jfree.chart.swing.editor;

import org.jfree.chart.plot.PlotOrientation;

import javax.swing.*;
import java.io.Serializable;

public class DefaultPlotEditorProductProduct implements Serializable {
    /**
     * The orientation for the plot (for <tt>CategoryPlot</tt>s and
     * <tt>XYPlot</tt>s).
     */
    private PlotOrientation plotOrientation;
    /**
     * The orientation combo box (for <tt>CategoryPlot</tt>s and
     * <tt>XYPlot</tt>s).
     */
    private JComboBox orientationCombo;
    /**
     * Whether or not to draw lines between each data point (for
     * <tt>LineAndShapeRenderer</tt>s and <tt>StandardXYItemRenderer</tt>s).
     */
    private Boolean drawLines;
    /**
     * The checkbox for whether or not to draw lines between each data point.
     */
    private JCheckBox drawLinesCheckBox;
    /**
     * Whether or not to draw shapes at each data point (for
     * <tt>LineAndShapeRenderer</tt>s and <tt>StandardXYItemRenderer</tt>s).
     */
    private Boolean drawShapes;
    /**
     * The checkbox for whether or not to draw shapes at each data point.
     */
    private JCheckBox drawShapesCheckBox;

    public PlotOrientation getPlotOrientation() {
        return plotOrientation;
    }

    public void setPlotOrientation(PlotOrientation plotOrientation) {
        this.plotOrientation = plotOrientation;
    }

    public JComboBox getOrientationCombo() {
        return orientationCombo;
    }

    public void setOrientationCombo(JComboBox orientationCombo) {
        this.orientationCombo = orientationCombo;
    }

    public Boolean getDrawLines() {
        return drawLines;
    }

    public void setDrawLines(Boolean drawLines) {
        this.drawLines = drawLines;
    }

    public JCheckBox getDrawLinesCheckBox() {
        return drawLinesCheckBox;
    }

    public void setDrawLinesCheckBox(JCheckBox drawLinesCheckBox) {
        this.drawLinesCheckBox = drawLinesCheckBox;
    }

    public Boolean getDrawShapes() {
        return drawShapes;
    }

    public void setDrawShapes(Boolean drawShapes) {
        this.drawShapes = drawShapes;
    }

    public JCheckBox getDrawShapesCheckBox() {
        return drawShapesCheckBox;
    }

    public void setDrawShapesCheckBox(JCheckBox drawShapesCheckBox) {
        this.drawShapesCheckBox = drawShapesCheckBox;
    }

    /**
     * Allow the user to modify the plot orientation if this is an editor for a
     * <tt>CategoryPlot</tt> or a <tt>XYPlot</tt>.
     */
    public void attemptOrientationSelection() {

        int index = this.orientationCombo.getSelectedIndex();

        if (index == DefaultPlotEditor.ORIENTATION_VERTICAL) {
            this.plotOrientation = PlotOrientation.VERTICAL;
        } else {
            this.plotOrientation = PlotOrientation.HORIZONTAL;
        }
    }

    /**
     * Allow the user to modify whether or not lines are drawn between data
     * points by <tt>LineAndShapeRenderer</tt>s and
     * <tt>StandardXYItemRenderer</tt>s.
     */
    public void attemptDrawLinesSelection() {
        this.drawLines = this.drawLinesCheckBox.isSelected();
    }

    /**
     * Allow the user to modify whether or not shapes are drawn at data points
     * by <tt>LineAndShapeRenderer</tt>s and <tt>StandardXYItemRenderer</tt>s.
     */
    public void attemptDrawShapesSelection() {
        this.drawShapes = this.drawShapesCheckBox.isSelected();
    }
}