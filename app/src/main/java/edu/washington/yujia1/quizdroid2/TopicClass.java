package edu.washington.yujia1.quizdroid2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class TopicClass {
    private String name;
    private String description;
    private List<QuestionClass> questions = new ArrayList<QuestionClass>();

    public TopicClass(String name, String description,Collection<QuestionClass> questions) {
        this.name = name;
        this.description = description;
        this.questions.addAll(questions);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<QuestionClass> getQuestions() {
        return questions;
    }



}
