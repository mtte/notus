package kaymattern.notus.database;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
     * Loads all subjects from the database.
     */
    private ObservableList<Subject> loadSubjects() {
        Object[][] subjectsData = database.executePreparedStatement("SELECT id, name FROM subject");
        return Arrays.stream(subjectsData)
                .map(row -> new Subject((int) row[0], (String) row[1]))
                .peek(subject -> subject.getMarks().addAll(
                        new Mark("1", LocalDate.now(), 5f, 1.0f),
                        new Mark("2", LocalDate.now(), 6f, 0.5f)))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    /**
     * Loads all marks for the given subject.
     * @param subject The subject to load the marks.
     */
    private void loadMarks(Subject subject) {
        Object[][] marksData = database.executePreparedStatement("SELECT name, date, value, weight FROM mark WHERE subject_id = ?", subject.getId());
        subject.getMarks().addAll(Arrays.stream(marksData)
                .map(row -> new Mark((String) row[0], Date.valueOf((String) row[1]).toLocalDate(), (float) row[2], (float) row[3]))
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
     * Returns the next available id of a table
     * @param tableName The table
     * @return The next id
     */
    private int getNextId(String tableName) {
        Object[][] result = database.executePreparedStatement("SELECT max(id) FROM " + tableName);
        int maxId = (int) result[0][0];
        return ++maxId;
    }

    /**
     * Remove a subject from the database.
     * <strong>Note:</strong> the marks of the subject are removed too.
     * @param subject The subject to remove
     */
    private void removeSubject(Subject subject) {
        database.executePreparedUpdate("REMOVE FROM subject WHERE subject_id = ?", subject.getId());
        database.executePreparedUpdate("REMOVE FORM mark WHERE subject_id = ?", subject.getId());
    }

    /**
     * Adds a mark to the database.
     * @param mark The mark to add
     */
    private void addMark(Mark mark) {
        database.executePreparedUpdate("INSERT INTO mark (name, value, weight, date, subject_id) VALUES (?,?,?,?,?)",
                mark.getName(), mark.getValue(), mark.getWeight(), mark.getDate());
    }

    /**
     * Removes a mark from the database.
     * @param mark The mark to remove
     */
    private void removeMark(Mark mark) {
        database.executePreparedUpdate("REMOVE FROM mark WHERE id = ?");
    }

}
