import java.io.File;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import java.awt.image.BufferedImage;
import javafx.scene.text.FontPosture;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.text.TextAlignment;

public class GUIBorderPane extends BorderPane
{
    File file;
    
    VBox DipOperations = new VBox(20);
    HBox inputOutputResults = new HBox(40);
    HBox inputOutputResults2 = new HBox(40);

    Image queryImage = null;
    ImageView queryImageView;

    Image outputImage = null;
    ImageView outputImageView;

    Image outputImageH = null;
    ImageView outputImageViewH;

    Image outputImageEH = null;
    ImageView outputImageViewEH;

    Image equalizedGrayImage = null;
    ImageView equalizedGrayImageView;

    final TextField getA = new TextField();
    final TextField getB = new TextField();
    final TextField getAlpha = new TextField();
    final TextField getBeta = new TextField();
    final TextField getGamma = new TextField();
    final TextField getMaskSize = new TextField();
    final TextField getVariance = new TextField();
    final TextField getMean = new TextField();
    
    
    public GUIBorderPane()
    {
        createUI();
    }
     
    public void getDipOperations()
    {
        Button queryImageButton = new Button("Selecionar imagem");
        Button grayButton = new Button("Aplicar tom de cinza");
        Button contrastButton = new Button("Aplicar Contraste do cinza");
        Button equalizeImageButton = new Button("Equalizar imagem");
        Button convolutionButton = new Button("Convolucionar imagem");
        Button noiseButton = new Button("Adicionar ruído");
  
        Label contrastBoxes = new Label("Atributos");
        getA.setPromptText("Digite A");
        getB.setPromptText("Digite B");
        getAlpha.setPromptText("Digite Alpha");
        getBeta.setPromptText("Digite Beta");
        getGamma.setPromptText("Digite Gamma");
        getMaskSize.setPromptText("Digite (1-8)");
        getMean.setPromptText("Digite quantidade");
        getVariance.setPromptText("Digite variância");

        queryImageButton.setOnAction(new MyQueryImageHandler());
        grayButton.setOnAction(new MyGrayImageHandler());
        contrastButton.setOnAction(new MyGrayContrastImageHandler());
        equalizeImageButton.setOnAction(new MyEqualizeImageHandler());
        convolutionButton.setOnAction(new MyConvolutionImageHandler());
        noiseButton.setOnAction(new MyNoiseImageHandler());

        DipOperations.getChildren().add(queryImageButton);
        DipOperations.getChildren().add(grayButton);
        DipOperations.getChildren().add(contrastBoxes);
        DipOperations.getChildren().add(getA);
        DipOperations.getChildren().add(getB);
        DipOperations.getChildren().add(getAlpha);
        DipOperations.getChildren().add(getBeta);
        DipOperations.getChildren().add(getGamma);
        DipOperations.getChildren().add(contrastButton);
        DipOperations.getChildren().add(equalizeImageButton);
        DipOperations.getChildren().add(getMaskSize);
        DipOperations.getChildren().add(convolutionButton);
        DipOperations.getChildren().add(getMean);
        DipOperations.getChildren().add(getVariance);
        DipOperations.getChildren().add(noiseButton);
        
    } 

    private void createUI()
    {
        Text topText = new Text();
        topText.setWrappingWidth(800);
        topText.setFont(Font.font("Verdana", FontPosture.ITALIC, 40));
        topText.setFill(Color.BROWN);
        topText.setTextAlignment(TextAlignment.CENTER);
        BorderPane.setAlignment(topText, Pos.CENTER);
        this.setTop(topText);

        queryImageView = new ImageView();
        queryImageView.setFitHeight(500);
        queryImageView.setFitWidth(500);
        queryImageView.setImage(queryImage);

        outputImageView = new ImageView();
        outputImageView.setFitHeight(500);
        outputImageView.setFitWidth(500);
        outputImageView.setImage(outputImage);

        outputImageViewH = new ImageView();
        outputImageViewH.setFitHeight(500);
        outputImageViewH.setFitWidth(500);
        outputImageViewH.setImage(outputImageH);

        inputOutputResults.getChildren().addAll(queryImageView);
        inputOutputResults.getChildren().addAll(outputImageView);
        inputOutputResults.getChildren().addAll(outputImageViewH);
        
        this.setCenter(inputOutputResults);
        getDipOperations();
        this.setLeft(DipOperations);

        equalizedGrayImageView = new ImageView();
        equalizedGrayImageView.setFitHeight(500);
        equalizedGrayImageView.setFitWidth(500);
        equalizedGrayImageView.setImage(equalizedGrayImage);

        outputImageViewEH = new ImageView();
        outputImageViewEH.setFitHeight(500);
        outputImageViewEH.setFitWidth(500);
        outputImageViewEH.setImage(outputImageEH);
        
        inputOutputResults2.getChildren().addAll(equalizedGrayImageView);
        inputOutputResults2.getChildren().addAll(outputImageViewEH);
    }

    class MyQueryImageHandler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {
            final FileChooser fileChooser = new FileChooser();

            fileChooser.setTitle("Ver imagem");
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All Images", "*.*"),
                    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                    new FileChooser.ExtensionFilter("PNG", "*.png")
            );

            file = fileChooser.showOpenDialog(null);
            if (file != null && file.isFile()) {
                Image queryImage2 = null;
                try {
                    queryImage2 = new Image(file.toURI().toURL().toExternalForm());
                } catch (Exception e) { }
                queryImageView.setImage(queryImage2);       
            }
        }
    }
    
    class MyNoiseImageHandler implements EventHandler<ActionEvent>
    {
        BufferedImage inImage;
        BufferedImage outImage;

        @Override
        public void handle(ActionEvent event)
        {
            {
                int mean = Integer.parseInt(getMean.getText());
                int variance = Integer.parseInt(getVariance.getText());

                try {
                    inImage = ImageIo.readImage(file.getPath());
                } catch (Exception e) { }

                Object[] byteArrays = ImageIo.getColorByteImageArray2DFromBufferedImage(inImage);
                byte[][] byteData= (byte[][]) byteArrays[0];

                byte[][] gausianNoise = Noise.createGaussionNoise(mean, variance, byteData[0].length, byteData.length);

                Object[] byteArrayswithGaussianNoise = Noise.addGaussianNoise_3(byteArrays,gausianNoise);
                byte[][] rByteData= (byte[][]) byteArrayswithGaussianNoise[0];
                byte[][] gByteData= (byte[][]) byteArrayswithGaussianNoise[1];
                byte[][] bByteData= (byte[][]) byteArrayswithGaussianNoise[2];

                outImage = ImageIo.setColorByteImageArray2DToBufferedImage(rByteData, gByteData, bByteData);
                ImageIo.writeImage(outImage, "jpg", "noise.jpg");
                Image outputImage = SwingFXUtils.toFXImage(outImage, null);
                outputImageView.setImage(outputImage);            
            }
        }
    }

    class MyConvolutionImageHandler implements EventHandler<ActionEvent>
    {
        BufferedImage inImage;
        BufferedImage outImage;
        
        @Override
        public void handle(ActionEvent event)
        {
            int maskNumber = Integer.parseInt(getMaskSize.getText());

            float eMask1[][] = {
                {0.25f, 0.25f},
                {0.25f, 0.25f},
            };

            float eMask2[][] = {
                {0.0625f, 0.0625f, 0.0625f, 0.0625f},
                {0.0625f, 0.0625f, 0.0625f, 0.0625f},
                {0.0625f, 0.0625f, 0.0625f, 0.0625f},
                {0.0625f, 0.0625f, 0.0625f, 0.0625f},
            };

            float eMask3[][] = {
                {0.0277777778f, 0.0277777778f, 0.0277777778f, 0.0277777778f, 0.0277777778f, 0.0277777778f},
                {0.0277777778f, 0.0277777778f, 0.0277777778f, 0.0277777778f, 0.0277777778f, 0.0277777778f},
                {0.0277777778f, 0.0277777778f, 0.0277777778f, 0.0277777778f, 0.0277777778f, 0.0277777778f},
                {0.0277777778f, 0.0277777778f, 0.0277777778f, 0.0277777778f, 0.0277777778f, 0.0277777778f},
                {0.0277777778f, 0.0277777778f, 0.0277777778f, 0.0277777778f, 0.0277777778f, 0.0277777778f},
                {0.0277777778f, 0.0277777778f, 0.0277777778f, 0.0277777778f, 0.0277777778f, 0.0277777778f},
            };
            
            float hMask1[][] = {
                {-1.0f, 0, 1.0f},
                {-2.0f, 0, 2.0f},
                {-1.0f, 0, 1.0f},
            };

            float hMask2[][] = {
                {-1.0f, -2.0f, -1.0f},
                {0.0f, 0.0f, 0.0f},
                {1.0f, 2.0f, 1.0f},
            };

            float hMask3[][] = {
                {0.04f, 0.04f, 0.04f, 0.04f, 0.04f},
                {0.04f, 0.04f, 0.04f, 0.04f, 0.04f},
                {0.04f, 0.04f, 0.04f, 0.04f, 0.04f},
                {0.04f, 0.04f, 0.04f, 0.04f, 0.04f},
                {0.04f, 0.04f, 0.04f, 0.04f, 0.04f},
            };
            
            float hMask4[][] = {
                {-1.0f, -1.0f, -1.0f},
                {-1.0f, 9.0f, -1.0f},
                {-1.0f, -1.0f, -1.0f},
            };

            float hMask5[][] = {
                {0f, -1.0f, 0f},
                {-1.0f, 5.0f, -1.0f},
                {0f, -1.0f, 0f},
            };

            try {
                    inImage = ImageIo.readImage(file.getPath());
            } catch (Exception e) { }

            Object[] byteArrays = ImageIo.getColorByteImageArray2DFromBufferedImage(inImage);
            byte[][] byteData= (byte[][]) byteArrays[0];
            byte[][] rByteData= (byte[][]) byteArrays[0];
            byte[][] gByteData= (byte[][]) byteArrays[1];
            byte[][] bByteData= (byte[][]) byteArrays[2];

            byte[][] rByteDataNew = new byte[byteData.length] [byteData[0].length];
            byte[][] gByteDataNew = new byte[byteData.length] [byteData[0].length];
            byte[][] bByteDataNew = new byte[byteData.length] [byteData[0].length];

            Convolution cnv = new Convolution();

            float mask[][];
            switch (maskNumber) {
                case 1:
                    mask = eMask1;
                    cnv.convolveEvenMask(rByteData, gByteData, bByteData, rByteDataNew, gByteDataNew, bByteDataNew, mask);   
                    break;
                case 2:
                    mask = eMask2;
                    cnv.convolveEvenMask(rByteData, gByteData, bByteData, rByteDataNew, gByteDataNew, bByteDataNew, mask);   
                    break;
                case 3:
                    mask = eMask3;
                    cnv.convolveEvenMask(rByteData, gByteData, bByteData, rByteDataNew, gByteDataNew, bByteDataNew, mask);   
                    break;
                case 4:
                    mask = hMask1;
                    cnv.convolveOddMask(rByteData, gByteData, bByteData, rByteDataNew, gByteDataNew, bByteDataNew, mask);
                    break;
                case 5:
                    mask = hMask2;
                    cnv.convolveOddMask(rByteData, gByteData, bByteData, rByteDataNew, gByteDataNew, bByteDataNew, mask);
                    break;   
                case 6:
                    mask = hMask3;
                    cnv.convolveOddMask(rByteData, gByteData, bByteData, rByteDataNew, gByteDataNew, bByteDataNew, mask);
                    break;
                case 7:
                    mask = hMask4;
                    cnv.convolveOddMask(rByteData, gByteData, bByteData, rByteDataNew, gByteDataNew, bByteDataNew, mask);
                    break;
                case 8:
                    mask = hMask5;
                    cnv.convolveOddMask(rByteData, gByteData, bByteData, rByteDataNew, gByteDataNew, bByteDataNew, mask);
                    break;
                default:
                    break;
            }

            outImage = ImageIo.setColorByteImageArray2DToBufferedImage(rByteDataNew, gByteDataNew, bByteDataNew);
            ImageIo.writeImage(outImage, "jpg", "convolution.jpg");
            Image outputImage = SwingFXUtils.toFXImage(outImage, null);
            outputImageView.setImage(outputImage);
        }
    }    

    class MyGrayImageHandler implements EventHandler<ActionEvent>
    {
        BufferedImage inImage;
        BufferedImage outImage;
       
        @Override
        public void handle(ActionEvent event)
        {
            try {
                    inImage = ImageIo.readImage(file.getPath());
            } catch (Exception e) { }

            BufferedImage grayImage = ImageIo.toGray(inImage);
            byte[][] grayByteData=ImageIo.getGrayByteImageArray2DFromBufferedImage(grayImage);

            outImage = ImageIo.setGrayByteImageArray2DToBufferedImage(grayByteData);
            ImageIo.writeImage(outImage, "jpg", "greyImage.jpg");
            Image outputImage = SwingFXUtils.toFXImage(outImage, null);
            outputImageView.setImage(outputImage);

            byte[][] grayLevel = new byte[256][256];

            Histogram myHist = new Histogram();
            myHist.setImageGray(grayByteData);
            myHist.calcHist();
            myHist.calcHistN();
            myHist.scaleTo();

            int[] graylevelcount = myHist.getHistogramGrayLevels();
                  
            for (int i = 0; i < graylevelcount.length; i++) {
                for (int j = 256 -graylevelcount[i]; j < 256; j++) {
                    grayLevel[j][i]= (byte)(255);
                }
            }

            outImage = ImageIo.setGrayByteImageArray2DToBufferedImage(grayLevel);
            ImageIo.writeImage(outImage, "jpg", "normalHistogram.jpg");
            Image outputImageH = SwingFXUtils.toFXImage(outImage, null);
            outputImageViewH.setImage(outputImageH);
        }
    }

    class MyGrayContrastImageHandler implements EventHandler<ActionEvent>
    {
        BufferedImage inImage;
        BufferedImage outImage;
        
        @Override
        public void handle(ActionEvent event)
        {
            try {
                    inImage = ImageIo.readImage(file.getPath());
            } catch (Exception e) { }

            BufferedImage grayImage = ImageIo.toGray(inImage);
            byte[][] grayByteData = ImageIo.getGrayByteImageArray2DFromBufferedImage(grayImage);

            int a = Integer.parseInt(getA.getText());
            int b = Integer.parseInt(getB.getText());
            double alpha = Double.parseDouble(getAlpha.getText());
            double beta = Double.parseDouble(getBeta.getText());
            double gamma = Double.parseDouble(getGamma.getText());

            double[] lut= new double[256];
            for (int i = 0; i < lut.length; i++) {
                double clip;

                if(i < a) {
                    clip = (alpha * i);

                    if(clip > 255) clip = 255;
                    if(clip < 0) clip = 0;

                    lut[i] = clip;
                } else if(i >= a && i < b) {
                     clip = (beta*(i-a)) + 30;
                     if(clip > 255) clip = 255;
                     if(clip < 0) clip = 0;

                    lut[i] = clip;

                } else if(i >= b){
                    clip = ((gamma * (i-b)) + 200);

                    if(clip > 255) clip = 255;
                    if(clip < 0) clip = 0;

                    lut[i] = clip;
                }
            }

            for(int i = 0; i < grayByteData.length; i++) {
                for(int j = 0; j < grayByteData[0].length; j++) {
                    grayByteData[i][j] = (byte)lut[(grayByteData[i][j] & 0xff)];              
                }
            }    

            outImage = ImageIo.setGrayByteImageArray2DToBufferedImage(grayByteData);
            ImageIo.writeImage(outImage, "jpg", "changeContrast.jpg");
            Image outputImage = SwingFXUtils.toFXImage(outImage, null);
            outputImageView.setImage(outputImage);

            byte[][] grayLevel = new byte[256][256];

            Histogram myHist = new Histogram();
            myHist.setImageGray(grayByteData);
            myHist.calcHist();
            myHist.calcHistN();
            myHist.scaleTo();

            int[] graylevelcount = myHist.getHistogramGrayLevels();

            for (int i = 0; i < graylevelcount.length; i++) {
                for (int j = 256 -graylevelcount[i]; j < 256; j++) {
                    grayLevel[j][i]= (byte)(255);
                }
            }

            outImage = ImageIo.setGrayByteImageArray2DToBufferedImage(grayLevel);
            ImageIo.writeImage(outImage, "jpg", "normalHistogram.jpg");
            Image outputImageH = SwingFXUtils.toFXImage(outImage, null);
            outputImageViewH.setImage(outputImageH);
        }   
    }

    class MyEqualizeImageHandler implements EventHandler<ActionEvent>
    {
        BufferedImage inImage;
        BufferedImage outImage;
       
        @Override
        public void handle(ActionEvent event)
        {
            try {
                    inImage = ImageIo.readImage(file.getPath());
            } catch (Exception e) { }

            BufferedImage grayImage = ImageIo.toGray(inImage);
            byte[][] grayByteData=ImageIo.getGrayByteImageArray2DFromBufferedImage(grayImage);        

            Histogram myHist = new Histogram();
            byte[][] grayLevel = new byte[256][256];

            myHist.setImageGray(grayByteData);
            myHist.calcHist();
            myHist.calcCumulativeHist();
            myHist.calcHistNFromC();
            myHist.calcEqualHist();

            for(int i = 0; i < grayByteData.length; i++) {
                for(int j = 0; j < grayByteData[0].length; j++) {
                    int oldGrayLevelValue = (grayByteData[i][j] & 0xff);
                    int newGrayLevelValue = myHist.getHistogramS()[oldGrayLevelValue];

                    grayByteData[i][j] = (byte) newGrayLevelValue;
                }
            }

            outImage = ImageIo.setGrayByteImageArray2DToBufferedImage(grayByteData);
            ImageIo.writeImage(outImage, "jpg", "equalizedImage.jpg");
            Image equalizedImage = SwingFXUtils.toFXImage(outImage, null);
            equalizedGrayImageView.setImage(equalizedImage); 

            myHist.setImageGray(grayByteData);
            myHist.calcHist();
            myHist.calcHistN();
            myHist.scaleTo();

            int[] graylevelcount = myHist.getHistogramGrayLevels();
            for (int i = 0; i < graylevelcount.length; i++) {
                for (int j = 256 -graylevelcount[i]; j < 256; j++) {
                    grayLevel[j][i]= (byte)(255);
                }
            }

            outImage = ImageIo.setGrayByteImageArray2DToBufferedImage(grayLevel);
            ImageIo.writeImage(outImage, "jpg", "equalizedHistogram.jpg");
            Image outputImageEH = SwingFXUtils.toFXImage(outImage, null);
            outputImageViewEH.setImage(outputImageEH);     

            Pane secondWindow = new Pane();
            Scene secondScene = new Scene(secondWindow);
            Stage secondStage = new Stage();
            secondStage.setTitle("Imagem equalizada");
            secondStage.setScene(secondScene);
            secondWindow.getChildren().add(inputOutputResults2);
            secondStage.show();
        }
    }

}
