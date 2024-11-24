package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class BookShelfParser {

    public static void main(String[] args) {
        // JSON sample data
        String sampleJSON = """
                {
                    "BookShelf": [
                        {
                            "title": "Book One",
                            "publishedYear": 2001,
                            "numberOfPages": 300,
                            "authors": ["Author A", "Author B"]
                        },
                        {
                            "title": "Book Two",
                            "publishedYear": 2005,
                            "numberOfPages": 250,
                            "authors": ["Author C"]
                        },
                        {
                            "title": "Book Three",
                            "publishedYear": 2010,
                            "numberOfPages": 400,
                            "authors": ["Author D", "Author E"]
                        }
                    ]
                }
                """;

        // Step 1: Parse and print the JSON data
        System.out.println("Parsing JSON:");
        parseJSON(sampleJSON);

        // Step 2: Add a new book to JSON and print it again
        System.out.println("\nAdding new book to JSON:");
        String updatedJSON = addBookToJSON(sampleJSON);
        parseJSON(updatedJSON);
    }

    // Method to parse JSON and print the book details
    public static void parseJSON(String json) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        JsonArray bookShelf = jsonObject.getAsJsonArray("BookShelf");

        for (int i = 0; i < bookShelf.size(); i++) {
            JsonObject book = bookShelf.get(i).getAsJsonObject();
            String title = book.get("title").getAsString();
            int publishedYear = book.get("publishedYear").getAsInt();
            int numberOfPages = book.get("numberOfPages").getAsInt();
            JsonArray authors = book.getAsJsonArray("authors");

            System.out.println("Book:");
            System.out.println("  Title: " + title);
            System.out.println("  Published Year: " + publishedYear);
            System.out.println("  Number of Pages: " + numberOfPages);
            System.out.print("  Authors: ");
            for (int j = 0; j < authors.size(); j++) {
                System.out.print(authors.get(j).getAsString() + (j < authors.size() - 1 ? ", " : ""));
            }
            System.out.println("\n");
        }
    }

    // Method to add a new book to the JSON string
    public static String addBookToJSON(String json) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        JsonArray bookShelf = jsonObject.getAsJsonArray("BookShelf");

        // Create a new book object
        JsonObject newBook = new JsonObject();
        newBook.addProperty("title", "Book Four");
        newBook.addProperty("publishedYear", 2022);
        newBook.addProperty("numberOfPages", 350);
        JsonArray authors = new JsonArray();
        authors.add("Author F");
        authors.add("Author G");
        newBook.add("authors", authors);

        // Add the new book to the JSON array
        bookShelf.add(newBook);

        // Return the updated JSON string
        return gson.toJson(jsonObject);
    }
}
