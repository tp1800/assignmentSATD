package org.jfree.chart.swing.editor;

import org.jfree.chart.api.RectangleInsets;
import org.jfree.chart.plot.PlotOrientation;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class DefaultPlotEditorProduct implements Serializable {
    private DefaultPlotEditorProductProduct defaultPlotEditorProductProduct = new DefaultPlotEditorProductProduct();
    /**
     * The paint (color) used to fill the background of the plot.
     */
    private PaintSample backgroundPaintSample;
    /**
     * The stroke used to draw the outline of the plot.
     */
    private StrokeSample outlineStrokeSample;
    /**
     * The paint (color) used to draw the outline of the plot.
     */
    private PaintSample outlinePaintSample;
    /**
     * An array of stroke samples to choose from.
     */
    private StrokeSample[] availableStrokeSamples;
    /**
     * The insets for the plot.
     */
    private RectangleInsets plotInsets;

    public PaintSample getBackgroundPaintSample() {
        return backgroundPaintSample;
    }

    public void setBackgroundPaintSample(PaintSample backgroundPaintSample) {
        this.backgroundPaintSample = backgroundPaintSample;
    }

    public StrokeSample getOutlineStrokeSample() {
        return outlineStrokeSample;
    }

    public void setOutlineStrokeSample(StrokeSample outlineStrokeSample) {
        this.outlineStrokeSample = outlineStrokeSample;
    }

    public PaintSample getOutlinePaintSample() {
        return outlinePaintSample;
    }

    public void setOutlinePaintSample(PaintSample outlinePaintSample) {
        this.outlinePaintSample = outlinePaintSample;
    }

    public StrokeSample[] getAvailableStrokeSamples() {
        return availableStrokeSamples;
    }

    public void setAvailableStrokeSamples(StrokeSample[] availableStrokeSamples) {
        this.availableStrokeSamples = availableStrokeSamples;
    }

    public void setPlotInsets(RectangleInsets plotInsets) {
        this.plotInsets = plotInsets;
    }

    public PlotOrientation getPlotOrientation() {
        return defaultPlotEditorProductProduct.getPlotOrientation();
    }

    public void setPlotOrientation(PlotOrientation plotOrientation) {
        defaultPlotEditorProductProduct.setPlotOrientation(plotOrientation);
    }

    public JComboBox getOrientationCombo() {
        return defaultPlotEditorProductProduct.getOrientationCombo();
    }

    public void setOrientationCombo(JComboBox orientationCombo) {
        defaultPlotEditorProductProduct.setOrientationCombo(orientationCombo);
    }

    public Boolean getDrawLines() {
        return defaultPlotEditorProductProduct.getDrawLines();
    }

    public void setDrawLines(Boolean drawLines) {
        defaultPlotEditorProductProduct.setDrawLines(drawLines);
    }

    public JCheckBox getDrawLinesCheckBox() {
        return defaultPlotEditorProductProduct.getDrawLinesCheckBox();
    }

    public void setDrawLinesCheckBox(JCheckBox drawLinesCheckBox) {
        defaultPlotEditorProductProduct.setDrawLinesCheckBox(drawLinesCheckBox);
    }

    public Boolean getDrawShapes() {
        return defaultPlotEditorProductProduct.getDrawShapes();
    }

    public void setDrawShapes(Boolean drawShapes) {
        defaultPlotEditorProductProduct.setDrawShapes(drawShapes);
    }

    public JCheckBox getDrawShapesCheckBox() {
        return defaultPlotEditorProductProduct.getDrawShapesCheckBox();
    }

    public void setDrawShapesCheckBox(JCheckBox drawShapesCheckBox) {
        defaultPlotEditorProductProduct.setDrawShapesCheckBox(drawShapesCheckBox);
    }

    /**
     * Allow the user to change the background paint.
     */
    public void attemptBackgroundPaintSelection(DefaultPlotEditor defaultPlotEditor) {
        Color c;
        c = JColorChooser.showDialog(defaultPlotEditor, DefaultPlotEditor.localizationResources.getString(
                "Background_Color"), Color.BLUE);
        if (c != null) {
            this.backgroundPaintSample.setPaint(c);
        }
    }

    /**
     * Allow the user to change the outline stroke.
     */
    public void attemptOutlineStrokeSelection(DefaultPlotEditor defaultPlotEditor) {
        StrokeChooserPanel panel = new StrokeChooserPanel(
                this.outlineStrokeSample, this.availableStrokeSamples);
        int result = JOptionPane.showConfirmDialog(defaultPlotEditor, panel,
                DefaultPlotEditor.localizationResources.getString("Stroke_Selection"),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            this.outlineStrokeSample.setStroke(panel.getSelectedStroke());
        }
    }

    /**
     * Allow the user to change the outline paint.  We use JColorChooser, so
     * the user can only choose colors (a subset of all possible paints).
     */
    public void attemptOutlinePaintSelection(DefaultPlotEditor defaultPlotEditor) {
        Color c;
        c = JColorChooser.showDialog(defaultPlotEditor, DefaultPlotEditor.localizationResources.getString(
                "Outline_Color"), Color.BLUE);
        if (c != null) {
            this.outlinePaintSample.setPaint(c);
        }
    }

    /**
     * Returns the current plot insets.
     *
     * @return The current plot insets.
     */
    public RectangleInsets getPlotInsets() {
        if (this.plotInsets == null) {
            this.plotInsets = new RectangleInsets(0.0, 0.0, 0.0, 0.0);
        }
        return this.plotInsets;
    }

    /**
     * Allow the user to modify the plot orientation if this is an editor for a
     * <tt>CategoryPlot</tt> or a <tt>XYPlot</tt>.
     */
    public void attemptOrientationSelection() {
        defaultPlotEditorProductProduct.attemptOrientationSelection();
    }

    /**
     * Allow the user to modify whether or not lines are drawn between data
     * points by <tt>LineAndShapeRenderer</tt>s and
     * <tt>StandardXYItemRenderer</tt>s.
     */
    public void attemptDrawLinesSelection() {
        defaultPlotEditorProductProduct.attemptDrawLinesSelection();
    }

    /**
     * Allow the user to modify whether or not shapes are drawn at data points
     * by <tt>LineAndShapeRenderer</tt>s and <tt>StandardXYItemRenderer</tt>s.
     */
    public void attemptDrawShapesSelection() {
        defaultPlotEditorProductProduct.attemptDrawShapesSelection();
    }
}