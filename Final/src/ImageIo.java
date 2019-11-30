import java.io.File;
import java.awt.Font;
import java.awt.Color;
import java.awt.Point;
import java.io.IOException;
import java.awt.Graphics2D;
import java.awt.Transparency;
import javax.imageio.ImageIO;
import java.awt.image.Raster;
import java.awt.color.ColorSpace;
import java.awt.image.DataBuffer;
import java.awt.image.SampleModel;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.awt.image.DataBufferByte;
import java.awt.image.ComponentColorModel;

public class ImageIo
{
    public static BufferedImage readImage(String fname)
    {
        BufferedImage bImage = null;
        try {
            File fobject = new File(fname);
            bImage = ImageIO.read(fobject);
        } catch (IOException e) {
            System.exit(-1);
        }

        return bImage;
    }
    
    public static void writeImage(BufferedImage bImage, String type, String fname)
    {
        try {
            File outputfile = new File(fname);
            ImageIO.write(bImage, type, outputfile);
        } catch (IOException e) {
            System.exit(-1);
        }
    }

     public static Object[] getColorByteImageArray2DFromBufferedImage(BufferedImage image)
     {
        int channels;
        byte[] byteData_1d;
        int Rows = image.getHeight();
        int Cols = image.getWidth();

        byte[][] rByteData= new byte[Rows][Cols];
        byte[][] gByteData= new byte[Rows][Cols];
        byte[][] bByteData= new byte[Rows][Cols];
        
        getBufferedImageType(image, "getColorByteImageArray2DFromBufferedImage");
        int m, n, i, i4,pixelLength;
        byteData_1d = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        final boolean hasAlphaChannel = image.getAlphaRaster() != null;
        if (hasAlphaChannel) 
            pixelLength = 4;
        else 
            pixelLength = 3;
        
        if (image.getType() == BufferedImage.TYPE_BYTE_GRAY || image.getType() == BufferedImage.TYPE_USHORT_GRAY) {
            pixelLength = 1;
        }

        if (image.getType() == BufferedImage.TYPE_BYTE_GRAY || image.getType() == BufferedImage.TYPE_USHORT_GRAY)
            channels=1;
        else
            channels=3;
        
        if(channels==1) {
            for (i = 0; i < byteData_1d.length; i ++) {
                m = i / Cols;
                n = i % Cols;
                rByteData[m][n] = byteData_1d[i];
            }
        } else {
            for (i = 0; i < byteData_1d.length; i += pixelLength) {
                i4 = i / pixelLength;
                m = i4 / Cols;
                n = i4 % Cols;
                if (pixelLength==3) {
                    bByteData[m][n]=(byte) byteData_1d[i];
                    gByteData[m][n]=(byte) byteData_1d[i+1];
                    rByteData[m][n]=(byte) byteData_1d[i+2];
                } else if (pixelLength==4) {
                    bByteData[m][n]=byteData_1d[i+1];
                    gByteData[m][n]=byteData_1d[i+2];
                    rByteData[m][n]=byteData_1d[i+3];   
                } else
                    channels=0;
            }   
        }
        return new Object[]{rByteData, gByteData,bByteData};
    }
     
    public static BufferedImage setColorByteImageArray2DToBufferedImage(byte[][] rByteData, byte[][] gByteData,byte[][] bByteData)
    {
        int width=rByteData[0].length;
        int height=rByteData.length;
        int i,m,n,i3,pixelLength=3;
        byte[] byteData_1d = new byte[3* width * height];
        for (i = 0; i < byteData_1d.length; i += pixelLength) {
                i3 = i / pixelLength;
                m = i3 / width;
                n = i3 % width;
                byteData_1d[i]  =  (byte) rByteData[m][n];
                byteData_1d[i+1]=  (byte) gByteData[m][n];
                byteData_1d[i+2]=  (byte) bByteData[m][n];
        }
        int dataType = DataBuffer.TYPE_BYTE;
        DataBufferByte buffer = new DataBufferByte(byteData_1d, byteData_1d.length);

        int cs = ColorSpace.CS_LINEAR_RGB;
        ColorSpace cSpace = ColorSpace.getInstance(cs);
        ComponentColorModel ccm;
        if (dataType == DataBuffer.TYPE_INT || dataType == DataBuffer.TYPE_BYTE) {
            ccm = new ComponentColorModel(cSpace,
                    ((cs == ColorSpace.CS_GRAY)
                            ? new int[]{8} : new int[]{8, 8, 8}),
                    false, false, Transparency.OPAQUE, dataType);
        } else {
            ccm = new ComponentColorModel(
                    cSpace, false, false, Transparency.OPAQUE, dataType);
        }
        SampleModel sm = ccm.createCompatibleSampleModel(width, height);
        WritableRaster raster = Raster.createWritableRaster(sm, buffer, new Point(0, 0));
        return new BufferedImage(ccm, raster, false, null);
    }

    public static byte[] getColorByteImageArray1DFromBufferedImage(BufferedImage image)
    {
        byte[] byteData_1d;
        int Rows = image.getHeight();
        int Cols = image.getWidth();
        getBufferedImageType(image, "getColorByteImageArray1DFromBufferedImage");
        byteData_1d = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        return byteData_1d;
    }
    
    public static int[] getIntImageArray1DFromBufferedImage(BufferedImage image)
    {
        byte[] byteData_1d;
        int[] intData_1d;
        int Rows = image.getHeight(); int Cols = image.getWidth();
        int i, m,n,i4, blue, pixelLength;
        getBufferedImageType(image, "getImageArray1DFromBufferedImage");
        intData_1d = new int[image.getHeight() * image.getWidth()];
        byteData_1d = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        
        final boolean hasAlphaChannel = image.getAlphaRaster() != null;
        if (hasAlphaChannel) {
            pixelLength = 4;
        } else {
            pixelLength = 3;
        }

        if (image.getType() == BufferedImage.TYPE_BYTE_GRAY || image.getType() == BufferedImage.TYPE_USHORT_GRAY) {
            pixelLength = 1;
        }

        for (i = 0; i < byteData_1d.length; i += pixelLength) {
            i4 = i / pixelLength;
            m = i4 / Cols;
            n = i4 % Cols;
            if (image.getType() == BufferedImage.TYPE_BYTE_GRAY || image.getType() == BufferedImage.TYPE_USHORT_GRAY) {
                blue = ((int) byteData_1d[i] & 0xff);
            } else {
                blue = ((int) byteData_1d[i + 1] & 0xff);
            }
            intData_1d[i4] = blue;
        }

        return intData_1d;
    }

    public static byte[] getGrayByteImageArray1DFromBufferedImage(BufferedImage image)
    {
        byte[] byteData_1d;
        int Rows = image.getHeight();
        int Cols = image.getWidth();
        getBufferedImageType(image, "getByteImageArray1DFromBufferedImage");
        if (image.getType() != BufferedImage.TYPE_BYTE_GRAY) {
            return null;
        }

        byteData_1d = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        return byteData_1d;
    }

    public static BufferedImage setGrayByteImageArray1DToBufferedImage(byte[] byteData_1d, int width, int height)
    {
        int dataType = DataBuffer.TYPE_BYTE;
        DataBufferByte buffer = new DataBufferByte(byteData_1d, byteData_1d.length);

        int cs = ColorSpace.CS_GRAY;
        ColorSpace cSpace = ColorSpace.getInstance(cs);
        ComponentColorModel ccm = null;
        if (dataType == DataBuffer.TYPE_INT || dataType == DataBuffer.TYPE_BYTE) {
            ccm = new ComponentColorModel(cSpace,
                    ((cs == ColorSpace.CS_GRAY)
                            ? new int[]{8} : new int[]{8, 8, 8}),
                    false, false, Transparency.OPAQUE, dataType);
        } else {
            ccm = new ComponentColorModel(
                    cSpace, false, false, Transparency.OPAQUE, dataType);
        }

        SampleModel sm = ccm.createCompatibleSampleModel(width, height);
        WritableRaster raster = Raster.createWritableRaster(sm, buffer, new Point(0, 0));

        return new BufferedImage(ccm, raster, false, null);
    }
        
    public static byte[][] getGrayByteImageArray2DFromBufferedImage(BufferedImage image)
    {
        byte[] byteData_1d;
        int Rows = image.getHeight();
        int Cols = image.getWidth();
        getBufferedImageType(image, "getByteImageArray1DFromBufferedImage");
        if (image.getType() != BufferedImage.TYPE_BYTE_GRAY) {
            return null;
        }

        byteData_1d = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        byte[][] byteData_2d = new byte[Rows][Cols];
        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Cols; j++) {
                byteData_2d[i][j] = byteData_1d[i * Cols + j];
            }
        }

        return byteData_2d;
    }

    public static BufferedImage setGrayByteImageArray2DToBufferedImage(byte[][] byteData_2d)
    {
        int width=byteData_2d[0].length;
        int height=byteData_2d.length;       
        byte[] byteData_1d = new byte[width * height];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                byteData_1d[i * width + j] = byteData_2d[i][j];
            }
        }
        int dataType = DataBuffer.TYPE_BYTE;
        DataBufferByte buffer = new DataBufferByte(byteData_1d, byteData_1d.length);

        int cs = ColorSpace.CS_GRAY;
        ColorSpace cSpace = ColorSpace.getInstance(cs);
        ComponentColorModel ccm ;
        if (dataType == DataBuffer.TYPE_INT || dataType == DataBuffer.TYPE_BYTE) {
            ccm = new ComponentColorModel(cSpace,
                    ((cs == ColorSpace.CS_GRAY)
                            ? new int[]{8} : new int[]{8, 8, 8}),
                    false, false, Transparency.OPAQUE, dataType);
        } else {
            ccm = new ComponentColorModel(
                    cSpace, false, false, Transparency.OPAQUE, dataType);
        }

        SampleModel sm = ccm.createCompatibleSampleModel(width, height);
        WritableRaster raster = Raster.createWritableRaster(sm, buffer, new Point(0, 0));

        return new BufferedImage(ccm, raster, false, null);
    }

    static int clip(float xx)
    {
        int x = Math.round(xx);
        if (x > 255) 
            x = 255;
        if (x < 0)
            x = 0;

        return x;
    }

    public static BufferedImage imageCopy(BufferedImage input)
    {
        BufferedImage output = createImage(input.getWidth(), input.getHeight(), 0, 0, 0);
        for (int i = 0; i < input.getWidth(); i++) {
            for (int j = 0; j < input.getHeight(); j++) {
                output.setRGB(i, j, input.getRGB(i, j));
            }
        }

        return output;
    }

    public static void getBufferedImageType(BufferedImage bImage, String from)
    {
        switch (bImage.getType()) {
            case BufferedImage.TYPE_INT_RGB:
                break;
            case BufferedImage.TYPE_INT_ARGB:
                break;
            case BufferedImage.TYPE_INT_ARGB_PRE:
                break;
            case BufferedImage.TYPE_INT_BGR:
                break;
            case BufferedImage.TYPE_3BYTE_BGR:
                break;
            case BufferedImage.TYPE_4BYTE_ABGR:
                break;
            case BufferedImage.TYPE_4BYTE_ABGR_PRE:
                break;
            case BufferedImage.TYPE_BYTE_GRAY:
                break;
            case BufferedImage.TYPE_BYTE_BINARY:
                break;
            case BufferedImage.TYPE_USHORT_GRAY:
                break;
            default:
                break;
        }
    }

    public static BufferedImage createImage(int width, int height, int r, int g, int b)
    {
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

        int argb, a;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                a = 255;
                argb = 0;
                argb += a;
                argb = argb << 8;
                argb += r;
                argb = argb << 8;
                argb += g;
                argb = argb << 8;
                argb += b;
                newImage.setRGB(i, j, argb);
            }
        }

        return newImage;
    }

    public static BufferedImage toGray(BufferedImage original)
    {
        BufferedImage grayKeyImage = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g2d = grayKeyImage.createGraphics();
        g2d.drawImage(original, 0, 0, null);

        return grayKeyImage;
    }
    
    public static BufferedImage addText(BufferedImage original, Color clr,String s, int w, int h)
    {
        Graphics2D g2d = original.createGraphics();
        g2d.setPaint(clr);
        g2d.setFont(new Font("Serif", Font.BOLD, 20));
        g2d.drawString(s, w, h);
        g2d.dispose();

        return original;
    }
    
    public static byte[][] threshold(byte[][] original, int th)
    {
        int h = original.length;
        int w = original[0].length;

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if ((int) (original[i][j] & 0xFF) >= th) {
                    original[i][j] = (byte) 255;
                } else {
                    original[i][j] = (byte) 0;
                }
            }
        }

        return original;
    }

}
