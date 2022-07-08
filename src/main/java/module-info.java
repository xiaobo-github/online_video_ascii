module com.orakcool.video_player2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires javafx.web;

    opens com.orakcool.video_player2 to javafx.fxml;
    exports com.orakcool.video_player2;
}