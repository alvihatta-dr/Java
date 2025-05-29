module com.alvi.todolistapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics; // Opsional, biasanya sudah di-transitively required

    exports com.alvi.todolistapp; // Ini penting jika App.java ada di root package
    exports com.alvi.todolistapp.model; // Tetap pertahankan

    // Ini adalah baris yang perlu DITAMBAHKAN/DIPERBAIKI
    // Mengizinkan reflective access ke paket controller oleh javafx.fxml
    opens com.alvi.todolistapp.controller to javafx.fxml;
}
