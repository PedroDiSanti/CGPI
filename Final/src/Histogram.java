public class Histogram
{
    float sum = 0;
    private byte[][] imageGray;
    private int[] histogram = new int[256];
    private int[] histogramC = new int[265];
    private int[] histogramS = new int[256];
    private float[] histogramN = new float[256];
    private int[] histogramGrayLevels = new int[256];
    
    public void scaleTo()
    {
        float min=histogramN[0];
        float max=histogramN[0];
                
        for (int i = 0; i < histogramN.length; i++) {
            if( histogramN[i] >max )
                max = histogramN[i];
            if( histogramN[i] < min )
                min = histogramN[i];
        }       
        
        float r=max-min;
        for (int i = 0; i < histogramN.length; i++) {
            getHistogramGrayLevels()[i] = (int) (((histogramN[i] - min)/r)*255);
        }
    }

    public void calcHist()
    {
        for (int i = 0; i < imageGray.length; i++) {
            for (int j = 0; j < imageGray[0].length; j++) {
                histogram[(imageGray[i][j] & 0xff)]++;
            }
        }
    }

    public void calcCumulativeHist()
    {
        for(int i = 1; i < histogram.length; i++) {
            histogramC[0] = histogram[0]; 
            histogramC[i] = histogramC[i-1] + histogram[i]; 
        }
    }

    public void calcHistNFromC()
    {
        for (int i = 0; i < histogramN.length; i++) {
           histogramN[i] = histogramC[i] * ((float)1.0 /(imageGray.length * imageGray[0].length));    
        }   
    }

    public void calcEqualHist()
    {
        for(int i = 0; i < histogramS.length; i++) {
            histogramS[i] = Math.round(histogramS.length * histogramN[i]);
            
            if(histogramS[i] >= 256)
                histogramS[i] = 255;
        }
    }

    public void calcHistN()
    {
        sum=0;
        for (int i = 0; i < histogram.length; i++) {
            sum += histogram[i];
        }

        sum = (float) 1.0/(imageGray.length * imageGray[0].length);
        for (int i = 0; i < histogram.length; i++) {
           histogramN[i] = histogram[i] * sum;    
        }
    }

    public byte[][] getImageGray() {
        return imageGray;
    }

    public void setImageGray(byte[][] imageGray) {
        this.imageGray = imageGray;
    }

    public int[] getHistogram() {
        return histogram;
    }

    public void setHistogram(int[] histogram) {
        this.histogram = histogram;
    }

    public float[] getHistogramN() {
        return histogramN;
    }

    public void setHistogramN(float[] histogramN) { this.histogramN = histogramN; }

    public int[] getHistogramGrayLevels() {
        return histogramGrayLevels;
    }

    public void setHistogramGrayLevels(int[] histogramGrayLevels) {
        this.histogramGrayLevels = histogramGrayLevels;
    }
    
    public int[] getHistogramC() {
        return histogramC;
    }

    public void setHistogramC(int[] histogramC) {
        this.histogramC = histogramC;
    }

    public int[] getHistogramS() {
        return histogramS;
    }

    public void setHistogramS(int[] histogramS) {
        this.histogramS = histogramS;
    }
    
}
