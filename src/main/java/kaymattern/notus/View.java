package kaymattern.notus;

/**
 * Enum to hold all views of Notus.
 */
public enum View {
    SUBJECT_OVERVIEW("SubjectOverview", "Fächer Übersicht", false),
    MARK_OVERVIEW("MarkOverview", "Noten Übersicht", false),
    ADD_MARK("AddMark", "Note hinzufügen", true),
    EDIT_MARK("EditMark", "Note bearbeiten", true),
    ADD_SUBJECT("AddSubject", "Fach hinzufügen", true),
    EDIT_SUBJECT("EditSubject", "Fach bearbeiten", true);

    private String name;
    private String displayName;
    private boolean isDialog;

    View(String name, String displayName, boolean isDialog) {
        this.name = name;
        this.displayName = displayName;
        this.isDialog = isDialog;
    }

    public String getName() {
        return this.name;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public boolean isDialog() {
        return this.isDialog;
    }

}
