package springboot.dependencyinjection.springbootsimpleproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

@Controller
public class LinguController {
    private static final int UNDEFINED = -1;
    private static final int ADD_ENTRY = 0;
    private static final int TEST = 1;
    private static final int CLOSE_APP = 2;

    @Autowired
    private EntryRepository entryRepository = new EntryRepository();
    private FileService fileService = new FileService();
    private Scanner scanner = new Scanner(System.in);

    void mainLoop() {
        System.out.println("Welcome to LinguApp");
        int option = UNDEFINED;
        while (option != CLOSE_APP) {
            printMenu();
            option = chooseOption();
            executeOption(option);
        }
    }

    private void executeOption(int option) {
        switch (option) {
            case ADD_ENTRY:

                addEntry();
                break;

            case TEST:

                test();
                break;
            case CLOSE_APP:

                close();
                break;
            default:
                System.out.println("Undefined option");
        }
    }

    private void test() {
        if (entryRepository.isEmpty()) {
            System.out.println("Add at least one word");
            return;
        }
        final int testSize = entryRepository.size() > 10 ? 10 : entryRepository.size();
        Set<Entry> randomEntries = entryRepository.getRandomEntries(testSize);
        int score = 0;
        for (Entry entry : randomEntries) {
            System.out.printf("Add translation for :\"%s\"\n", entry.getOriginalWord());
            String translation = scanner.nextLine();
            if (entry.getTranslatedWord().equalsIgnoreCase(translation)) {
                System.out.println("Correct answer.");
                score++;
            } else {
                System.out.println("Answer incorrect - " + entry.getTranslatedWord());
            }

        }
        System.out.printf("Your score: %d/%d\n", score, testSize);
    }

    private void addEntry() {
        System.out.println("Please give me a word.");
        String original = scanner.nextLine();
        System.out.println("Please give translation");
        String translation = scanner.nextLine();
        Entry entry = new Entry(original, translation);
        entryRepository.add(entry);

    }

    private void close() {
        try {
            fileService.saveEntries(entryRepository.getAll());
            System.out.println("Changes saved");
        } catch (IOException e) {
            System.out.println("Something went wrong :( ");
        }
        System.out.println("Bye Bye!");
    }

    private void printMenu() {
        System.out.println("Option:");
        System.out.println("0 - Add word ");
        System.out.println("1 - Test ");
        System.out.println("2 - End ");
    }

    private int chooseOption() {
        int option;
        try {
            option = scanner.nextInt();
        } catch (InputMismatchException e) {
            option = UNDEFINED;
        } finally {
            scanner.nextLine();
        }
        if (option > UNDEFINED && option <= CLOSE_APP)
            return option;
        else
            return UNDEFINED;
    }
}

