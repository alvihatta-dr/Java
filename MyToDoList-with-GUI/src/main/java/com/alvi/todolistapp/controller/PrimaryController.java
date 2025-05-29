package com.alvi.todolistapp.controller; // PASTIKAN NAMA PACKAGE INI BENAR

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle; // Tambahkan import Locale
import java.util.function.Predicate; // Tambahkan import Predicate

import com.alvi.todolistapp.model.Task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox; // Untuk pop-up Alert

public class PrimaryController implements Initializable {

    // --- Deklarasi Elemen Sidebar ---
    @FXML private VBox sidebarVBox;
    @FXML private VBox profileVBox;
    @FXML private ImageView avatarImageView;
    @FXML private Label userNameLabel;
    @FXML private Label userEmailLabel;
    @FXML private HBox searchHBox;
    @FXML private TextField searchField;
    @FXML private Button searchButton;
    @FXML private ListView<String> sidebarListView;
    @FXML private HBox newListHBox;
    @FXML private Button addNewListButton;
    @FXML private String currentSelectedList = "My Day";

    // --- Deklarasi Elemen Header Konten Utama ---
    @FXML private HBox headerHBox;
    @FXML private Label listTitleLabel;
    @FXML private Label dateLabel;
    @FXML private HBox headerIconsHBox;
    @FXML private ImageView sortIcon;
    @FXML private ImageView optionsIcon;

    // --- Deklarasi Elemen Konten Utama ---
    @FXML private StackPane mainContentStackPane;
    @FXML private ListView<Task> taskListView;
    @FXML private VBox emptyListPlaceholderVBox;
    @FXML private ImageView calendarIcon;
    @FXML private Label focusTitleLabel;
    @FXML private Label focusDescLabel;

    // --- Deklarasi Elemen Input Tugas Baru ---
    @FXML private HBox taskInputHBox;
    @FXML private TextField newTaskInputField;

    // --- Data Model ---
    private ObservableList<Task> allTasks; // Daftar semua tugas
    private FilteredList<Task> filteredTasks; // Daftar tugas yang ditampilkan setelah difilter
    private ObservableList<String> sidebarLists; // Untuk item navigasi sidebar

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // --- Inisialisasi Data dan UI Awal ---
        allTasks = FXCollections.observableArrayList();
        filteredTasks = new FilteredList<>(allTasks, p -> true); // Awalnya, tampilkan semua
        taskListView.setItems(filteredTasks); // Hubungkan ListView ke FilteredList

        listTitleLabel.setText("My Day"); // Judul default
        updateDateLabel(); // Set tanggal di header

        // --- Konfigurasi Profil Sidebar ---
        userNameLabel.setText("Alvi Hatta Dwi Rangga");
        userEmailLabel.setText("alvihatta13@gmail.com");
        // Muat gambar avatar (Dengan penanganan error yang lebih baik)
        try {
            String avatarPath = "/com/alvi/todolistapp/icons/avatar.png";
            URL avatarUrl = getClass().getResource(avatarPath);
            if (avatarUrl == null) {
                System.err.println("ERROR: Avatar image not found at: " + avatarPath);
            } else {
                avatarImageView.setImage(new Image(avatarUrl.toExternalForm()));
            }
        } catch (Exception e) {
            System.err.println("CRITICAL ERROR loading avatar image: " + e.getMessage());
        }

        // --- Konfigurasi Sidebar ListView ---
        sidebarLists = FXCollections.observableArrayList("My Day", "Important", "Planned", "Assigned to me", "Tasks", "Example ToDoList");
        sidebarListView.setItems(sidebarLists);
        sidebarListView.setCellFactory(lv -> new ListCell<String>() {
            private final HBox graphicBox = new HBox(10); // Spacing 10
            private final ImageView iconView = new ImageView();
            private final Label textLabel = new Label();

            {
                iconView.setFitWidth(20);
                iconView.setFitHeight(20);
                graphicBox.getChildren().addAll(iconView, textLabel);
                graphicBox.setStyle("-fx-alignment: CENTER_LEFT;");
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    textLabel.setText(item);
                    String iconFileName = null;
                    switch (item) {
                        case "My Day": iconFileName = "my_day_icon.png"; break;
                        case "Important": iconFileName = "important_icon.png"; break;
                        case "Planned": iconFileName = "planned_icon.png"; break;
                        case "Assigned to me": iconFileName = "assigned_icon.png"; break;
                        case "Tasks": iconFileName = "tasks_icon.png"; break;
                        case "Example ToDoList": iconFileName = "list_icon.png"; break;
                        default: iconFileName = "default_list_icon.png"; break; // Pastikan file ini ada!
                    }
                    try {
                        String fullIconPath = "/com/alvi/todolistapp/icons/" + iconFileName;
                        URL iconUrl = getClass().getResource(fullIconPath);
                        if (iconUrl == null) {
                            System.err.println("ERROR: Sidebar icon not found for '" + item + "' at: " + fullIconPath);
                            iconView.setImage(null);
                        } else {
                            iconView.setImage(new Image(iconUrl.toExternalForm()));
                        }
                    } catch (Exception e) {
                        System.err.println("CRITICAL ERROR loading sidebar icon for item: " + item + " - Error: " + e.getMessage());
                        iconView.setImage(null);
                    }
                    setGraphic(graphicBox);
                }
            }
        });

        // Listener untuk sidebar selection
        sidebarListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                listTitleLabel.setText(newVal);
                currentSelectedList = newVal; // Simpan daftar yang dipilih
                applyFilter(newVal); // Panggil metode baru untuk menerapkan filter
            }
        });

        // Atur tugas pertama sebagai default terpilih di sidebar
        sidebarListView.getSelectionModel().selectFirst();


        // --- Konfigurasi taskListView (CellFactory) ---
        taskListView.setCellFactory(lv -> new ListCell<Task>() {
            private final CheckBox completeCheckBox = new CheckBox();
            private final Label descriptionLabel = new Label();
            private final Region spacer = new Region();
            private final Button deleteButton = new Button("🗑️"); // Ikon sampah

            {
                HBox.setHgrow(spacer, Priority.ALWAYS);
                HBox hbox = new HBox(completeCheckBox, descriptionLabel, spacer, deleteButton);
                hbox.setSpacing(10);
                hbox.setPrefHeight(30);
                hbox.setStyle("-fx-alignment: CENTER_LEFT;");

                completeCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
                    if (getItem() != null) {
                        getItem().setCompleted(newVal);
                        updateStyle();
                        // FilteredList akan otomatis memperbarui tampilan jika Predicate-nya berubah
                        // atau jika ObservableList asli berubah.
                    }
                });

                deleteButton.setOnAction(event -> {
                    if (getItem() != null) {
                        allTasks.remove(getItem()); // Hapus dari allTasks, FilteredList akan otomatis update
                        updateTaskListDisplay(); // Perbarui visibilitas placeholder
                    }
                });
            }

            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);
                if (empty || task == null) {
                    setGraphic(null);
                    setText(null);
                    setStyle("");
                } else {
                    completeCheckBox.setSelected(task.isCompleted());
                    descriptionLabel.setText(task.getDescription());
                    setGraphic(completeCheckBox.getParent());
                    setText(null);
                    updateStyle();
                }
            }

            private void updateStyle() {
                if (getItem() != null && getItem().isCompleted()) {
                    descriptionLabel.setStyle("-fx-strikethrough: true; -fx-text-fill: gray;");
                    completeCheckBox.setStyle("-fx-border-color: transparent; -fx-background-color: transparent;");
                } else {
                    descriptionLabel.setStyle("-fx-strikethrough: false; -fx-text-fill: black;");
                    completeCheckBox.setStyle("");
                }
            }
        });

        // --- Contoh Tugas Awal ---
        allTasks.add(new Task("Belajar JavaFX GUI", "My Day")); // Tambahkan listName
        allTasks.add(new Task("Desain UI Mirip MS To Do", "My Day"));
        Task importantTask = new Task("Menyiapkan makan malam", "Important"); // Tandai penting & listName
        importantTask.setImportant(true);
        allTasks.add(importantTask);
        allTasks.add(new Task("Istirahat sebentar", "Tasks")); // Tambahkan listName
        allTasks.get(0).setCompleted(true);

        // Konfigurasi Placeholder Daftar Kosong
        try {
            String calendarIconPath = "/com/alvi/todolistapp/icons/calendar_icon.png";
            URL calendarIconUrl = getClass().getResource(calendarIconPath);
            if (calendarIconUrl == null) {
                System.err.println("ERROR: Calendar icon not found at: " + calendarIconPath);
            } else {
                calendarIcon.setImage(new Image(calendarIconUrl.toExternalForm()));
            }
        } catch (Exception e) {
            System.err.println("CRITICAL ERROR loading calendar icon: " + e.getMessage());
        }
        focusTitleLabel.setText("Focus on your day");
        focusDescLabel.setText("Get things done with My Day, a list that refreshes every day.");

        // Panggil untuk pertama kali saat aplikasi dimulai untuk mengatur visibilitas placeholder
        // (updateTaskListDisplay akan dipicu juga oleh selectFirst() di atas)
        // updateTaskListDisplay(); // Tidak perlu panggil di sini lagi


        // --- Fungsionalitas Input Tugas (dengan Enter) ---
        newTaskInputField.setOnKeyReleased(event -> {
            if (event.getCode().equals(javafx.scene.input.KeyCode.ENTER)) {
                addTask();
            }
        });

        // --- Tombol "Tambah Daftar Baru" ---
        addNewListButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add New List");
            alert.setHeaderText(null);
            alert.setContentText("Fitur 'Add New List' akan segera hadir!");
            alert.showAndWait();
        });

        // --- Konfigurasi Search Button (jika ada) ---
        if (searchButton != null) {
            try {
                String searchIconPath = "/com/alvi/todolistapp/icons/search_icon.png";
                URL searchIconUrl = getClass().getResource(searchIconPath);
                if (searchIconUrl == null) {
                    System.err.println("ERROR: Search icon not found at: " + searchIconPath);
                } else {
                    ImageView searchGraphic = new ImageView(new Image(searchIconUrl.toExternalForm(), 20, 20, true, true));
                    searchButton.setGraphic(searchGraphic);
                }
            } catch (Exception e) {
                System.err.println("Error loading search icon: " + e.getMessage());
            }
            searchButton.setOnAction(event -> {
                System.out.println("Search clicked. Query: " + searchField.getText());
            });
        }

        // --- Konfigurasi Header Icons (sortIcon, optionsIcon) ---
        if (sortIcon != null) {
            try {
                String sortIconPath = "/com/alvi/todolistapp/icons/sort_icon.png";
                URL sortIconUrl = getClass().getResource(sortIconPath);
                if (sortIconUrl == null) {
                    System.err.println("ERROR: Sort icon not found at: " + sortIconPath);
                } else {
                    sortIcon.setImage(new Image(sortIconUrl.toExternalForm()));
                    sortIcon.setFitWidth(24);
                    sortIcon.setFitHeight(24);
                    sortIcon.setPreserveRatio(true);
                }
            } catch (Exception e) {
                System.err.println("Error loading sort icon: " + e.getMessage());
            }
            sortIcon.setOnMouseClicked(event -> System.out.println("Sort clicked!"));
        }
        if (optionsIcon != null) {
            try {
                String optionsIconPath = "/com/alvi/todolistapp/icons/options_icon.png";
                URL optionsIconUrl = getClass().getResource(optionsIconPath);
                if (optionsIconUrl == null) {
                    System.err.println("ERROR: Options icon not found at: " + optionsIconPath);
                } else {
                    optionsIcon.setImage(new Image(optionsIconUrl.toExternalForm()));
                    optionsIcon.setFitWidth(24);
                    optionsIcon.setFitHeight(24);
                    optionsIcon.setPreserveRatio(true);
                }
            } catch (Exception e) {
                System.err.println("Error loading options icon: " + e.getMessage());
            }
            optionsIcon.setOnMouseClicked(event -> System.out.println("Options clicked!"));
        }

    } // Akhir dari initialize()

    // --- Metode Helper dan Event Handler ---

    // Metode untuk memperbarui tampilan daftar tugas (menampilkan/menyembunyikan placeholder)
    private void updateTaskListDisplay() {
        boolean hasVisibleTasks = !filteredTasks.isEmpty(); // Menggunakan filteredTasks
        taskListView.setVisible(hasVisibleTasks);
        emptyListPlaceholderVBox.setVisible(!hasVisibleTasks);

        // Jika daftar adalah "My Day" dan tidak ada tugas yang terlihat,
        // set placeholder yang spesifik untuk "My Day" (sesuai screenshot Anda)
        if ("My Day".equals(listTitleLabel.getText()) && !hasVisibleTasks) {
            focusTitleLabel.setText("Focus on your day");
            focusDescLabel.setText("Get things done with My Day, a list that refreshes every day.");
            try {
                // Pastikan calendar_icon.png ada
                String calendarPath = "/com/alvi/todolistapp/icons/calendar_icon.png";
                URL calendarUrl = getClass().getResource(calendarPath);
                if (calendarUrl == null) {
                     System.err.println("ERROR: Calendar icon not found at: " + calendarPath);
                     calendarIcon.setImage(null);
                } else {
                     calendarIcon.setImage(new Image(calendarUrl.toExternalForm()));
                }
            } catch (Exception e) { System.err.println("Error loading calendar icon in update: " + e.getMessage()); }
        } else if (!hasVisibleTasks) {
            // Placeholder generik untuk daftar kosong lainnya
            focusTitleLabel.setText("No tasks here yet");
            focusDescLabel.setText("Add a new task to get started!");
            try {
                // Pastikan default_empty_list_icon.png ada
                String defaultEmptyIconPath = "/com/alvi/todolistapp/icons/default_empty_list_icon.png";
                URL defaultEmptyIconUrl = getClass().getResource(defaultEmptyIconPath);
                if (defaultEmptyIconUrl == null) {
                     System.err.println("ERROR: Default empty list icon not found at: " + defaultEmptyIconPath);
                     calendarIcon.setImage(null);
                } else {
                     calendarIcon.setImage(new Image(defaultEmptyIconUrl.toExternalForm()));
                }
            } catch (Exception e) { System.err.println("Error loading default empty icon in update: " + e.getMessage()); }
        }
    }

    // Metode untuk memperbarui tanggal di header
    private void updateDateLabel() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM", Locale.of("id", "ID"));
        dateLabel.setText(today.format(formatter));
    }

    private void applyFilter(String listName) {
        Predicate<Task> filterPredicate = task -> true; // Default

        switch (listName) {
            case "My Day":
                // Untuk My Day, kita akan tampilkan tugas yang BELUM selesai,
                // dan yang listName-nya "My Day" atau "Tasks".
                // Atau, jika Anda ingin seperti MS To Do, ini adalah tugas "Tasks" yang ditandai untuk hari ini.
                // Untuk saat ini, mari kita asumsikan My Day menampilkan tugas dari "Tasks" yang belum selesai.
                filterPredicate = task -> !task.isCompleted() && task.getListName().equals("Tasks");
                // Anda bisa menambahkan logika isMyDay di sini jika ingin fitur "My Day" yang lebih dinamis.
                break;
            case "Important":
                filterPredicate = Task::isImportant; // Hanya tampilkan tugas penting
                break;
            case "Planned":
                filterPredicate = task -> false; // Untuk demo, tidak tampilkan apa-apa
                break;
            case "Assigned to me":
                filterPredicate = task -> false; // Untuk demo, tidak tampilkan apa-apa
                break;
            case "Tasks":
                filterPredicate = task -> task.getListName().equals("Tasks"); // Hanya tampilkan tugas dari daftar "Tasks"
                break;
            case "Example ToDoList":
                filterPredicate = task -> task.getListName().equals("Example ToDoList"); // Filter berdasarkan nama daftar
                break;
            default:
                filterPredicate = task -> true; // Fallback: tampilkan semua
                break;
        }
        filteredTasks.setPredicate(filterPredicate);
        updateTaskListDisplay();
    }

    // Metode yang dipanggil saat tugas baru ditambahkan (via Enter)
    @FXML
    private void addTask() {
        String newTaskDesc = newTaskInputField.getText().trim();
        if (!newTaskDesc.isEmpty()) {
            Task newTask = new Task(newTaskDesc, currentSelectedList); // <-- Berikan listName

            // Logika khusus untuk "My Day" dan "Important"
            if (currentSelectedList.equals("My Day")) {
                // Di MS To Do, tugas yang ditambahkan ke "My Day" sebenarnya hanya tugas biasa yang
                // ditampilkan di daftar "My Day" hari itu.
                // Jika Anda ingin semua tugas yang ditambahkan ke My Day juga muncul di "Tasks",
                // Anda bisa set listName-nya ke "Tasks" dan tetap menampilkannya di "My Day"
                // berdasarkan kriteria lain (misalnya, isMyDay property).
                // Untuk kesederhanaan saat ini:
                // Jika di My Day, tugas itu tetap dianggap bagian dari "Tasks" tetapi tampil di "My Day"
                newTask.setListName("Tasks"); // Tugas My Day sebenarnya adalah tugas "Tasks"
            } else if (currentSelectedList.equals("Important")) {
                newTask.setImportant(true); // Jika ditambahkan di daftar Important, tandai sebagai penting
                newTask.setListName("Tasks"); // Tugas penting juga adalah bagian dari "Tasks"
            } else if (currentSelectedList.equals("Planned") || currentSelectedList.equals("Assigned to me")) {
                newTask.setListName("Tasks"); // Tugas Planned/Assigned juga adalah bagian dari "Tasks"
            }

            allTasks.add(newTask);
            newTaskInputField.clear();
            taskListView.scrollTo(filteredTasks.size() - 1);
            updateTaskListDisplay();
        }
    }
}
