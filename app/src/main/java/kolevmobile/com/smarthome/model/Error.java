package kolevmobile.com.smarthome.model;

public enum Error {

    COMUNICATING_ERROR,
    READING_ERROR,
    SCHEMA_ERROR;

    static public Error fromString(String error) {
        switch (error) {
            case "COMUNICATING_ERROR":
                return COMUNICATING_ERROR;
            case "READING_ERROR":
                return READING_ERROR;
            case "SCHEMA_ERROR":
                return SCHEMA_ERROR;
            default:
                return COMUNICATING_ERROR;
        }

    }
}
