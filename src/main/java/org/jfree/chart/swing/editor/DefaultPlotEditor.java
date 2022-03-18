/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2022, by David Gilbert and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.]
 *
 * ----------------------
 * DefaultPlotEditor.java
 * ----------------------
 * (C) Copyright 2005-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Andrzej Porebski;
 *                   Arnaud Lelievre;
 *                   Daniel Gredler;
 *
 */

package org.jfree.chart.swing.editor;

import org.jfree.chart.api.RectangleInsets;
import org.jfree.chart.axis.Axis;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

/**
 * A panel for editing the properties of a {@link Plot}.
 */
class DefaultPlotEditor extends JPanel implements ActionListener {
    private DefaultPlotEditorProduct defaultPlotEditorProduct = new DefaultPlotEditorProduct();

    /**
     * Orientation constants.
     */
    private final static String[] orientationNames = {"Vertical", "Horizontal"};
    public final static int ORIENTATION_VERTICAL = 0;
    private final static int ORIENTATION_HORIZONTAL = 1;

    /**
     * A panel used to display/edit the properties of the domain axis (if any).
     */
    private DefaultAxisEditor domainAxisPropertyPanel;

    /**
     * A panel used to display/edit the properties of the range axis (if any).
     */
    private DefaultAxisEditor rangeAxisPropertyPanel;

    /**
     * The resourceBundle for the localization.
     */
    public static ResourceBundle localizationResources
            = ResourceBundle.getBundle("org.jfree.chart.editor.LocalizationBundle");

    /**
     * Standard constructor - constructs a panel for editing the properties of
     * the specified plot.
     * <p>
     * In designing the panel, we need to be aware that subclasses of Plot will
     * need to implement subclasses of PlotPropertyEditPanel - so we need to
     * leave one or two 'slots' where the subclasses can extend the user
     * interface.
     *
     * @param plot the plot, which should be changed.
     */
    public DefaultPlotEditor(Plot plot) {
        JPanel panel = createPlotPanel(plot);
        add(panel);
    }

    /**
     * Creates a panel for the plot.
     *
     * @param plot the plot.
     * @return The panel.
     */
    protected JPanel createPlotPanel(Plot plot) {
        defaultPlotEditorProduct.setPlotInsets(plot.getInsets());
        defaultPlotEditorProduct.setBackgroundPaintSample(new PaintSample(plot.getBackgroundPaint()));
        defaultPlotEditorProduct.setOutlineStrokeSample(new StrokeSample(plot.getOutlineStroke()));
        defaultPlotEditorProduct.setOutlinePaintSample(new PaintSample(plot.getOutlinePaint()));
        if (plot instanceof CategoryPlot) {
            defaultPlotEditorProduct.setPlotOrientation(((CategoryPlot) plot).getOrientation());
        } else if (plot instanceof XYPlot) {
            defaultPlotEditorProduct.setPlotOrientation(((XYPlot) plot).getOrientation());
        }
        if (plot instanceof CategoryPlot) {
            CategoryItemRenderer renderer = ((CategoryPlot) plot).getRenderer();
            if (renderer instanceof LineAndShapeRenderer) {
                LineAndShapeRenderer r = (LineAndShapeRenderer) renderer;
                defaultPlotEditorProduct.setDrawLines(r.getDefaultLinesVisible());
                defaultPlotEditorProduct.setDrawShapes(r.getDefaultShapesVisible());
            }
        } else if (plot instanceof XYPlot) {
            XYItemRenderer renderer = ((XYPlot) plot).getRenderer();
            if (renderer instanceof StandardXYItemRenderer) {
                StandardXYItemRenderer r = (StandardXYItemRenderer) renderer;
                defaultPlotEditorProduct.setDrawLines(r.getPlotLines());
                defaultPlotEditorProduct.setDrawShapes(r.getBaseShapesVisible());
            }
        }

        setLayout(new BorderLayout());

        defaultPlotEditorProduct.setAvailableStrokeSamples(new StrokeSample[4]);
        this.defaultPlotEditorProduct.getAvailableStrokeSamples()[0] = new StrokeSample(null);
        this.defaultPlotEditorProduct.getAvailableStrokeSamples()[1] = new StrokeSample(
                new BasicStroke(1.0f));
        this.defaultPlotEditorProduct.getAvailableStrokeSamples()[2] = new StrokeSample(
                new BasicStroke(2.0f));
        this.defaultPlotEditorProduct.getAvailableStrokeSamples()[3] = new StrokeSample(
                new BasicStroke(3.0f));

        // create a panel for the settings...
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), plot.getPlotType()
                        + localizationResources.getString(":")));

        JPanel general = new JPanel(new BorderLayout());
        general.setBorder(BorderFactory.createTitledBorder(
                localizationResources.getString("General")));

        JPanel interior = new JPanel(new LCBLayout(7));
        interior.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

//        interior.add(new JLabel(localizationResources.getString("Insets")));
//        JButton button = new JButton(
//            localizationResources.getString("Edit...")
//        );
//        button.setActionCommand("Insets");
//        button.addActionListener(this);
//
//        this.insetsTextField = new InsetsTextField(this.plotInsets);
//        this.insetsTextField.setEnabled(false);
//        interior.add(this.insetsTextField);
//        interior.add(button);

        interior.add(new JLabel(localizationResources.getString(
                "Outline_stroke")));
        JButton button = new JButton(localizationResources.getString(
                "Select..."));
        button.setActionCommand("OutlineStroke");
        button.addActionListener(this);
        interior.add(this.defaultPlotEditorProduct.getOutlineStrokeSample());
        interior.add(button);

        interior.add(new JLabel(localizationResources.getString(
                "Outline_Paint")));
        button = new JButton(localizationResources.getString("Select..."));
        button.setActionCommand("OutlinePaint");
        button.addActionListener(this);
        interior.add(this.defaultPlotEditorProduct.getOutlinePaintSample());
        interior.add(button);

        interior.add(new JLabel(localizationResources.getString(
                "Background_paint")));
        button = new JButton(localizationResources.getString("Select..."));
        button.setActionCommand("BackgroundPaint");
        button.addActionListener(this);
        interior.add(this.defaultPlotEditorProduct.getBackgroundPaintSample());
        interior.add(button);

        if (this.defaultPlotEditorProduct.getPlotOrientation() != null) {
            boolean isVertical = this.defaultPlotEditorProduct.getPlotOrientation().equals(
                    PlotOrientation.VERTICAL);
            int index = isVertical ? ORIENTATION_VERTICAL
                    : ORIENTATION_HORIZONTAL;
            interior.add(new JLabel(localizationResources.getString(
                    "Orientation")));
            defaultPlotEditorProduct.setOrientationCombo(new JComboBox(orientationNames));
            this.defaultPlotEditorProduct.getOrientationCombo().setSelectedIndex(index);
            this.defaultPlotEditorProduct.getOrientationCombo().setActionCommand("Orientation");
            this.defaultPlotEditorProduct.getOrientationCombo().addActionListener(this);
            interior.add(new JPanel());
            interior.add(this.defaultPlotEditorProduct.getOrientationCombo());
        }

        if (this.defaultPlotEditorProduct.getDrawLines() != null) {
            interior.add(new JLabel(localizationResources.getString(
                    "Draw_lines")));
            defaultPlotEditorProduct.setDrawLinesCheckBox(new JCheckBox());
            this.defaultPlotEditorProduct.getDrawLinesCheckBox().setSelected(this.defaultPlotEditorProduct.getDrawLines());
            this.defaultPlotEditorProduct.getDrawLinesCheckBox().setActionCommand("DrawLines");
            this.defaultPlotEditorProduct.getDrawLinesCheckBox().addActionListener(this);
            interior.add(new JPanel());
            interior.add(this.defaultPlotEditorProduct.getDrawLinesCheckBox());
        }

        if (this.defaultPlotEditorProduct.getDrawShapes() != null) {
            interior.add(new JLabel(localizationResources.getString(
                    "Draw_shapes")));
            defaultPlotEditorProduct.setDrawShapesCheckBox(new JCheckBox());
            this.defaultPlotEditorProduct.getDrawShapesCheckBox().setSelected(this.defaultPlotEditorProduct.getDrawShapes());
            this.defaultPlotEditorProduct.getDrawShapesCheckBox().setActionCommand("DrawShapes");
            this.defaultPlotEditorProduct.getDrawShapesCheckBox().addActionListener(this);
            interior.add(new JPanel());
            interior.add(this.defaultPlotEditorProduct.getDrawShapesCheckBox());
        }

        general.add(interior, BorderLayout.NORTH);

        JPanel appearance = new JPanel(new BorderLayout());
        appearance.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        appearance.add(general, BorderLayout.NORTH);

        JTabbedPane tabs = createPlotTabs(plot);
        tabs.add(localizationResources.getString("Appearance"), appearance);
        panel.add(tabs);

        return panel;
    }

    /**
     * Creates a tabbed pane for the plot.
     *
     * @param plot the plot.
     * @return A tabbed pane.
     */
    protected JTabbedPane createPlotTabs(Plot plot) {
        JTabbedPane tabs = new JTabbedPane();
        tabs.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

        Axis domainAxis = null;
        if (plot instanceof CategoryPlot) {
            domainAxis = ((CategoryPlot) plot).getDomainAxis();
        } else if (plot instanceof XYPlot) {
            domainAxis = ((XYPlot) plot).getDomainAxis();
        }
        this.domainAxisPropertyPanel = DefaultAxisEditor.getInstance(
                domainAxis);
        if (this.domainAxisPropertyPanel != null) {
            this.domainAxisPropertyPanel.setBorder(
                    BorderFactory.createEmptyBorder(2, 2, 2, 2));
            tabs.add(localizationResources.getString("Domain_Axis"),
                    this.domainAxisPropertyPanel);
        }

        Axis rangeAxis = null;
        if (plot instanceof CategoryPlot) {
            rangeAxis = ((CategoryPlot) plot).getRangeAxis();
        } else if (plot instanceof XYPlot) {
            rangeAxis = ((XYPlot) plot).getRangeAxis();
        } else if (plot instanceof PolarPlot) {
            rangeAxis = ((PolarPlot) plot).getAxis();
        }

        this.rangeAxisPropertyPanel = DefaultAxisEditor.getInstance(rangeAxis);
        if (this.rangeAxisPropertyPanel != null) {
            this.rangeAxisPropertyPanel.setBorder(
                    BorderFactory.createEmptyBorder(2, 2, 2, 2));
            tabs.add(localizationResources.getString("Range_Axis"),
                    this.rangeAxisPropertyPanel);
        }

        return tabs;
    }

    /**
     * Returns the current plot insets.
     *
     * @return The current plot insets.
     */
    public RectangleInsets getPlotInsets() {
        return defaultPlotEditorProduct.getPlotInsets();
    }

    /**
     * Returns the current background paint.
     *
     * @return The current background paint.
     */
    public Paint getBackgroundPaint() {
        return this.defaultPlotEditorProduct.getBackgroundPaintSample().getPaint();
    }

    /**
     * Returns the current outline stroke.
     *
     * @return The current outline stroke (possibly {@code null}).
     */
    public Stroke getOutlineStroke() {
        return this.defaultPlotEditorProduct.getOutlineStrokeSample().getStroke();
    }

    /**
     * Returns the current outline paint.
     *
     * @return The current outline paint.
     */
    public Paint getOutlinePaint() {
        return this.defaultPlotEditorProduct.getOutlinePaintSample().getPaint();
    }

    /**
     * Returns a reference to the panel for editing the properties of the
     * domain axis.
     *
     * @return A reference to a panel.
     */
    public DefaultAxisEditor getDomainAxisPropertyEditPanel() {
        return this.domainAxisPropertyPanel;
    }

    /**
     * Returns a reference to the panel for editing the properties of the
     * range axis.
     *
     * @return A reference to a panel.
     */
    public DefaultAxisEditor getRangeAxisPropertyEditPanel() {
        return this.rangeAxisPropertyPanel;
    }

    /**
     * Handles user actions generated within the panel.
     *
     * @param event the event
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        if (command.equals("BackgroundPaint")) {
            defaultPlotEditorProduct.attemptBackgroundPaintSelection(this);
        } else if (command.equals("OutlineStroke")) {
            defaultPlotEditorProduct.attemptOutlineStrokeSelection(this);
        } else if (command.equals("OutlinePaint")) {
            defaultPlotEditorProduct.attemptOutlinePaintSelection(this);
        }
//        else if (command.equals("Insets")) {
//            editInsets();
//        }
        else if (command.equals("Orientation")) {
            defaultPlotEditorProduct.attemptOrientationSelection();
        } else if (command.equals("DrawLines")) {
            defaultPlotEditorProduct.attemptDrawLinesSelection();
        } else if (command.equals("DrawShapes")) {
            defaultPlotEditorProduct.attemptDrawShapesSelection();
        }
    }

    //    /**
//     * Allow the user to edit the individual insets' values.
//     */
//    private void editInsets() {
//        InsetsChooserPanel panel = new InsetsChooserPanel(this.plotInsets);
//        int result = JOptionPane.showConfirmDialog(
//            this, panel, localizationResources.getString("Edit_Insets"),
//            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
//        );
//
//        if (result == JOptionPane.OK_OPTION) {
//            this.plotInsets = panel.getInsets();
//            this.insetsTextField.setInsets(this.plotInsets);
//        }
//
//    }
//

    /**
     * Updates the plot properties to match the properties defined on the panel.
     *
     * @param plot The plot.
     */
    public void updatePlotProperties(Plot plot) {

        // set the plot properties...
        plot.setOutlinePaint(getOutlinePaint());
        plot.setOutlineStroke(getOutlineStroke());
        plot.setBackgroundPaint(getBackgroundPaint());
        plot.setInsets(defaultPlotEditorProduct.getPlotInsets());

        // then the axis properties...
        if (this.domainAxisPropertyPanel != null) {
            Axis domainAxis = null;
            if (plot instanceof CategoryPlot) {
                CategoryPlot p = (CategoryPlot) plot;
                domainAxis = p.getDomainAxis();
            } else if (plot instanceof XYPlot) {
                XYPlot p = (XYPlot) plot;
                domainAxis = p.getDomainAxis();
            }
            if (domainAxis != null) {
                this.domainAxisPropertyPanel.setAxisProperties(domainAxis);
            }
        }

        if (this.rangeAxisPropertyPanel != null) {
            Axis rangeAxis = null;
            if (plot instanceof CategoryPlot) {
                CategoryPlot p = (CategoryPlot) plot;
                rangeAxis = p.getRangeAxis();
            } else if (plot instanceof XYPlot) {
                XYPlot p = (XYPlot) plot;
                rangeAxis = p.getRangeAxis();
            } else if (plot instanceof PolarPlot) {
                PolarPlot p = (PolarPlot) plot;
                rangeAxis = p.getAxis();
            }
            if (rangeAxis != null) {
                this.rangeAxisPropertyPanel.setAxisProperties(rangeAxis);
            }
        }

        if (this.defaultPlotEditorProduct.getPlotOrientation() != null) {
            if (plot instanceof CategoryPlot) {
                CategoryPlot p = (CategoryPlot) plot;
                p.setOrientation(this.defaultPlotEditorProduct.getPlotOrientation());
            } else if (plot instanceof XYPlot) {
                XYPlot p = (XYPlot) plot;
                p.setOrientation(this.defaultPlotEditorProduct.getPlotOrientation());
            }
        }

        if (this.defaultPlotEditorProduct.getDrawLines() != null) {
            if (plot instanceof CategoryPlot) {
                CategoryPlot p = (CategoryPlot) plot;
                CategoryItemRenderer r = p.getRenderer();
                if (r instanceof LineAndShapeRenderer) {
                    ((LineAndShapeRenderer) r).setDefaultLinesVisible(this.defaultPlotEditorProduct.getDrawLines());
                }
            } else if (plot instanceof XYPlot) {
                XYPlot p = (XYPlot) plot;
                XYItemRenderer r = p.getRenderer();
                if (r instanceof StandardXYItemRenderer) {
                    ((StandardXYItemRenderer) r).setPlotLines(this.defaultPlotEditorProduct.getDrawLines());
                }
            }
        }

        if (this.defaultPlotEditorProduct.getDrawShapes() != null) {
            if (plot instanceof CategoryPlot) {
                CategoryPlot p = (CategoryPlot) plot;
                CategoryItemRenderer r = p.getRenderer();
                if (r instanceof LineAndShapeRenderer) {
                    ((LineAndShapeRenderer) r).setDefaultShapesVisible(this.defaultPlotEditorProduct.getDrawShapes());
                }
            } else if (plot instanceof XYPlot) {
                XYPlot p = (XYPlot) plot;
                XYItemRenderer r = p.getRenderer();
                if (r instanceof StandardXYItemRenderer) {
                    ((StandardXYItemRenderer) r).setBaseShapesVisible(
                            this.defaultPlotEditorProduct.getDrawShapes());
                }
            }
        }

    }

}
