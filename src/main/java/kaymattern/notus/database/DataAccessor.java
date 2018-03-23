package kaymattern.notus.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import kaymattern.notus.model.Mark;
import kaymattern.notus.model.Subject;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class DataAccessor {

    private final ObservableMap<Subject, Set<Mark>> data = FXCollections.observableHashMap();
    private final Database database;

    public DataAccessor() {
        this.database = new Database("notus");
    }

    public void loadData() {
        Object[][] all = database.executePreparedStatement("SELECT m.name, m.value, m.weight, m.date, s.id, s.name " +
                "FROM mark m " +
                "LEFT JOIN subject s ON m.subject_id = s.id");

        // magic
        Arrays.stream(all).parallel().collect(Collectors.groupingBy(row -> row[4])).forEach((subjectId, markData) -> {
            Subject subject = new Subject((int) subjectId, (String) markData.get(0)[5]);
            data.putIfAbsent(subject, new HashSet<>());
            markData.forEach(e -> data.get(subject).add(new Mark((String) e[0], LocalDate.of((Integer) e[1], 1,1),
                    (int) e[1], new BigDecimal((double) e[2]).floatValue())));
        });
    }

    private void addSubject(Subject subject) {
        database.executePreparedUpdate("INSERT INTO subject (name) VALUES (?)", subject.getName());
    }

    private void addMark(Mark mark) {
        database.executePreparedUpdate("INSERT INTO mark (name, value, weight, date, subject_id) VALUES (?,?,?,?,?)",
                mark.getName(), mark.getValue(), mark.getWeight(), mark.getDate());
    }

}
