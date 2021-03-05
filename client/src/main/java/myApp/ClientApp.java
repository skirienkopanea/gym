package myApp;

import myApp.views.HomePage;

/* Main entry for the Client side application*/
public class ClientApp {
    public static void main(String[] args) {
        /* Executes the main method of the public class HomePage, which happens to be
        located in ./views, and it will basically load the home page view */
        HomePage.main(new String[0]);
    }
}