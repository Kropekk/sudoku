import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.beans.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class Cell extends StackPane implements Serializable {
    static final private long serialVersionUID = 4278979L;
    static final private String COLLIDES_WITH_UNMODIFIABLE = "New value collides with some initialized cell.";
    static final private ArrayList<String> LIST_FOR_4 = new ArrayList<>();
    static final private ArrayList<String> LIST_FOR_6 = new ArrayList<>();
    static final private ArrayList<String> LIST_FOR_9 = new ArrayList<>();

    static {
        for (int i = 0; i < 5; ++i) {
            LIST_FOR_4.add(Integer.toString(i));
            LIST_FOR_6.add(Integer.toString(i));
            LIST_FOR_9.add(Integer.toString(i));
        }
        for (int i = 5; i < 7; ++i) {
            LIST_FOR_6.add(Integer.toString(i));
            LIST_FOR_9.add(Integer.toString(i));
        }
        for (int i = 7; i < 10; ++i) {
            LIST_FOR_9.add(Integer.toString(i));
        }

    }

    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private VetoableChangeSupport vcs = new VetoableChangeSupport(this);

    public synchronized void addPropertyChangeListener(PropertyChangeListener lst) {
        pcs.addPropertyChangeListener(lst);
    }

    public synchronized void removePropertyChangeListener(PropertyChangeListener lst) {
        pcs.removePropertyChangeListener(lst);
    }

    public synchronized void addVetoableChangeListener(VetoableChangeListener lst) {
        vcs.addVetoableChangeListener(lst);
    }

    public synchronized void removeVetoableChangeListener(VetoableChangeListener lst) {
        vcs.removeVetoableChangeListener(lst);
    }

    private Text text = new Text();
    private Rectangle cellBorder = new Rectangle(90, 90);
    private int value = 0;
    private int row;
    private int column;
    private int groupId;
    private boolean modifiable;
    private Board board;
    private HashSet<Cell> conflictingCells;


    class CheckUnmodifiableCells implements VetoableChangeListener {
        @Override
        public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
            if (!evt.getPropertyName().equals("value")) {
                return;
            }
            int newValue = (int) evt.getNewValue();
            if (newValue == 0) {  // clean the field
                return;
            }
            int size = board.getSize();
            if (newValue > size || newValue < 0) { // shouldn't even happen
                System.out.println("Value out of range");
                throw new PropertyVetoException("Value out of range", evt);
            }
            Cell cell;
            for (int i = 0; i < size; ++i) {
                if (!(cell = board.getBoard().get(row).get(i)).modifiable && cell.getValue() == newValue && !cell.equals(Cell.this)) {
                    throw new PropertyVetoException(COLLIDES_WITH_UNMODIFIABLE, evt);
                }
                if (!(cell = board.getBoard().get(i).get(column)).modifiable && cell.getValue() == newValue && !cell.equals(Cell.this)) {
                    throw new PropertyVetoException(COLLIDES_WITH_UNMODIFIABLE, evt);
                }
                if (!(cell = board.getGroups().get(groupId).get(i)).modifiable && cell.getValue() == newValue && !cell.equals(Cell.this)) {
                    throw new PropertyVetoException(COLLIDES_WITH_UNMODIFIABLE, evt);
                }
            }
        }
    }

    class CheckModifiableCells implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (!evt.getPropertyName().equals("value")) {
                return;
            }
            int newValue = (int) evt.getNewValue();
            for (Cell cell : conflictingCells) {
                cell.getConflictingCells().remove(Cell.this);
                if (cell.getConflictingCells().isEmpty()) {
                    cell.text.setFill(Color.BLACK);
                }
            }
            conflictingCells.clear();
            text.setFill(Color.BLACK);
            if (newValue == 0) {  // clean the field
                return;
            }
            int size = board.getSize();
            Cell cell;
            for (int i = 0; i < size; ++i) {
                if ((cell = board.getBoard().get(row).get(i)).getValue() == newValue && !cell.equals(Cell.this)) { // no need to check modifiable, it's not
                    cell.getConflictingCells().add(Cell.this);
                    cell.text.setFill(Color.RED);
                    conflictingCells.add(cell);
                    text.setFill(Color.RED);
                }
                if ((cell = board.getBoard().get(i).get(column)).getValue() == newValue && !cell.equals(Cell.this)) {
                    cell.getConflictingCells().add(Cell.this);
                    cell.text.setFill(Color.RED);
                    conflictingCells.add(cell);
                    text.setFill(Color.RED);
                }
                if ((cell = board.getGroups().get(groupId).get(i)).getValue() == newValue && !cell.equals(Cell.this)) {
                    cell.getConflictingCells().add(Cell.this);
                    cell.text.setFill(Color.RED);
                    conflictingCells.add(cell);
                    text.setFill(Color.RED);
                }
            }
        }
    }

    public int getValue() {
        return value;
    }

    public synchronized void setValue(int value) throws PropertyVetoException {
        int oldVal = getValue();
        vcs.fireVetoableChange("value", oldVal, value);
        this.value = value;
        pcs.firePropertyChange("value", oldVal, value);
        text.setText(value != 0 ? Integer.toString(value) : "");
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public void setModifiable(boolean modifiable) {
        this.modifiable = modifiable;
        if (!modifiable) {
            text.setFill(Color.GREEN);
            text.setUnderline(true);
        }
    }

    public HashSet<Cell> getConflictingCells() {
        return conflictingCells;
    }


    public Text getText() {
        return text;
    }

    public Rectangle getCellBorder() {
        return cellBorder;
    }

    public void showPopUp() {
        if (!modifiable) {
            return;
        }
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(Sudoku.mainStage);
        ChoiceBox cb = new ChoiceBox();
        if (board.getBoard().size() == 6) {
            cb.setItems(FXCollections.observableArrayList(LIST_FOR_6));
        } else if (board.getBoard().size() == 9) {
            cb.setItems(FXCollections.observableArrayList(LIST_FOR_9));
        } else if (board.getBoard().size() == 4) { // only option left
            cb.setItems(FXCollections.observableArrayList(LIST_FOR_4));
        }
        Scene dialogScene = new Scene(cb, 0.001, 0.001);
        dialog.setScene(dialogScene);
        dialog.show();
        cb.show();
        cb.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) -> {
            try {
                setValue((Integer) t1);
            } catch (PropertyVetoException e) {
                System.out.println(COLLIDES_WITH_UNMODIFIABLE);
            }
            cb.hide();
            dialog.close();
        });
    }

    public Cell() {
        addVetoableChangeListener(new CheckUnmodifiableCells());
        addPropertyChangeListener(new CheckModifiableCells());
        cellBorder.setStrokeWidth(10.);
        text.setFont(Font.font(36));
        text.setText(value == 0 ? "" : Integer.toString(value));
        text.setVisible(true);
        cellBorder.setFill(null);
        getChildren().addAll(cellBorder, text);
        setOnMouseClicked(e -> showPopUp());
    }


    public Cell(int val, int r, int c, int group, boolean mod, Board b) {
        this();
        value = val;
        row = r;
        column = c;
        groupId = group;
        modifiable = mod;
        board = b;
        conflictingCells = new HashSet<>();
        setTranslateY(row * 100);
        setTranslateX(column * 100);
    }
}
