import java.beans.PropertyVetoException;
import java.io.Serializable;
import java.util.ArrayList;

public class Board implements Serializable {
    static final long serialVersionUID = 427897943L;
    private final int size; // board is always square

    ArrayList<ArrayList<Cell>> board;
    ArrayList<ArrayList<Cell>> groups; // groups[0] - array of cells in one group. (one cell is in boards[] and in groups[] simultaneously

    public ArrayList<ArrayList<Cell>> getGroups() {
        return groups;
    }

    public ArrayList<ArrayList<Cell>> getBoard() {
        return board;
    }

    public Board() {
        size = 0;
    }

    public Board(int sizee) {
        if (sizee == 6) {
            size = 6;
        } else if (sizee == 9) {
            size = 9;
        } else if (sizee == 4) {
            size = 4;
        } else {
            size = 0;
        }
        board = new ArrayList<ArrayList<Cell>>(size);
        for (int i = 0; i < size; ++i) {
            board.add(new ArrayList<Cell>(size));
            for (int j = 0; j < size; ++j) {
                board.get(i).add(new Cell(0, i, j, 0, true, this));
            }
        }
        groups = new ArrayList<ArrayList<Cell>>(size);
        for (int i = 0; i < size; ++i) {
            groups.add(new ArrayList<Cell>(size));
        }
    }


    public int getSize() {
        return size;
    }

    public void setInitialValues() {
        if (size == 6) {
            int groupId = 0;
            for (int i = 0; i < 6; ++i) {
                for (int j = 0; j < 6; ++j) {
                    Cell cell = board.get(i).get(j);
                    cell.setGroupId(j < 3 ? groupId : groupId + 1);
                    groups.get(j < 3 ? groupId : groupId + 1).add(cell);
                }
                if (i % 2 == 1) {
                    groupId += 2;
                }
            }
            try {
                board.get(0).get(0).setValue(6);
                board.get(0).get(0).setModifiable(false);
                board.get(0).get(2).setValue(1);
                board.get(0).get(2).setModifiable(false);
                board.get(0).get(3).setValue(3);
                board.get(0).get(3).setModifiable(false);
                board.get(0).get(5).setValue(2);
                board.get(0).get(5).setModifiable(false);

                board.get(1).get(1).setValue(5);
                board.get(1).get(1).setModifiable(false);
                board.get(1).get(4).setValue(1);
                board.get(1).get(4).setModifiable(false);

                board.get(2).get(0).setValue(1);
                board.get(2).get(0).setModifiable(false);
                board.get(2).get(2).setValue(4);
                board.get(2).get(2).setModifiable(false);
                board.get(2).get(5).setValue(5);
                board.get(2).get(5).setModifiable(false);

                board.get(3).get(0).setValue(3);
                board.get(3).get(0).setModifiable(false);
                board.get(3).get(3).setValue(6);
                board.get(3).get(3).setModifiable(false);
                board.get(3).get(5).setValue(1);
                board.get(3).get(5).setModifiable(false);

                board.get(4).get(1).setValue(3);
                board.get(4).get(1).setModifiable(false);
                board.get(4).get(4).setValue(6);
                board.get(4).get(4).setModifiable(false);

                board.get(5).get(0).setValue(4);
                board.get(5).get(0).setModifiable(false);
                board.get(5).get(2).setValue(6);
                board.get(5).get(2).setModifiable(false);
                board.get(5).get(3).setValue(5);
                board.get(5).get(3).setModifiable(false);
                board.get(5).get(5).setValue(3);
                board.get(5).get(5).setModifiable(false);
            } catch (PropertyVetoException e) {
                e.printStackTrace();
                System.out.println("Shouldn't happen here!");
                System.exit(1);
            }
        } else if (size == 9) {
            int groupId = 0;
            for (int i = 0; i < 9; ++i) {
                for (int j = 0; j < 9; ++j) {
                    Cell cell = board.get(i).get(j);
                    if (j < 3) {
                        cell.setGroupId(groupId);
                        groups.get(groupId).add(cell);
                    } else if (j < 6) {
                        cell.setGroupId(groupId + 1);
                        groups.get(groupId + 1).add(cell);
                    } else {
                        cell.setGroupId(groupId + 2);
                        groups.get(groupId + 2).add(cell);
                    }
                }
                if (i == 2) {
                    groupId += 3;
                }
                if (i == 5) {
                    groupId += 3;
                }
            }
            try {
                board.get(0).get(1).setValue(9);
                board.get(0).get(1).setModifiable(false);
                board.get(0).get(3).setValue(2);
                board.get(0).get(3).setModifiable(false);
                board.get(0).get(5).setValue(4);
                board.get(0).get(5).setModifiable(false);
                board.get(0).get(6).setValue(8);
                board.get(0).get(6).setModifiable(false);
                board.get(0).get(8).setValue(5);
                board.get(0).get(8).setModifiable(false);

                board.get(1).get(0).setValue(5);
                board.get(1).get(0).setModifiable(false);
                board.get(1).get(4).setValue(8);
                board.get(1).get(4).setModifiable(false);
                board.get(1).get(7).setValue(4);
                board.get(1).get(7).setModifiable(false);

                board.get(2).get(1).setValue(3);
                board.get(2).get(1).setModifiable(false);
                board.get(2).get(2).setValue(4);
                board.get(2).get(2).setModifiable(false);
                board.get(2).get(4).setValue(1);
                board.get(2).get(4).setModifiable(false);
                board.get(2).get(5).setValue(6);
                board.get(2).get(5).setModifiable(false);
                board.get(2).get(6).setValue(2);
                board.get(2).get(6).setModifiable(false);
                board.get(2).get(8).setValue(7);
                board.get(2).get(8).setModifiable(false);

                board.get(3).get(1).setValue(6);
                board.get(3).get(1).setModifiable(false);
                board.get(3).get(3).setValue(3);
                board.get(3).get(3).setModifiable(false);
                board.get(3).get(6).setValue(7);
                board.get(3).get(6).setModifiable(false);

                board.get(4).get(1).setValue(5);
                board.get(4).get(1).setModifiable(false);
                board.get(4).get(2).setValue(9);
                board.get(4).get(2).setModifiable(false);
                board.get(4).get(3).setValue(1);
                board.get(4).get(3).setModifiable(false);
                board.get(4).get(5).setValue(7);
                board.get(4).get(5).setModifiable(false);
                board.get(4).get(8).setValue(8);
                board.get(4).get(8).setModifiable(false);

                board.get(5).get(4).setValue(6);
                board.get(5).get(4).setModifiable(false);
                board.get(5).get(5).setValue(5);
                board.get(5).get(5).setModifiable(false);
                board.get(5).get(7).setValue(1);
                board.get(5).get(7).setModifiable(false);

                board.get(6).get(6).setValue(4);
                board.get(6).get(6).setModifiable(false);
                board.get(6).get(8).setValue(6);
                board.get(6).get(8).setModifiable(false);


                board.get(7).get(2).setValue(7);
                board.get(7).get(2).setModifiable(false);
                board.get(7).get(6).setValue(5);
                board.get(7).get(6).setModifiable(false);


                board.get(8).get(0).setValue(2);
                board.get(8).get(0).setModifiable(false);
                board.get(8).get(6).setValue(3);
                board.get(8).get(6).setModifiable(false);

            } catch (PropertyVetoException e) {
                e.printStackTrace();
                System.out.println("Shouldn't happen here!");
                System.exit(1);
            }
        } else if (size == 4) {
            int tmp = 0;
            Cell cell;
            for (int i = 0; i < 4; ++i) {
                for (int j = 0; j < 3; ++j) {
                    cell = board.get(j + tmp).get(i);
                    cell.setGroupId(i);
                    groups.get(i).add(cell);
                }
                if (i == 0) {
                    tmp = 1;
                } else if (i == 2) {
                    tmp = 0;
                }
            }
            cell = board.get(0).get(1);
            cell.setGroupId(0);
            groups.get(0).add(cell);

            cell = board.get(3).get(0);
            cell.setGroupId(1);
            groups.get(1).add(cell);

            cell = board.get(3).get(3);
            cell.setGroupId(2);
            groups.get(2).add(cell);

            cell = board.get(0).get(2);
            cell.setGroupId(3);
            groups.get(3).add(cell);
            try {
                board.get(2).get(0).setValue(1);
                board.get(2).get(0).setModifiable(false);
                board.get(2).get(1).setValue(2);
                board.get(2).get(1).setModifiable(false);
                board.get(3).get(2).setValue(3);
                board.get(3).get(2).setModifiable(false);
            } catch (PropertyVetoException e) {
                e.printStackTrace();
                System.out.println("Shouldn't happen here!");
                System.exit(1);
            }
        } else {
            System.out.println("Initialization only for sizes 4, 6 and 9");
        }
    }
}