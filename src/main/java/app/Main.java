package app;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello, World!");

        String API_KEY = System.getenv("SENDGRID_API_KEY");
        System.out.println(API_KEY);

    }

}