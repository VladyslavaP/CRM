package ua.nure.crm.controller.util;

public final class RoutingConstants {

    public static final String ROOT = "/";
    public static final String UPDATE_NAME = "/updateName";
    public static final String UPDATE_DETAILS = "/updateDetails";
    public static final String COWORKERS = "/coworkers";

    public static final String LOGIN = "/login";
    public static final String LOGIN_FAILURE = "/login?error";
    public static final String LOGOUT= "/logout";
    public static final String EVENTS = "/users/{userId}/events";
    public static final String EVENT_ID = "/{eventId}";
    public static final String PHOTOS = "/photos";
    public static final String PHOTO_ID = "/{photoId}";
    public static final String NO_PHOTO = "/noPhoto";
}
