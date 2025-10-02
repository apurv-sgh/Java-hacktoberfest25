public class ConfigurationManager {

    // 1. Private Static Instance Variable:
    // Volatile keyword ensures that multiple threads handle the instance 
    // variable correctly when it is being initialized.
    private static volatile ConfigurationManager instance;

    // 2. Private Constructor:
    // Prevents direct instantiation of the class from outside.
    private ConfigurationManager() {
        // Optional: Initialization logic can go here (e.g., loading config files)
        System.out.println("ConfigurationManager instance created.");
    }

    // 3. Public Static Access Method:
    // Provides the global access point to the single instance.
    public static ConfigurationManager getInstance() {
        // First Check: Avoids synchronization overhead once the instance is created.
        if (instance == null) {
            // Synchronized Block: Ensures only one thread can execute this block 
            // at a time, preventing multiple instances from being created.
            synchronized (ConfigurationManager.class) {
                // Second Check (Double-Checked Locking): 
                // Necessary inside the synchronized block to re-check 
                // if another thread created the instance while this thread was waiting.
                if (instance == null) {
                    instance = new ConfigurationManager();
                }
            }
        }
        return instance;
    }

    // Example Business Logic Method
    public String getAppSetting(String key) {
        if (key.equals("version")) {
            return "1.0.5 (DCL)";
        }
        return "Setting not found.";
    }

    // Example Main Method to Demonstrate Usage
    public static void main(String[] args) {
        // Both variables point to the exact same object in memory
        ConfigurationManager config1 = ConfigurationManager.getInstance();
        ConfigurationManager config2 = ConfigurationManager.getInstance();

        System.out.println("\n--- Singleton Demonstration ---");
        
        // Test if both references point to the same object
        if (config1 == config2) {
            System.out.println("Success! config1 and config2 reference the SAME object.");
        } else {
            System.out.println("Error! Multiple instances were created.");
        }
        
        System.out.println("Config 1 Version: " + config1.getAppSetting("version"));
        System.out.println("Config 2 Version: " + config2.getAppSetting("version"));
        
        // Print Hash Codes to confirm they are identical
        System.out.println("Hash Code of config1: " + config1.hashCode());
        System.out.println("Hash Code of config2: " + config2.hashCode());
    }
}
