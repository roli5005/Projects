package a3.clinic;

public class UrlMapping {
    public static final String API_PATH = "/api";
    public static final String ENTITY = "/{id}";

    public static final String AUTH = API_PATH + "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";

    public static final String PATIENTS = API_PATH + "/patients";
    public static final String CONSULTATIONS = API_PATH + "/consultations";
    public static final String USERS = API_PATH + "/users";

    public static final String CONSULTATION_DESCRIPTION = "/descriptions";
    public static final String NOTIFICATION = CONSULTATION_DESCRIPTION + "notification";
}
