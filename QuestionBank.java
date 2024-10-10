import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionBank {
    private List<Question> questions;
    private List<Question> selectedQuestions;

    public QuestionBank() {
        questions = new ArrayList<>();
        // Add 20 questions about Pakistan
        questions.add(new Question("Which is the national bird of Pakistan?",
                new String[]{"Chukar", "Peacock", "Sparrow", "Parrot"}, 0));
        questions.add(new Question("What is the national flower of Pakistan?",
                new String[]{"Rose", "Jasmine", "Lotus", "Lily"}, 1));
        questions.add(new Question("Which mountain range is located in Pakistan?",
                new String[]{"Andes", "Himalayas", "Karakoram", "Alps"}, 2));
        questions.add(new Question("What is the capital city of Pakistan?",
                new String[]{"Karachi", "Lahore", "Islamabad", "Rawalpindi"}, 2));
        questions.add(new Question("Which river flows through the city of Lahore?",
                new String[]{"Indus", "Jhelum", "Chenab", "Ravi"}, 3));
        questions.add(new Question("Which Pakistani cricketer has the most centuries in Test cricket?",
                new String[]{"Imran Khan", "Wasim Akram", "Javed Miandad", "Younis Khan"}, 3));
        questions.add(new Question("Which is the oldest university in Pakistan?",
                new String[]{"Lahore University of Management Sciences (LUMS)", "Quaid-i-Azam University", "Aga Khan University", "University of the Punjab"}, 3));
        questions.add(new Question("Who is the founder of Pakistan?",
                new String[]{"Muhammad Ali Jinnah", "Allama Iqbal", "Liaquat Ali Khan", "Ayub Khan"}, 0));
        questions.add(new Question("Which Pakistani city is famous for its food street?",
                new String[]{"Islamabad", "Lahore", "Karachi", "Peshawar"}, 1));
        questions.add(new Question("Which Pakistani artist won an Oscar?",
                new String[]{"Ali Zafar", "Atif Aslam", "Rahat Fateh Ali Khan", "Sharmeen Obaid-Chinoy"}, 3));
        questions.add(new Question("What is the national language of Pakistan?",
                new String[]{"Sindhi", "Urdu", "Pashto", "Punjabi"}, 1));
        questions.add(new Question("Which is the largest mosque in Pakistan?",
                new String[]{"Faisal Mosque", "Badshahi Mosque", "Lal Masjid", "Shah Jahan Mosque"}, 0));
        questions.add(new Question("Which Pakistani scientist won the Nobel Prize in Physics?",
                new String[]{"Abdus Salam", "Pervez Hoodbhoy", "Atta-ur-Rahman", "A.Q. Khan"}, 0));
        questions.add(new Question("Which Pakistani city is known as the city of gardens?",
                new String[]{"Karachi", "Lahore", "Islamabad", "Rawalpindi"}, 1));
        questions.add(new Question("What is the national dress of Pakistan?",
                new String[]{"Shalwar Kameez", "Sari", "Kurta Pajama", "Lehenga"}, 0));
        questions.add(new Question("Which Pakistani cricketer has scored the most runs in ODIs?",
                new String[]{"Inzamam-ul-Haq", "Saeed Anwar", "Javed Miandad", "Shoaib Malik"}, 1));
        questions.add(new Question("Which Pakistani city is known as the city of lights?",
                new String[]{"Karachi", "Lahore", "Islamabad", "Rawalpindi"}, 0));
        questions.add(new Question("Who is known as the 'Father of the Nation' in Pakistan?",
                new String[]{"Allama Iqbal", "Muhammad Ali Jinnah", "Liaquat Ali Khan", "Benazir Bhutto"}, 1));
        questions.add(new Question("Which Pakistani cricket team won the ICC World Cup?",
                new String[]{"1992", "1996", "2000", "2008"}, 0));
        questions.add(new Question("Which Pakistani female politician became the first female Prime Minister of Pakistan?",
                new String[]{"Benazir Bhutto", "Asma Jahangir", "Fatima Jinnah", "Hina Rabbani Khar"}, 0));

        // Shuffle and select 10 random questions
        selectedQuestions = getTenRandomQuestions();
    }

    public Question getQuestion(int index) {
        return selectedQuestions.get(index);
    }

    public int getTotalQuestions() {
        return selectedQuestions.size();
    }

    // Method to select 10 random questions from the available questions
    public List<Question> getTenRandomQuestions() {
        List<Question> randomQuestions = new ArrayList<>(questions);
        Collections.shuffle(randomQuestions);
        return randomQuestions.subList(0, 10);
    }
}
