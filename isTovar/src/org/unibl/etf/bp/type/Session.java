package org.unibl.etf.bp.type;

public class Session {
    private static int loggedUserId;

    public static void setLoggedUserId(int id) {
        loggedUserId = id;
    }

    public static int getLoggedUserId() {
        return loggedUserId;
    }
}

