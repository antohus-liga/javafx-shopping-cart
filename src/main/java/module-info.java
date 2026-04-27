module napetrico.gen.arturroblox {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;

    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens napetrico.gen.arturroblox to javafx.fxml;
    exports napetrico.gen.arturroblox;
}