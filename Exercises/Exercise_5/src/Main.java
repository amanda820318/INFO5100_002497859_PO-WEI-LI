import java.util.ArrayList;
import java.util.List;

// Singleton Pattern: ConfigManager
class ConfigManager {
    private static ConfigManager instance;
    private String appTheme;

    private ConfigManager() {
        this.appTheme = "Light"; 
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    public String getAppTheme() {
        return appTheme;
    }

    public void setAppTheme(String appTheme) {
        this.appTheme = appTheme;
    }
}

// Factory Pattern: Drink Factory
abstract class Drink {
    public abstract void serve();
}

class Coffee extends Drink {
    @Override
    public void serve() {
        System.out.println("Here's your hot coffee. Enjoy!");
    }
}

class Tea extends Drink {
    @Override
    public void serve() {
        System.out.println("Here's your hot tea. Enjoy!");
    }
}

class DrinkFactory {
    public static Drink makeDrink(String type) {
        if (type.equalsIgnoreCase("coffee")) {
            return new Coffee();
        } else if (type.equalsIgnoreCase("tea")) {
            return new Tea();
        } else {
            throw new IllegalArgumentException("We don't serve that drink!");
        }
    }
}

// Observer Pattern: Simple Notification System
interface Subscriber {
    void notify(String message);
}

class Newsletter {
    private List<Subscriber> subscribers = new ArrayList<>();

    public void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void unsubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    public void sendMessage(String message) {
        for (Subscriber subscriber : subscribers) {
            subscriber.notify(message);
        }
    }
}

class User implements Subscriber {
    private String name;

    public User(String name) {
        this.name = name;
    }

    @Override
    public void notify(String message) {
        System.out.println(name + " received notification: " + message);
    }
}

// Main Program
public class Main {
    public static void main(String[] args) {
        ConfigManager config = ConfigManager.getInstance();
        System.out.println("Current theme: " + config.getAppTheme());
        config.setAppTheme("Dark");
        System.out.println("Updated theme: " + config.getAppTheme());

        System.out.println("\n[Drink System]");
        Drink coffee = DrinkFactory.makeDrink("coffee");
        coffee.serve();
        Drink tea = DrinkFactory.makeDrink("tea");
        tea.serve();
        
        System.out.println("\n[Subscription System]");
        Newsletter newsletter = new Newsletter();
        User alice = new User("Alice");
        User bob = new User("Bob");

        newsletter.subscribe(alice);
        newsletter.subscribe(bob);

        newsletter.sendMessage("A new event has started!");
        newsletter.unsubscribe(alice);
        newsletter.sendMessage("The event ends tomorrow!");
    }
}
