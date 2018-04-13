package kaymattern.notus.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kaymattern.notus.model.Mark;
import kaymattern.notus.model.Subject;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

public class DataAccessor {

    private final Database database;
    private final ObservableList<Subject> subjects;

    public DataAccessor() {
        this.database = new Database("notus");
        this.subjects = loadSubjects();
    }

    public ObservableList<Subject> getSubjects() {
        return this.subjects;
    }


    /**
     * Returns the next available id of a table
     * @param tableName The table
     * @return The next id
     */
    private int getNextId(String tableName) {
        Object[][] result = database.executePreparedStatement("SELECT max(id) FROM " + tableName);
        int maxId = result[0][0] == null ? 0 : (int) result[0][0];
        return ++maxId;
    }

    /**
     * Loads all subjects from the database.
     */
    private ObservableList<Subject> loadSubjects() {
        Object[][] subjectsData = database.executePreparedStatement("SELECT id, name FROM subject");
        return Arrays.stream(subjectsData)
                .map(row -> new Subject((int) row[0], (String) row[1]))
                .peek(this::loadMarks)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    /**
     * Loads all marks for the given subject.
     * @param subject The subject to load the marks.
     */
    public void loadMarks(Subject subject) {
        Object[][] marksData = database.executePreparedStatement("SELECT id, name, date, value, weight FROM mark WHERE subject_id = ?", subject.getId());
        subject.getMarks().addAll(Arrays.stream(marksData)
                .map(row -> new Mark((int) row[0], (String) row[1], new Date((Long) row[2]).toLocalDate(), ((Double) row[3]).floatValue(), ((Double) row[4]).floatValue()))
                .collect(Collectors.toCollection(FXCollections::observableSet)));
    }

    /**
     * Create a subject and saves it to the database.
     * @param name The name of the subhect.
     */
    public void createSubject(String name) {
        int nextId = getNextId("subject");
        Subject subject = new Subject(nextId, name);
        addSubject(subject);
    }

    /**
     * Adds a subject to the database.
     * @param subject The subject to add
     */
    private void addSubject(Subject subject) {
        database.executePreparedUpdate("INSERT INTO subject (name) VALUES (?)", subject.getName());
        this.subjects.add(subject);
    }

    /**
     * Remove a subject from the database.
     * <strong>Note:</strong> the marks of the subject are removed too.
     * @param subject The subject to remove
     */
    public void removeSubject(Subject subject) {
        database.executePreparedUpdate("DELETE FROM subject WHERE id = ?", subject.getId());
        database.executePreparedUpdate("DELETE FROM mark WHERE subject_id = ?", subject.getId());
        subject.getMarks().clear();
        this.subjects.remove(subject);
    }

    /**
     * Edit a subject.
     * @param subject The subject to edit
     * @param newName The new name
     */
    public void editSubject(Subject subject, String newName) {
        database.executePreparedUpdate("UPDATE subject SET name = ? WHERE id = ?", newName, subject.getId());
        subject.setName(newName);
    }

    /**
     * Create a subject and save it to the database.
     */
    public void createMark(Subject subject, String name, LocalDate date, float value, float weight) {
        int nextId = getNextId("mark");
        Mark mark = new Mark(nextId, name, date, value, weight);
        addMark(subject, mark);
    }

    /**
     * Adds a mark.
     * @param mark The mark to add
     */
    private void addMark(Subject subject, Mark mark) {
        database.executePreparedUpdate("INSERT INTO mark (id, name, value, weight, date, subject_id) VALUES (?,?,?,?,?,?)",
                mark.getId(), mark.getName(), mark.getValue(), mark.getWeight(), Date.valueOf(mark.getDate()), subject.getId());
        subject.getMarks().add(mark);
    }

    /**
     * Removes a mark.
     * @param subject The subject of the mark
     * @param mark The mark to remove
     */
    public void removeMark(Subject subject, Mark mark) {
        if (! subject.getMarks().contains(mark)) {
            throw new IllegalArgumentException("Mark must belong to the given subject!");
        }
        database.executePreparedUpdate("DELETE FROM mark WHERE id = ?", mark.getId());
        subject.getMarks().remove(mark);
    }

    /**
     * Edit a mark.
     * @param subject The subject of the mark
     * @param mark The mark to edit
     * @param newName A new name
     * @param newDate A new date
     * @param newValue A new value
     * @param newWeight A new weight
     */
    public void editMark(Subject subject, Mark mark, String newName, LocalDate newDate, Float newValue, Float newWeight) {
        if (! subject.getMarks().contains(mark)) {
            throw new IllegalArgumentException("Mark must belong to the given subject!");
        }

        database.executePreparedUpdate("UPDATE mark SET name = ?, date = ?, value = ?, weight = ? WHERE id = ?",
                newName, Date.valueOf(newDate), newValue, newWeight, mark.getId());

        mark.setName(newName);
        mark.setDate(newDate);
        mark.setValue(newValue);
        mark.setWeight(newWeight);

    }

}
