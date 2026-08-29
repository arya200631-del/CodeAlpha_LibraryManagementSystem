import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AIChatbot {
    private static final Map<String, String> faqDatabase = new HashMap<>();
    private static final List<String> positiveKeywords = Arrays.asList("good", "great", "awesome", "excellent", "happy", "love");
    private static final List<String> negativeKeywords = Arrays.asList("bad", "sad", "terrible", "angry", "hate", "unhappy");

    public static void main(String[] args) {
        initializeKnowledgeBase();
        Scanner scanner = new Scanner(System.in);

        System.out.println("=================================================");
        System.out.println("  🤖 Welcome to AlphaBot - Java AI Assistant     ");
        System.out.println("  (Type 'exit', 'quit', or 'bye' to end the chat)");
        System.out.println("=================================================\n");

        System.out.println("AlphaBot: Hello! I am your AI assistant. How can I assist you today?");

        while (true) {
            System.out.print("\nYou: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("quit") || input.equalsIgnoreCase("bye")) {
                System.out.println("AlphaBot: Goodbye! Have a wonderful day ahead!");
                break;
            }

            if (input.isEmpty()) {
                System.out.println("AlphaBot: Please type something so I can help you.");
                continue;
            }

            String response = generateResponse(input);
            System.out.println("AlphaBot: " + response);
        }

        scanner.close();
    }

    private static void initializeKnowledgeBase() {
        faqDatabase.put("what is your name", "I am AlphaBot, an AI chatbot built with Java.");
        faqDatabase.put("who created you", "I was created as an internship project for CodeAlpha.");
        faqDatabase.put("what is java", "Java is a high-level, class-based, object-oriented programming language.");
        faqDatabase.put("what is oop", "OOP (Object-Oriented Programming) organizes software design around objects rather than functions.");
        faqDatabase.put("what is ai", "Artificial Intelligence is the simulation of human intelligence in machines.");
        faqDatabase.put("help", "You can ask me about Java, OOP concepts, current time, or general questions!");
    }

    private static String generateResponse(String input) {
        String normalized = input.toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "");

        // 1. Direct Knowledge Base / FAQ match
        for (Map.Entry<String, String> entry : faqDatabase.entrySet()) {
            if (normalized.contains(entry.getKey())) {
                return entry.getValue();
            }
        }

        // 2. Dynamic Commands
        if (normalized.contains("time") || normalized.contains("date")) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return "Current date and time is: " + dtf.format(LocalDateTime.now());
        }

        // 3. Greetings
        if (normalized.startsWith("hi") || normalized.startsWith("hello") || normalized.startsWith("hey")) {
            return "Greetings! How can I make your day more productive?";
        }

        // 4. Sentiment Analysis
        String sentiment = analyzeSentiment(normalized);
        if (!sentiment.isEmpty()) {
            return sentiment;
        }

        // 5. Fallback Response
        return "I'm still learning! Could you rephrase your question or ask about Java, OOP, or system features?";
    }

    private static String analyzeSentiment(String input) {
        String[] words = input.split("\\s+");
        int score = 0;

        for (String word : words) {
            if (positiveKeywords.contains(word)) score++;
            if (negativeKeywords.contains(word)) score--;
        }

        if (score > 0) return "I'm glad to hear that! Positivity makes everything better.";
        if (score < 0) return "I'm sorry you're feeling down. Let me know if I can help you with anything.";
        return "";
    }
}
