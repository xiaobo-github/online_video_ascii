package com.orakcool.video_player2;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class HelloApplication extends Application {
        int sWidth = 900;
        int sHeight = 650;
        String myColors = " .,:;ox%#@";
        String randomColors ="qwertyuiop[]asdfghjkl;'zxcvbnm,./123456678890-=QWERTYUIOPASDFGHJKZXCXVBNM";
        int scale = 9;

        @Override
        public void start(Stage stage) throws Exception {
            Random random = new Random();
            Stage stage2 = new Stage();
            Group root = new Group();
            Canvas canvas = new Canvas(sWidth, sHeight);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setLineWidth(1);
            gc.setFontSmoothingType(FontSmoothingType.GRAY);
            root.getChildren().add(canvas);
            stage2.setScene(new Scene(root));

            WebView webview = new WebView();
            webview.getEngine().load(
                    "http://www.youtube.com/embed/uXGE0vuuaDo?autoplay=1"
            );
            webview.setPrefSize(sWidth, sHeight);
            Scene scene = new Scene(webview);
            stage.setScene(scene);

            Timeline timeline = new Timeline();
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(30), (ActionEvent event) ->{
                PixelReader pixelReader = scene.snapshot(null).getPixelReader();
                //sWidth = (int)scene.getWidth();
                //sHeight = (int)scene.getHeight();
                //canvas.setWidth(sWidth);
                //canvas.setHeight(sHeight);

                gc.setFill(Color.BLACK);
                gc.fillRect(0, 0, sWidth, sHeight);
                Color inColor = Color.DARKGREEN;

                Color outColor = Color.BLACK;

                for(int j=1 ; j<sHeight;j+=scale)
                    for (int i = 1; i < sWidth; i+=scale){
                        double grayScale = 0;

                        for(int j2=1;j2<scale;j2++)
                            for (int i2 = 1; i2 < scale; i2++) {
                                inColor = pixelReader.getColor(i , j );
                                grayScale += inColor.getRed() * 0.29 + inColor.getGreen() * 0.587 + inColor.getBlue() * 0.114;
                            }

                        grayScale /= ((scale-1)*(scale-1));
                        if(grayScale<0.5) {
                            if (inColor.getRed() > inColor.getGreen()) {
                                outColor = Color.color(0.5, 0, 0);
                            } else if (inColor.getGreen() > inColor.getBlue()) {
                                outColor = Color.color(0, 0.5, 0);
                            } else {
                                outColor = Color.color(0, 0, 0.5);
                            }
                        }else{
                            outColor = inColor;
                        }

                        gc.setFill(outColor);
                        //gc.setStroke(outColor);
                        int indexChar = (int)(myColors.length()*grayScale);
                        if(random.nextInt(400) == 2){
                            gc.fillText(Character.toString(randomColors.charAt(random.nextInt(randomColors.length()))), i, j);
                        }else {
                            gc.fillText(Character.toString(myColors.charAt(indexChar)), i, j);
                        }
                    }

            }));
            timeline.play();


            stage.show();
            stage2.show();
        }

    public static void main(String[] args) {
        launch();
    }

}
