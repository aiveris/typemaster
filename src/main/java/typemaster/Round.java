package typemaster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Round {
    private static List<String> ENGLISH_WORDS = Arrays.asList("account", "act", "addition", "adjustment", "advertisement", "agreement", "air", "amount", "amusement", "animal", "answer", "apparatus", "approval", "argument", "art", "attack", "attempt", "attention", "attraction", "authority", "back", "balance", "base", "behaviour", "belief", "birth", "bit", "bite", "blood", "blow", "body", "brass", "bread", "breath", "brother", "building", "burn", "burst", "business", "butter", "canvas");
    private static int TOTAL_WORDS_TO_TYPE = 5;

    enum State {
        READY,
        RUNNING,
        STOPPED
    }

    private List<String> words;
    private State currentState = State.READY;
    private int currentWordIndex = 0;
    private int currentCharacterIndex = 0;
    private long startTimeMillis;

    public Round() {
        words = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < TOTAL_WORDS_TO_TYPE; i++) {
            int wordIndex = random.nextInt(ENGLISH_WORDS.size());
            words.add(ENGLISH_WORDS.get(wordIndex));
        }
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public int getCurrentWordIndex() {
        return currentWordIndex;
    }

    public void setCurrentWordIndex(int currentWordIndex) {
        this.currentWordIndex = currentWordIndex;
    }

    public int getCurrentCharacterIndex() {
        return currentCharacterIndex;
    }

    public void setCurrentCharacterIndex(int currentCharacterIndex) {
        this.currentCharacterIndex = currentCharacterIndex;
    }

    public long getStartTimeMillis() {
        return startTimeMillis;
    }

    public void setStartTimeMillis(long startTimeMillis) {
        this.startTimeMillis = startTimeMillis;
    }

    public long getElapsedTimeInMillis() {
        return System.currentTimeMillis() - startTimeMillis;
    }

    public long getWordsPerMinute() {
        return currentWordIndex * 60_000 / getElapsedTimeInMillis();
    }

    public void start() {
        currentState = State.RUNNING;
        startTimeMillis = System.currentTimeMillis();
    }

    public String getCurrentWord() {
        return words.get(currentWordIndex);
    }

    public String getNextExpectedCharacter() {
        String currentWord = getCurrentWord();
        return currentWord.substring(
                currentCharacterIndex, currentCharacterIndex + 1);
    }
}