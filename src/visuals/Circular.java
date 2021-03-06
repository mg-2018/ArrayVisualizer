package visuals;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Polygon;

import main.ArrayVisualizer;
import utils.Highlights;
import utils.Renderer;

/*
 * 
MIT License

Copyright (c) 2019 w0rthy
Copyright (c) 2020 aphitorite

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 *
 */

final public class Circular extends Visual {
    final private static double CIRC_HEIGHT_RATIO = 45 / 18d;
    final private static double CIRC_WIDTH_RATIO = 80 / 18d;
    
    final private static double PNT_HEIGHT_RATIO = 45 / 19d;
    final private static double PNT_WIDTH_RATIO = 80 / 19d;
    
    public Circular(ArrayVisualizer ArrayVisualizer) {
        super(ArrayVisualizer);
    }
    
    // The reason we use cosine with height (expressed in terms of y) and sine with width (expressed in terms of x) is because our circles are rotated 90 degrees.
    // After that rotation, sine is on the x-axis and cosine is on the y-axis.
    
    // If we we use sine with height and cosine with width, the sorts would start from the right side of the circle,
    // just like the unit circle from trigonometry.
    
    private static double getSinOfDegrees(double d, int halfCirc) {
        return Math.sin((d * Math.PI) / halfCirc);
    }
    
    private static double getCosOfDegrees(double d, int halfCirc) {
        return Math.cos((d * Math.PI) / halfCirc);
    }
    
    @Override
    public void drawVisual(int[] array, ArrayVisualizer ArrayVisualizer, Renderer Renderer, Highlights Highlights) {
        for(int i = 0; i < ArrayVisualizer.getCurrentLength(); i++){
            if(i < Highlights.getFancyFinishPosition()) {
                this.mainRender.setColor(Color.getHSBColor((1f/3f), 1f, 0.8f));
            }
            else if(!ArrayVisualizer.colorEnabled() && (ArrayVisualizer.spiralEnabled() || ArrayVisualizer.distanceEnabled() || ArrayVisualizer.pixelsEnabled())) {
                this.mainRender.setColor(Color.WHITE);
            }
            else this.mainRender.setColor(getIntColor(array[i], ArrayVisualizer.getCurrentLength()));
            //else this.mainRender.setColor(getIntColor(ArrayVisualizer.getShadowArray()[array[i]], ArrayVisualizer.getCurrentLength()));
            
            if(Highlights.fancyFinishActive()) {
                drawFancyFinish(ArrayVisualizer.getLogBaseTwoOfLength(), i, Highlights.getFancyFinishPosition(), this.mainRender, ArrayVisualizer.rainbowEnabled(), ArrayVisualizer.colorEnabled());
            }
            else {
                if(ArrayVisualizer.pointerActive()) {
                    if(Highlights.containsPosition(i)) {
                        if(ArrayVisualizer.analysisEnabled()) {
                            this.extraRender.setColor(Color.GRAY);
                        }
                        else {
                            this.extraRender.setColor(Color.WHITE);
                        }

                        int pointerSize = 10;
                        if(ArrayVisualizer.getCurrentLength() < 2048) {
                            int multiple = ArrayVisualizer.getCurrentLength();
                            do {
                                pointerSize += 5;
                            } while((multiple *= 2) < 2048);
                        }
                        
                        //Create new Polygon for the pointer
                        Polygon pointer = new Polygon();

                        //Calculate radians
                        double degrees = 360 * ((double) i / ArrayVisualizer.getCurrentLength());
                        double radians = Math.toRadians(degrees);
                        
                        int pointerWidthRatio  = ArrayVisualizer.windowHalfWidth()  + (int) (Circular.getSinOfDegrees(((i + (i + 1)) / 2d), ArrayVisualizer.halfCircle()) * ((ArrayVisualizer.currentWidth() - 64) / PNT_WIDTH_RATIO));
                        int pointerHeightRatio = ArrayVisualizer.windowHalfHeight() - (int) (Circular.getCosOfDegrees(((i + (i + 1)) / 2d), ArrayVisualizer.halfCircle()) * ((ArrayVisualizer.windowHeight() - 96) / PNT_HEIGHT_RATIO));
                        
                        //First step: draw a triangle
                        int[] pointerXValues = {pointerWidthRatio - pointerSize,
                                                pointerWidthRatio,
                                                pointerWidthRatio + pointerSize};
                        
                        int[] pointerYValues = {pointerHeightRatio - (10 + (pointerSize - 10)),
                                                pointerHeightRatio + 10,
                                                pointerHeightRatio - (10 + (pointerSize - 10))};
                        
                        //Second step: rotate triangle (https://en.wikipedia.org/wiki/Rotation_matrix)
                        for(int j = 0; j < pointerXValues.length; j++) {
                            double x = pointerXValues[j] - pointerWidthRatio;
                            double y = pointerYValues[j] - pointerHeightRatio;
                            
                            pointerXValues[j] = (int) (pointerWidthRatio
                                                    + x*Math.cos(radians)
                                                    - y*Math.sin(radians));
                            pointerYValues[j] = (int) (pointerHeightRatio
                                                    + x*Math.sin(radians)
                                                    + y*Math.cos(radians));
                        }
                        
                        for(int j = 0; j < pointerXValues.length; j++) {
                            pointer.addPoint(pointerXValues[j], pointerYValues[j]);
                        }
                                                
                        this.extraRender.fillPolygon(pointer);                        
                    }
                }
                else if(ArrayVisualizer.getCurrentLength() != 2){
                    colorMarkedBars(ArrayVisualizer.getLogBaseTwoOfLength(), i, Highlights, this.mainRender, ArrayVisualizer.rainbowEnabled(), ArrayVisualizer.colorEnabled(), ArrayVisualizer.analysisEnabled());
                }
            }
                
            if(ArrayVisualizer.distanceEnabled()) {
                //TODO: Rewrite this abomination
                //double len = ((ArrayVisualizer.getCurrentLength() / 2d) - Math.min(Math.min(Math.abs(i - array[i]), Math.abs(i - array[i] + ArrayVisualizer.getCurrentLength())), Math.abs(i - array[i] - ArrayVisualizer.getCurrentLength()))) / (ArrayVisualizer.getCurrentLength() / 2d);

                // Both formulas written by aphitorite (https://github.com/aphitorite)
                // Original triangle wave formula, rewritten
                // double len = 2 * Math.abs(Math.abs(array[i] - i) - ArrayVisualizer.getCurrentLength() * 0.5) / ArrayVisualizer.getCurrentLength();
                
                // Cosine formula
                double len = (1 + Math.cos((Math.PI * (array[i] - i)) / (ArrayVisualizer.getCurrentLength() * 0.5))) * 0.5;
                
                if(ArrayVisualizer.pixelsEnabled()) {
                    int linkedpixX = ArrayVisualizer.windowHalfWidth()  + (int) (Circular.getSinOfDegrees(i, ArrayVisualizer.halfCircle()) * ((ArrayVisualizer.windowWidth()  - 64) / CIRC_WIDTH_RATIO  * len)) + Renderer.getDotWidth()  / 2;
                    int linkedpixY = ArrayVisualizer.windowHalfHeight() - (int) (Circular.getCosOfDegrees(i, ArrayVisualizer.halfCircle()) * ((ArrayVisualizer.windowHeight() - 96) / CIRC_HEIGHT_RATIO * len)) + Renderer.getDotHeight() / 2;

                    if(ArrayVisualizer.linesEnabled()) {
                        if(i > 0) {
                            if(Highlights.fancyFinishActive()) {
                                if(i < Highlights.getFancyFinishPosition()) {
                                    lineFancy(this.mainRender, ArrayVisualizer.currentWidth());
                                }
                                else {
                                    lineClear(this.mainRender, ArrayVisualizer.colorEnabled(), array, i, ArrayVisualizer.getCurrentLength(), ArrayVisualizer.currentWidth());
                                }

                                drawFancyFinishLine(ArrayVisualizer.getLogBaseTwoOfLength(), i, Highlights.getFancyFinishPosition(), this.mainRender, ArrayVisualizer.currentWidth(), ArrayVisualizer.colorEnabled());
                            }
                            else {
                                if(Highlights.containsPosition(i)) {
                                    lineMark(this.mainRender, ArrayVisualizer.currentWidth(), ArrayVisualizer.colorEnabled(), ArrayVisualizer.analysisEnabled());
                                }
                                else lineClear(this.mainRender, ArrayVisualizer.colorEnabled(), array, i, ArrayVisualizer.getCurrentLength(), ArrayVisualizer.currentWidth());
                            }
                            this.mainRender.drawLine(linkedpixX, linkedpixY, Renderer.getLineX(), Renderer.getLineY());
                        }
                        Renderer.setLineX(linkedpixX);
                        Renderer.setLineY(linkedpixY);
                    }
                    else {
                        boolean drawRect = false;
                        if(Highlights.containsPosition(i)) {
                            setRectColor(this.extraRender, ArrayVisualizer.colorEnabled(), ArrayVisualizer.analysisEnabled());
                            drawRect = true;
                        }

                        if(drawRect) {
                            this.extraRender.setStroke(ArrayVisualizer.getThickStroke());
                            if(Highlights.fancyFinishActive()) {
                                this.extraRender.fillRect((linkedpixX - Renderer.getDotWidth() / 2) - 10, (linkedpixY - Renderer.getDotHeight() / 2) - 10, Renderer.getDotWidth() + 20, Renderer.getDotHeight() + 20);
                            }
                            else {
                                this.extraRender.drawRect((linkedpixX - Renderer.getDotWidth() / 2) - 10, (linkedpixY - Renderer.getDotHeight() / 2) - 10, Renderer.getDotWidth() + 20, Renderer.getDotHeight() + 20);
                            }
                            this.extraRender.setStroke(new BasicStroke(3f * (ArrayVisualizer.currentWidth() / 1280f)));
                        }
                        this.mainRender.fillRect(linkedpixX - Renderer.getDotWidth() / 2, linkedpixY - Renderer.getDotHeight() / 2, Renderer.getDotWidth(), Renderer.getDotHeight());
                    }
                }
                else {
                    Polygon p = new Polygon();
                    
                    p.addPoint(ArrayVisualizer.windowHalfWidth(),
                               ArrayVisualizer.windowHalfHeight());
                    
                    p.addPoint(ArrayVisualizer.windowHalfWidth()  + (int) (Circular.getSinOfDegrees(i, ArrayVisualizer.halfCircle()) * (((ArrayVisualizer.currentWidth() - 64)  / CIRC_WIDTH_RATIO)  * len)),
                               ArrayVisualizer.windowHalfHeight() - (int) (Circular.getCosOfDegrees(i, ArrayVisualizer.halfCircle()) * (((ArrayVisualizer.currentHeight() - 96) / CIRC_HEIGHT_RATIO) * len)));
                    
                    p.addPoint(ArrayVisualizer.windowHalfWidth()  + (int) (Circular.getSinOfDegrees(i + 1, ArrayVisualizer.halfCircle()) * (((ArrayVisualizer.currentWidth()  - 64) / CIRC_WIDTH_RATIO)  * len)),
                               ArrayVisualizer.windowHalfHeight() - (int) (Circular.getCosOfDegrees(i + 1, ArrayVisualizer.halfCircle()) * (((ArrayVisualizer.currentHeight() - 96) / CIRC_HEIGHT_RATIO) * len)));
                    
                    this.mainRender.fillPolygon(p);
                }
            }
            else if(ArrayVisualizer.spiralEnabled()) {
                if(ArrayVisualizer.pixelsEnabled()) {
                    if(ArrayVisualizer.linesEnabled()) {
                        if(i > 0) {
                            if(Highlights.fancyFinishActive()) {
                                if(i < Highlights.getFancyFinishPosition()) {
                                    lineFancy(this.mainRender, ArrayVisualizer.currentWidth());
                                }
                                else lineClear(this.mainRender, ArrayVisualizer.colorEnabled(), array, i, ArrayVisualizer.getCurrentLength(), ArrayVisualizer.currentWidth());

                                drawFancyFinishLine(ArrayVisualizer.getLogBaseTwoOfLength(), i, Highlights.getFancyFinishPosition(), this.mainRender, ArrayVisualizer.currentWidth(), ArrayVisualizer.colorEnabled());
                            }
                            else {
                                if(Highlights.containsPosition(i)) {
                                    lineMark(this.mainRender, ArrayVisualizer.currentWidth(), ArrayVisualizer.colorEnabled(), ArrayVisualizer.analysisEnabled());
                                }
                                else lineClear(this.mainRender, ArrayVisualizer.colorEnabled(), array, i, ArrayVisualizer.getCurrentLength(), ArrayVisualizer.currentWidth());
                            }
                            this.mainRender.drawLine(ArrayVisualizer.windowHalfWidth()  + (int) (Circular.getSinOfDegrees(i, ArrayVisualizer.halfCircle()) * ((((ArrayVisualizer.windowWidth()  - 64) / 3.0) * array[i]) / ArrayVisualizer.getCurrentLength())), 
                                                ArrayVisualizer.windowHalfHeight() - (int) (Circular.getCosOfDegrees(i, ArrayVisualizer.halfCircle()) * ((((ArrayVisualizer.windowHeight() - 96) / 2.0) * array[i]) / ArrayVisualizer.getCurrentLength())), 
                                                Renderer.getLineX(),
                                                Renderer.getLineY());
                        }
                        Renderer.setLineX(ArrayVisualizer.windowHalfWidth()  + (int) (Circular.getSinOfDegrees(i, ArrayVisualizer.halfCircle()) * ((((ArrayVisualizer.windowWidth()  - 64) / 3.0) * array[i]) / ArrayVisualizer.getCurrentLength())));
                        Renderer.setLineY(ArrayVisualizer.windowHalfHeight() - (int) (Circular.getCosOfDegrees(i, ArrayVisualizer.halfCircle()) * ((((ArrayVisualizer.windowHeight() - 96) / 2.0) * array[i]) / ArrayVisualizer.getCurrentLength())));      
                    }
                    else {
                        boolean drawRect = false;
                        if(Highlights.containsPosition(i)) {
                            setRectColor(this.extraRender, ArrayVisualizer.colorEnabled(), ArrayVisualizer.analysisEnabled());
                            drawRect = true;
                        }

                        int rectx = ArrayVisualizer.windowHalfWidth()  + (int) (Circular.getSinOfDegrees(i, ArrayVisualizer.halfCircle()) * (((((ArrayVisualizer.windowWidth()  - 64) / 3.0) * array[i]) / ArrayVisualizer.getCurrentLength())));
                        int recty = ArrayVisualizer.windowHalfHeight() - (int) (Circular.getCosOfDegrees(i, ArrayVisualizer.halfCircle()) * (((((ArrayVisualizer.windowHeight() - 96) / 2.0) * array[i]) / ArrayVisualizer.getCurrentLength())));

                        this.mainRender.fillRect(rectx, recty, Renderer.getDotWidth(), Renderer.getDotHeight());

                        if(drawRect) {
                            this.extraRender.setStroke(ArrayVisualizer.getThickStroke());
                            if(Highlights.fancyFinishActive()) {
                                this.extraRender.fillRect(rectx - 10, recty - 10, Renderer.getDotWidth() + 20, Renderer.getDotHeight() + 20);
                            }
                            else {
                                this.extraRender.drawRect(rectx - 10, recty - 10, Renderer.getDotWidth() + 20, Renderer.getDotHeight() + 20);
                            }
                            this.extraRender.setStroke(new BasicStroke(3f * (ArrayVisualizer.currentWidth() / 1280f)));
                        }
                    }
                }
                else {
                    if(Highlights.containsPosition(i)) {
                        markBar(this.mainRender, ArrayVisualizer.colorEnabled(), ArrayVisualizer.rainbowEnabled(), ArrayVisualizer.analysisEnabled());
                    }

                    Polygon p = new Polygon();
                    
                    p.addPoint(ArrayVisualizer.windowHalfWidth(),
                               ArrayVisualizer.windowHalfHeight());
                    
                    p.addPoint(ArrayVisualizer.windowHalfWidth()  + (int) (Circular.getSinOfDegrees(i, ArrayVisualizer.halfCircle()) * ((((ArrayVisualizer.windowWidth()  - 64) / 3.0) * array[i]) / ArrayVisualizer.getCurrentLength())),
                               ArrayVisualizer.windowHalfHeight() - (int) (Circular.getCosOfDegrees(i, ArrayVisualizer.halfCircle()) * ((((ArrayVisualizer.windowHeight() - 96) / 2.0) * array[i]) / ArrayVisualizer.getCurrentLength())));
                    
                    p.addPoint(ArrayVisualizer.windowHalfWidth()  + (int) (Circular.getSinOfDegrees(i + 1, ArrayVisualizer.halfCircle()) * ((((ArrayVisualizer.windowWidth()  - 64) / 3.0) * array[Math.min(i + 1, ArrayVisualizer.getCurrentLength() - 1)]) / ArrayVisualizer.getCurrentLength())),
                               ArrayVisualizer.windowHalfHeight() - (int) (Circular.getCosOfDegrees(i + 1, ArrayVisualizer.halfCircle()) * ((((ArrayVisualizer.windowHeight() - 96) / 2.0) * array[Math.min(i + 1, ArrayVisualizer.getCurrentLength() - 1)]) / ArrayVisualizer.getCurrentLength())));
                    
                    this.mainRender.fillPolygon(p);
                }
            }
            else {
                Polygon p = new Polygon();
                
                p.addPoint(ArrayVisualizer.windowHalfWidth(),
                           ArrayVisualizer.windowHalfHeight());
                
                p.addPoint(ArrayVisualizer.windowHalfWidth()  + (int) (Circular.getSinOfDegrees(i, ArrayVisualizer.halfCircle()) * ((ArrayVisualizer.windowWidth()  - 64) / CIRC_WIDTH_RATIO)),
                           ArrayVisualizer.windowHalfHeight() - (int) (Circular.getCosOfDegrees(i, ArrayVisualizer.halfCircle()) * ((ArrayVisualizer.windowHeight() - 96) / CIRC_HEIGHT_RATIO)));
                
                p.addPoint(ArrayVisualizer.windowHalfWidth()  + (int) (Circular.getSinOfDegrees(i + 1, ArrayVisualizer.halfCircle()) * ((ArrayVisualizer.windowWidth()  - 64) / CIRC_WIDTH_RATIO)),
                           ArrayVisualizer.windowHalfHeight() - (int) (Circular.getCosOfDegrees(i + 1, ArrayVisualizer.halfCircle()) * ((ArrayVisualizer.windowHeight() - 96) / CIRC_HEIGHT_RATIO)));
                
                this.mainRender.fillPolygon(p);
            }
        }
    }
}